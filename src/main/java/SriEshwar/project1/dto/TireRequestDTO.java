package SriEshwar.project1.dto;

public class TireRequestDTO {
    private String serialNumber;
    private String brand;
    private String size;
    private Long vehicleId;
    private String position;
    private Double currentPsi;
    private Double wearPercentage;

    public TireRequestDTO() {}

    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public Long getVehicleId() { return vehicleId; }
    public void setVehicleId(Long vehicleId) { this.vehicleId = vehicleId; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public Double getCurrentPsi() { return currentPsi; }
    public void setCurrentPsi(Double currentPsi) { this.currentPsi = currentPsi; }

    public Double getWearPercentage() { return wearPercentage; }
    public void setWearPercentage(Double wearPercentage) { this.wearPercentage = wearPercentage; }
}
