package com.drycode.web;

import com.drycode.models.Status;
import com.drycode.services.StatusService;
import com.drycode.utils.ConstantString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class StatusResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatusResource.class);
    private static final String ENTITY = "Status";
    private final StatusService statusService;

    public StatusResource(StatusService statusService) {
        this.statusService = statusService;
    }

    @PostMapping("/status")
    public ResponseEntity<Status> createStatus(@RequestBody Status status) throws Exception {
        LOGGER.info(ConstantString.REST_SAVE_LOG.replaceAll("#ENTITY", ENTITY), status);
        if (status.getId() != null) {
            throw new Exception(ConstantString.ID_EXISTS.replaceAll("#ID", String.valueOf(status.getId()))
                    .replaceAll("#ENTITY", ENTITY));
        }
        Status save = this.statusService.save(status);
        return ResponseEntity.status(200).body(save);
    }


    @PutMapping("/status")
    public ResponseEntity<Status> updateStatus(@RequestBody Status status) throws Exception {
        LOGGER.info(ConstantString.REST_UPDATE_LOG.replaceAll("#ENTITY", ENTITY), status);
        if (status.getId() == null) {
            throw new Exception(ConstantString.ID_NULL.replaceAll("#ID", "")
                    .replaceAll("#ENTITY", ENTITY));
        }
        Status save = this.statusService.save(status);
        return ResponseEntity.status(200).body(save);
    }

    @GetMapping("/status")
    public ResponseEntity<Page<Status>> findAllStatus(Pageable pageable) {
        LOGGER.info(ConstantString.REST_GET_LOG.replaceAll("#ENTITY", ENTITY), pageable);
        return ResponseEntity.ok(this.statusService.findAll(pageable));
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<Status> findById(@PathVariable Long id) {
        LOGGER.info(ConstantString.REST_GET_LOG.replaceAll("#ENTITY", ENTITY), id);
        return ResponseEntity.ok(this.statusService.findById(id));
    }

    @DeleteMapping("/status")
    public ResponseEntity deleteStatus(@RequestBody Status status) {
        LOGGER.info(ConstantString.REST_DELETE_LOG.replaceAll("#ENTITY", ENTITY), status);
        this.statusService.delete(status);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
