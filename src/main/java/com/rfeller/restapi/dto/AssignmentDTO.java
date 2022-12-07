package com.rfeller.restapi.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class AssignmentDTO {
    private Integer id;

    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @NotNull(message = "No userId given")
    private String userId;

    @NotNull(message = "No creator given")
    private String creator;

    private String executor;

    private Date executionDateTime;

    private String executionPrice;
}
