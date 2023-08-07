package com.drycode.repositories;

import com.drycode.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, Long> {

    User findByUsername(String username);

    List<User> findAllByUsername(String username);

    Page<User> findByPosition(String position, Pageable pageable);
}
