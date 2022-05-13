package br.com.boardpadbackend.repositories;

import br.com.boardpadbackend.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository <CategoryEntity, Long> {
}
