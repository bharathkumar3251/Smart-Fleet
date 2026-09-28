package SriEshwar.project1.service;

import SriEshwar.project1.dto.JournalEntryRequestDTO;
import SriEshwar.project1.dto.PurchaseOrderRequestDTO;
import SriEshwar.project1.dto.VendorBillRequestDTO;
import SriEshwar.project1.dto.VendorPaymentRequestDTO;
import SriEshwar.project1.entity.*;
import SriEshwar.project1.enums.AccountType;
import SriEshwar.project1.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProcurementService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private VendorBillRepository vendorBillRepository;

    @Autowired
    private VendorPaymentRepository vendorPaymentRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AccountingService accountingService;

    public PurchaseOrder createPurchaseOrder(PurchaseOrderRequestDTO dto) {
        Contact vendor = contactRepository.findById(dto.getVendorId())
                .orElseThrow(() -> new RuntimeException("Vendor not found with id: " + dto.getVendorId()));
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + dto.getProductId()));

        PurchaseOrder po = new PurchaseOrder();
        po.setVendor(vendor);
        po.setProduct(product);
        po.setQuantity(dto.getQuantity());
        po.setUnitPrice(dto.getUnitPrice());
        po.setTotal(dto.getQuantity() * dto.getUnitPrice());
        po.setStatus("CREATED");

        return purchaseOrderRepository.save(po);
    }

    public List<PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderRepository.findAll();
    }

    @Transactional
    public VendorBill createVendorBill(VendorBillRequestDTO dto) {
        PurchaseOrder po = purchaseOrderRepository.findById(dto.getPurchaseOrderId())
                .orElseThrow(() -> new RuntimeException("Purchase Order not found with id: " + dto.getPurchaseOrderId()));

        VendorBill bill = new VendorBill();
        bill.setPurchaseOrder(po);
        bill.setVendor(po.getVendor());
        bill.setAmount(po.getTotal());
        bill.setStatus("UNPAID");
        bill.setBillDate(LocalDateTime.now());

        VendorBill savedBill = vendorBillRepository.save(bill);

        po.setStatus("BILLED");
        purchaseOrderRepository.save(po);

        // Accounting Journal Entry:
        // DEBIT Fleet Tires Inventory
        // CREDIT Tire Supplier Creditors
        Account inventoryAcc = accountingService.getAccountByNameOrCreate("Fleet Tires Inventory", AccountType.ASSET);
        Account creditorAcc = accountingService.getAccountByNameOrCreate("Tire Supplier Creditors", AccountType.LIABILITY);

        JournalEntryRequestDTO entryDTO = new JournalEntryRequestDTO();
        entryDTO.setDescription("Vendor Bill for PO #" + po.getId());
        entryDTO.setReference("BILL-" + savedBill.getId());

        List<JournalEntryRequestDTO.JournalEntryLineDTO> lines = new ArrayList<>();

        JournalEntryRequestDTO.JournalEntryLineDTO debitLine = new JournalEntryRequestDTO.JournalEntryLineDTO();
        debitLine.setAccountId(inventoryAcc.getId());
        debitLine.setDebit(savedBill.getAmount());
        debitLine.setCredit(0.0);
        lines.add(debitLine);

        JournalEntryRequestDTO.JournalEntryLineDTO creditLine = new JournalEntryRequestDTO.JournalEntryLineDTO();
        creditLine.setAccountId(creditorAcc.getId());
        creditLine.setDebit(0.0);
        creditLine.setCredit(savedBill.getAmount());
        lines.add(creditLine);

        entryDTO.setLines(lines);
        accountingService.createJournalEntry(entryDTO);

        return savedBill;
    }

    public List<VendorBill> getAllVendorBills() {
        return vendorBillRepository.findAll();
    }

    @Transactional
    public VendorPayment processVendorPayment(VendorPaymentRequestDTO dto) {
        VendorBill bill = vendorBillRepository.findById(dto.getVendorBillId())
                .orElseThrow(() -> new RuntimeException("Vendor Bill not found with id: " + dto.getVendorBillId()));

        VendorPayment payment = new VendorPayment();
        payment.setVendorBill(bill);
        payment.setAmount(dto.getAmount());
        payment.setPaymentMode(dto.getPaymentMode());
        payment.setPaymentDate(LocalDateTime.now());

        VendorPayment savedPayment = vendorPaymentRepository.save(payment);

        bill.setStatus("PAID");
        vendorBillRepository.save(bill);

        // Accounting Journal Entry:
        // DEBIT Tire Supplier Creditors
        // CREDIT Cash / Bank
        Account creditorAcc = accountingService.getAccountByNameOrCreate("Tire Supplier Creditors", AccountType.LIABILITY);
        Account bankAcc = accountingService.getAccountByNameOrCreate("Cash / Bank", AccountType.ASSET);

        JournalEntryRequestDTO entryDTO = new JournalEntryRequestDTO();
        entryDTO.setDescription("Vendor Payment for Bill #" + bill.getId());
        entryDTO.setReference("PAY-" + savedPayment.getId());

        List<JournalEntryRequestDTO.JournalEntryLineDTO> lines = new ArrayList<>();

        JournalEntryRequestDTO.JournalEntryLineDTO debitLine = new JournalEntryRequestDTO.JournalEntryLineDTO();
        debitLine.setAccountId(creditorAcc.getId());
        debitLine.setDebit(savedPayment.getAmount());
        debitLine.setCredit(0.0);
        lines.add(debitLine);

        JournalEntryRequestDTO.JournalEntryLineDTO creditLine = new JournalEntryRequestDTO.JournalEntryLineDTO();
        creditLine.setAccountId(bankAcc.getId());
        creditLine.setDebit(0.0);
        creditLine.setCredit(savedPayment.getAmount());
        lines.add(creditLine);

        entryDTO.setLines(lines);
        accountingService.createJournalEntry(entryDTO);

        return savedPayment;
    }

    public List<VendorPayment> getAllVendorPayments() {
        return vendorPaymentRepository.findAll();
    }
}
