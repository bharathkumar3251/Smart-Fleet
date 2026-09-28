package SriEshwar.project1.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tire_telemetry")
public class TireTelemetry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tire_id")
    private Tire tire;

    private Double psi;
    private Double temperature;
    private LocalDateTime readingTime;

    public TireTelemetry() {}

    public TireTelemetry(Long id, Tire tire, Double psi, Double temperature, LocalDateTime readingTime) {
        this.id = id;
        this.tire = tire;
        this.psi = psi;
        this.temperature = temperature;
        this.readingTime = readingTime;
    }

    @PrePersist
    public void onCreate() {
        if (this.readingTime == null) {
            this.readingTime = LocalDateTime.now();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Tire getTire() { return tire; }
    public void setTire(Tire tire) { this.tire = tire; }

    public Double getPsi() { return psi; }
    public void setPsi(Double psi) { this.psi = psi; }

    public Double getTemperature() { return temperature; }
    public void setTemperature(Double temperature) { this.temperature = temperature; }

    public LocalDateTime getReadingTime() { return readingTime; }
    public void setReadingTime(LocalDateTime readingTime) { this.readingTime = readingTime; }
}
