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
        return modelMapper.map(assignment, AssignmentDTO.class);
    }

    public Assignment convertDtoToEntity(AssignmentDTO assignmentDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(assignmentDTO, Assignment.class);
    }

    public List<AssignmentDTO> convertEntitiesToDtos(List<Assignment> assignments) {
        ModelMapper modelMapper = new ModelMapper();

        return Arrays.asList(modelMapper.map(assignments, AssignmentDTO[].class));
    }
}