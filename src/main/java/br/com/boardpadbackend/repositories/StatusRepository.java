package br.com.boardpadbackend.repositories;

import br.com.boardpadbackend.entity.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<StatusEntity, Long> {
}
