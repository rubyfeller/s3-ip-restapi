package com.rfeller.restapi.dal;

import com.rfeller.restapi.RestapiApplication;
import com.rfeller.restapi.containers.MySQLTestContainer;
import com.rfeller.restapi.dal.models.Assignment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.shaded.org.hamcrest.CoreMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.testcontainers.shaded.org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest(classes = RestapiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AssignmentRepositoryTest extends MySQLTestContainer {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Test
    public void When_Get_All_By_UserId_Verify_Assignment() {
        Assignment assignment = new Assignment();

        assignment.setTitle("test");
        assignment.setDescription("test");
        assignment.setUserId("1");
        assignment.setCreator("1");

        List<Assignment> assignments = new ArrayList<>();

        assignments.add(assignment);

        assignmentRepository.save(assignment);

        List<Assignment> expectedAssignments = assignmentRepository.findAllByUserId("1");

        Assertions.assertEquals(assignments.get(0).getUserId(), expectedAssignments.get(0).getUserId());
        assertThat(assignments, CoreMatchers.hasItem(assignment));
        Assertions.assertEquals(1, expectedAssignments.size());
    }
}