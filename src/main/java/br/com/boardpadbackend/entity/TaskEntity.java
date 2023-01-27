package br.com.boardpadbackend.entity;

import br.com.boardpadbackend.dto.StatusDto;
import lombok.*;

import javax.persistence.*;
import java.math.BigInteger;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "table_tasks")
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tasks")
    private Long idTask;
    @Column(name = "title_tasks")
    private String titleTask;
    @Column(name = "description_tasks")
    private String descriptionTask;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_status", nullable = false)
    private StatusEntity statusEntity;

    @ManyToOne
    @JoinColumn(name = "id_category")
    private CategoryEntity categoryEntity;

    @ManyToOne
    @JoinColumn(name = "id_board")
    private BoardEntity boardEntity;
}
