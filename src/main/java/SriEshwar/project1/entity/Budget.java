package SriEshwar.project1.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "budgets")
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "analytic_account_id")
    private AnalyticAccount analyticAccount;

    private Double allocatedBudget;
    private String period;

    public Budget() {}

    public Budget(Long id, AnalyticAccount analyticAccount, Double allocatedBudget, String period) {
        this.id = id;
        this.analyticAccount = analyticAccount;
        this.allocatedBudget = allocatedBudget;
        this.period = period;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public AnalyticAccount getAnalyticAccount() { return analyticAccount; }
    public void setAnalyticAccount(AnalyticAccount analyticAccount) { this.analyticAccount = analyticAccount; }

    public Double getAllocatedBudget() { return allocatedBudget; }
    public void setAllocatedBudget(Double allocatedBudget) { this.allocatedBudget = allocatedBudget; }

    public String getPeriod() { return period; }
    public void setPeriod(String period) { this.period = period; }
}
