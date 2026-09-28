package SriEshwar.project1.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "maintenance_tickets")
public class MaintenanceTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "tire_id")
    private Tire tire;

    private String reason;
    private Double triggerPsi;
    private String status;

    private LocalDateTime createdAt;
    private LocalDateTime resolvedAt;

    public MaintenanceTicket() {}

    public MaintenanceTicket(Long id, Vehicle vehicle, Tire tire, String reason, Double triggerPsi, String status, LocalDateTime createdAt, LocalDateTime resolvedAt) {
        this.id = id;
        this.vehicle = vehicle;
        this.tire = tire;
        this.reason = reason;
        this.triggerPsi = triggerPsi;
        this.status = status;
        this.createdAt = createdAt;
        this.resolvedAt = resolvedAt;
    }

    @PrePersist
    public void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
        if (this.status == null) {
            this.status = "OPEN";
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Vehicle getVehicle() { return vehicle; }
    public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }

    public Tire getTire() { return tire; }
    public void setTire(Tire tire) { this.tire = tire; }

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
