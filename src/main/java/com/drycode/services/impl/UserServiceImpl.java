package com.drycode.services.impl;

import com.drycode.models.Status;
import com.drycode.models.User;
import com.drycode.repositories.UserRepository;
import com.drycode.services.GenerateIDService;
import com.drycode.services.UserService;
import com.drycode.utils.ConstantString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    private static final String ENTITY = "User";
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final GenerateIDService generateSequence;


    public UserServiceImpl(UserRepository userRepository, GenerateIDService generateSequence) {
        this.userRepository = userRepository;
        this.generateSequence = generateSequence;
    }

    @Override
    public User findByUsername(String username) {
        LOGGER.info(ConstantString.FIND_BY_LOG.replaceAll("#ENTITY", ENTITY), username);
        return this.userRepository.findByUsername(username);
    }

    @Override
    public User save(User user) throws Exception {
        LOGGER.info(ConstantString.SAVE_LOG.replaceAll("#ENTITY", ENTITY), user);
        this.validaUsername(user);
        return this.userRepository.save(user);
    }

    @Override
    public void delete(User user) throws Exception {
        LOGGER.info(ConstantString.DELETE_LOG.replaceAll("#ENTITY", ENTITY), user);
        this.userRepository.delete(user);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        LOGGER.info(ConstantString.FIND_BY_LOG.replaceAll("#ENTITY", ENTITY), pageable);
        return this.userRepository.findAll(pageable);
    }

    @Override
    public Page<User> findAll(Pageable pageable, String position) {
        LOGGER.info(ConstantString.SAVE_LOG.replaceAll("#ENTITY", ENTITY), pageable);
        return this.userRepository.findByPosition(position, pageable);
    }

    @Override
    public User findById(Long id) {
        LOGGER.info(ConstantString.FIND_BY_LOG.replaceAll("#ENTITY", ENTITY), id);
        return this.userRepository.findById(id).orElse(null);
    }
    private void validaUsername(User user) throws Exception {
        List<User> statusExist = this.userRepository.findAllByUsername(user.getUsername());
        if (StringUtils.isEmpty(user.getId())) {
            user.setId(generateSequence.generateSequence(Status.SEQUENCE_NAME));
            if (statusExist.size() > 0) {
                throw new Exception(ConstantString.NAMES_EXIST.replaceAll("#ENTITY", ENTITY)
                        .replace("#NAMEEXISTS", "username '"+ user.getUsername()+"'"));
            }
        } else {
            boolean userExist = statusExist.stream().filter(s -> Objects.equals(s.getId(), user.getId())).count() > 0;
            if (statusExist.size() > 1 && userExist) {
                throw new Exception(ConstantString.NAMES_EXIST.replaceAll("#ENTITY", ENTITY)
                        .replace("#NAMEEXISTS", user.getUsername()));
            }
        }
    }
}
