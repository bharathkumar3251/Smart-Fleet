package SriEshwar.project1.dto;

import java.util.Map;

public class ProfitLossDTO {
    private Map<String, Double> income;
    private Map<String, Double> expenses;
    private Double totalIncome;
    private Double totalExpenses;
    private Double netProfit;

    public ProfitLossDTO() {}

    public ProfitLossDTO(Map<String, Double> income, Map<String, Double> expenses, Double totalIncome, Double totalExpenses, Double netProfit) {
        this.income = income;
        this.expenses = expenses;
        this.totalIncome = totalIncome;
        this.totalExpenses = totalExpenses;
        this.netProfit = netProfit;
    }

    public Map<String, Double> getIncome() { return income; }
    public void setIncome(Map<String, Double> income) { this.income = income; }

    public Map<String, Double> getExpenses() { return expenses; }
    public void setExpenses(Map<String, Double> expenses) { this.expenses = expenses; }

    public Double getTotalIncome() { return totalIncome; }
    public void setTotalIncome(Double totalIncome) { this.totalIncome = totalIncome; }

    public Double getTotalExpenses() { return totalExpenses; }
    public void setTotalExpenses(Double totalExpenses) { this.totalExpenses = totalExpenses; }

    public Double getNetProfit() { return netProfit; }
    public void setNetProfit(Double netProfit) { this.netProfit = netProfit; }
}
