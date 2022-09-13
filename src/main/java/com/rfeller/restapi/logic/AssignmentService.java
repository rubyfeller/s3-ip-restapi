package com.rfeller.restapi.logic;

import com.rfeller.restapi.dto.AssignmentDTO;

public interface AssignmentService {
    AssignmentDTO addAssignment(AssignmentDTO assignmentDTO);

    Iterable<AssignmentDTO> getAll();

    AssignmentDTO getById(Integer id);

    String delete(Integer id);

    AssignmentDTO update(Integer id, AssignmentDTO assignmentDTO);
}
