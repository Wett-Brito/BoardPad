package br.com.boardpadbackend.service;

import br.com.boardpadbackend.dto.StatusDto;
import br.com.boardpadbackend.dto.SynopsisStatus;
import br.com.boardpadbackend.entity.StatusEntity;

import java.math.BigInteger;
import java.util.List;

public interface StatusService {

    List<StatusDto> listAllStatus(String boardCode);

    StatusDto createNewStatus(String boardCode, String statusName);

    void deleteStatus(String boardCode, Long idStatus) ;

    void updateStatusName(Long idStatus, String newStatusName, String boardCode);

    StatusEntity getStatusEntityByBoardCodeAndStatusId (String boardCode, Long statusId);

    SynopsisStatus getStatusById (Long statusId);
}
