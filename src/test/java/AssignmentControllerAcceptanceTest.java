import com.fasterxml.jackson.databind.ObjectMapper;
import com.rfeller.restapi.RestapiApplication;
import com.rfeller.restapi.containers.MySQLTestContainer;
import com.rfeller.restapi.dto.AssignmentDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = RestapiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AssignmentControllerAcceptanceTest extends MySQLTestContainer {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;
    @LocalServerPort
    int randomServerPort;

    private String url;

    @BeforeEach
    public void setUp() {
        url = "http://localhost:" + randomServerPort + "/assignment";

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

    }

    @Test
    public void When_Add_Assignment_Succeed() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        AssignmentDTO assignmentDTO = new AssignmentDTO();

        assignmentDTO.setUserId("1");
        assignmentDTO.setTitle("Test gig");
        assignmentDTO.setDescription("Testing");
        assignmentDTO.setCreator("Tester");

        mockMvc.perform(MockMvcRequestBuilders.post(url + "/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(assignmentDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Assignment valid"));
    }

    @Test
    public void When_Add_Invalid_Assignment_Fail() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        AssignmentDTO assignmentDTO = new AssignmentDTO();

        assignmentDTO.setTitle("Test gig");
        assignmentDTO.setDescription("Testing");
        assignmentDTO.setCreator("Tester");

        mockMvc.perform(MockMvcRequestBuilders.post(url + "/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(assignmentDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void When_Get_Assignments_Succeed() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(url + "/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void When_Add_Assignment_And_Get_Assignment_By_Id_Succeed() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        AssignmentDTO assignmentDTO = new AssignmentDTO();

        assignmentDTO.setUserId("1");
        assignmentDTO.setTitle("Test gig");
        assignmentDTO.setDescription("Testing");
        assignmentDTO.setCreator("Tester");

        mockMvc.perform(MockMvcRequestBuilders.post(url + "/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(assignmentDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Assignment valid"));

        mockMvc.perform(MockMvcRequestBuilders.get(url + "/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void When_Add_Assignment_And_Get_Assignment_By_User_Id_Succeed() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        AssignmentDTO assignmentDTO = new AssignmentDTO();

        assignmentDTO.setUserId("1");
        assignmentDTO.setTitle("Test gig");
        assignmentDTO.setDescription("Testing");
        assignmentDTO.setCreator("Tester");

        mockMvc.perform(MockMvcRequestBuilders.post(url + "/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(assignmentDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Assignment valid"));

        mockMvc.perform(MockMvcRequestBuilders.get(url + "/getByUserId/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void When_Add_Assignment_And_Update_Assignment_Succeed() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        AssignmentDTO assignmentDTO = new AssignmentDTO();

        assignmentDTO.setUserId("1");
        assignmentDTO.setTitle("Test gig");
        assignmentDTO.setDescription("Testing");
        assignmentDTO.setCreator("Tester");

        mockMvc.perform(MockMvcRequestBuilders.post(url + "/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(assignmentDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Assignment valid"));

        AssignmentDTO assignmentDTOUpdated = new AssignmentDTO();

        assignmentDTOUpdated.setTitle("Test gig updated");
        assignmentDTOUpdated.setDescription("Testing updating");

        mockMvc.perform(MockMvcRequestBuilders.put(url + "/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(assignmentDTOUpdated))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void When_Add_Assignment_And_Delete_Assignment_Succeed() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        AssignmentDTO assignmentDTO = new AssignmentDTO();

        assignmentDTO.setUserId("1");
        assignmentDTO.setTitle("Test gig");
        assignmentDTO.setDescription("Testing");
        assignmentDTO.setCreator("Tester");

        mockMvc.perform(MockMvcRequestBuilders.post(url + "/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(assignmentDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Assignment valid"));

        mockMvc.perform(MockMvcRequestBuilders.delete(url + "/delete/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Successfully deleted assignment"));
    }
}