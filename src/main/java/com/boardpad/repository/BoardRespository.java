package com.boardpad.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boardpad.entity.Board;

public interface BoardRespository extends JpaRepository<Board, Long>{

}
