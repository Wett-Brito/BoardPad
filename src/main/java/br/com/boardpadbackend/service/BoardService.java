package br.com.boardpadbackend.service;

import br.com.boardpadbackend.dto.BoardDto;
import br.com.boardpadbackend.entity.BoardEntity;

public interface BoardService {
    BoardDto createBoard (String boardCode);
    BoardDto findBoardByBoardCode(String boardCode);
    BoardDto getBoardWithAllDataByBoardCode(String boardConde);
}
