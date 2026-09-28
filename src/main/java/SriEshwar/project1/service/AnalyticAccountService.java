package SriEshwar.project1.service;

import SriEshwar.project1.entity.AnalyticAccount;
import SriEshwar.project1.repository.AnalyticAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalyticAccountService {

    @Autowired
    private AnalyticAccountRepository repository;

    public AnalyticAccount createAnalyticAccount(AnalyticAccount account) {
        return repository.save(account);
    }

    public List<AnalyticAccount> getAllAnalyticAccounts() {
        return repository.findAll();
    }
}
