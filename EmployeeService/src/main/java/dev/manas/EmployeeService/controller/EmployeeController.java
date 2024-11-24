package dev.manas.EmployeeService.controller;

import dev.manas.EmployeeService.dto.EmployeeRequest;
import dev.manas.EmployeeService.dto.EmployeeResponse;
import dev.manas.EmployeeService.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee/api")
public class EmployeeController {
    private EmployeeService employeeService;
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String addEmployee(@RequestBody EmployeeRequest employeeRequest) {
        employeeService.addEmployee(employeeRequest);
        return "Employee added";
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public EmployeeResponse getEmployeeById(@RequestParam long id) {
        return employeeService.getEmployeeById(id);
    }
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeResponse> getAllEmployees()
    {
        return employeeService.getAllEmployees();
    }
    @GetMapping("/total-salary")
    @ResponseStatus(HttpStatus.OK)
    public double getTotalSalary()
    {
        return employeeService.getTotalSalary();
    }
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public String updateEmployee(@RequestParam long id, @RequestBody EmployeeRequest employeeRequest)
    {
        return employeeService.updateEmployee(id, employeeRequest);
    }
    @GetMapping("/salary")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeResponse> getEmployeesWithSalaryBetween(@RequestParam double minSalary, @RequestParam double maxSalary)
    {
        return employeeService.getEmployeesWithSalaryBetween(minSalary, maxSalary);
    }
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public String deleteEmployee(@RequestParam long id)
    {
        return employeeService.deleteEmployee(id);
    }
}
