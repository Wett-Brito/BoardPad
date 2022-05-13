package br.com.boardpadbackend.controllers;

import br.com.boardpadbackend.dto.StatusDto;
import br.com.boardpadbackend.service.StatusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<StatusDto>> listAllStatus() {
        List<StatusDto> statusList = statusService.listAllStatus();
        return (statusList.size() > 0)? ResponseEntity.ok().body(statusList) :
                ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Creates new status")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Request error"),
            @ApiResponse(code = 500, message = "Server error, please try later.")
    })
    @PostMapping
    public StatusDto createNewStatus (@RequestBody String statusName) {
        return statusService.createNewStatus(statusName);
    }

    @ApiOperation(value = "Deletes a status")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Request error"),
            @ApiResponse(code = 500, message = "Server error, please try later.")
    })
    @DeleteMapping(path = "{id}")
    public void deleteStatus (@PathVariable("id") Long idStatus) {
        statusService.deleteStatus(idStatus);
    }
}
