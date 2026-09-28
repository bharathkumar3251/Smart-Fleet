package SriEshwar.project1.dto;

public class CustomerInvoiceRequestDTO {
    private Long salesOrderId;

    public CustomerInvoiceRequestDTO() {}

    public Long getSalesOrderId() { return salesOrderId; }
    public void setSalesOrderId(Long salesOrderId) { this.salesOrderId = salesOrderId; }
}
