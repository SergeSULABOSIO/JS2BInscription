/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TESTS_EXEMPLES;

import SOURCES.Utilitaires.*;
import SOURCES.Interfaces.InterfaceClasse;
import SOURCES.Interfaces.InterfaceFrais;
import java.util.Vector;

/**
 *
 * @author HP Pavilion
 */
public class TEST_Frais implements InterfaceFrais{
    public int id;
    public int idUtilisateur;
    public int idEntreprise;
    public int idExercice;
    public int idMonnaie;
    public String nom;
    public String monnaie;
    public long signatureMonnaie;
    public int nbTranches;
    public double montant;
    public Vector<LiaisonClasseFrais> liaisons = new Vector<LiaisonClasseFrais>();

    public TEST_Frais(int id, int idUtilisateur, int idEntreprise, int idExercice, int idMonnaie, long signatureMonnaie, String nom, String monnaie, int nbTranches, double montant, Vector<LiaisonClasseFrais> liaisons) {
        this.id = id;
        this.idUtilisateur = idUtilisateur;
        this.idEntreprise = idEntreprise;
        this.idExercice = idExercice;
        this.nom = nom;
        this.nbTranches = nbTranches;
        this.liaisons = liaisons;
        this.montant = montant;
        this.idMonnaie = idMonnaie;
        this.monnaie = monnaie;
        this.signatureMonnaie = signatureMonnaie;
    }

    public int getIdMonnaie() {
        return idMonnaie;
    }

    public void setIdMonnaie(int idMonnaie) {
        this.idMonnaie = idMonnaie;
    }

    public String getMonnaie() {
        return monnaie;
    }

    public void setMonnaie(String monnaie) {
        this.monnaie = monnaie;
    }

    public long getSignatureMonnaie() {
        return signatureMonnaie;
    }

    public void setSignatureMonnaie(long signatureMonnaie) {
        this.signatureMonnaie = signatureMonnaie;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }
    
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public int getIdEntreprise() {
        return idEntreprise;
    }

    public void setIdEntreprise(int idEntreprise) {
        this.idEntreprise = idEntreprise;
    }

    public int getIdExercice() {
        return idExercice;
    }

    public void setIdExercice(int idExercice) {
        this.idExercice = idExercice;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getNbTranches() {
        return nbTranches;
    }

    public void setNbTranches(int nbTranches) {
        this.nbTranches = nbTranches;
    }

    public Vector<LiaisonClasseFrais> getLiaisons() {
        return liaisons;
    }

    public void setLiaisons(Vector<LiaisonClasseFrais> liaisons) {
        this.liaisons = liaisons;
    }

    @Override
    public LiaisonClasseFrais getLiaison(InterfaceClasse classe) {
        for(LiaisonClasseFrais liaisonClasseFrais : liaisons){
            if(liaisonClasseFrais.getClasse().getId() == classe.getId() && liaisonClasseFrais.getClasse().getNom().equals(classe.getNom())){
                return liaisonClasseFrais;
            }
        }
        return null;
    }

    @Override
    public void ajouterLiaisons(LiaisonClasseFrais liaison) {
        liaisons.add(liaison);
    }

    @Override
    public void viderLiaisons() {
        liaisons.removeAllElements();
    }

    @Override
    public double getMontant_default() {
        return montant;
    }

    @Override
    public void setMontant_default(double montant) {
        this.montant = montant;
    }
}
