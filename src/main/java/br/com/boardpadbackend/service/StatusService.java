package br.com.boardpadbackend.service;

import br.com.boardpadbackend.dto.StatusDto;

import java.util.List;

public interface StatusService {
    List<StatusDto> listAllStatus();

    StatusDto createNewStatus(String statusName);

    void deleteStatus(Long idStatus);

    void updateStatusName(Long idStatus, String newStatusName) ;
}
