package br.com.boardpadbackend.service.impl;

import java.util.Optional;

import br.com.boardpadbackend.exceptions.BadRequestException;
import br.com.boardpadbackend.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.boardpadbackend.dto.BoardDto;
import br.com.boardpadbackend.entity.BoardEntity;
import br.com.boardpadbackend.repositories.BoardRepository;
import br.com.boardpadbackend.service.BoardService;

@Service
public class BoardServiceImpl implements BoardService {

    private BoardRepository boardRepository;

    @Autowired
    public BoardServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;

    }


    @Override
    public BoardDto createBoard(String boardCode) {
        try {
            Optional<BoardEntity> existentBoard = boardRepository.findByCodeBoard(boardCode);
            if (existentBoard.isPresent()) {
                return BoardDto.builder()
                        .id(existentBoard.get().getIdBoard())
                        .codeBoard(existentBoard.get().getCodeBoard())
                        .build();
            }
            BoardEntity newBoardEntity = boardRepository.save(BoardEntity.builder()
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

    public BoardEntity findBoardByBoardCode(String boardCode) {
        Optional<BoardEntity> foundBoard = boardRepository.findByCodeBoard(boardCode);
        if(foundBoard.isEmpty()) throw new NotFoundException("The board [" + boardCode + "] doesn't exists.");
        return foundBoard.get();
    }

}
