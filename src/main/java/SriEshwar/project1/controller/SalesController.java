package SriEshwar.project1.controller;

import SriEshwar.project1.dto.CustomerInvoiceRequestDTO;
import SriEshwar.project1.dto.CustomerPaymentRequestDTO;
import SriEshwar.project1.dto.SalesOrderRequestDTO;
import SriEshwar.project1.entity.CustomerInvoice;
import SriEshwar.project1.entity.CustomerPayment;
import SriEshwar.project1.entity.SalesOrder;
import SriEshwar.project1.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fleet")
@CrossOrigin(origins = "*")
public class SalesController {

    @Autowired
    private SalesService salesService;

    @PostMapping("/sales-orders")
    public ResponseEntity<SalesOrder> createSalesOrder(@RequestBody SalesOrderRequestDTO dto) {
        return new ResponseEntity<>(salesService.createSalesOrder(dto), HttpStatus.CREATED);
    }

    @GetMapping("/sales-orders")
    public ResponseEntity<List<SalesOrder>> getAllSalesOrders() {
        return ResponseEntity.ok(salesService.getAllSalesOrders());
    }

    @PostMapping("/customer-invoices")
    public ResponseEntity<CustomerInvoice> createCustomerInvoice(@RequestBody CustomerInvoiceRequestDTO dto) {
        return new ResponseEntity<>(salesService.createCustomerInvoice(dto), HttpStatus.CREATED);
    }

    @GetMapping("/customer-invoices")
    public ResponseEntity<List<CustomerInvoice>> getAllCustomerInvoices() {
        return ResponseEntity.ok(salesService.getAllCustomerInvoices());
    }

    @PostMapping("/customer-payments")
    public ResponseEntity<CustomerPayment> processCustomerPayment(@RequestBody CustomerPaymentRequestDTO dto) {
        return new ResponseEntity<>(salesService.processCustomerPayment(dto), HttpStatus.CREATED);
    }

    @GetMapping("/customer-payments")
    public ResponseEntity<List<CustomerPayment>> getAllCustomerPayments() {
        return ResponseEntity.ok(salesService.getAllCustomerPayments());
    }
}
