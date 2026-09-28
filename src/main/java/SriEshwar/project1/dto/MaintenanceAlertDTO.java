package SriEshwar.project1.dto;

import java.time.LocalDateTime;

public class MaintenanceAlertDTO {
    private Long ticketId;
    private String vehicleNumber;
    private String tireSerial;
    private String reason;
    private Double triggerPsi;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime resolvedAt;

    public MaintenanceAlertDTO() {}

    public MaintenanceAlertDTO(Long ticketId, String vehicleNumber, String tireSerial, String reason, Double triggerPsi, String status, LocalDateTime createdAt, LocalDateTime resolvedAt) {
        this.ticketId = ticketId;
        this.vehicleNumber = vehicleNumber;
        this.tireSerial = tireSerial;
        this.reason = reason;
        this.triggerPsi = triggerPsi;
        this.status = status;
        this.createdAt = createdAt;
        this.resolvedAt = resolvedAt;
    }

    public Long getTicketId() { return ticketId; }
    public void setTicketId(Long ticketId) { this.ticketId = ticketId; }

    public String getVehicleNumber() { return vehicleNumber; }
    public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }

    public String getTireSerial() { return tireSerial; }
    public void setTireSerial(String tireSerial) { this.tireSerial = tireSerial; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public Double getTriggerPsi() { return triggerPsi; }
    public void setTriggerPsi(Double triggerPsi) { this.triggerPsi = triggerPsi; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getResolvedAt() { return resolvedAt; }
    public void setResolvedAt(LocalDateTime resolvedAt) { this.resolvedAt = resolvedAt; }
}
