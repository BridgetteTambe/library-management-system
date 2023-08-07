package com.drycode.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
@Setter
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)

@Document(collection = "users")
public class User extends Person {
    public static final String SEQUENCE_NAME = "user";
    @Id
    @Field(name = "id")
    private Long id;

    @Field(name = "Username")
    private String username;

    @Field(name = "Password")
    private String password;

    @Column(name = "LastLoginDate")
    private LocalDateTime lastLoginDate;

    @Column(name = "LockTime")
    private LocalDateTime lockTime;

    @Column(name = "FailedAttempt")
    private Short failedAttempt;

    @Column(name = "UserLocked")
    private Boolean userLocked;

    @Column(name = "Enabled")
    private Boolean enabled;

    @Field(name = "LockedReason")
    private String lockedReason;

    @Field(name = "Position")
    private String position;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
