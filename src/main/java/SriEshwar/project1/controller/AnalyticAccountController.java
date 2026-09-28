package SriEshwar.project1.controller;

import SriEshwar.project1.entity.AnalyticAccount;
import SriEshwar.project1.service.AnalyticAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/analytic-accounts")
@CrossOrigin(origins = "*")
public class AnalyticAccountController {

    @Autowired
    private AnalyticAccountService service;

    @PostMapping
    public ResponseEntity<AnalyticAccount> createAnalyticAccount(@RequestBody AnalyticAccount account) {
        return new ResponseEntity<>(service.createAnalyticAccount(account), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AnalyticAccount>> getAllAnalyticAccounts() {
        return ResponseEntity.ok(service.getAllAnalyticAccounts());
    }
}
