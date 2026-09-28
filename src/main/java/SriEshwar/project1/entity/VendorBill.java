package SriEshwar.project1.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "vendor_bills")
public class VendorBill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "purchase_order_id")
    private PurchaseOrder purchaseOrder;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private Contact vendor;

    private Double amount;
    private String status;
    private LocalDateTime billDate;

    public VendorBill() {}

    public VendorBill(Long id, PurchaseOrder purchaseOrder, Contact vendor, Double amount, String status, LocalDateTime billDate) {
        this.id = id;
        this.purchaseOrder = purchaseOrder;
        this.vendor = vendor;
        this.amount = amount;
        this.status = status;
        this.billDate = billDate;
    }

    @PrePersist
    public void onCreate() {
        if (this.billDate == null) {
            this.billDate = LocalDateTime.now();
        }
        if (this.status == null) {
            this.status = "UNPAID";
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public PurchaseOrder getPurchaseOrder() { return purchaseOrder; }
    public void setPurchaseOrder(PurchaseOrder purchaseOrder) { this.purchaseOrder = purchaseOrder; }

    public Contact getVendor() { return vendor; }
    public void setVendor(Contact vendor) { this.vendor = vendor; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getBillDate() { return billDate; }
    public void setBillDate(LocalDateTime billDate) { this.billDate = billDate; }
}
