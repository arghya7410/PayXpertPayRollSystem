package test;

import dao.EmployeeService;
import dao.IEmployeeService;
import dao.IPayrollService;
import dao.PayrollService;
import entity.Employee;
import entity.Payroll;
import exception.EmployeeNotFoundException;
import exception.PayrollGenerationException;
import exception.PayrollNotFoundException;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class PayrollServiceTest {
    private static IPayrollService payrollService;
    private static IEmployeeService employeeService;
    private int employeeId;

    @BeforeAll
    public static void setup() {
        payrollService = new PayrollService();
        employeeService = new EmployeeService();
    }

    @BeforeEach
    public void createEmployee() {
        Employee employee = new Employee();
        employee.setFirstName("PayrollTest");
        employee.setLastName("User");
        employee.setDateOfBirth(LocalDate.of(1990, 1, 1));
        employee.setGender("Male");
        employee.setEmail("payroll.test.user@example.com");
        employee.setPhoneNumber("0987654321");
        employee.setAddress("456 Payroll St");
        employee.setPosition("Tester");
        employee.setJoiningDate(LocalDate.now());
        employee.setTerminationDate(null);

        employeeService.AddEmployee(employee);
        employeeId = employee.getEmployeeID();
    }

    @AfterEach
    public void deleteEmployee() {
        try {
            employeeService.RemoveEmployee(employeeId);
        } catch (EmployeeNotFoundException e) {
            // Handle exception if the employee was not found
            System.err.println("Failed to delete employee: " + e.getMessage());
        }
    }

    @Test
    public void testGeneratePayroll() {
        try {
            Payroll payroll = payrollService.GeneratePayroll(employeeId, LocalDate.now().minusDays(15), LocalDate.now());
            assertNotNull(payroll);
            assertTrue(payroll.getNetSalary() > 0);
        } catch (PayrollGenerationException e) {
            fail("Payroll generation failed: " + e.getMessage());
        }
    }

    @Test
    public void testGetPayrollByIdNotFound() {
        Exception exception = assertThrows(PayrollNotFoundException.class, () -> {
            payrollService.GetPayrollById(-1);
        });
        String expectedMessage = "Payroll with ID -1 not found.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}