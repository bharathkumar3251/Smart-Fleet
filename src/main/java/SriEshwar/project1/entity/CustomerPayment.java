package SriEshwar.project1.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "customer_payments")
public class CustomerPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_invoice_id")
    private CustomerInvoice customerInvoice;

    private Double amount;
    private String paymentMode;
    private LocalDateTime paymentDate;

    public CustomerPayment() {}

    public CustomerPayment(Long id, CustomerInvoice customerInvoice, Double amount, String paymentMode, LocalDateTime paymentDate) {
        this.id = id;
        this.customerInvoice = customerInvoice;
        this.amount = amount;
        this.paymentMode = paymentMode;
        this.paymentDate = paymentDate;
    }

    @PrePersist
    public void onCreate() {
        if (this.paymentDate == null) {
            this.paymentDate = LocalDateTime.now();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public CustomerInvoice getCustomerInvoice() { return customerInvoice; }
    public void setCustomerInvoice(CustomerInvoice customerInvoice) { this.customerInvoice = customerInvoice; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getPaymentMode() { return paymentMode; }
    public void setPaymentMode(String paymentMode) { this.paymentMode = paymentMode; }

    public LocalDateTime getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDateTime paymentDate) { this.paymentDate = paymentDate; }
}
