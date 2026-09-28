package SriEshwar.project1.controller;

import SriEshwar.project1.dto.PurchaseOrderRequestDTO;
import SriEshwar.project1.dto.VendorBillRequestDTO;
import SriEshwar.project1.dto.VendorPaymentRequestDTO;
import SriEshwar.project1.entity.PurchaseOrder;
import SriEshwar.project1.entity.VendorBill;
import SriEshwar.project1.entity.VendorPayment;
import SriEshwar.project1.service.ProcurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/procurement")
@CrossOrigin(origins = "*")
public class ProcurementController {

    @Autowired
    private ProcurementService procurementService;

    @PostMapping("/purchase-orders")
    public ResponseEntity<PurchaseOrder> createPurchaseOrder(@RequestBody PurchaseOrderRequestDTO dto) {
        return new ResponseEntity<>(procurementService.createPurchaseOrder(dto), HttpStatus.CREATED);
    }

    @GetMapping("/purchase-orders")
    public ResponseEntity<List<PurchaseOrder>> getAllPurchaseOrders() {
        return ResponseEntity.ok(procurementService.getAllPurchaseOrders());
    }

    @PostMapping("/vendor-bills")
    public ResponseEntity<VendorBill> createVendorBill(@RequestBody VendorBillRequestDTO dto) {
        return new ResponseEntity<>(procurementService.createVendorBill(dto), HttpStatus.CREATED);
    }

    @GetMapping("/vendor-bills")
    public ResponseEntity<List<VendorBill>> getAllVendorBills() {
        return ResponseEntity.ok(procurementService.getAllVendorBills());
    }

    @PostMapping("/payments")
    public ResponseEntity<VendorPayment> processVendorPayment(@RequestBody VendorPaymentRequestDTO dto) {
        return new ResponseEntity<>(procurementService.processVendorPayment(dto), HttpStatus.CREATED);
    }

    @GetMapping("/payments")
    public ResponseEntity<List<VendorPayment>> getAllVendorPayments() {
        return ResponseEntity.ok(procurementService.getAllVendorPayments());
    }
}
