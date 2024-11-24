package dev.manas.EmployeeService.dto;

import lombok.Data;

@Data
public class EmployeeResponse {
    private long id;
    private String name;
    private double sal;
    private String dept;


}
