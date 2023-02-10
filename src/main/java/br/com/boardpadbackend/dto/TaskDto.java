package br.com.boardpadbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private Long idStatus;
    private String nameStatus;
    private Long idCategory;
    private String nameCategory;
    private Long idBoard;
    private String boardCode;
}
