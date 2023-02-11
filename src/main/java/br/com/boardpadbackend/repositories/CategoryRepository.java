package br.com.boardpadbackend.repositories;

import br.com.boardpadbackend.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository <CategoryEntity, Long> {

    @Query("SELECT ce FROM CategoryEntity ce INNER JOIN FETCH ce.board board WHERE board.codeBoard = :boardCode")
    List<CategoryEntity> findAllByBoardCode(String boardCode);

    @Query ("SELECT ce FROM CategoryEntity ce WHERE ce.board.codeBoard = :boardCode AND ce.idCategory = :categoryId")
    Optional<CategoryEntity> findByBoardCodeAndCategoryId(String boardCode, Long categoryId);
}
