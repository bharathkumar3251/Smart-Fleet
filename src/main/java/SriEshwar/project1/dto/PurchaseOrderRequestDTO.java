package SriEshwar.project1.dto;

public class PurchaseOrderRequestDTO {
    private Long vendorId;
    private Long productId;
    private Double quantity;
    private Double unitPrice;

    public PurchaseOrderRequestDTO() {}

    public Long getVendorId() { return vendorId; }
    public void setVendorId(Long vendorId) { this.vendorId = vendorId; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Double getQuantity() { return quantity; }
    public void setQuantity(Double quantity) { this.quantity = quantity; }

    public Double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(Double unitPrice) { this.unitPrice = unitPrice; }
}
