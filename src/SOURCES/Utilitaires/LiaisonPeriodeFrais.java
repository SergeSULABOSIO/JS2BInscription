/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.Utilitaires;

/**
 *
 * @author HP Pavilion
 */
public class LiaisonPeriodeFrais {
    private int idPeriode;
    private String nomPeriode;
    private long signaturePeriode;
    private double pourcentage;

    public LiaisonPeriodeFrais() {
    }

    public LiaisonPeriodeFrais(int idPeriode, String nomPeriode, long signaturePeriode, double pourcentage) {
        this.idPeriode = idPeriode;
        this.nomPeriode = nomPeriode;
        this.signaturePeriode = signaturePeriode;
        this.pourcentage = pourcentage;
    }

    public long getSignaturePeriode() {
        return signaturePeriode;
    }

    public void setSignaturePeriode(long signaturePeriode) {
        this.signaturePeriode = signaturePeriode;
    }
    
    

    public int getIdPeriode() {
        return idPeriode;
    }

    public void setIdPeriode(int idPeriode) {
        this.idPeriode = idPeriode;
    }

    public String getNomPeriode() {
        return nomPeriode;
    }

    public void setNomPeriode(String nomPeriode) {
        this.nomPeriode = nomPeriode;
    }

    public double getPourcentage() {
        return pourcentage;
    }

    public void setPourcentage(double pourcentage) {
        this.pourcentage = pourcentage;
    }

    @Override
    public String toString() {
        return "LiaisonPeriodeFrais{" + "idPeriode=" + idPeriode + ", nomPeriode=" + nomPeriode + ", signaturePeriode=" + signaturePeriode + ", pourcentage=" + pourcentage + '}';
    }
}





