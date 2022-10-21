package com.rfeller.restapi.logic;

import com.rfeller.restapi.dto.AssignmentDTO;
import com.rfeller.restapi.dto.AssignmentExecutorPOJO;

public interface AssignmentService {
    AssignmentDTO addAssignment(AssignmentDTO assignmentDTO);

    Iterable<AssignmentDTO> getAll();

    AssignmentDTO getById(Integer id);

    AssignmentExecutorPOJO acceptAssignment(Integer id, AssignmentExecutorPOJO executor);

    String delete(Integer id);

    AssignmentDTO update(Integer id, AssignmentDTO assignmentDTO);
}
