package SriEshwar.project1.service;

import SriEshwar.project1.dto.MaintenanceAlertDTO;
import SriEshwar.project1.entity.MaintenanceTicket;
import SriEshwar.project1.repository.MaintenanceTicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaintenanceService {

    @Autowired
    private MaintenanceTicketRepository ticketRepository;

    public List<MaintenanceAlertDTO> getAllAlerts() {
        List<MaintenanceTicket> tickets = ticketRepository.findAll();
        return tickets.stream().map(t -> new MaintenanceAlertDTO(
                t.getId(),
                t.getVehicle() != null ? t.getVehicle().getVehicleNumber() : "N/A",
                t.getTire() != null ? t.getTire().getSerialNumber() : "N/A",
                t.getReason(),
                t.getTriggerPsi(),
                t.getStatus(),
                t.getCreatedAt(),
                t.getResolvedAt()
        )).collect(Collectors.toList());
    }
}
