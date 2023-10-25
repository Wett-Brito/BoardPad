package br.com.boardpadbackend.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

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
    @Column(name = "dtcreation_task", nullable = false)
    private Date dateCreationTask;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_status")
    private StatusEntity statusEntity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "id_board", referencedColumnName = "id_board", nullable = false)
    private BoardEntity board;
}
