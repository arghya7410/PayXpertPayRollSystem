package dao;

import entity.Employee;
import exception.EmployeeNotFoundException;

import java.util.List;

public interface IEmployeeService {
    Employee GetEmployeeById(int employeeId) throws EmployeeNotFoundException;
    List<Employee> GetAllEmployees();
    void AddEmployee(Employee employee);
    void UpdateEmployee(Employee employee) throws EmployeeNotFoundException;
    void RemoveEmployee(int employeeId) throws EmployeeNotFoundException;
}
