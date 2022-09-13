package com.rfeller.restapi.logic;

import com.rfeller.restapi.converter.AssignmentConverter;
import com.rfeller.restapi.dal.AssignmentRespository;
import com.rfeller.restapi.dal.models.Assignment;
import com.rfeller.restapi.dto.AssignmentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentServiceImpl implements AssignmentService {
    @Autowired
    private AssignmentRespository assignmentRespository;

    @Autowired
    AssignmentConverter assignmentConverter;

    public AssignmentDTO addAssignment(AssignmentDTO assignmentDTO) {
        Assignment assignment = assignmentConverter.convertDtoToEntity(assignmentDTO);
        assignment = assignmentRespository.save(assignment);

        return assignmentConverter.convertEntityToDto(assignment);
    }

    @Override
    public Iterable<AssignmentDTO> getAll() {
        List<Assignment> assignments = (List<Assignment>) assignmentRespository.findAll();
        List<AssignmentDTO> assignmentDTOs = assignmentConverter.convertEntitiesToDtos(assignments);

        return assignmentDTOs;
    }

    @Override
    public AssignmentDTO getById(Integer id) {
        Assignment assignment = assignmentRespository.findById(id).orElse(null);
        AssignmentDTO assignmentDTO = assignmentConverter.convertEntityToDto(assignment);

        return assignmentDTO;
    }

    @Override
    public String delete(Integer id) {
        if (assignmentRespository.existsById(id)) {
            assignmentRespository.deleteById(id);
        } else {
            return "No assignment found";
        }
        return "Deleted assignment";
    }

    @Override
    public AssignmentDTO update(Integer id, AssignmentDTO assignmentDTO) {
        Assignment existingAssignment = assignmentRespository.findById(id).orElse(null);
        existingAssignment.setTitle(assignmentDTO.getTitle());
        existingAssignment.setDescription(assignmentDTO.getDescription());

        AssignmentDTO existingAssignmentDTO = assignmentConverter.convertEntityToDto(existingAssignment);

        assignmentRespository.save(existingAssignment);

        return existingAssignmentDTO;
    }
}