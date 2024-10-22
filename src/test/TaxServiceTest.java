package test;

import dao.EmployeeService;
import dao.IEmployeeService;
import dao.ITaxService;
import dao.TaxService;
import entity.Employee;
import entity.Tax;
import exception.EmployeeNotFoundException;
import exception.TaxCalculationException;
import exception.TaxNotFoundException;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class TaxServiceTest {
    private static ITaxService taxService;
    private static IEmployeeService employeeService;
    private int employeeId;

    @BeforeAll
    public static void setup() {
        taxService = new TaxService();
        employeeService = new EmployeeService();
    }

    @BeforeEach
    public void createEmployee() {
        Employee employee = new Employee();
        employee.setFirstName("TaxTest");
        employee.setLastName("User");
        employee.setDateOfBirth(LocalDate.of(1990, 1, 1));
        employee.setGender("Female");
        employee.setEmail("tax.test.user@example.com");
        employee.setPhoneNumber("1122334455");
        employee.setAddress("789 Tax Ave");
        employee.setPosition("Accountant");
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
    public void testCalculateTax() {
        try {
            Tax tax = taxService.CalculateTax(employeeId, 2023);
            assertNotNull(tax);
            assertTrue(tax.getTaxAmount() > 0);
        } catch (TaxCalculationException e) {
            fail("Tax calculation failed: " + e.getMessage());
        }
    }

    @Test
    public void testGetTaxByIdNotFound() {
        Exception exception = assertThrows(TaxNotFoundException.class, () -> {
            taxService.GetTaxById(-1);
        });
        String expectedMessage = "Tax with ID -1 not found.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}