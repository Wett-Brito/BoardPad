package br.com.boardpadbackend.controllers;

import br.com.boardpadbackend.dto.GenericResponseDTO;
import br.com.boardpadbackend.dto.StatusDto;
import br.com.boardpadbackend.dto.SynopsisStatus;
import br.com.boardpadbackend.exceptions.NotFoundException;
import br.com.boardpadbackend.mockedclasses.StatusEntityAndDto;
import br.com.boardpadbackend.service.StatusService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigInteger;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(StatusController.class)
class StatusControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private StatusService statusService;

    /**
     * When has status created on the board
     * @throws Exception
     */
    @Test
    public void listAllStatus_WhenSuccess() throws Exception{
        when(statusService.listAllStatus(eq("board-test")))
                .thenReturn(List.of(StatusEntityAndDto.MOCKED_STATUS_DTO));

        mockMvc.perform(get("/status").queryParam("board-code", "board-test"))
                .andExpect(status().isOk());
    }

    /**
     * When has no status created on the board to list.
     * @throws Exception
     */
    @Test
    public void listAllStatus_WhenNoTasksFound() throws Exception{
        when(statusService.listAllStatus(eq("board-test")))
                .thenThrow(new NotFoundException("No status found to board [board-test]."));

        mockMvc.perform(get("/status").queryParam("board-code", "board-test"))
                .andExpect(status().isNotFound());
    }

    /**
     * When status is successfully created
     * @throws Exception
     */
    @Test
    public void createNewStatus_WhenSuccess() throws Exception{
        when(statusService.createNewStatus(eq("board-test"), eq("to do")))
                .thenReturn(StatusEntityAndDto.MOCKED_STATUS_DTO);

        mockMvc.perform(post("/status")
                        .queryParam("board-code", "board-test")
                        .queryParam("new-status-name", "to do"))
                .andExpectAll(status().isCreated(),
                        content().json(objectMapper.writeValueAsString(
                                GenericResponseDTO.<StatusDto>builder()
                                        .status("OK")
                                        .response(StatusEntityAndDto.MOCKED_STATUS_DTO)
                                        .build())
                        )
                );
    }

    /**
     * When status isn't created because board doesn't exist
     * @throws Exception
     */
    @Test
    public void createNewStatus_WhenBoardDoesntExists() throws Exception{
        when(statusService.createNewStatus(eq("board-test"), eq("to do")))
                .thenThrow(new NotFoundException("The board [board-test] doesn't exists."));

        mockMvc.perform(post("/status")
                        .queryParam("board-code", "board-test")
                        .queryParam("new-status-name","to do"))
                .andExpect(status().isNotFound());
    }

    /**
     * When status is updated with success
     * @throws Exception
     */
    @Test
    public void updateStatusName_WhenSuccess() throws Exception{
        doNothing()
                .when(statusService)
                .updateStatusName(eq(1L),eq("to do"), eq("board-test"));

        mockMvc.perform(put("/status/1")
                        .queryParam("board-code", "board-test")
                        .queryParam("new-name","to do"))
                .andExpect(status().isOk());
    }

    /**
     * When status isn't updated because status isn't created on the board
     * @throws Exception
     */
    @Test
    public void updateStatusName_WhenStatusIsNotCreatedOnTheBoard() throws Exception{
        doThrow(NotFoundException.class)
                .when(statusService)
                .updateStatusName(eq(1L),eq("to do"), eq("board-test"));

        mockMvc.perform(put("/status/1")
                        .queryParam("board-code", "board-test")
                        .queryParam("new-name","to do"))
                .andExpect(status().isNotFound());
    }

    /**
     * When status is deleted with success
     * @throws Exception
     */
    @Test
    public void deleteStatus_WhenSuccess() throws Exception{
        doNothing()
                .when(statusService)
                .deleteStatus(eq("board-test"),eq(1L));

        mockMvc.perform(delete("/status/1")
                        .queryParam("board-code", "board-test"))
                .andExpect(status().isNoContent());
    }

    /**
     * When client tries to delete a status but this status is related with some task
     * @throws Exception
     */
    @Test
    public void deleteStatus_WhenStatusIsLinkedWithTasks() throws Exception{
        doThrow(DataIntegrityViolationException.class)
                .when(statusService)
                .deleteStatus(eq("board-test"),eq(1L));

        mockMvc.perform(delete("/status/1")
                        .queryParam("board-code", "board-test"))
                .andExpectAll(
                        status().isBadRequest(),
                        content().json(
                                objectMapper.writeValueAsString(
                                        GenericResponseDTO.builder()
                                                .status("NOK")
                                                .message("To delete this status, you must change the status of all tasks related to this status.")
                                                .build()
                                )
                        ));
    }

    /**
     * When service throws a NotFoundException in case of status is not created on the board
     * @throws Exception
     */
    @Test
    public void deleteStatus_WhenStatusIsNotCreatedOnTheBoard() throws Exception{
        doThrow(NotFoundException.class)
                .when(statusService)
                .deleteStatus(eq("board-test"),eq(1L));

        mockMvc.perform(delete("/status/1")
                        .queryParam("board-code", "board-test"))
                .andExpect(status().isNotFound());
    }

    /**
     * When service throws a non mapped exception
     * @throws Exception
     */
    @Test
    public void deleteStatus_WhenNoMappedExceptionIsThrowingByService() throws Exception{
        doThrow(RuntimeException.class)
                .when(statusService)
                .deleteStatus(eq("board-test"),eq(1L));

        mockMvc.perform(delete("/status/1")
                        .queryParam("board-code", "board-test"))
                .andExpectAll(status().isInternalServerError(),
                        content().json(
                                objectMapper.writeValueAsString(
                                        GenericResponseDTO.builder()
                                                .status("NOK")
                                                .message("An internal server error has occurred. Please try again later.")
                                                .build()
                                )
                        ));
    }

    @Test
    public void getStatusById_WhenFound() throws Exception{
        when(statusService.getStatusById(eq(1L)))
                .thenReturn(
                        SynopsisStatus.builder()
                                .id(BigInteger.ONE)
                                .name("To Do")
                                .build());

        mockMvc.perform(get("/status/1"))
                .andExpect(status().isOk());
    }
    @Test
    public void getStatusById_WhenNotFound() throws Exception{
        when(statusService.getStatusById(eq(1L)))
                .thenThrow(new NotFoundException("No status found with id 1"));

        mockMvc.perform(get("/status/1"))
                .andExpect(status().isNotFound());
    }
}