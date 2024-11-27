package dev.manas.EmployeeService.service;


import dev.manas.EmployeeService.dto.EmployeeRequest;
import dev.manas.EmployeeService.dto.EmployeeResponse;

import java.util.List;

public interface EmployeeServiceImpl {

    public String addEmployee(EmployeeRequest employeeRequest );
    public EmployeeResponse getEmployeeById(long id);
    public List<EmployeeResponse> getAllEmployees();
    public double getTotalSalary();
    public String updateEmployee(long id, EmployeeRequest employeeRequest);
    public List<EmployeeResponse> getEmployeesWithSalaryBetween(double minSalary, double maxSalary) ;
    public String deleteEmployee(long id);
    public String deleteAllEmployees();

}
