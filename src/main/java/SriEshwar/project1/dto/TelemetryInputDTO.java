package SriEshwar.project1.dto;

public class TelemetryInputDTO {
    private Long tireId;
    private Double psi;
    private Double temperature;

    public TelemetryInputDTO() {}

    public Long getTireId() { return tireId; }
    public void setTireId(Long tireId) { this.tireId = tireId; }

    public Double getPsi() { return psi; }
    public void setPsi(Double psi) { this.psi = psi; }

    public Double getTemperature() { return temperature; }
    public void setTemperature(Double temperature) { this.temperature = temperature; }
}
