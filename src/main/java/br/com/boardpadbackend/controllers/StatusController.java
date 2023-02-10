package br.com.boardpadbackend.controllers;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.boardpadbackend.dto.StatusDto;
import br.com.boardpadbackend.service.StatusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/status")
@Api(value = "Status", tags = {"Status"})
public class StatusController {
    private StatusService statusService;

    @Autowired
    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @ApiOperation(value = "Returns all status available")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, message = "No content to show")
    })
    @GetMapping
    public ResponseEntity<List<StatusDto>> listAllStatus(@RequestParam("board-code") String boardCode) {
        var statusList = statusService.listAllStatus(boardCode);
        return ResponseEntity.ok().body(statusList);
    }

    @ApiOperation(value = "Creates new status")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Request error"),
            @ApiResponse(code = 500, message = "Server error, please try later.")
    })
    @PostMapping
    public StatusDto createNewStatus(@RequestParam("new-status-name") String statusName,
                                     @RequestParam("board-code") String boardCode) {
        return statusService.createNewStatus(boardCode, statusName);
    }

    @ApiOperation(value = "Deletes a status")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Status deleted successfully"),
            @ApiResponse(code = 400, message = "Error, please delete this status tasks before delete this status"),
            @ApiResponse(code = 500, message = "Server error, please try later.")
    })
    @DeleteMapping(path = "{id}")
    public ResponseEntity<String> deleteStatus(@PathVariable("id") Long idStatus) {
        try {
            statusService.deleteStatus(idStatus);
            return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Para remover essa coluna de status voce deve primeiro remover suas tarefas.");
        }
    }

    @ApiOperation(value = "Updates the status name")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Server error, please try later.")
    })
    @PutMapping(path = "{id}")
    public ResponseEntity<?> updateStatusName (@PathVariable("id") Long idStatus, @RequestParam(name = "new-name") String newStatusName) {
        try {
            statusService.updateStatusName(idStatus, newStatusName);
            return ResponseEntity.ok().body("Atualizado com sucesso!");
        }catch(Exception ex){
            log.error(ex);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno do servidor, por favor tente novamente mais tarde.");
        }
    }
}
