package SriEshwar.project1.service;

import SriEshwar.project1.dto.JournalEntryRequestDTO;
import SriEshwar.project1.entity.Account;
import SriEshwar.project1.entity.JournalEntry;
import SriEshwar.project1.entity.JournalEntryLine;
import SriEshwar.project1.enums.AccountType;
import SriEshwar.project1.repository.AccountRepository;
import SriEshwar.project1.repository.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountingService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccountByNameOrCreate(String name, AccountType type) {
        return accountRepository.findByName(name)
                .orElseGet(() -> {
                    Account acc = new Account();
                    acc.setName(name);
                    acc.setAccountType(type);
                    acc.setBalance(0.0);
                    return accountRepository.save(acc);
                });
    }

    @Transactional
    public JournalEntry createJournalEntry(JournalEntryRequestDTO dto) {
        if (dto.getLines() == null || dto.getLines().isEmpty()) {
            throw new IllegalArgumentException("Journal entry must contain lines.");
        }

        double totalDebit = 0.0;
        double totalCredit = 0.0;

        for (JournalEntryRequestDTO.JournalEntryLineDTO lineDTO : dto.getLines()) {
            totalDebit += (lineDTO.getDebit() != null ? lineDTO.getDebit() : 0.0);
            totalCredit += (lineDTO.getCredit() != null ? lineDTO.getCredit() : 0.0);
        }

        // Validate double-entry balancing rule
        if (Math.abs(totalDebit - totalCredit) > 0.001) {
            throw new IllegalArgumentException("Unbalanced Journal Entry: Total Debit (" + totalDebit 
                    + ") must equal Total Credit (" + totalCredit + ")");
        }

        JournalEntry journalEntry = new JournalEntry();
        journalEntry.setEntryDate(LocalDateTime.now());
        journalEntry.setDescription(dto.getDescription());
        journalEntry.setReference(dto.getReference());

        List<JournalEntryLine> lines = new ArrayList<>();
        for (JournalEntryRequestDTO.JournalEntryLineDTO lineDTO : dto.getLines()) {
            Account account = accountRepository.findById(lineDTO.getAccountId())
                    .orElseThrow(() -> new RuntimeException("Account not found with id: " + lineDTO.getAccountId()));

            double debit = lineDTO.getDebit() != null ? lineDTO.getDebit() : 0.0;
            double credit = lineDTO.getCredit() != null ? lineDTO.getCredit() : 0.0;

            JournalEntryLine line = new JournalEntryLine();
            line.setJournalEntry(journalEntry);
            line.setAccount(account);
            line.setDebit(debit);
            line.setCredit(credit);
            lines.add(line);

            // Update account balance based on normal balance type
            if (account.getAccountType() == AccountType.ASSET || account.getAccountType() == AccountType.EXPENSE) {
                account.setBalance(account.getBalance() + debit - credit);
            } else {
                account.setBalance(account.getBalance() + credit - debit);
            }
            accountRepository.save(account);
        }

        journalEntry.setLines(lines);
        return journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAllJournalEntries() {
        return journalEntryRepository.findAll();
    }
}
