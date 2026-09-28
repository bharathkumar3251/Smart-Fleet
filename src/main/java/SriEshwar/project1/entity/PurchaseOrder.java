package SriEshwar.project1.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "purchase_orders")
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private Contact vendor;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Double quantity;
    private Double unitPrice;
    private Double total;
    private String status;

    public PurchaseOrder() {}

    public PurchaseOrder(Long id, Contact vendor, Product product, Double quantity, Double unitPrice, Double total, String status) {
        this.id = id;
        this.vendor = vendor;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.total = total;
        this.status = status;
    }

    @PrePersist
    @PreUpdate
    public void calculateTotal() {
        if (this.quantity != null && this.unitPrice != null) {
            this.total = this.quantity * this.unitPrice;
        }
        if (this.status == null) {
            this.status = "CREATED";
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Contact getVendor() { return vendor; }
    public void setVendor(Contact vendor) { this.vendor = vendor; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public Double getQuantity() { return quantity; }
    public void setQuantity(Double quantity) { this.quantity = quantity; }

    public Double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(Double unitPrice) { this.unitPrice = unitPrice; }

    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
