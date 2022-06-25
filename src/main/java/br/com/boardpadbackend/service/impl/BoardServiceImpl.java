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
			BoardEntity boardEntity = BoardRepository.findByCode(boardCode);
			
			return BoardDto.builder()
					.id(boardEntity.getIdBoard())
					.codeBoard(boardEntity.getCodeBoard())
					.build();
			
		} catch (NullPointerException e) {
			BoardEntity boardEntity =  BoardRepository.save(BoardEntity.builder().codeBoard(boardCode).build());

			return BoardDto.builder()
					.id(boardEntity.getIdBoard())
					.codeBoard(boardEntity.getCodeBoard())
					.build();

		}
		
	}

}
