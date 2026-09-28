package SriEshwar.project1.service;

import SriEshwar.project1.entity.AnalyticAccount;
import SriEshwar.project1.entity.Budget;
import SriEshwar.project1.repository.AnalyticAccountRepository;
import SriEshwar.project1.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private AnalyticAccountRepository analyticAccountRepository;

    public Budget createBudget(Budget budget) {
        if (budget.getAnalyticAccount() != null && budget.getAnalyticAccount().getId() != null) {
            AnalyticAccount account = analyticAccountRepository.findById(budget.getAnalyticAccount().getId())
                    .orElseThrow(() -> new RuntimeException("Analytic Account not found with id: " + budget.getAnalyticAccount().getId()));
            budget.setAnalyticAccount(account);
        }
        return budgetRepository.save(budget);
    }

    public List<Budget> getAllBudgets() {
        return budgetRepository.findAll();
    }
}
