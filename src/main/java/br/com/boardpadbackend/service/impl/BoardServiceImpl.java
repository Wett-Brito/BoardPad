package br.com.boardpadbackend.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import br.com.boardpadbackend.converters.BoardDtoConverter;

import br.com.boardpadbackend.dto.*;
import br.com.boardpadbackend.exceptions.NotFoundException;
import br.com.boardpadbackend.useCase.BoardTasksGrouping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.boardpadbackend.entity.BoardEntity;
import br.com.boardpadbackend.repositories.BoardRepository;
import br.com.boardpadbackend.service.BoardService;

@Service
public class BoardServiceImpl implements BoardService {
    private BoardRepository boardRepository;
    private BoardTasksGrouping boardTasksGrouping;

    @Autowired
    public BoardServiceImpl(BoardRepository boardRepository, BoardTasksGrouping boardTasksGrouping) {
        this.boardRepository = boardRepository;
        this.boardTasksGrouping = boardTasksGrouping;
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

    @Override
    public BoardDto findBoardByBoardCode(String boardCode) {
        var foundBoard = boardRepository.findByCodeBoard(boardCode);
        if (foundBoard.isEmpty()) throw new NotFoundException("The board [" + boardCode + "] doesn't exists.");
        return BoardDtoConverter.INSTANCE.entityToDto(foundBoard.get());
    }

    @Override
    public BoardDto getBoardWithAllDataByBoardCode(String boardCode) {
        var foundBoard = this.findBoardByBoardCode(boardCode);
        return boardTasksGrouping.getBoard(foundBoard);
    }

}
