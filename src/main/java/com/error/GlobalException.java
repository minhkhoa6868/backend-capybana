package com.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.model.RestResponse;
import com.mysql.cj.x.protobuf.MysqlxDatatypes.Object;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(value = ResInvalidException.class)
    public ResponseEntity<RestResponse<Object>> handleException(ResInvalidException invalidExcep) {
        RestResponse<Object> res = new RestResponse<Object>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setError(invalidExcep.getMessage());
        res.setMessage("API fail");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }
}
