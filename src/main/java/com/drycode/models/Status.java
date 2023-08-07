package com.drycode.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Setter
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
@Document(collection = "status")
public class Status {
    public static final String SEQUENCE_NAME = "status";
    @Id
    private Long id;
    private String name;
    private String description;
}
