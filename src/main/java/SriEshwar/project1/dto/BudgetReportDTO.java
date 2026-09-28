package SriEshwar.project1.dto;

public class BudgetReportDTO {
    private String fleetDivision;
    private Double allocatedBudget;
    private Double actualExpense;
    private Double variance;

    public BudgetReportDTO() {}

    public BudgetReportDTO(String fleetDivision, Double allocatedBudget, Double actualExpense, Double variance) {
        this.fleetDivision = fleetDivision;
        this.allocatedBudget = allocatedBudget;
        this.actualExpense = actualExpense;
        this.variance = variance;
    }

    public String getFleetDivision() { return fleetDivision; }
    public void setFleetDivision(String fleetDivision) { this.fleetDivision = fleetDivision; }

    public Double getAllocatedBudget() { return allocatedBudget; }
    public void setAllocatedBudget(Double allocatedBudget) { this.allocatedBudget = allocatedBudget; }

    public Double getActualExpense() { return actualExpense; }
    public void setActualExpense(Double actualExpense) { this.actualExpense = actualExpense; }

    public Double getVariance() { return variance; }
    public void setVariance(Double variance) { this.variance = variance; }
}
