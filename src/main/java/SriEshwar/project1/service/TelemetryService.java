package SriEshwar.project1.service;

import SriEshwar.project1.dto.TelemetryInputDTO;
import SriEshwar.project1.entity.MaintenanceTicket;
import SriEshwar.project1.entity.Tire;
import SriEshwar.project1.entity.TireTelemetry;
import SriEshwar.project1.repository.MaintenanceTicketRepository;
import SriEshwar.project1.repository.TireRepository;
import SriEshwar.project1.repository.TireTelemetryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TelemetryService {

    @Autowired
    private TireRepository tireRepository;

    @Autowired
    private TireTelemetryRepository telemetryRepository;

    @Autowired
    private MaintenanceTicketRepository maintenanceTicketRepository;

    public TireTelemetry recordTelemetry(TelemetryInputDTO dto) {
        Tire tire = tireRepository.findById(dto.getTireId())
                .orElseThrow(() -> new RuntimeException("Tire not found with id: " + dto.getTireId()));

        TireTelemetry telemetry = new TireTelemetry();
        telemetry.setTire(tire);
        telemetry.setPsi(dto.getPsi());
        telemetry.setTemperature(dto.getTemperature());
        telemetry.setReadingTime(LocalDateTime.now());

        TireTelemetry savedTelemetry = telemetryRepository.save(telemetry);

        // Update tire's current PSI
        tire.setCurrentPsi(dto.getPsi());
        tireRepository.save(tire);

        // Business rule check: create ticket if PSI < 28
        if (dto.getPsi() < 28.0) {
            MaintenanceTicket ticket = new MaintenanceTicket();
            ticket.setVehicle(tire.getVehicle());
            ticket.setTire(tire);
            ticket.setReason("LOW_TIRE_PRESSURE");
            ticket.setTriggerPsi(dto.getPsi());
            ticket.setStatus("OPEN");
            ticket.setCreatedAt(LocalDateTime.now());

            maintenanceTicketRepository.save(ticket);
        }

        return savedTelemetry;
    }
}
