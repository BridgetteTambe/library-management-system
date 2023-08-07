package com.drycode.services.impl;

import com.drycode.models.Status;
import com.drycode.repositories.StatusRepository;
import com.drycode.services.GenerateIDService;
import com.drycode.services.StatusService;
import com.drycode.utils.ConstantString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class StatusServiceImpl implements StatusService {

    private static final String ENTITY = "Status";
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final StatusRepository statusRepository;
    private final GenerateIDService generateSequence;

    public StatusServiceImpl(StatusRepository statusRepository, GenerateIDService generateSequence) {
        this.statusRepository = statusRepository;
        this.generateSequence = generateSequence;
    }

    @Override
    public List<Status> findByName(String name) {
        LOGGER.info(ConstantString.GET_LOG.replaceAll("#ENTITY", ENTITY), name);
        return this.statusRepository.findByName(name);
    }

    @Override
    public Status save(Status status) throws Exception {
        LOGGER.info(ConstantString.SAVE_LOG.replaceAll("#ENTITY", ENTITY), status);
        if (StringUtils.isEmpty(status.getName())) {
            throw new Exception(ConstantString.NAME_NULL.replaceAll("#ENTITY", ENTITY));
        }
        this.validaName(status);
        return this.statusRepository.save(status);
    }

    @Override
    public Page<Status> findAll(Pageable pageable) {
        LOGGER.info(ConstantString.GET_LOG.replaceAll("#ENTITY", ENTITY), pageable);
        return this.statusRepository.findAll(pageable);
    }

    @Override
    public void delete(Status status) {
        LOGGER.info(ConstantString.DELETE_LOG.replaceAll("#ENTITY", ENTITY), status);
        this.statusRepository.delete(status);
    }

    @Override
    public Status findById(Long id) {
        LOGGER.info(ConstantString.GET_LOG.replaceAll("#ENTITY", ENTITY), id);
        return this.statusRepository.findById(id).orElse(null);
    }

    private void validaName(Status status) throws Exception {
        List<Status> statusExist = this.findByName(status.getName());
        if (StringUtils.isEmpty(status.getId())) {
            status.setId(generateSequence.generateSequence(Status.SEQUENCE_NAME));
            if (statusExist.size() > 0) {
                throw new Exception(ConstantString.NAMES_EXIST.replaceAll("#ENTITY", ENTITY)
                        .replace("#NAMEEXISTS", status.getName()));
            }
        } else {
            if (statusExist.size() >= 2) {
                throw new Exception(ConstantString.NAMES_EXIST.replaceAll("#ENTITY", ENTITY)
                        .replace("#NAMEEXISTS", status.getName()));
            }
        }
    }
}
