package SriEshwar.project1.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "customer_invoices")
public class CustomerInvoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sales_order_id")
    private SalesOrder salesOrder;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Contact customer;

    private Double amount;
    private String status;
    private LocalDateTime invoiceDate;

    public CustomerInvoice() {}

    public CustomerInvoice(Long id, SalesOrder salesOrder, Contact customer, Double amount, String status, LocalDateTime invoiceDate) {
        this.id = id;
        this.salesOrder = salesOrder;
        this.customer = customer;
        this.amount = amount;
        this.status = status;
        this.invoiceDate = invoiceDate;
    }

    @PrePersist
    public void onCreate() {
        if (this.invoiceDate == null) {
            this.invoiceDate = LocalDateTime.now();
        }
        if (this.status == null) {
            this.status = "UNPAID";
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public SalesOrder getSalesOrder() { return salesOrder; }
    public void setSalesOrder(SalesOrder salesOrder) { this.salesOrder = salesOrder; }

    public Contact getCustomer() { return customer; }
    public void setCustomer(Contact customer) { this.customer = customer; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getInvoiceDate() { return invoiceDate; }
    public void setInvoiceDate(LocalDateTime invoiceDate) { this.invoiceDate = invoiceDate; }
}
