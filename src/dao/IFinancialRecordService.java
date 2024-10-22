package dao;

import entity.FinancialRecord;
import exception.FinancialRecordException;
import exception.FinancialRecordNotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface IFinancialRecordService {
    void AddFinancialRecord(int employeeId, String description, double amount, String recordType) throws FinancialRecordException;
    FinancialRecord GetFinancialRecordById(int recordId) throws FinancialRecordNotFoundException;
    List<FinancialRecord> GetFinancialRecordsForEmployee(int employeeId);
    List<FinancialRecord> GetFinancialRecordsForDate(LocalDate recordDate);
}
