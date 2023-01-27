package br.com.boardpadbackend.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.boardpadbackend.dto.BoardDto;
import br.com.boardpadbackend.entity.BoardEntity;
import br.com.boardpadbackend.repositories.BoardRepository;
import br.com.boardpadbackend.service.BoardService;

@Service
public class BoardServiceImpl implements BoardService {

    private BoardRepository BoardRepository;

    @Autowired
    public BoardServiceImpl(BoardRepository BoardRepository) {
        this.BoardRepository = BoardRepository;

    }


    @Override
    public BoardDto createBoard(String boardCode) {
        try {
            Optional<BoardEntity> existentBoard = BoardRepository.findByCodeBoard(boardCode);
            if (existentBoard.isPresent()) {
                return BoardDto.builder()
                        .id(existentBoard.get().getIdBoard())
                        .codeBoard(existentBoard.get().getCodeBoard())
                        .build();
            }
            BoardEntity newBoardEntity = BoardRepository.save(BoardEntity.builder()
							.codeBoard(boardCode)
							.build());

            return BoardDto.builder()
                    .id(newBoardEntity.getIdBoard())
                    .codeBoard(newBoardEntity.getCodeBoard())
                    .build();

        } catch (Exception e) {
        	return null;
        }
    }

}
