package br.com.boardpadbackend.repositories;

import br.com.boardpadbackend.entity.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface StatusRepository extends JpaRepository<StatusEntity, Long> {
}
