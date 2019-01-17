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
public class LiaisonEleveFrais {
    private long signatureEleve;
    private int idFrais;
    private double montant;
    private int monnaie;

    public LiaisonEleveFrais(long signatureEleve, int idFrais, double montant, int monnaie) {
        this.signatureEleve = signatureEleve;
        this.idFrais = idFrais;
        this.montant = montant;
        this.monnaie = monnaie;
    }

    public long getSignatureEleve() {
        return signatureEleve;
    }

    public void setSignatureEleve(long signatureEleve) {
        this.signatureEleve = signatureEleve;
    }

    public int getIdFrais() {
        return idFrais;
    }

    public void setIdFrais(int idFrais) {
        this.idFrais = idFrais;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public int getMonnaie() {
        return monnaie;
    }

    public void setMonnaie(int monnaie) {
        this.monnaie = monnaie;
    }

    @Override
    public String toString() {
        return "LiaisonEleveFrais{" + "signatureEleve=" + signatureEleve + ", idFrais=" + idFrais + ", montant=" + montant + ", monnaie=" + monnaie + '}';
    }
}
