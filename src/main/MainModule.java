package main;

// Import statements
import dao.*;
import entity.*;
import exception.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MainModule {
    public static void main(String[] args) {
        IEmployeeService employeeService = new EmployeeService();
        IPayrollService payrollService = new PayrollService();
        ITaxService taxService = new TaxService();
        IFinancialRecordService financialRecordService = new FinancialRecordService();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== PayXpert Payroll Management System ===");
            System.out.println("1. Add Employee");
            System.out.println("2. View Employee By ID");
            System.out.println("3. View All Employees");
            System.out.println("4. Update Employee");
            System.out.println("5. Remove Employee");
            System.out.println("6. Generate Payroll");
            System.out.println("7. Calculate Tax");
            System.out.println("8. Add Financial Record");
            System.out.println("9. Exit");
            System.out.print("Enter option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            switch (option) {
                case 1:
                    // Add Employee
                    Employee newEmployee = new Employee();
                    System.out.print("Enter First Name: ");
                    newEmployee.setFirstName(scanner.nextLine());
                    System.out.print("Enter Last Name: ");
                    newEmployee.setLastName(scanner.nextLine());
                    System.out.print("Enter Date of Birth (YYYY-MM-DD): ");
                    newEmployee.setDateOfBirth(LocalDate.parse(scanner.nextLine()));
                    System.out.print("Enter Gender: ");
                    newEmployee.setGender(scanner.nextLine());
                    System.out.print("Enter Email: ");
                    newEmployee.setEmail(scanner.nextLine());
                    System.out.print("Enter Phone Number: ");
                    newEmployee.setPhoneNumber(scanner.nextLine());
                    System.out.print("Enter Address: ");
                    newEmployee.setAddress(scanner.nextLine());
                    System.out.print("Enter Position: ");
                    newEmployee.setPosition(scanner.nextLine());
                    System.out.print("Enter Joining Date (YYYY-MM-DD): ");
                    newEmployee.setJoiningDate(LocalDate.parse(scanner.nextLine()));
                    newEmployee.setTerminationDate(null); // Assuming new employee
                    employeeService.AddEmployee(newEmployee);
                    System.out.println("Employee added successfully.");
                    break;
                case 2:
                    // View Employee By ID
                    System.out.print("Enter Employee ID: ");
                    int employeeId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    try {
                        Employee employee = employeeService.GetEmployeeById(employeeId);
                        System.out.println("Employee Details:");
                        System.out.println(employee);
                    } catch (EmployeeNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    // View All Employees
                    List<Employee> employees = employeeService.GetAllEmployees();
                    System.out.println("All Employees:");
                    for (Employee emp : employees) {
                        System.out.println(emp);
                    }
                    break;
                case 4:
                    // Update Employee
                    System.out.print("Enter Employee ID to update: ");
                    int empIdToUpdate = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    try {
                        Employee employeeToUpdate = employeeService.GetEmployeeById(empIdToUpdate);
                        System.out.print("Enter New First Name: ");
                        employeeToUpdate.setFirstName(scanner.nextLine());
                        // Update other fields similarly
                        employeeService.UpdateEmployee(employeeToUpdate);
                        System.out.println("Employee updated successfully.");
                    } catch (EmployeeNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 5:
                    // Remove Employee
                    System.out.print("Enter Employee ID to remove: ");
                    int empIdToRemove = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    try {
                        employeeService.RemoveEmployee(empIdToRemove);
                        System.out.println("Employee removed successfully.");
                    } catch (EmployeeNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 6:
                    // Generate Payroll
                    System.out.print("Enter Employee ID: ");
                    int empIdForPayroll = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Pay Period Start Date (YYYY-MM-DD): ");
                    LocalDate startDate = LocalDate.parse(scanner.nextLine());
                    System.out.print("Enter Pay Period End Date (YYYY-MM-DD): ");
                    LocalDate endDate = LocalDate.parse(scanner.nextLine());
                    try {
                        Payroll payroll = payrollService.GeneratePayroll(empIdForPayroll, startDate, endDate);
                        System.out.println("Payroll generated successfully. Payroll ID: " + payroll.getPayrollID());
                    } catch (PayrollGenerationException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 7:
                    // Calculate Tax
                    System.out.print("Enter Employee ID: ");
                    int empIdForTax = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Tax Year: ");
                    int taxYear = scanner.nextInt();
                    scanner.nextLine();
                    try {
                        Tax tax = taxService.CalculateTax(empIdForTax, taxYear);
                        System.out.println("Tax calculated successfully. Tax ID: " + tax.getTaxID());
                    } catch (TaxCalculationException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 8:
                    // Add Financial Record
                    System.out.print("Enter Employee ID: ");
                    int empIdForRecord = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Description: ");
                    String description = scanner.nextLine();
                    System.out.print("Enter Amount: ");
                    double amount = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print("Enter Record Type (Income/Expense): ");
                    String recordType = scanner.nextLine();
                    try {
                        financialRecordService.AddFinancialRecord(empIdForRecord, description, amount, recordType);
                        System.out.println("Financial record added successfully.");
                    } catch (FinancialRecordException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 9:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid Option.");
            }
        }
    }
}
