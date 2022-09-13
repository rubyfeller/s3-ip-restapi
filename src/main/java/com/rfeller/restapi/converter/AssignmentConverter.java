package com.rfeller.restapi.converter;

import com.rfeller.restapi.dto.AssignmentDTO;
import com.rfeller.restapi.dal.models.Assignment;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class AssignmentConverter {

    public AssignmentDTO convertEntityToDto(Assignment assignment) {
        ModelMapper modelMapper = new ModelMapper();
        AssignmentDTO assignmentDTO = modelMapper.map(assignment, AssignmentDTO.class);
        return assignmentDTO;
    }

    public Assignment convertDtoToEntity(AssignmentDTO assignmentDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Assignment assignment = modelMapper.map(assignmentDTO, Assignment.class);
        return assignment;
    }

    public List<AssignmentDTO> convertEntitiesToDtos(List<Assignment> assignments) {
        ModelMapper modelMapper = new ModelMapper();
        List<AssignmentDTO> assignmentDTOs = Arrays.asList(modelMapper.map(assignments, AssignmentDTO[].class));

        return assignmentDTOs;
    }
}