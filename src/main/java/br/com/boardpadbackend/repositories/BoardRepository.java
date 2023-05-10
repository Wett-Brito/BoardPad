package br.com.boardpadbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.boardpadbackend.entity.BoardEntity;

import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository <BoardEntity, Long> {

    Optional<BoardEntity> findByCodeBoard (String code_board);
}