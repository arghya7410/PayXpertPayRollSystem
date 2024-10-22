package test;

import dao.EmployeeService;
import dao.IEmployeeService;
import entity.Employee;
import exception.EmployeeNotFoundException;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeServiceTest {
    private static IEmployeeService employeeService;
    private int employeeId;

    @BeforeAll
    public static void setup() {
        employeeService = new EmployeeService();
    }

    @BeforeEach
    public void init() {
       
    }

    @AfterEach
    public void tearDown() {
        try {
            employeeService.RemoveEmployee(employeeId);
        } catch (EmployeeNotFoundException e) {
            // Handle exception if the employee was not found
            System.err.println("Failed to delete employee: " + e.getMessage());
        }
    }

    @Test
    public void testAddAndGetEmployee() {
        Employee employee = new Employee();
        employee.setFirstName("Test");
        employee.setLastName("User");
        employee.setDateOfBirth(LocalDate.of(1990, 1, 1));
        employee.setGender("Male");
        employee.setEmail("test.user@example.com");
        employee.setPhoneNumber("1234567890");
        employee.setAddress("123 Test St");
        employee.setPosition("Developer");
        employee.setJoiningDate(LocalDate.now());
        employee.setTerminationDate(null);

        employeeService.AddEmployee(employee);
        employeeId = employee.getEmployeeID(); 

        // Optionally, retrieve the employee by ID to confirm insertion
        try {
            Employee retrievedEmployee = employeeService.GetEmployeeById(employeeId);
            assertEquals("Test", retrievedEmployee.getFirstName());
            assertEquals("User", retrievedEmployee.getLastName());
        } catch (EmployeeNotFoundException e) {
            fail("Employee not found after insertion: " + e.getMessage());
        }
    }

    @Test
    public void testGetEmployeeByIdNotFound() {
        Exception exception = assertThrows(EmployeeNotFoundException.class, () -> {
            employeeService.GetEmployeeById(-1);
        });
        String expectedMessage = "Employee with ID -1 not found.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}