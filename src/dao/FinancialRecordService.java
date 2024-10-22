package dao;

import entity.FinancialRecord;
import exception.FinancialRecordException;
import exception.FinancialRecordNotFoundException;
import exception.DatabaseConnectionException;
import util.DBConnUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FinancialRecordService implements IFinancialRecordService {

    @Override
    public void AddFinancialRecord(int employeeId, String description, double amount, String recordType) throws FinancialRecordException {
        FinancialRecord record = new FinancialRecord();
        record.setEmployeeID(employeeId);
        record.setRecordDate(LocalDate.now());
        record.setDescription(description);
        record.setAmount(amount);
        record.setRecordType(recordType);

        Connection conn = null;
        try {
            conn = DBConnUtil.getConnection();
            String sql = "INSERT INTO FinancialRecord (EmployeeID, RecordDate, Description, Amount, RecordType) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, record.getEmployeeID());
            pstmt.setDate(2, Date.valueOf(record.getRecordDate()));
            pstmt.setString(3, record.getDescription());
            pstmt.setDouble(4, record.getAmount());
            pstmt.setString(5, record.getRecordType());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new FinancialRecordException("Failed to add financial record for Employee ID: " + employeeId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseConnectionException("Error connecting to database.");
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    @Override
    public FinancialRecord GetFinancialRecordById(int recordId) throws FinancialRecordNotFoundException {
        Connection conn = null;
        FinancialRecord record = null;
        try {
            conn = DBConnUtil.getConnection();
            String sql = "SELECT * FROM FinancialRecord WHERE RecordID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, recordId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                record = mapResultSetToFinancialRecord(rs);
            } else {
                throw new FinancialRecordNotFoundException("Financial Record with ID " + recordId + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseConnectionException("Error connecting to database.");
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return record;
    }

    @Override
    public List<FinancialRecord> GetFinancialRecordsForEmployee(int employeeId) {
        Connection conn = null;
        List<FinancialRecord> records = new ArrayList<>();
        try {
            conn = DBConnUtil.getConnection();
            String sql = "SELECT * FROM FinancialRecord WHERE EmployeeID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, employeeId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                FinancialRecord record = mapResultSetToFinancialRecord(rs);
                records.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseConnectionException("Error connecting to database.");
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return records;
    }

    @Override
    public List<FinancialRecord> GetFinancialRecordsForDate(LocalDate recordDate) {
        Connection conn = null;
        List<FinancialRecord> records = new ArrayList<>();
        try {
            conn = DBConnUtil.getConnection();
            String sql = "SELECT * FROM FinancialRecord WHERE RecordDate = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, Date.valueOf(recordDate));
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                FinancialRecord record = mapResultSetToFinancialRecord(rs);
                records.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseConnectionException("Error connecting to database.");
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return records;
    }

    private FinancialRecord mapResultSetToFinancialRecord(ResultSet rs) throws SQLException {
        FinancialRecord record = new FinancialRecord();
        record.setRecordID(rs.getInt("RecordID"));
        record.setEmployeeID(rs.getInt("EmployeeID"));
        record.setRecordDate(rs.getDate("RecordDate").toLocalDate());
        record.setDescription(rs.getString("Description"));
        record.setAmount(rs.getDouble("Amount"));
        record.setRecordType(rs.getString("RecordType"));
        return record;
    }
}
