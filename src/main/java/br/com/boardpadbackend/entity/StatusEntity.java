package br.com.boardpadbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;

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

    @ManyToOne(targetEntity = BoardEntity.class, optional = false)
    @JoinColumn(name = "id_board", referencedColumnName = "id_board")
    private BigInteger boardId;
}
