package br.com.boardpadbackend.service.impl;

import br.com.boardpadbackend.dto.StatusDto;
import br.com.boardpadbackend.entity.BoardEntity;
import br.com.boardpadbackend.entity.StatusEntity;
import br.com.boardpadbackend.exceptions.BadRequestException;
import br.com.boardpadbackend.exceptions.NotFoundException;
import br.com.boardpadbackend.repositories.BoardRepository;
import br.com.boardpadbackend.repositories.StatusRepository;
import br.com.boardpadbackend.repositories.TaskRepository;
import br.com.boardpadbackend.service.StatusService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
public class StatusServiceImpl implements StatusService {
    private StatusRepository statusRepository;
    private TaskRepository taskRepository;
    private BoardRepository boardRepository;

    @Autowired
    public StatusServiceImpl(StatusRepository statusRepository, TaskRepository taskRepository, BoardRepository boardRepository) {
        this.statusRepository = statusRepository;
        this.taskRepository = taskRepository;
        this.boardRepository = boardRepository;
    }

    @Override
    public List<StatusDto> listAllStatus(String boardCode) {
        var statusList = statusRepository.listAllStatusFromBoardCode(boardCode);
        if (statusList.isEmpty()) throw new NotFoundException("No status found to board [" + boardCode + "].");

        return statusList.stream()
                .map(item -> StatusDto.builder()
                        .id(item.getIdStatus())
                        .name(item.getNameStatus())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public StatusDto createNewStatus(String boardCode, String statusName) {
        Optional<BoardEntity> foundBoard = boardRepository.findByCodeBoard(boardCode);
        if(foundBoard.isEmpty()) throw new BadRequestException("The board wasn't created. Please create a board before try create a category");

        var newStatus = statusRepository.save(StatusEntity.builder()
                .nameStatus(statusName)
                .board(foundBoard.get())
                .build());
        return StatusDto.builder()
                .id(newStatus.getIdStatus())
                .name(newStatus.getNameStatus())
                .build();

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
