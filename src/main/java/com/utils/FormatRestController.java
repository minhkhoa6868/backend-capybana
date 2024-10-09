package com.utils;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.model.RestResponse;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;

@ControllerAdvice
public class FormatRestController implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    @Nullable
    public Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType, MediaType selectedContentType,
            Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();
        int statusCode = servletResponse.getStatus();
        RestResponse<Object> result = new RestResponse<Object>();
        result.setStatusCode(statusCode);
        if (statusCode >= 400) {
            return body;
        } else {
            result.setData(body);
            result.setMessage("Call Success");
        }
        return result;
    }

}