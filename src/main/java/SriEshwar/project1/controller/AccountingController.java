package SriEshwar.project1.controller;

import SriEshwar.project1.dto.JournalEntryRequestDTO;
import SriEshwar.project1.entity.Account;
import SriEshwar.project1.entity.JournalEntry;
import SriEshwar.project1.service.AccountingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounting")
@CrossOrigin(origins = "*")
public class AccountingController {

    @Autowired
    private AccountingService accountingService;

    @PostMapping("/accounts")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        return new ResponseEntity<>(accountingService.createAccount(account), HttpStatus.CREATED);
    }

    @GetMapping("/accounts")
    public ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.ok(accountingService.getAllAccounts());
    }

    @PostMapping("/journal-entries")
    public ResponseEntity<?> createJournalEntry(@RequestBody JournalEntryRequestDTO dto) {
        try {
            JournalEntry entry = accountingService.createJournalEntry(dto);
            return new ResponseEntity<>(entry, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/journal-entries")
    public ResponseEntity<List<JournalEntry>> getAllJournalEntries() {
        return ResponseEntity.ok(accountingService.getAllJournalEntries());
    }
}
