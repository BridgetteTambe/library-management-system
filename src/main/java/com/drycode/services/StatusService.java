package com.drycode.services;

import com.drycode.models.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StatusService {

    List<Status>  findByName(String name);
    Status save(Status status) throws Exception;

    Page<Status> findAll(Pageable pageable);
    Status findById(Long id);

    void delete(Status status);
}
