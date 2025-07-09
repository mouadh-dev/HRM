package entities;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class PayrollRecord {
    private final SimpleIntegerProperty payrollId;
    private final SimpleIntegerProperty employeeId;
    private final SimpleDoubleProperty basicSalary;
    private final SimpleDoubleProperty bonuses;
    private final SimpleDoubleProperty deductions;
    private final SimpleDoubleProperty tax;
    private final SimpleDoubleProperty netSalary;
    private final SimpleStringProperty paymentDate;

    public PayrollRecord(int payrollId, int employeeId, double basicSalary, double bonuses, double deductions, double tax, double netSalary, String paymentDate) {
        this.payrollId = new SimpleIntegerProperty(payrollId);
        this.employeeId = new SimpleIntegerProperty(employeeId);
        this.basicSalary = new SimpleDoubleProperty(basicSalary);
        this.bonuses = new SimpleDoubleProperty(bonuses);
        this.deductions = new SimpleDoubleProperty(deductions);
        this.tax = new SimpleDoubleProperty(tax);
        this.netSalary = new SimpleDoubleProperty(netSalary);
        this.paymentDate = new SimpleStringProperty(paymentDate);
    }

    public int getPayrollId() { return payrollId.get(); }
    public int getEmployeeId() { return employeeId.get(); }
    public double getBasicSalary() { return basicSalary.get(); }
    public double getBonuses() { return bonuses.get(); }
    public double getDeductions() { return deductions.get(); }
    public double getTax() { return tax.get(); }
    public double getNetSalary() { return netSalary.get(); }
    public String getPaymentDate() { return paymentDate.get(); }
}
