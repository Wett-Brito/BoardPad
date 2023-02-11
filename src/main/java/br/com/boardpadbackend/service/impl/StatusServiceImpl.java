package br.com.boardpadbackend.service.impl;

import br.com.boardpadbackend.dto.StatusDto;
import br.com.boardpadbackend.entity.BoardEntity;
import br.com.boardpadbackend.entity.StatusEntity;
import br.com.boardpadbackend.exceptions.NotFoundException;
import br.com.boardpadbackend.repositories.StatusRepository;
import br.com.boardpadbackend.repositories.TaskRepository;
import br.com.boardpadbackend.service.BoardService;
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
    private BoardService boardService;

    @Autowired
    public StatusServiceImpl(StatusRepository statusRepository,
                             TaskRepository taskRepository,
                             BoardService boardService) {
        this.statusRepository = statusRepository;
        this.taskRepository = taskRepository;
        this.boardService = boardService;
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
        BoardEntity foundBoard = boardService.findBoardByBoardCode(boardCode);

        var newStatus = statusRepository.save(StatusEntity.builder()
                .nameStatus(statusName)
                .board(foundBoard)
                .build());
        return StatusDto.builder()
                .id(newStatus.getIdStatus())
                .name(newStatus.getNameStatus())
                .build();

    }

    @Transactional
    @Override
    public void deleteStatus(String boardCode, Long idStatus) {
        taskRepository.setAllStatusNullOfTasksThatBelongsToStatusById(idStatus);
        var statusFound = statusRepository.getStatusByIdAndBoardCode(idStatus, boardCode);
        statusFound.orElseThrow(()-> new NotFoundException("The status ["
                + idStatus
                + "] wasn't found on board ["
                + boardCode
                + "]"));
        statusFound.ifPresent(entity->statusRepository.delete(entity));
    }

    @Transactional
    @Override
    public void updateStatusName(Long idStatus, String newStatusName, String boardCode) {
        StatusEntity newStatusEntity = getStatusEntityByBoardCodeAndStatusId(boardCode, idStatus);
        newStatusEntity.setNameStatus(newStatusName);
    }
    public StatusEntity getStatusEntityByBoardCodeAndStatusId (String boardCode, Long statusId) {
        Optional<StatusEntity> statusEntity = statusRepository.getStatusByIdAndBoardCode(statusId, boardCode);
        if(statusEntity.isEmpty()) throw new NotFoundException("The status ["
                + statusId
                + "] wasn't found on board ["
                + boardCode
                + "]");
        return statusEntity.get();
    }
}
