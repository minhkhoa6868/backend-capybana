package com.service;

import com.model.FileEntity;
import com.repository.FileRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class FileService {

    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public FileEntity saveFile(String fileName, byte[] data, String filePath) {
        FileEntity fileEntity = new FileEntity(fileName, data, LocalDateTime.now(), filePath);
        return fileRepository.save(fileEntity);
    }
    

    public List<FileEntity> getAllFiles() {
        return fileRepository.findAll();
    }

    public Optional<FileEntity> getFileById(Long id) {
        return fileRepository.findById(id);
    }

    public void deleteFile(Long id) {
        fileRepository.deleteById(id);
    }
}
