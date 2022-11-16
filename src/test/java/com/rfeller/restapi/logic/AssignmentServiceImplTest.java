package com.rfeller.restapi.logic;

import com.rfeller.restapi.RestapiApplication;
import com.rfeller.restapi.containers.MySQLTestContainer;
import com.rfeller.restapi.dto.AssignmentDTO;
import com.rfeller.restapi.dto.AssignmentExecutorPOJO;
import com.rfeller.restapi.exception.ApiException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = RestapiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AssignmentServiceImplTest extends MySQLTestContainer {

    @Autowired
    private AssignmentService assignmentService;

    @Test
    void When_addAssignment_Verify_Fields() {
        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentDTO.setTitle("test");
        assignmentDTO.setDescription("test");
        assignmentDTO.setUserId("1");
        assignmentDTO.setCreator("1");

        assignmentService.addAssignment(assignmentDTO);

        AssignmentDTO expectedAssignment = assignmentService.getById(1);

        assertEquals(assignmentDTO.getTitle(), expectedAssignment.getTitle());
        assertEquals(assignmentDTO.getDescription(), expectedAssignment.getDescription());
        assertEquals(assignmentDTO.getUserId(), expectedAssignment.getUserId());
        assertEquals(assignmentDTO.getCreator(), expectedAssignment.getCreator());

    }

    @Test
    void When_getAll_Expect_Empty_List() {
        List<AssignmentDTO> list = (List<AssignmentDTO>) assignmentService.getAll();

        assertEquals(0, list.size());
    }

    @Test
    void When_getByUserId_Verify_userId() {
        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentDTO.setTitle("test");
        assignmentDTO.setDescription("test");
        assignmentDTO.setUserId("1");
        assignmentDTO.setCreator("1");

        assignmentService.addAssignment(assignmentDTO);

        List<AssignmentDTO> expectedAssignment = (List<AssignmentDTO>) assignmentService.getByUserId("1");

        assertEquals(assignmentDTO.getUserId(), expectedAssignment.get(0).getUserId());
    }

    @Test
    void When_getById_Verify_Fields() {
        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentDTO.setTitle("test");
        assignmentDTO.setDescription("test");
        assignmentDTO.setUserId("1");
        assignmentDTO.setCreator("1");

        assignmentService.addAssignment(assignmentDTO);

        AssignmentDTO expectedAssignment = assignmentService.getById(1);

        assertEquals(assignmentDTO.getTitle(), expectedAssignment.getTitle());
        assertEquals(assignmentDTO.getDescription(), expectedAssignment.getDescription());
        assertEquals(assignmentDTO.getUserId(), expectedAssignment.getUserId());
        assertEquals(assignmentDTO.getCreator(), expectedAssignment.getCreator());
    }

    @Test
    void When_acceptAssignment_Verify_Fields() {
        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentDTO.setTitle("test");
        assignmentDTO.setDescription("test");
        assignmentDTO.setUserId("1");
        assignmentDTO.setCreator("1");

        assignmentService.addAssignment(assignmentDTO);

        AssignmentExecutorPOJO assignmentExecutorPOJO = new AssignmentExecutorPOJO();
        assignmentExecutorPOJO.setExecutor("Ruby");
        assignmentExecutorPOJO.setExecutionPrice("300");
        assignmentExecutorPOJO.setExecutionDateTime(new Date());

        AssignmentExecutorPOJO executor = assignmentService.acceptAssignment(1, assignmentExecutorPOJO);

        assertEquals(executor.getExecutor(), assignmentExecutorPOJO.getExecutor());
        assertEquals(executor.getExecutionPrice(), assignmentExecutorPOJO.getExecutionPrice());
        assertEquals(executor.getExecutionDateTime(), assignmentExecutorPOJO.getExecutionDateTime());
    }

    @Test
    void When_acceptAssignment_InvalidId_CheckForError() {
        AssignmentExecutorPOJO assignmentExecutorPOJO = new AssignmentExecutorPOJO();

        assignmentExecutorPOJO.setExecutor("Ruby");
        assignmentExecutorPOJO.setExecutionPrice("300");
        assignmentExecutorPOJO.setExecutionDateTime(new Date());

        int assignmentToAcceptId = 1;

        Throwable exception = assertThrows(ApiException.class, () -> assignmentService.acceptAssignment(assignmentToAcceptId, assignmentExecutorPOJO));
        assertEquals("Assignment with id " + assignmentToAcceptId + " was not found", exception.getMessage());
    }

    @Test
    void delete() {
        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentDTO.setTitle("test");
        assignmentDTO.setDescription("test");
        assignmentDTO.setUserId("1");
        assignmentDTO.setCreator("1");

        assignmentService.addAssignment(assignmentDTO);

        assignmentService.delete(1);

        List<AssignmentDTO> assignments = (List<AssignmentDTO>) assignmentService.getAll();

        assertEquals(0, assignments.size());
    }

    @Test
    void update() {
        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentDTO.setTitle("test");
        assignmentDTO.setDescription("test");
        assignmentDTO.setUserId("1");
        assignmentDTO.setCreator("1");

        assignmentService.addAssignment(assignmentDTO);

        AssignmentDTO updatedAssignmentDTO = new AssignmentDTO();
        updatedAssignmentDTO.setTitle("test updated");
        updatedAssignmentDTO.setDescription("test updated");
        updatedAssignmentDTO.setUserId("2");
        updatedAssignmentDTO.setCreator("2");

        assignmentService.update(1, updatedAssignmentDTO);

        AssignmentDTO retrievedUpdatedAssignment = assignmentService.getById(1);

        assertEquals(updatedAssignmentDTO.getTitle(), retrievedUpdatedAssignment.getTitle());

    }
}