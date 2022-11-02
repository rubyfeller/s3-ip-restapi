package com.rfeller.restapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter

public class AssignmentExecutorPOJO {
    private String executor;
    private Date executionDateTime;
    private String executionPrice;

}
