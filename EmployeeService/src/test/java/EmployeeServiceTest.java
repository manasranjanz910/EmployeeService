import dev.manas.EmployeeService.dto.EmployeeRequest;
import dev.manas.EmployeeService.dto.EmployeeResponse;
import dev.manas.EmployeeService.exceptions.EmployeeNotFoundException;
import dev.manas.EmployeeService.model.Employee;
import dev.manas.EmployeeService.repository.EmployeeRepository;
import dev.manas.EmployeeService.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EmployeeServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddEmployee_Success() {
        EmployeeRequest request = new EmployeeRequest("John Doe", 45000, "IT");
        Employee employee = new Employee(1L, "John Doe", 45000, "IT");

        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        String result = employeeService.addEmployee(request);
        assertEquals("Employee added", result);
    }

    @Test
    void testAddEmployee_NullName() {
        EmployeeRequest request = new EmployeeRequest(null, 45000, "IT");

        assertThrows(IllegalArgumentException.class, () -> employeeService.addEmployee(request));
    }

    @Test
    void testAddEmployee_NegativeSalary() {
        EmployeeRequest request = new EmployeeRequest("John Doe", -45000, "IT");

        assertThrows(IllegalArgumentException.class, () -> employeeService.addEmployee(request));
    }

    @Test
    void testAddEmployee_EmptyDepartment() {
        EmployeeRequest request = new EmployeeRequest("John Doe", 45000, "");

        assertThrows(IllegalArgumentException.class, () -> employeeService.addEmployee(request));
    }

    @Test
    void testGetEmployeeById_Success() {
        Employee employee = new Employee(1L, "John Doe", 45000, "IT");

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        EmployeeResponse response = employeeService.getEmployeeById(1L);
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("John Doe", response.getName());
        assertEquals(45000, response.getSal());
        assertEquals("IT", response.getDept());
    }

    @Test
    void testGetEmployeeById_NotFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployeeById(1L));
    }

    @Test
    void testGetAllEmployees() {
        Employee employee1 = new Employee(1L, "John Doe", 45000, "IT");
        Employee employee2 = new Employee(2L, "Jane Doe", 50000, "HR");

        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee1, employee2));

        List<EmployeeResponse> employees = employeeService.getAllEmployees();
        assertEquals(2, employees.size());
    }

    @Test
    void testUpdateEmployee_Success() {
        Employee employee = new Employee(1L, "John Doe", 45000, "IT");
        EmployeeRequest request = new EmployeeRequest("John Smith", 50000, "HR");

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        String result = employeeService.updateEmployee(1L, request);
        assertEquals("Employee updated", result);
    }

    @Test
    void testUpdateEmployee_NotFound() {
        EmployeeRequest request = new EmployeeRequest("John Smith", 50000, "HR");

        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.updateEmployee(1L, request));
    }
    @Test
    void testGetTotalSalary() {
        Employee employee1 = new Employee(1L, "John Doe", 45000, "IT");
        Employee employee2 = new Employee(2L, "Jane Doe", 50000, "HR");

        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee1, employee2));

        double totalSalary = employeeService.getTotalSalary();
        assertEquals(95000, totalSalary);
    }

    @Test
    void testGetEmployeesWithSalaryBetween() {
        Employee employee1 = new Employee(1L, "John Doe", 45000, "IT");
        Employee employee2 = new Employee(2L, "Jane Doe", 50000, "HR");

        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee1, employee2));

        List<EmployeeResponse> employees = employeeService.getEmployeesWithSalaryBetween(40000, 50000);
        assertEquals(2, employees.size());
    }

    @Test
    void testDeleteEmployee_Success() {
        doNothing().when(employeeRepository).deleteById(1L);

        String result = employeeService.deleteEmployee(1L);
        assertEquals("Employee Deleted", result);
    }

    @Test
    void testDeleteEmployee_NotFound() {
        doThrow(new EmployeeNotFoundException("Employee Not Found For The Id 1")).when(employeeRepository).deleteById(1L);

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.deleteEmployee(1L));
    }
    @Test
    void testGetEmployeesWithSalaryBetween_NoEmployeesInRange() {
        Employee employee1 = new Employee(1L, "John Doe", 30000, "IT");
        Employee employee2 = new Employee(2L, "Jane Doe", 60000, "HR");

        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee1, employee2));

        List<EmployeeResponse> employees = employeeService.getEmployeesWithSalaryBetween(40000, 50000);
        assertEquals(0, employees.size());
    }

    @Test
    void testGetTotalSalary_NoEmployees() {
        when(employeeRepository.findAll()).thenReturn(Arrays.asList());

        double totalSalary = employeeService.getTotalSalary();
        assertEquals(0, totalSalary);
    }
    @Test
    void testAddEmployee_EmptyName() {
        EmployeeRequest request = new EmployeeRequest("", 45000, "IT");

        assertThrows(IllegalArgumentException.class, () -> employeeService.addEmployee(request));
    }

    @Test
    void testAddEmployee_NullDepartment() {
        EmployeeRequest request = new EmployeeRequest("John Doe", 45000, null);

        assertThrows(IllegalArgumentException.class, () -> employeeService.addEmployee(request));
    }

    @Test
    void testGetEmployeeById_InvalidId() {
        long invalidId = -1L;

        assertThrows(IllegalArgumentException.class, () -> employeeService.getEmployeeById(invalidId));
    }

    @Test
    void testUpdateEmployee_EmptyName() {
        Employee employee = new Employee(1L, "John Doe", 45000, "IT");
        EmployeeRequest request = new EmployeeRequest("", 50000, "HR");

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        assertThrows(IllegalArgumentException.class, () -> employeeService.updateEmployee(1L, request));
    }
    @Test
    void testDeleteEmployee_InvalidId() {
        long invalidId = -1L;

        assertThrows(IllegalArgumentException.class, () -> employeeService.deleteEmployee(invalidId));
    }
    @Test
    void testDeleteAllEmployees_Success() {
        doNothing().when(employeeRepository).deleteAll();

        String result = employeeService.deleteAllEmployees();
        assertEquals("All Employees Deleted", result);

        verify(employeeRepository).deleteAll();
    }
}





