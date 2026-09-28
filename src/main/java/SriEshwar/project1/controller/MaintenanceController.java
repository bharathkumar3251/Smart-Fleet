package SriEshwar.project1.controller;

import SriEshwar.project1.dto.MaintenanceAlertDTO;
import SriEshwar.project1.service.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fleet/maintenance-alerts")
@CrossOrigin(origins = "*")
public class MaintenanceController {

    @Autowired
    private MaintenanceService maintenanceService;

    @GetMapping
    public ResponseEntity<List<MaintenanceAlertDTO>> getMaintenanceAlerts() {
        return ResponseEntity.ok(maintenanceService.getAllAlerts());
    }
}
