package SriEshwar.project1.service;

import SriEshwar.project1.dto.BalanceSheetDTO;
import SriEshwar.project1.dto.BudgetReportDTO;
import SriEshwar.project1.dto.ProfitLossDTO;
import SriEshwar.project1.entity.Account;
import SriEshwar.project1.entity.Budget;
import SriEshwar.project1.enums.AccountType;
import SriEshwar.project1.repository.AccountRepository;
import SriEshwar.project1.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BudgetRepository budgetRepository;

    public BalanceSheetDTO getBalanceSheet() {
        List<Account> assetAccounts = accountRepository.findByAccountType(AccountType.ASSET);
        List<Account> liabilityAccounts = accountRepository.findByAccountType(AccountType.LIABILITY);

        Map<String, Double> assetsMap = new HashMap<>();
        double totalAssets = 0.0;
        for (Account acc : assetAccounts) {
            double bal = acc.getBalance() != null ? acc.getBalance() : 0.0;
            assetsMap.put(acc.getName(), bal);
            totalAssets += bal;
        }

        Map<String, Double> liabilitiesMap = new HashMap<>();
        double totalLiabilities = 0.0;
        for (Account acc : liabilityAccounts) {
            double bal = acc.getBalance() != null ? acc.getBalance() : 0.0;
            liabilitiesMap.put(acc.getName(), bal);
            totalLiabilities += bal;
        }

        double equity = totalAssets - totalLiabilities;

        return new BalanceSheetDTO(assetsMap, liabilitiesMap, totalAssets, totalLiabilities, equity);
    }

    public ProfitLossDTO getProfitLoss() {
        List<Account> incomeAccounts = accountRepository.findByAccountType(AccountType.INCOME);
        List<Account> expenseAccounts = accountRepository.findByAccountType(AccountType.EXPENSE);

        Map<String, Double> incomeMap = new HashMap<>();
        double totalIncome = 0.0;
        for (Account acc : incomeAccounts) {
            double bal = acc.getBalance() != null ? acc.getBalance() : 0.0;
            incomeMap.put(acc.getName(), bal);
            totalIncome += bal;
        }

        Map<String, Double> expenseMap = new HashMap<>();
        double totalExpenses = 0.0;
        for (Account acc : expenseAccounts) {
            double bal = acc.getBalance() != null ? acc.getBalance() : 0.0;
            expenseMap.put(acc.getName(), bal);
            totalExpenses += bal;
        }

        double netProfit = totalIncome - totalExpenses;

        return new ProfitLossDTO(incomeMap, expenseMap, totalIncome, totalExpenses, netProfit);
    }

    public List<BudgetReportDTO> getBudgetReport() {
        List<Budget> budgets = budgetRepository.findAll();
        List<BudgetReportDTO> report = new ArrayList<>();

        // Calculate total actual expenses from database expense accounts
        List<Account> expenseAccounts = accountRepository.findByAccountType(AccountType.EXPENSE);
        double totalActualExpense = 0.0;
        for (Account acc : expenseAccounts) {
            if (acc.getBalance() != null) {
                totalActualExpense += acc.getBalance();
            }
        }

        for (Budget b : budgets) {
            String divisionName = (b.getAnalyticAccount() != null && b.getAnalyticAccount().getName() != null)
                    ? b.getAnalyticAccount().getName() : "Unassigned Division";
            double allocated = b.getAllocatedBudget() != null ? b.getAllocatedBudget() : 0.0;
            double actual = totalActualExpense;
            double variance = allocated - actual;

            report.add(new BudgetReportDTO(divisionName, allocated, actual, variance));
        }

        return report;
    }
}
