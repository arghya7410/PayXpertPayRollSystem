CREATE DATABASE PayXpert;
USE PayXpert;

-- Employee Table
CREATE TABLE Employee (
    EmployeeID INT PRIMARY KEY AUTO_INCREMENT,
    FirstName VARCHAR(50),
    LastName VARCHAR(50),
    DateOfBirth DATE,
    Gender VARCHAR(10),
    Email VARCHAR(100),
    PhoneNumber VARCHAR(20),
    Address VARCHAR(200),
    Position VARCHAR(50),
    JoiningDate DATE,
    TerminationDate DATE
);

-- Payroll Table
CREATE TABLE Payroll (
    PayrollID INT PRIMARY KEY AUTO_INCREMENT,
    EmployeeID INT,
    PayPeriodStartDate DATE,
    PayPeriodEndDate DATE,
    BasicSalary DOUBLE,
    OvertimePay DOUBLE,
    Deductions DOUBLE,
    NetSalary DOUBLE,
    FOREIGN KEY (EmployeeID) REFERENCES Employee(EmployeeID) ON DELETE CASCADE
);

-- Tax Table
CREATE TABLE Tax (
    TaxID INT PRIMARY KEY AUTO_INCREMENT,
    EmployeeID INT,
    TaxYear INT,
    TaxableIncome DOUBLE,
    TaxAmount DOUBLE,
    FOREIGN KEY (EmployeeID) REFERENCES Employee(EmployeeID) ON DELETE CASCADE
);

-- FinancialRecord Table
CREATE TABLE FinancialRecord (
    RecordID INT PRIMARY KEY AUTO_INCREMENT,
    EmployeeID INT,
    RecordDate DATE,
    Description VARCHAR(200),
    Amount DOUBLE,
    RecordType VARCHAR(50),
    FOREIGN KEY (EmployeeID) REFERENCES Employee(EmployeeID) ON DELETE CASCADE
);

SELECT * FROM Employee;
SELECT * FROM Payroll;
SELECT * FROM Tax;
SELECT * FROM FinancialRecord;
