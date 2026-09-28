package SriEshwar.project1.entity;

import SriEshwar.project1.enums.AccountType;
import jakarta.persistence.*;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    private Double balance;

    public Account() {}

    public Account(Long id, String name, AccountType accountType, Double balance) {
        this.id = id;
        this.name = name;
        this.accountType = accountType;
        this.balance = balance;
    }

    @PrePersist
    public void onCreate() {
        if (this.balance == null) {
            this.balance = 0.0;
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public AccountType getAccountType() { return accountType; }
    public void setAccountType(AccountType accountType) { this.accountType = accountType; }

    public Double getBalance() { return balance; }
    public void setBalance(Double balance) { this.balance = balance; }
}
