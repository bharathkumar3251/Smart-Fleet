package SriEshwar.project1.service;

import SriEshwar.project1.dto.CustomerInvoiceRequestDTO;
import SriEshwar.project1.dto.CustomerPaymentRequestDTO;
import SriEshwar.project1.dto.JournalEntryRequestDTO;
import SriEshwar.project1.dto.SalesOrderRequestDTO;
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
public class SalesService {

    @Autowired
    private SalesOrderRepository salesOrderRepository;

    @Autowired
    private CustomerInvoiceRepository customerInvoiceRepository;

    @Autowired
    private CustomerPaymentRepository customerPaymentRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AccountingService accountingService;

    public SalesOrder createSalesOrder(SalesOrderRequestDTO dto) {
        Contact customer = contactRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + dto.getCustomerId()));
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + dto.getProductId()));

        SalesOrder so = new SalesOrder();
        so.setCustomer(customer);
        so.setProduct(product);
        so.setQuantity(dto.getQuantity());
        so.setUnitPrice(dto.getUnitPrice());
        so.setTotal(dto.getQuantity() * dto.getUnitPrice());
        so.setStatus("CREATED");

        return salesOrderRepository.save(so);
    }

    public List<SalesOrder> getAllSalesOrders() {
        return salesOrderRepository.findAll();
    }

    @Transactional
    public CustomerInvoice createCustomerInvoice(CustomerInvoiceRequestDTO dto) {
        SalesOrder so = salesOrderRepository.findById(dto.getSalesOrderId())
                .orElseThrow(() -> new RuntimeException("Sales Order not found with id: " + dto.getSalesOrderId()));

        CustomerInvoice invoice = new CustomerInvoice();
        invoice.setSalesOrder(so);
        invoice.setCustomer(so.getCustomer());
        invoice.setAmount(so.getTotal());
        invoice.setStatus("UNPAID");
        invoice.setInvoiceDate(LocalDateTime.now());

        CustomerInvoice savedInvoice = customerInvoiceRepository.save(invoice);

        so.setStatus("INVOICED");
        salesOrderRepository.save(so);

        // Accounting Journal Entry:
        // DEBIT Customer Receivable
        // CREDIT Internal Fleet Service Billing
        Account receivableAcc = accountingService.getAccountByNameOrCreate("Customer Receivable", AccountType.ASSET);
        Account revenueAcc = accountingService.getAccountByNameOrCreate("Internal Fleet Service Billing", AccountType.INCOME);

        JournalEntryRequestDTO entryDTO = new JournalEntryRequestDTO();
        entryDTO.setDescription("Customer Invoice for SO #" + so.getId());
        entryDTO.setReference("INV-" + savedInvoice.getId());

        List<JournalEntryRequestDTO.JournalEntryLineDTO> lines = new ArrayList<>();

        JournalEntryRequestDTO.JournalEntryLineDTO debitLine = new JournalEntryRequestDTO.JournalEntryLineDTO();
        debitLine.setAccountId(receivableAcc.getId());
        debitLine.setDebit(savedInvoice.getAmount());
        debitLine.setCredit(0.0);
        lines.add(debitLine);

        JournalEntryRequestDTO.JournalEntryLineDTO creditLine = new JournalEntryRequestDTO.JournalEntryLineDTO();
        creditLine.setAccountId(revenueAcc.getId());
        creditLine.setDebit(0.0);
        creditLine.setCredit(savedInvoice.getAmount());
        lines.add(creditLine);

        entryDTO.setLines(lines);
        accountingService.createJournalEntry(entryDTO);

        return savedInvoice;
    }

    public List<CustomerInvoice> getAllCustomerInvoices() {
        return customerInvoiceRepository.findAll();
    }

    @Transactional
    public CustomerPayment processCustomerPayment(CustomerPaymentRequestDTO dto) {
        CustomerInvoice invoice = customerInvoiceRepository.findById(dto.getCustomerInvoiceId())
                .orElseThrow(() -> new RuntimeException("Customer Invoice not found with id: " + dto.getCustomerInvoiceId()));

        CustomerPayment payment = new CustomerPayment();
        payment.setCustomerInvoice(invoice);
        payment.setAmount(dto.getAmount());
        payment.setPaymentMode(dto.getPaymentMode());
        payment.setPaymentDate(LocalDateTime.now());

        CustomerPayment savedPayment = customerPaymentRepository.save(payment);

        invoice.setStatus("PAID");
        customerInvoiceRepository.save(invoice);

        // Accounting Journal Entry:
        // DEBIT Cash / Bank
        // CREDIT Customer Receivable
        Account bankAcc = accountingService.getAccountByNameOrCreate("Cash / Bank", AccountType.ASSET);
        Account receivableAcc = accountingService.getAccountByNameOrCreate("Customer Receivable", AccountType.ASSET);

        JournalEntryRequestDTO entryDTO = new JournalEntryRequestDTO();
        entryDTO.setDescription("Customer Payment for Invoice #" + invoice.getId());
        entryDTO.setReference("CPAY-" + savedPayment.getId());

        List<JournalEntryRequestDTO.JournalEntryLineDTO> lines = new ArrayList<>();

        JournalEntryRequestDTO.JournalEntryLineDTO debitLine = new JournalEntryRequestDTO.JournalEntryLineDTO();
        debitLine.setAccountId(bankAcc.getId());
        debitLine.setDebit(savedPayment.getAmount());
        debitLine.setCredit(0.0);
        lines.add(debitLine);

        JournalEntryRequestDTO.JournalEntryLineDTO creditLine = new JournalEntryRequestDTO.JournalEntryLineDTO();
        creditLine.setAccountId(receivableAcc.getId());
        creditLine.setDebit(0.0);
        creditLine.setCredit(savedPayment.getAmount());
        lines.add(creditLine);

        entryDTO.setLines(lines);
        accountingService.createJournalEntry(entryDTO);

        return savedPayment;
    }

    public List<CustomerPayment> getAllCustomerPayments() {
        return customerPaymentRepository.findAll();
    }
}
