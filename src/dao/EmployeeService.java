package dao;

import entity.Employee;
import exception.EmployeeNotFoundException;
import exception.DatabaseConnectionException;
import util.DBConnUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService implements IEmployeeService {

    @Override
    public Employee GetEmployeeById(int employeeId) throws EmployeeNotFoundException {
        Connection conn = null;
        Employee employee = null;
        try {
            conn = DBConnUtil.getConnection();
            String sql = "SELECT * FROM Employee WHERE EmployeeID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, employeeId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                employee = mapResultSetToEmployee(rs);
            } else {
                throw new EmployeeNotFoundException("Employee with ID " + employeeId + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseConnectionException("Error connecting to database.");
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return employee;
    }

    @Override
    public List<Employee> GetAllEmployees() {
        Connection conn = null;
        List<Employee> employees = new ArrayList<>();
        try {
            conn = DBConnUtil.getConnection();
            String sql = "SELECT * FROM Employee";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Employee employee = mapResultSetToEmployee(rs);
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseConnectionException("Error connecting to database.");
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return employees;
    }

    @Override
    public void AddEmployee(Employee employee) {
        Connection conn = null;
        try {
            conn = DBConnUtil.getConnection();
            String sql = "INSERT INTO Employee (FirstName, LastName, DateOfBirth, Gender, Email, PhoneNumber, Address, Position, JoiningDate, TerminationDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, employee.getFirstName());
            pstmt.setString(2, employee.getLastName());
            pstmt.setDate(3, Date.valueOf(employee.getDateOfBirth()));
            pstmt.setString(4, employee.getGender());
            pstmt.setString(5, employee.getEmail());
            pstmt.setString(6, employee.getPhoneNumber());
            pstmt.setString(7, employee.getAddress());
            pstmt.setString(8, employee.getPosition());
            pstmt.setDate(9, Date.valueOf(employee.getJoiningDate()));
            if (employee.getTerminationDate() != null) {
                pstmt.setDate(10, Date.valueOf(employee.getTerminationDate()));
            } else {
                pstmt.setNull(10, Types.DATE);
            }
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating employee failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    employee.setEmployeeID(generatedId);
                } else {
                    throw new SQLException("Creating employee failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseConnectionException("Error connecting to database.");
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void UpdateEmployee(Employee employee) throws EmployeeNotFoundException {
        Connection conn = null;
        try {
            conn = DBConnUtil.getConnection();
            String sql = "UPDATE Employee SET FirstName=?, LastName=?, DateOfBirth=?, Gender=?, Email=?, PhoneNumber=?, Address=?, Position=?, JoiningDate=?, TerminationDate=? WHERE EmployeeID=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, employee.getFirstName());
            pstmt.setString(2, employee.getLastName());
            pstmt.setDate(3, Date.valueOf(employee.getDateOfBirth()));
            pstmt.setString(4, employee.getGender());
            pstmt.setString(5, employee.getEmail());
            pstmt.setString(6, employee.getPhoneNumber());
            pstmt.setString(7, employee.getAddress());
            pstmt.setString(8, employee.getPosition());
            pstmt.setDate(9, Date.valueOf(employee.getJoiningDate()));
            if (employee.getTerminationDate() != null) {
                pstmt.setDate(10, Date.valueOf(employee.getTerminationDate()));
            } else {
                pstmt.setNull(10, Types.DATE);
            }
            pstmt.setInt(11, employee.getEmployeeID());
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new EmployeeNotFoundException("Employee with ID " + employee.getEmployeeID() + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseConnectionException("Error updating employee in database.");
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void RemoveEmployee(int employeeId) throws EmployeeNotFoundException {
        Connection conn = null;
        try {
            conn = DBConnUtil.getConnection();
            String sql = "DELETE FROM Employee WHERE EmployeeID=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, employeeId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new EmployeeNotFoundException("Employee with ID " + employeeId + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseConnectionException("Error deleting employee from database.");
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private Employee mapResultSetToEmployee(ResultSet rs) throws SQLException {
        Employee employee = new Employee();
        employee.setEmployeeID(rs.getInt("EmployeeID"));
        employee.setFirstName(rs.getString("FirstName"));
        employee.setLastName(rs.getString("LastName"));
        employee.setDateOfBirth(rs.getDate("DateOfBirth").toLocalDate());
        employee.setGender(rs.getString("Gender"));
        employee.setEmail(rs.getString("Email"));
        employee.setPhoneNumber(rs.getString("PhoneNumber"));
        employee.setAddress(rs.getString("Address"));
        employee.setPosition(rs.getString("Position"));
        employee.setJoiningDate(rs.getDate("JoiningDate").toLocalDate());
        Date termDate = rs.getDate("TerminationDate");
        if (termDate != null) {
            employee.setTerminationDate(termDate.toLocalDate());
        }
        return employee;
    }
}