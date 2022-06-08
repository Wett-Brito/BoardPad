package br.com.boardpadbackend.service.impl;

import br.com.boardpadbackend.dto.StatusDto;
import br.com.boardpadbackend.entity.StatusEntity;
import br.com.boardpadbackend.repositories.StatusRepository;
import br.com.boardpadbackend.repositories.TaskRepository;
import br.com.boardpadbackend.service.StatusService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
public class StatusServiceImpl implements StatusService {
    private StatusRepository statusRepository;
    private TaskRepository taskRepository;

    @Autowired
    public StatusServiceImpl(StatusRepository statusRepository,
                             TaskRepository taskRepository) {
        this.statusRepository = statusRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public List<StatusDto> listAllStatus() {
        return statusRepository.findAll().stream()
                .map(item -> StatusDto.builder()
                        .id(item.getIdStatus())
                        .name(item.getNameStatus())
                        .build())
                .collect(Collectors.toList());
    }
    @Override
    public StatusDto createNewStatus(String statusName) {
        try {
            StatusEntity newStatus = statusRepository.save(StatusEntity.builder().nameStatus(statusName).build());
            return StatusDto.builder()
                    .id(newStatus.getIdStatus())
                    .name(newStatus.getNameStatus())
                    .build();
        }catch(Exception ex){
            log.error(ex.getMessage());
            throw new RuntimeException("Erro ao criar task");
        }
    }

    @Transactional
    @Override
    public void deleteStatus(Long idStatus) {
        taskRepository.deleteAllByStatusEntityIdStatus(idStatus);
        statusRepository.deleteById(idStatus);
    }

    @Transactional
    @Override
    public void updateStatusName(Long idStatus, String newStatusName) {
        Optional<StatusEntity> newStatusEntity = statusRepository.findById(idStatus);
        newStatusEntity.ifPresent(statusEntity -> statusEntity.setNameStatus(newStatusName));
    }
}
