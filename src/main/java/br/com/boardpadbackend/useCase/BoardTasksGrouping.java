package br.com.boardpadbackend.useCase;

import br.com.boardpadbackend.dto.BoardDto;
import br.com.boardpadbackend.dto.SynopsisStatus;

import java.util.List;

public interface BoardTasksGrouping {
    BoardDto getBoard(BoardDto boardDto);
    List<SynopsisStatus> findAndGroupTasksIntoStatus(BoardDto boardDto);
}
