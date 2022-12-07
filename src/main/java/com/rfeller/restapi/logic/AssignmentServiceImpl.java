package com.rfeller.restapi.logic;

import com.rfeller.restapi.converter.AssignmentConverter;
import com.rfeller.restapi.dal.AssignmentRepository;
import com.rfeller.restapi.dal.models.Assignment;
import com.rfeller.restapi.dto.AssignmentDTO;
import com.rfeller.restapi.dto.AssignmentExecutorPOJO;
import com.rfeller.restapi.exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentServiceImpl implements AssignmentService {
    @Autowired
    private AssignmentRepository assignmentRespository;

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

        return assignmentConverter.convertEntitiesToDtos(assignments);
    }

    @Override
    public Iterable<AssignmentDTO> getByUserId(String userId) {
        List<Assignment> assignments = assignmentRespository.findAllByUserId(userId);

        return assignmentConverter.convertEntitiesToDtos(assignments);
    }

    @Override
    public AssignmentDTO getById(Integer id) {
        Assignment assignment = assignmentRespository.findById(id).orElseThrow(() -> new ApiException("Assignment with id " + id + " was not found"));

        return assignmentConverter.convertEntityToDto(assignment);
    }

    @Override
    public AssignmentExecutorPOJO acceptAssignment(Integer id, AssignmentExecutorPOJO executor) {
        Assignment assignment = assignmentRespository.findById(id)
                .orElseThrow(() -> new ApiException("Assignment with id " + id + " was not found"));

        assignment.setExecutor(executor.getExecutor());
        assignment.setExecutionPrice(executor.getExecutionPrice());
        assignment.setExecutionDateTime(executor.getExecutionDateTime());
        assignmentRespository.save(assignment);

        return executor;
    }

    @Override
    public boolean delete(Integer id) {
        if (assignmentRespository.existsById(id)) {
            assignmentRespository.deleteById(id);
        } else {
            return false;
        }
        return true;
    }

    @Override
    public AssignmentDTO update(Integer id, AssignmentDTO assignmentDTO) {
        Assignment existingAssignment = assignmentRespository.findById(id).orElseThrow(() -> new ApiException("Assignment with id " + id + " was not found"));
        existingAssignment.setTitle(assignmentDTO.getTitle());
        existingAssignment.setDescription(assignmentDTO.getDescription());

        AssignmentDTO existingAssignmentDTO = assignmentConverter.convertEntityToDto(existingAssignment);

        assignmentRespository.save(existingAssignment);

        return existingAssignmentDTO;
    }
}