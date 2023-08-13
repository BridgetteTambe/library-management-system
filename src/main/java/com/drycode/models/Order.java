package com.drycode.models;

import com.drycode.models.dto.BookDTO;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.TreeSet;

@Data
@Setter
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
@Document(collection = "orders")
public class Order {
    @Id
    @Field(name = "id")
    private Long id;
    @Field(name = "OrderPlacedDate")
    private LocalDateTime orderPlacedDate;
    @Field(name = "IssuedDate")
    private LocalDateTime issuedDate;
    @Field(name = "ReturnDate")
    private LocalDateTime returnDate;
    @Field(name = "Fine")
    private Double fine;
    @Field(name = "Books")
    private Set<BookDTO> books = new TreeSet<>();
    @Field(name = "Status")
    private Status status;
    @Field(name = "User")
    private User user;
}
