package entity;

import java.time.LocalDate;

public class FinancialRecord {
    private int RecordID;
    private int EmployeeID;
    private LocalDate RecordDate;
    private String Description;
    private double Amount;
    private String RecordType;

    public FinancialRecord() {
    }

    public FinancialRecord(int recordID, int employeeID, LocalDate recordDate, String description, double amount, String recordType) {
        RecordID = recordID;
        EmployeeID = employeeID;
        RecordDate = recordDate;
        Description = description;
        Amount = amount;
        RecordType = recordType;
    }

    // Getters and Setters
    public int getRecordID() { return RecordID; }
    public void setRecordID(int recordID) { RecordID = recordID; }

    public int getEmployeeID() { return EmployeeID; }
    public void setEmployeeID(int employeeID) { EmployeeID = employeeID; }

    public LocalDate getRecordDate() { return RecordDate; }
    public void setRecordDate(LocalDate recordDate) { RecordDate = recordDate; }

    public String getDescription() { return Description; }
    public void setDescription(String description) { Description = description; }

    public double getAmount() { return Amount; }
    public void setAmount(double amount) { Amount = amount; }

    public String getRecordType() { return RecordType; }
    public void setRecordType(String recordType) { RecordType = recordType; }

    @Override
    public String toString() {
        return "RecordID: " + RecordID +
                ", EmployeeID: " + EmployeeID +
                ", Amount: " + Amount +
                ", Type: " + RecordType;
    }
}
