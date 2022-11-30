import com.fasterxml.jackson.databind.ObjectMapper;
import com.rfeller.restapi.RestapiApplication;
import com.rfeller.restapi.dto.AssignmentDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = RestapiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AssignmentControllerAcceptanceTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;
    @LocalServerPort
    int randomServerPort;

    private TestRestTemplate restTemplate;
    private String url;

    @BeforeEach
    public void setUp() {
        restTemplate = new TestRestTemplate();
        url = "http://localhost:" + randomServerPort + "/assignment" + "/add";

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

    }

    @Test
    public void When_Add_Assignment_Without_Authorization_Unauthorized_401() {
        AssignmentDTO assignmentDTO = new AssignmentDTO();

        ResponseEntity<AssignmentDTO> result = restTemplate.postForEntity(url, assignmentDTO, AssignmentDTO.class);

        assignmentDTO.setUserId("1");
        assignmentDTO.setTitle("Test gig");
        assignmentDTO.setDescription("Testing");
        assignmentDTO.setCreator("Tester");

        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }

    @Test
    @WithMockUser
    public void When_Add_Assignment_With_Authorization_Mock_Succeed() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        AssignmentDTO assignmentDTO = new AssignmentDTO();

        assignmentDTO.setUserId("1");
        assignmentDTO.setTitle("Test gig");
        assignmentDTO.setDescription("Testing");
        assignmentDTO.setCreator("Tester");

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(assignmentDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Assignment valid"));
    }
}