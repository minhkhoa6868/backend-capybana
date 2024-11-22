package com.controller;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import com.repository.FileRepository;
import java.io.File;
import com.model.FileEntity;

import java.util.ArrayList;
import java.util.List;



@RestController
@RequestMapping("api/upload")
public class FileController {
    @Value("${file.upload-dir:./uploads}")
    private String uploadDir;

    private final FileRepository fileRepository;
    @Autowired
    public FileController(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @PostMapping
    public ResponseEntity<List<String>> uploadFile(@RequestParam("file") List<MultipartFile> files) {
    try {
        List<String> responses = new ArrayList<>();
        // Tạo thư mục nếu chưa tồn tại
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            boolean dirCreated = directory.mkdirs();
            if (!dirCreated) {
                return ResponseEntity.status(500).body(List.of("Could not create directory for uploads."));
            }
        }

        // Lưu file vào thư mục
        for(MultipartFile file : files) {
        String filePath = uploadDir + "/" + file.getOriginalFilename();
        file.transferTo(new File(filePath));
        // Lưu thông tin file vào database
        FileEntity fileEntity = new FileEntity(
            file.getOriginalFilename(),
            filePath,
            file.getSize()
        );
        
        fileRepository.save(fileEntity);
        responses.add("File uploaded successfully");
        }
        return ResponseEntity.ok(responses);
    } catch (IOException e) {
        return ResponseEntity.status(500).body(List.of("Error uploading file: " + e.getMessage()));
    }
}

@GetMapping
public ResponseEntity<List<FileEntity>> getAllFiles() {
    try{
        //get all files from dtb
        List<FileEntity> files  = fileRepository.findAll();

        if (files.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(files);
    } catch (Exception e){
        return ResponseEntity.status(500).body(null);
    }
}

@DeleteMapping
public ResponseEntity<String> deleteFile(@RequestBody List<Long> fileIds) {
    try {
        if (fileIds == null || fileIds.isEmpty()) {
            return ResponseEntity.badRequest().body("File IDs cannot be null or empty.");
        }

        // Lấy danh sách file tồn tại trong database
        List<FileEntity> files = fileRepository.findAllById(fileIds);
        List<Long> existingFileIds = files.stream().map(FileEntity::getId).toList();

        // Tìm các file IDs không tồn tại trong database
        List<Long> notFoundIds = new ArrayList<>();
        for (Long fileId : fileIds) {
            if (!existingFileIds.contains(fileId)) {
                notFoundIds.add(fileId);
            }
        }

        // Nếu có ID không tồn tại, trả về lỗi
        if (!notFoundIds.isEmpty()) {
            return ResponseEntity.badRequest().body("The following file IDs were not found: " + notFoundIds);
        }

        // Xóa file vật lý và thông tin trong database
        for (FileEntity file : files) {
            File physicalFile = new File(file.getFilePath());
            if (physicalFile.exists() && !physicalFile.delete()) {
                return ResponseEntity.status(500).body("Failed to delete file: " + file.getFilePath());
            }
        }
        fileRepository.deleteAllById(fileIds);

        return ResponseEntity.ok("Deleted files successfully: " + existingFileIds);
    } catch (Exception e) {
        return ResponseEntity.status(500).body("Error deleting files: " + e.getMessage());
    }
}

}
