package br.com.boardpadbackend.service.impl;

import br.com.boardpadbackend.dto.StatusDto;
import br.com.boardpadbackend.entity.StatusEntity;
import br.com.boardpadbackend.repositories.StatusRepository;
import br.com.boardpadbackend.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatusServiceImpl implements StatusService {
    private StatusRepository statusRepository;

    @Autowired
    public StatusServiceImpl(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
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
        StatusEntity newStatus = statusRepository.save(StatusEntity.builder().nameStatus(statusName).build());
        return StatusDto.builder()
                .id(newStatus.getIdStatus())
                .name(newStatus.getNameStatus())
                .build();
    }

    @Override
    public void deleteStatus(Long idStatus) {
        statusRepository.deleteById(idStatus);
    }
}
