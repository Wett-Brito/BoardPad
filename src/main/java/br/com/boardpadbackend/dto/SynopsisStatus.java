package br.com.boardpadbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SynopsisStatus {
    public SynopsisStatus(BigInteger id, String name) {
        this.id = id;
        this.name = name;
    }

    private BigInteger id;
    private String name;
    private List<SynopsisTask> tasks;
}
