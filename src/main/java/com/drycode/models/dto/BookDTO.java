package com.drycode.models.dto;

import lombok.Data;

@Data
public class BookDTO {
    private String name;
    private String author;
    private String description;
    private Integer quantity;
    private Long bookId;
}
