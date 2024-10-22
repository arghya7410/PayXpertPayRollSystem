package dao;

import entity.Tax;
import exception.TaxCalculationException;
import exception.TaxNotFoundException;
import exception.DatabaseConnectionException;
import util.DBConnUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaxService implements ITaxService {

    @Override
    public Tax CalculateTax(int employeeId, int taxYear) throws TaxCalculationException {
        // For simplicity, let's assume TaxableIncome is fixed and TaxAmount is calculated at 10% rate
        double taxableIncome = 60000.0;
        double taxAmount = taxableIncome * 0.10;

        Tax tax = new Tax();
        tax.setEmployeeID(employeeId);
        tax.setTaxYear(taxYear);
        tax.setTaxableIncome(taxableIncome);
        tax.setTaxAmount(taxAmount);

        Connection conn = null;
        try {
            conn = DBConnUtil.getConnection();
            String sql = "INSERT INTO Tax (EmployeeID, TaxYear, TaxableIncome, TaxAmount) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, tax.getEmployeeID());
            pstmt.setInt(2, tax.getTaxYear());
            pstmt.setDouble(3, tax.getTaxableIncome());
            pstmt.setDouble(4, tax.getTaxAmount());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new TaxCalculationException("Failed to calculate tax for Employee ID: " + employeeId);
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    tax.setTaxID(generatedKeys.getInt(1));
                } else {
                    throw new TaxCalculationException("Failed to retrieve generated Tax ID.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseConnectionException("Error connecting to database.");
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }

        return tax;
    }

    @Override
    public Tax GetTaxById(int taxId) throws TaxNotFoundException {
        Connection conn = null;
        Tax tax = null;
        try {
            conn = DBConnUtil.getConnection();
            String sql = "SELECT * FROM Tax WHERE TaxID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, taxId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                tax = mapResultSetToTax(rs);
            } else {
                throw new TaxNotFoundException("Tax with ID " + taxId + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseConnectionException("Error connecting to database.");
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return tax;
    }

    @Override
    public List<Tax> GetTaxesForEmployee(int employeeId) {
        Connection conn = null;
        List<Tax> taxes = new ArrayList<>();
        try {
            conn = DBConnUtil.getConnection();
            String sql = "SELECT * FROM Tax WHERE EmployeeID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, employeeId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Tax tax = mapResultSetToTax(rs);
                taxes.add(tax);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseConnectionException("Error connecting to database.");
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return taxes;
    }

    @Override
    public List<Tax> GetTaxesForYear(int taxYear) {
        Connection conn = null;
        List<Tax> taxes = new ArrayList<>();
        try {
            conn = DBConnUtil.getConnection();
            String sql = "SELECT * FROM Tax WHERE TaxYear = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, taxYear);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Tax tax = mapResultSetToTax(rs);
                taxes.add(tax);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseConnectionException("Error connecting to database.");
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return taxes;
    }

    private Tax mapResultSetToTax(ResultSet rs) throws SQLException {
        Tax tax = new Tax();
        tax.setTaxID(rs.getInt("TaxID"));
        tax.setEmployeeID(rs.getInt("EmployeeID"));
        tax.setTaxYear(rs.getInt("TaxYear"));
        tax.setTaxableIncome(rs.getDouble("TaxableIncome"));
        tax.setTaxAmount(rs.getDouble("TaxAmount"));
        return tax;
    }
}
