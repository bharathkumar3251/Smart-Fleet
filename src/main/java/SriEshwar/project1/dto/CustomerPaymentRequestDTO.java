package SriEshwar.project1.dto;

public class CustomerPaymentRequestDTO {
    private Long customerInvoiceId;
    private Double amount;
    private String paymentMode;

    public CustomerPaymentRequestDTO() {}

    public Long getCustomerInvoiceId() { return customerInvoiceId; }
    public void setCustomerInvoiceId(Long customerInvoiceId) { this.customerInvoiceId = customerInvoiceId; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getPaymentMode() { return paymentMode; }
    public void setPaymentMode(String paymentMode) { this.paymentMode = paymentMode; }
}
