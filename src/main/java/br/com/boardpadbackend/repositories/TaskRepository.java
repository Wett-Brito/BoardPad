package br.com.boardpadbackend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.com.boardpadbackend.entity.TaskEntity;

public interface TaskRepository extends JpaRepository<TaskEntity, Long>{
    @Query("SELECT task FROM TaskEntity task " +
            "JOIN FETCH task.statusEntity " +
            "LEFT JOIN FETCH task.categoryEntity " +
            "WHERE task.board.codeBoard = :boardCode " +
            "ORDER BY task.idTask asc, task.dateCreationTask asc")
    List<TaskEntity> findAllWithCategoryAndStatus(String boardCode);

    @Modifying
    @Query("UPDATE TaskEntity task SET task.statusEntity.idStatus = :newStatusId WHERE task.idTask = :idTask ")
    void updateTaskStatus(Long idTask, Long newStatusId);

    @Modifying
    @Query(value = "UPDATE TaskEntity task SET task.statusEntity = null WHERE task.statusEntity.idStatus = :idStatus")
    void deleteAllByStatusEntityIdStatus(Long idStatus);
    
    @Modifying
    @Query("UPDATE TaskEntity task SET task.categoryEntity.idCategory = null WHERE task.categoryEntity.idCategory = :idCategory ")
    void updateTaskCategoryToNull(Long idCategory);
    
}