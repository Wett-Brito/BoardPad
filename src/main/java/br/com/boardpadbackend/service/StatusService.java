package br.com.boardpadbackend.service;

import br.com.boardpadbackend.dto.StatusDto;

import java.math.BigInteger;
import java.util.List;

public interface StatusService {

    List<StatusDto> listAllStatus(String boardCode);

    StatusDto createNewStatus(String boardCode, String statusName);

    void deleteStatus(String boardCode, Long idStatus) ;

    void updateStatusName(Long idStatus, String newStatusName, String boardCode);
}
