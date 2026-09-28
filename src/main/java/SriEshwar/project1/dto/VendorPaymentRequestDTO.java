package SriEshwar.project1.dto;

public class VendorPaymentRequestDTO {
    private Long vendorBillId;
    private Double amount;
    private String paymentMode;

    public VendorPaymentRequestDTO() {}

    public Long getVendorBillId() { return vendorBillId; }
    public void setVendorBillId(Long vendorBillId) { this.vendorBillId = vendorBillId; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getPaymentMode() { return paymentMode; }
    public void setPaymentMode(String paymentMode) { this.paymentMode = paymentMode; }
}
