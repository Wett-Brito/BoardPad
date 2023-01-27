package br.com.boardpadbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "table_category")
@Entity
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_category")
    private Long idCategory;
    @Column(name = "name_category")
    private String nameCategory;
    @ManyToOne(targetEntity = BoardEntity.class, fetch = FetchType.LAZY)
    @JoinColumn (name = "id_board", referencedColumnName = "id_board", nullable = false, insertable = false, updatable = false)
    private BoardEntity board;
}
