package entity;

import java.time.LocalDate;

public class Payroll {
    private int PayrollID;
    private int EmployeeID;
    private LocalDate PayPeriodStartDate;
    private LocalDate PayPeriodEndDate;
    private double BasicSalary;
    private double OvertimePay;
    private double Deductions;
    private double NetSalary;

    public Payroll() {
    }

    public Payroll(int payrollID, int employeeID, LocalDate payPeriodStartDate, LocalDate payPeriodEndDate,
                   double basicSalary, double overtimePay, double deductions, double netSalary) {
        PayrollID = payrollID;
        EmployeeID = employeeID;
        PayPeriodStartDate = payPeriodStartDate;
        PayPeriodEndDate = payPeriodEndDate;
        BasicSalary = basicSalary;
        OvertimePay = overtimePay;
        Deductions = deductions;
        NetSalary = netSalary;
    }

    // Getters and Setters
    public int getPayrollID() { return PayrollID; }
    public void setPayrollID(int payrollID) { PayrollID = payrollID; }

    public int getEmployeeID() { return EmployeeID; }
    public void setEmployeeID(int employeeID) { EmployeeID = employeeID; }

    public LocalDate getPayPeriodStartDate() { return PayPeriodStartDate; }
    public void setPayPeriodStartDate(LocalDate payPeriodStartDate) { PayPeriodStartDate = payPeriodStartDate; }

    public LocalDate getPayPeriodEndDate() { return PayPeriodEndDate; }
    public void setPayPeriodEndDate(LocalDate payPeriodEndDate) { PayPeriodEndDate = payPeriodEndDate; }

    public double getBasicSalary() { return BasicSalary; }
    public void setBasicSalary(double basicSalary) { BasicSalary = basicSalary; }

    public double getOvertimePay() { return OvertimePay; }
    public void setOvertimePay(double overtimePay) { OvertimePay = overtimePay; }

    public double getDeductions() { return Deductions; }
    public void setDeductions(double deductions) { Deductions = deductions; }

    public double getNetSalary() { return NetSalary; }
    public void setNetSalary(double netSalary) { NetSalary = netSalary; }

    @Override
    public String toString() {
        return "PayrollID: " + PayrollID +
                ", EmployeeID: " + EmployeeID +
                ", NetSalary: " + NetSalary;
    }
}
