package dao;

import entity.Payroll;
import exception.PayrollGenerationException;
import exception.PayrollNotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface IPayrollService {
    Payroll GeneratePayroll(int employeeId, LocalDate startDate, LocalDate endDate) throws PayrollGenerationException;
    Payroll GetPayrollById(int payrollId) throws PayrollNotFoundException;
    List<Payroll> GetPayrollsForEmployee(int employeeId);
    List<Payroll> GetPayrollsForPeriod(LocalDate startDate, LocalDate endDate);
}
