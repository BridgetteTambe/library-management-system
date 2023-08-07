package com.drycode.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Getter
@Setter
@ToString
@EqualsAndHashCode
public abstract class Person {

    @Field(name = "idNumber")
    private String idNumber;

    @Field(name = "firstName")
    private String firstName;

    @Field(name = "lastName")
    private String lastName;

    @Field(name = "phoneNumber")
    private String phoneNumber;

    @Field(name = "email")
    private String email;
}
