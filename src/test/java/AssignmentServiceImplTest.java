import com.rfeller.restapi.RestapiApplication;
import com.rfeller.restapi.containers.MySQLTestContainer;
import com.rfeller.restapi.dal.AssignmentRepository;
import com.rfeller.restapi.dto.AssignmentDTO;
import com.rfeller.restapi.dto.AssignmentExecutorPOJO;
import com.rfeller.restapi.exception.ApiException;
import com.rfeller.restapi.logic.AssignmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("serviceTest")
@SpringBootTest(classes = RestapiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AssignmentServiceImplTest extends MySQLTestContainer {
    @Autowired
    private AssignmentService assignmentService;


    @Autowired
    private AssignmentRepository assignmentRepository;

    @BeforeEach
    void testConfig() {
        assignmentRepository.deleteAll();
    }

    @Test
    void When_getAll_Verify_Fields() {
        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentDTO.setTitle("test f");
        assignmentDTO.setDescription("test");
        assignmentDTO.setUserId("1");
        assignmentDTO.setCreator("1");

        AssignmentDTO expectedAssignment = assignmentService.addAssignment(assignmentDTO);

        List<AssignmentDTO> receivedAssignments = (List<AssignmentDTO>) assignmentService.getAll();

        assertEquals(expectedAssignment.getTitle(), receivedAssignments.get(0).getTitle());
    }

    @Test
    void When_addAssignment_Verify_Fields() {
        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentDTO.setTitle("test");
        assignmentDTO.setDescription("test");
        assignmentDTO.setUserId("1");
        assignmentDTO.setCreator("1");

        AssignmentDTO expectedAssignment = assignmentService.addAssignment(assignmentDTO);

        assertEquals(assignmentDTO.getTitle(), expectedAssignment.getTitle());
        assertEquals(assignmentDTO.getDescription(), expectedAssignment.getDescription());
        assertEquals(assignmentDTO.getUserId(), expectedAssignment.getUserId());
        assertEquals(assignmentDTO.getCreator(), expectedAssignment.getCreator());

    }

    @Test
    void When_getByUserId_Verify_Fields() {
        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentDTO.setTitle("test");
        assignmentDTO.setDescription("test");
        assignmentDTO.setUserId("1");
        assignmentDTO.setCreator("1");

        AssignmentDTO expectedAssignment = assignmentService.addAssignment(assignmentDTO);

        assertEquals(assignmentDTO.getUserId(), expectedAssignment.getUserId());
        assertEquals(assignmentDTO.getTitle(), expectedAssignment.getTitle());
        assertEquals(assignmentDTO.getDescription(), expectedAssignment.getDescription());
        assertEquals(assignmentDTO.getCreator(), expectedAssignment.getCreator());
        assertEquals(assignmentDTO.getExecutionDateTime(), expectedAssignment.getExecutionDateTime());
        assertEquals(assignmentDTO.getExecutor(), expectedAssignment.getExecutor());
        assertEquals(assignmentDTO.getExecutionPrice(), expectedAssignment.getExecutionPrice());
    }

    @Test
    void When_getById_Verify_Fields() {
        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentDTO.setTitle("test");
        assignmentDTO.setDescription("test");
        assignmentDTO.setUserId("1");
        assignmentDTO.setCreator("1");

        AssignmentDTO expectedAssignment = assignmentService.addAssignment(assignmentDTO);

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

        AssignmentDTO expectedAssignnment = assignmentService.addAssignment(assignmentDTO);

        AssignmentExecutorPOJO assignmentExecutorPOJO = new AssignmentExecutorPOJO();
        assignmentExecutorPOJO.setExecutor("Ruby");
        assignmentExecutorPOJO.setExecutionPrice("300");
        assignmentExecutorPOJO.setExecutionDateTime(new Date());

        AssignmentExecutorPOJO executor = assignmentService.acceptAssignment(expectedAssignnment.getId(), assignmentExecutorPOJO);

        assertEquals(executor.getExecutor(), assignmentExecutorPOJO.getExecutor());
        assertEquals(executor.getExecutionPrice(), assignmentExecutorPOJO.getExecutionPrice());
        assertEquals(executor.getExecutionDateTime(), assignmentExecutorPOJO.getExecutionDateTime());
    }

    @Test
    void When_acceptAssignment_InvalidId_CheckForError() {
        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentDTO.setTitle("test");
        assignmentDTO.setDescription("test");
        assignmentDTO.setUserId("1");
        assignmentDTO.setCreator("1");

        AssignmentExecutorPOJO assignmentExecutorPOJO = new AssignmentExecutorPOJO();

        assignmentExecutorPOJO.setExecutor("Ruby");
        assignmentExecutorPOJO.setExecutionPrice("300");
        assignmentExecutorPOJO.setExecutionDateTime(new Date());

        Throwable exception = assertThrows(ApiException.class, () -> assignmentService.acceptAssignment(123, assignmentExecutorPOJO));
        assertEquals("Assignment with id " + 123 + " was not found", exception.getMessage());
    }

    @Test
    void When_delete_Verify_Not_Found() {
        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentDTO.setTitle("test");
        assignmentDTO.setDescription("test");
        assignmentDTO.setUserId("1");
        assignmentDTO.setCreator("1");

        AssignmentDTO addedAssignment = assignmentService.addAssignment(assignmentDTO);

        assignmentService.delete(addedAssignment.getId());

        Integer addedAssignmentId = addedAssignment.getId();

        Throwable exception = assertThrows(ApiException.class, () -> assignmentService.getById(addedAssignmentId));
        assertEquals("Assignment with id " + addedAssignment.getId() + " was not found", exception.getMessage());
    }

    @Test
    void When_update_verify_updated_fields() {
        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentDTO.setTitle("test");
        assignmentDTO.setDescription("test");
        assignmentDTO.setUserId("1");
        assignmentDTO.setCreator("1");

        AssignmentDTO addedAssignment = assignmentService.addAssignment(assignmentDTO);

        AssignmentDTO updatedAssignmentDTO = new AssignmentDTO();
        updatedAssignmentDTO.setTitle("test updated");
        updatedAssignmentDTO.setDescription("test updated");

        assignmentService.update(addedAssignment.getId(), updatedAssignmentDTO);

        AssignmentDTO retrievedUpdatedAssignment = assignmentService.getById(addedAssignment.getId());

        assertEquals(updatedAssignmentDTO.getTitle(), retrievedUpdatedAssignment.getTitle());
        assertEquals(updatedAssignmentDTO.getDescription(), retrievedUpdatedAssignment.getDescription());
    }
}