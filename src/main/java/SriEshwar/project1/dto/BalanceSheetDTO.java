package SriEshwar.project1.dto;

import java.util.Map;

public class BalanceSheetDTO {
    private Map<String, Double> assets;
    private Map<String, Double> liabilities;
    private Double totalAssets;
    private Double totalLiabilities;
    private Double equity;

    public BalanceSheetDTO() {}

    public BalanceSheetDTO(Map<String, Double> assets, Map<String, Double> liabilities, Double totalAssets, Double totalLiabilities, Double equity) {
        this.assets = assets;
        this.liabilities = liabilities;
        this.totalAssets = totalAssets;
        this.totalLiabilities = totalLiabilities;
        this.equity = equity;
    }

    public Map<String, Double> getAssets() { return assets; }
    public void setAssets(Map<String, Double> assets) { this.assets = assets; }

    public Map<String, Double> getLiabilities() { return liabilities; }
    public void setLiabilities(Map<String, Double> liabilities) { this.liabilities = liabilities; }

    public Double getTotalAssets() { return totalAssets; }
    public void setTotalAssets(Double totalAssets) { this.totalAssets = totalAssets; }

    public Double getTotalLiabilities() { return totalLiabilities; }
    public void setTotalLiabilities(Double totalLiabilities) { this.totalLiabilities = totalLiabilities; }

    public Double getEquity() { return equity; }
    public void setEquity(Double equity) { this.equity = equity; }
}
