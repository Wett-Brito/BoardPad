package br.com.boardpadbackend.repositories;

import br.com.boardpadbackend.entity.StatusEntity;
import org.aspectj.weaver.loadtime.Options;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface StatusRepository extends JpaRepository<StatusEntity, Long> {
    @Query("SELECT status FROM StatusEntity status WHERE status.board.codeBoard = :boardCode ORDER BY status.idStatus ASC")
    List<StatusEntity> listAllStatusFromBoardCode(String boardCode);

    @Query("SELECT status FROM StatusEntity status " +
            "WHERE status.idStatus = :statusId " +
            " AND status.board.codeBoard = :boardCode")
    Optional<StatusEntity> getStatusByIdAndBoardCode(Long statusId, String boardCode);

    Optional<StatusEntity> getStatusEntityByIdStatus(Long idStatus);
}
