import com.rfeller.restapi.RestapiApplication;
import com.rfeller.restapi.containers.MySQLTestContainer;
import com.rfeller.restapi.dal.AssignmentRepository;
import com.rfeller.restapi.dal.models.Assignment;
import com.rfeller.restapi.dto.AssignmentDTO;
import com.rfeller.restapi.logic.AssignmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = RestapiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AssignmentRepositoryTest extends MySQLTestContainer {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private AssignmentService assignmentService;

    @Test
    public void When_GetAssignments_Expect_EmptyList() {
        List<Assignment> list = (List<Assignment>) assignmentRepository.findAll();
        assertEquals(0, list.size());
    }

    @Test
    public void When_AddAssignment_Expect_Assignment() {
        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentDTO.setTitle("test");
        assignmentDTO.setDescription("test");
        assignmentDTO.setUserId("1");
        assignmentDTO.setCreator("1");

        assignmentService.addAssignment(assignmentDTO);

        List<Assignment> list = (List<Assignment>) assignmentRepository.findAll();

        assertEquals(1, list.size());
    }

    @Test
    public void When_AssignmentDeleted_Expect_EmptyList() {
        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentDTO.setTitle("test");
        assignmentDTO.setDescription("test");
        assignmentDTO.setUserId("1");
        assignmentDTO.setCreator("1");

        assignmentService.addAssignment(assignmentDTO);

        assignmentService.delete(1);

        List<Assignment> list = (List<Assignment>) assignmentRepository.findAll();

        assertEquals(0, list.size());
    }
}