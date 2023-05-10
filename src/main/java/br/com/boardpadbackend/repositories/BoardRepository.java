package br.com.boardpadbackend.repositories;

import br.com.boardpadbackend.repositories.projections.BoardWithItemsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.boardpadbackend.entity.BoardEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository <BoardEntity, Long> {

    Optional<BoardEntity> findByCodeBoard (String code_board);

    @Query(value = "SELECT " +
            "   board.idBoard AS boardId, " +
            "   board.codeBoard AS boardCode," +
            "   status.idStatus AS statusId, " +
            "   status.nameStatus AS statusName," +
            "   task.idTask AS taskId, " +
            "   task.titleTask AS titleTask," +
            "   category.nameCategory AS categoryName," +
            "   category.idCategory AS categoryId" +
            " FROM BoardEntity board " +
            " LEFT JOIN CategoryEntity category " +
            "   ON category.board.idBoard = board.idBoard " +
            " LEFT JOIN TaskEntity task " +
            "   ON task.board.idBoard = board.idBoard " +
            " LEFT JOIN StatusEntity status " +
            "   ON status.board.idBoard = task.statusEntity.idStatus " +
            "WHERE board.codeBoard = :codeBoard " +
            "ORDER BY status.idStatus")
    List<BoardWithItemsProjection> findByCodeBoardWithTasksCategoriesAndStatus (String codeBoard);
}