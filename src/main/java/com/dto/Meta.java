package com.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Meta {
    private int currentPage;
    private int pageSize;
    private int totalPages;
    private long totalElements;
}
