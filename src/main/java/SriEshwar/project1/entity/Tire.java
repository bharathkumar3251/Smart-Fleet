package SriEshwar.project1.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tires")
public class Tire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serialNumber;
    private String brand;
    private String size;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    private String position;
    private String status;
    private Double currentPsi;
    private Double wearPercentage;

    public Tire() {}

    public Tire(Long id, String serialNumber, String brand, String size, Vehicle vehicle, String position, String status, Double currentPsi, Double wearPercentage) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.brand = brand;
        this.size = size;
        this.vehicle = vehicle;
        this.position = position;
        this.status = status;
        this.currentPsi = currentPsi;
        this.wearPercentage = wearPercentage;
    }

    @PrePersist
    public void onCreate() {
        if (this.status == null) {
            this.status = "ACTIVE";
        }
        if (this.currentPsi == null) {
            this.currentPsi = 32.0;
        }
        if (this.wearPercentage == null) {
            this.wearPercentage = 0.0;
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public Vehicle getVehicle() { return vehicle; }
    public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Double getCurrentPsi() { return currentPsi; }
    public void setCurrentPsi(Double currentPsi) { this.currentPsi = currentPsi; }

    public Double getWearPercentage() { return wearPercentage; }
    public void setWearPercentage(Double wearPercentage) { this.wearPercentage = wearPercentage; }
}
