package com.drycode.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Data
@Setter
@Getter
@ToString
@Document(collection = "database_sequences")
public class DatabaseSequence {
    @Id
    private String id;
    private long seq;

}
