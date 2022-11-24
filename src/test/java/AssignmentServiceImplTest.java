import com.rfeller.restapi.RestapiApplication;
import com.rfeller.restapi.containers.MySQLTestContainer;
import com.rfeller.restapi.dto.AssignmentDTO;
import com.rfeller.restapi.dto.AssignmentExecutorPOJO;
import com.rfeller.restapi.exception.ApiException;
import com.rfeller.restapi.logic.AssignmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("serviceTest")
@SpringBootTest(classes = RestapiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AssignmentServiceImplTest extends MySQLTestContainer {

    @Autowired
    private AssignmentService assignmentService;

    @Test
    public void When_addAssignment_Verify_Fields() {
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
    public void When_getByUserId_Verify_userId() {
        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentDTO.setTitle("test");
        assignmentDTO.setDescription("test");
        assignmentDTO.setUserId("1");
        assignmentDTO.setCreator("1");

        AssignmentDTO expectedAssignment = assignmentService.addAssignment(assignmentDTO);

        assertEquals(assignmentDTO.getUserId(), expectedAssignment.getUserId());
    }

    @Test
    public void When_getById_Verify_Fields() {
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
    public void When_acceptAssignment_Verify_Fields() {
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
    public void When_acceptAssignment_InvalidId_CheckForError() {
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
    public void delete() {
        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentDTO.setTitle("test");
        assignmentDTO.setDescription("test");
        assignmentDTO.setUserId("1");
        assignmentDTO.setCreator("1");

        AssignmentDTO addedAssignment = assignmentService.addAssignment(assignmentDTO);

        assignmentService.delete(addedAssignment.getId());

        Throwable exception = assertThrows(ApiException.class, () -> assignmentService.getById(addedAssignment.getId()));
        assertEquals("Assignment with id " + addedAssignment.getId() + " was not found", exception.getMessage());
    }

    @Test
    public void update() {
        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentDTO.setTitle("test");
        assignmentDTO.setDescription("test");
        assignmentDTO.setUserId("1");
        assignmentDTO.setCreator("1");

        AssignmentDTO addedAssignment = assignmentService.addAssignment(assignmentDTO);

        AssignmentDTO updatedAssignmentDTO = new AssignmentDTO();
        updatedAssignmentDTO.setTitle("test updated");
        updatedAssignmentDTO.setDescription("test updated");
        updatedAssignmentDTO.setUserId("2");
        updatedAssignmentDTO.setCreator("2");

        assignmentService.update(addedAssignment.getId(), updatedAssignmentDTO);

        AssignmentDTO retrievedUpdatedAssignment = assignmentService.getById(addedAssignment.getId());

        assertEquals(updatedAssignmentDTO.getTitle(), retrievedUpdatedAssignment.getTitle());
    }
}