package com.rfeller.restapi.dal;

import com.rfeller.restapi.dal.models.Assignment;
import org.springframework.data.repository.CrudRepository;

import javax.validation.constraints.NotNull;
import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface AssignmentRespository extends CrudRepository<Assignment, Integer> {
    List<Assignment> findAllByUserId(@NotNull(message = "No userId given") String userId);

}

