package br.com.boardpadbackend.repositories;

import br.com.boardpadbackend.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity, Long>{
    @Query("SELECT task FROM TaskEntity task " +
            "JOIN FETCH task.categoryEntity " +
            "JOIN FETCH task.statusEntity")
    List<TaskEntity> findAllWithCategoryAndStatus();

    @Modifying
    @Query("UPDATE TaskEntity task SET task.statusEntity.idStatus = :newStatusId WHERE task.idTask = :idTask ")
    void updateTaskStatus(Long idTask, Long newStatusId);
}