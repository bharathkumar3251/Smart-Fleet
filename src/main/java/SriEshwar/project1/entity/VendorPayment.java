package SriEshwar.project1.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "vendor_payments")
public class VendorPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vendor_bill_id")
    private VendorBill vendorBill;

    private Double amount;
    private String paymentMode;
    private LocalDateTime paymentDate;

    public VendorPayment() {}

    public VendorPayment(Long id, VendorBill vendorBill, Double amount, String paymentMode, LocalDateTime paymentDate) {
        this.id = id;
        this.vendorBill = vendorBill;
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

    public VendorBill getVendorBill() { return vendorBill; }
    public void setVendorBill(VendorBill vendorBill) { this.vendorBill = vendorBill; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getPaymentMode() { return paymentMode; }
    public void setPaymentMode(String paymentMode) { this.paymentMode = paymentMode; }

    public LocalDateTime getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDateTime paymentDate) { this.paymentDate = paymentDate; }
}
