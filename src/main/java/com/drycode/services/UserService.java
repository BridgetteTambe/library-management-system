package com.drycode.services;

import com.drycode.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    User findByUsername(String username);

    User save(User user) throws Exception;

    void delete(User user) throws Exception;

    Page<User> findAll(Pageable pageable);

    User findById(Long id);

    Page<User> findAll(Pageable pageable, String position);
}
