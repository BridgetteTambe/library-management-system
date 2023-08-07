package com.drycode.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Setter
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
@Document(collection = "books")
public class Book {
    public static final String SEQUENCE_NAME = "book";
    @Id
    @Field(name = "id")
    private Long id;
    private String name;
    private String author;
    private String description;
    private Integer quantity;
}
