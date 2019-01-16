/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.Utilitaires;

import SOURCES.Interfaces.InterfaceEleve;

/**
 *
 * @author HP Pavilion
 */
public class LiaisonEleveFrais {
    private InterfaceEleve eleve;
    private double montant;

    public LiaisonEleveFrais(InterfaceEleve eleve, double montant) {
        this.eleve = eleve;
        this.montant = montant;
    }

    public InterfaceEleve getEleve() {
        return eleve;
    }

    public void setEleve(InterfaceEleve eleve) {
        this.eleve = eleve;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    @Override
    public String toString() {
        return "LiaisonEleveFrais{" + "eleve=" + eleve + ", montant=" + montant + '}';
    }
    
}
