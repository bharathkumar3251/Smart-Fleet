package SriEshwar.project1.controller;

import SriEshwar.project1.dto.TelemetryInputDTO;
import SriEshwar.project1.entity.TireTelemetry;
import SriEshwar.project1.service.TelemetryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fleet/telemetry")
@CrossOrigin(origins = "*")
public class TelemetryController {

    @Autowired
    private TelemetryService telemetryService;

    @PostMapping
    public ResponseEntity<TireTelemetry> recordTelemetry(@RequestBody TelemetryInputDTO dto) {
        return new ResponseEntity<>(telemetryService.recordTelemetry(dto), HttpStatus.CREATED);
    }
}
