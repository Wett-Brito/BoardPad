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
@Table(name = "table_status")
@Entity
public class StatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_status")
    private Long idStatus;
    @Column(name = "name_status")
    private String nameStatus;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "id_board", referencedColumnName = "id_board", nullable = false)
    private BoardEntity board;
}
