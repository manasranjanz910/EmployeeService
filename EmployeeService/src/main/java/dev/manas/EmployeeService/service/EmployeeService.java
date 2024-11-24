package dev.manas.EmployeeService.service;

import dev.manas.EmployeeService.dto.EmployeeRequest;
import dev.manas.EmployeeService.dto.EmployeeResponse;
import dev.manas.EmployeeService.exceptions.EmployeeNotFoundException;
import dev.manas.EmployeeService.model.Employee;
import dev.manas.EmployeeService.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService  implements EmployeeServiceImpl {
    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public String addEmployee(EmployeeRequest request) {
        if (request.getName() == null || request.getName().isEmpty()) {
            throw new IllegalArgumentException("Employee name cannot be null or empty");
        }
        if (request.getSal() < 0) {
            throw new IllegalArgumentException("Employee salary cannot be negative");
        }
        if (request.getDept() == null || request.getDept().isEmpty()) {
            throw new IllegalArgumentException("Employee department cannot be null or empty");
        }

        Employee employee = new Employee();
        employee.setName(request.getName());
        employee.setSal(request.getSal());
        employee.setDept(request.getDept());

        employeeRepository.save(employee);
        return "Employee added";
    }

    @Override
    public EmployeeResponse getEmployeeById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid employee ID");
        }
        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee Not Found For The Id " + id));
        EmployeeResponse response = new EmployeeResponse();
        response.setId(emp.getId());
        response.setName(emp.getName());
        response.setSal(emp.getSal());
        response.setDept(emp.getDept());
        return response;
    }

    @Override
    public List<EmployeeResponse> getAllEmployees() {
        List<Employee> employeeList = employeeRepository.findAll();
        List<EmployeeResponse> responseList = employeeList.stream().map(emp -> {
                    EmployeeResponse response = new EmployeeResponse();
                    response.setId(emp.getId());
                    response.setName(emp.getName());
                    response.setSal(emp.getSal());
                    response.setDept(emp.getDept());
                    return response;

                }
        ).collect(Collectors.toList());
        return responseList;
    }

    public double getTotalSalary() {
        return employeeRepository.findAll().stream()
                .mapToDouble(Employee::getSal)
                .sum();
    }

    @Override
    public String updateEmployee(long id, EmployeeRequest employeeRequest){
        if (employeeRequest.getName() == null || employeeRequest.getName().isEmpty()) {
            throw new IllegalArgumentException("Employee name cannot be null or empty");
        }
        if (employeeRequest.getSal() < 0) {
            throw new IllegalArgumentException("Employee salary cannot be negative");
        }
        if (employeeRequest.getDept() == null || employeeRequest.getDept().isEmpty()) {
            throw new IllegalArgumentException("Employee department cannot be null or empty");
        }
        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee Not Found For The Id " + id));
        emp.setName(employeeRequest.getName());
        emp.setSal(employeeRequest.getSal());
        emp.setDept(employeeRequest.getDept());
        employeeRepository.save(emp);
        return "Employee updated";
    }

    //List Of Employees With Salary Between 40000 and 50000
    public List<EmployeeResponse> getEmployeesWithSalaryBetween(double minSalary, double maxSalary) {
        List<Employee> employeeList = employeeRepository.findAll();
        return employeeList.stream()
                .filter(emp -> emp.getSal() >= minSalary && emp.getSal() <= maxSalary)
                .map(emp -> {
                    EmployeeResponse response = new EmployeeResponse();
                    response.setId(emp.getId());
                    response.setName(emp.getName());
                    response.setSal(emp.getSal());
                    response.setDept(emp.getDept());
                    return response;
                })
                .collect(Collectors.toList());
    }

    @Override
    public String deleteEmployee(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid employee ID");
        }
        employeeRepository.deleteById(id);
        return "Employee Deleted";
    }
}
