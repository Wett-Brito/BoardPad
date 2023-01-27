package br.com.boardpadbackend.entity;

import lombok.*;

import javax.persistence.*;

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

    @ManyToOne(targetEntity = BoardEntity.class, optional = false)
    @JoinColumn(name = "id_board", referencedColumnName = "id_board")
    private Long boardId;
}
