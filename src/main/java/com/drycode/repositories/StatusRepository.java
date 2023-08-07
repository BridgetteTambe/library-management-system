package com.drycode.repositories;

import com.drycode.models.Status;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StatusRepository extends MongoRepository<Status, Long> {
    List<Status> findByName(String name);
}
