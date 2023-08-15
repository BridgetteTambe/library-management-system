package com.drycode.web;

import com.drycode.models.User;
import com.drycode.services.GenerateIDService;
import com.drycode.services.UserService;
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
public class UserResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);
    private static final String ENTITY = "User";
    private final UserService userService;
    private final GenerateIDService generateSequence;

    public UserResource(UserService userService, GenerateIDService generateSequence) {
        this.userService = userService;
        this.generateSequence = generateSequence;
    }

    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody User user) throws Exception {
        LOGGER.info(ConstantString.REST_SAVE_LOG.replaceAll("#ENTITY", ENTITY), user);
        if (user.getId() != null) {
            throw new Exception(ConstantString.ID_EXISTS.replaceAll("#ID", String.valueOf(user.getId()))
                    .replaceAll("#ENTITY", ENTITY));
        }
        User save = this.userService.save(user);
        return ResponseEntity.ok(save);
    }

    @PutMapping("/user")
    public ResponseEntity<User> updateUser(@RequestBody User user) throws Exception {
        LOGGER.info(ConstantString.REST_UPDATE_LOG.replaceAll("#ENTITY", ENTITY), user);
        if (user.getId() == null) {
            throw new Exception(ConstantString.ID_NULL.replaceAll("#ID", "")
                    .replaceAll("#ENTITY", ENTITY));
        }
        User save = this.userService.save(user);
        return ResponseEntity.ok(save);
    }

    @GetMapping("/user")
    public ResponseEntity<Page<User>> findAllUser(Pageable pageable) {
        LOGGER.info(ConstantString.REST_GET_LOG.replaceAll("#ENTITY", ENTITY), pageable);
        return ResponseEntity.ok(this.userService.findAll(pageable));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        LOGGER.info(ConstantString.REST_GET_LOG.replaceAll("#ENTITY", ENTITY), id);
        return ResponseEntity.ok(this.userService.findById(id));
    }

    @DeleteMapping("/user")
    public ResponseEntity deleteUser(@RequestBody User user) throws Exception {
        LOGGER.info(ConstantString.REST_DELETE_LOG.replaceAll("#ENTITY", ENTITY), user);
        if(user.getId() == null){
            throw new Exception(ConstantString.USER_NULL.replaceAll("#ID", "")
                    .replaceAll("#ENTITY", ENTITY));
        }
        this.userService.delete(user);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

}

