package br.com.boardpadbackend.dto.inputs;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel("Data to task creation")
public class TaskInputDto {
    @ApiParam(name = "Tasks title")
    private String title;
    @ApiParam(name = "Tasks description")
    private String description;
    @ApiParam(name = "Id of task status")
    @NotNull
    private Long idStatus;
    @ApiParam(name = "Id of task category")
    @NotNull
    private Long idCategory;
}
