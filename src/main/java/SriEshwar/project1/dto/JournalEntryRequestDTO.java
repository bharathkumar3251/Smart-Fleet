package SriEshwar.project1.dto;

import java.util.List;

public class JournalEntryRequestDTO {
    private String description;
    private String reference;
    private List<JournalEntryLineDTO> lines;

    public JournalEntryRequestDTO() {}

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }

    public List<JournalEntryLineDTO> getLines() { return lines; }
    public void setLines(List<JournalEntryLineDTO> lines) { this.lines = lines; }

    public static class JournalEntryLineDTO {
        private Long accountId;
        private Double debit;
        private Double credit;

        public JournalEntryLineDTO() {}

        public Long getAccountId() { return accountId; }
        public void setAccountId(Long accountId) { this.accountId = accountId; }

        public Double getDebit() { return debit; }
        public void setDebit(Double debit) { this.debit = debit; }

        public Double getCredit() { return credit; }
        public void setCredit(Double credit) { this.credit = credit; }
    }
}
