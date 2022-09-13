package com.rfeller.restapi.presentation;

import com.rfeller.restapi.dto.AssignmentDTO;
import com.rfeller.restapi.logic.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/assignment")
public class AssignmentController {
    @Autowired
    private AssignmentService assignmentService;

    @PostMapping(path = "/add")
    public ResponseEntity<String> addAssignment(@Valid @RequestBody AssignmentDTO assignmentDTO) {
        assignmentService.addAssignment(assignmentDTO);
        return ResponseEntity.ok("Assignment valid");
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<AssignmentDTO> getAllAssignments() {
        return assignmentService.getAll();
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody
    AssignmentDTO getAssignmentById(@PathVariable Integer id) {
        return assignmentService.getById(id);
    }

    @PutMapping(path = "/update/{id}")
    public @ResponseBody
    AssignmentDTO updateAssignment(@PathVariable Integer id, @RequestBody AssignmentDTO assignmentDTO) {
        return assignmentService.update(id, assignmentDTO);
    }

    @DeleteMapping(path = "/delete/{id}")
    public @ResponseBody
    String deleteAssignment(@PathVariable Integer id) {
        return assignmentService.delete(id);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}