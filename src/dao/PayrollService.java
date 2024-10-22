package dao;

import entity.Payroll;
import exception.PayrollGenerationException;
import exception.PayrollNotFoundException;
import exception.DatabaseConnectionException;
import util.DBConnUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PayrollService implements IPayrollService {

    @Override
    public Payroll GeneratePayroll(int employeeId, LocalDate startDate, LocalDate endDate) throws PayrollGenerationException {
        // Logic to calculate payroll details
        double basicSalary = 5000.0;
        double overtimePay = 200.0;
        double deductions = 500.0;
        double netSalary = basicSalary + overtimePay - deductions;

        Payroll payroll = new Payroll();
        payroll.setEmployeeID(employeeId);
        payroll.setPayPeriodStartDate(startDate);
        payroll.setPayPeriodEndDate(endDate);
        payroll.setBasicSalary(basicSalary);
        payroll.setOvertimePay(overtimePay);
        payroll.setDeductions(deductions);
        payroll.setNetSalary(netSalary);

        Connection conn = null;
        try {
            conn = DBConnUtil.getConnection();
            String sql = "INSERT INTO Payroll (EmployeeID, PayPeriodStartDate, PayPeriodEndDate, BasicSalary, OvertimePay, Deductions, NetSalary) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, payroll.getEmployeeID());
            pstmt.setDate(2, Date.valueOf(payroll.getPayPeriodStartDate()));
            pstmt.setDate(3, Date.valueOf(payroll.getPayPeriodEndDate()));
            pstmt.setDouble(4, payroll.getBasicSalary());
            pstmt.setDouble(5, payroll.getOvertimePay());
            pstmt.setDouble(6, payroll.getDeductions());
            pstmt.setDouble(7, payroll.getNetSalary());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new PayrollGenerationException("Failed to generate payroll for Employee ID: " + employeeId);
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    payroll.setPayrollID(generatedKeys.getInt(1));
                } else {
                    throw new PayrollGenerationException("Failed to retrieve generated Payroll ID.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseConnectionException("Error connecting to database.");
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }

        return payroll;
    }

    @Override
    public Payroll GetPayrollById(int payrollId) throws PayrollNotFoundException {
        Connection conn = null;
        Payroll payroll = null;
        try {
            conn = DBConnUtil.getConnection();
            String sql = "SELECT * FROM Payroll WHERE PayrollID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, payrollId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                payroll = mapResultSetToPayroll(rs);
            } else {
                throw new PayrollNotFoundException("Payroll with ID " + payrollId + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseConnectionException("Error connecting to database.");
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return payroll;
    }

    @Override
    public List<Payroll> GetPayrollsForEmployee(int employeeId) {
        Connection conn = null;
        List<Payroll> payrolls = new ArrayList<>();
        try {
            conn = DBConnUtil.getConnection();
            String sql = "SELECT * FROM Payroll WHERE EmployeeID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, employeeId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Payroll payroll = mapResultSetToPayroll(rs);
                payrolls.add(payroll);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseConnectionException("Error connecting to database.");
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return payrolls;
    }

    @Override
    public List<Payroll> GetPayrollsForPeriod(LocalDate startDate, LocalDate endDate) {
        Connection conn = null;
        List<Payroll> payrolls = new ArrayList<>();
        try {
            conn = DBConnUtil.getConnection();
            String sql = "SELECT * FROM Payroll WHERE PayPeriodStartDate >= ? AND PayPeriodEndDate <= ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, Date.valueOf(startDate));
            pstmt.setDate(2, Date.valueOf(endDate));
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Payroll payroll = mapResultSetToPayroll(rs);
                payrolls.add(payroll);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseConnectionException("Error connecting to database.");
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return payrolls;
    }

    private Payroll mapResultSetToPayroll(ResultSet rs) throws SQLException {
        Payroll payroll = new Payroll();
        payroll.setPayrollID(rs.getInt("PayrollID"));
        payroll.setEmployeeID(rs.getInt("EmployeeID"));
        payroll.setPayPeriodStartDate(rs.getDate("PayPeriodStartDate").toLocalDate());
        payroll.setPayPeriodEndDate(rs.getDate("PayPeriodEndDate").toLocalDate());
        payroll.setBasicSalary(rs.getDouble("BasicSalary"));
        payroll.setOvertimePay(rs.getDouble("OvertimePay"));
        payroll.setDeductions(rs.getDouble("Deductions"));
        payroll.setNetSalary(rs.getDouble("NetSalary"));
        return payroll;
    }
}
