package SriEshwar.project1.controller;

import SriEshwar.project1.dto.BalanceSheetDTO;
import SriEshwar.project1.dto.BudgetReportDTO;
import SriEshwar.project1.dto.ProfitLossDTO;
import SriEshwar.project1.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/balance-sheet")
    public ResponseEntity<BalanceSheetDTO> getBalanceSheet() {
        return ResponseEntity.ok(reportService.getBalanceSheet());
    }

    @GetMapping("/profit-loss")
    public ResponseEntity<ProfitLossDTO> getProfitLoss() {
        return ResponseEntity.ok(reportService.getProfitLoss());
    }

    @GetMapping("/budget")
    public ResponseEntity<List<BudgetReportDTO>> getBudgetReport() {
        return ResponseEntity.ok(reportService.getBudgetReport());
    }
}
