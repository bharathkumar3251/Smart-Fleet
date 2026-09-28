package SriEshwar.project1.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "journal_entry_lines")
public class JournalEntryLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "journal_entry_id")
    @JsonBackReference
    private JournalEntry journalEntry;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    private Double debit;
    private Double credit;

    public JournalEntryLine() {}

    public JournalEntryLine(Long id, JournalEntry journalEntry, Account account, Double debit, Double credit) {
        this.id = id;
        this.journalEntry = journalEntry;
        this.account = account;
        this.debit = debit;
        this.credit = credit;
    }

    @PrePersist
    public void onCreate() {
        if (this.debit == null) {
            this.debit = 0.0;
        }
        if (this.credit == null) {
            this.credit = 0.0;
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public JournalEntry getJournalEntry() { return journalEntry; }
    public void setJournalEntry(JournalEntry journalEntry) { this.journalEntry = journalEntry; }

    public Account getAccount() { return account; }
    public void setAccount(Account account) { this.account = account; }

    public Double getDebit() { return debit; }
    public void setDebit(Double debit) { this.debit = debit; }

    public Double getCredit() { return credit; }
    public void setCredit(Double credit) { this.credit = credit; }
}
