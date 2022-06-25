package br.com.boardpadbackend.service;

import br.com.boardpadbackend.dto.BoardDto;

public interface BoardService {
    BoardDto createBoard (String boardCode);
}
