package br.com.boardpadbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.boardpadbackend.entity.BoardEntity;

@Repository
public interface BoardRepository extends JpaRepository <BoardEntity, Long> {
	
    @Query(value = "SELECT * FROM table_boards WHERE code_board = ?1", nativeQuery = true)
    BoardEntity findByCode(String code_board);
}