package entity;

public class Tax {
    private int TaxID;
    private int EmployeeID;
    private int TaxYear;
    private double TaxableIncome;
    private double TaxAmount;

    public Tax() {
    }

    public Tax(int taxID, int employeeID, int taxYear, double taxableIncome, double taxAmount) {
        TaxID = taxID;
        EmployeeID = employeeID;
        TaxYear = taxYear;
        TaxableIncome = taxableIncome;
        TaxAmount = taxAmount;
    }

    // Getters and Setters
    public int getTaxID() { return TaxID; }
    public void setTaxID(int taxID) { TaxID = taxID; }

    public int getEmployeeID() { return EmployeeID; }
    public void setEmployeeID(int employeeID) { EmployeeID = employeeID; }

    public int getTaxYear() { return TaxYear; }
    public void setTaxYear(int taxYear) { TaxYear = taxYear; }

    public double getTaxableIncome() { return TaxableIncome; }
    public void setTaxableIncome(double taxableIncome) { TaxableIncome = taxableIncome; }

    public double getTaxAmount() { return TaxAmount; }
    public void setTaxAmount(double taxAmount) { TaxAmount = taxAmount; }

    @Override
    public String toString() {
        return "TaxID: " + TaxID +
                ", EmployeeID: " + EmployeeID +
                ", TaxAmount: " + TaxAmount;
    }
}
