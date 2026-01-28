package mg.cico.models;

import java.time.LocalDateTime;

public class Stock {
    private int idStock;
    private String refArticle;
    private int idLieu;
    private double quantite;
    private LocalDateTime dernierMaj;
    
    public int getIdStock() {
        return idStock;
    }
    public void setIdStock(int idStock) {
        this.idStock = idStock;
    }
    public String getRefArticle() {
        return refArticle;
    }
    public void setRefArticle(String refArticle) {
        this.refArticle = refArticle;
    }
    public int getIdLieu() {
        return idLieu;
    }
    public void setIdLieu(int idLieu) {
        this.idLieu = idLieu;
    }
    public double getQuantite() {
        return quantite;
    }
    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }
    public LocalDateTime getDernierMaj() {
        return dernierMaj;
    }
    public void setDernierMaj(LocalDateTime dernierMaj) {
        this.dernierMaj = dernierMaj;
    }
    
}
