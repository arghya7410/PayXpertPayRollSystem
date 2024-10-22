package dao;

import entity.Tax;
import exception.TaxCalculationException;
import exception.TaxNotFoundException;

import java.util.List;

public interface ITaxService {
    Tax CalculateTax(int employeeId, int taxYear) throws TaxCalculationException;
    Tax GetTaxById(int taxId) throws TaxNotFoundException;
    List<Tax> GetTaxesForEmployee(int employeeId);
    List<Tax> GetTaxesForYear(int taxYear);
}
