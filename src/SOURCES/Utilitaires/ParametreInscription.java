/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.Utilitaires;

import SOURCES.Interfaces.InterfaceAnneeScolaire;
import SOURCES.Interfaces.InterfaceClasse;
import SOURCES.Interfaces.InterfaceEntreprise;
import SOURCES.Interfaces.InterfaceFrais;
import java.util.Vector;

/**
 *
 * @author user
 */
public class ParametreInscription {
    //Les données non modifiables, mais indispensables au bon fonctionnement de l'API
    public Vector<InterfaceClasse> listeClasses;
    public Vector<InterfaceFrais> listeFraises;
    public InterfaceEntreprise entreprise;
    public InterfaceAnneeScolaire anneeScolaire;
    public int idUtilisateur;
    public String nomUtilisateur;

    public ParametreInscription(Vector<InterfaceClasse> listeClasses, Vector<InterfaceFrais> listeFraises, InterfaceEntreprise entreprise, InterfaceAnneeScolaire anneeScolaire, int idUtilisateur, String nomUtilisateur) {
        this.listeClasses = listeClasses;
        this.listeFraises = listeFraises;
        this.entreprise = entreprise;
        this.anneeScolaire = anneeScolaire;
        this.idUtilisateur = idUtilisateur;
        this.nomUtilisateur = nomUtilisateur;
    }

    public Vector<InterfaceClasse> getListeClasses() {
        return listeClasses;
    }

    public void setListeClasses(Vector<InterfaceClasse> listeClasses) {
        this.listeClasses = listeClasses;
    }

    public Vector<InterfaceFrais> getListeFraises() {
        return listeFraises;
    }

    public void setListeFraises(Vector<InterfaceFrais> listeFraises) {
        this.listeFraises = listeFraises;
    }

    public InterfaceEntreprise getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(InterfaceEntreprise entreprise) {
        this.entreprise = entreprise;
    }

    public InterfaceAnneeScolaire getAnneeScolaire() {
        return anneeScolaire;
    }

    public void setAnneeScolaire(InterfaceAnneeScolaire anneeScolaire) {
        this.anneeScolaire = anneeScolaire;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    @Override
    public String toString() {
        return "ParametreInscription{" + "listeClasses=" + listeClasses + ", listeFraises=" + listeFraises + ", entreprise=" + entreprise + ", anneeScolaire=" + anneeScolaire + ", idUtilisateur=" + idUtilisateur + ", nomUtilisateur=" + nomUtilisateur + '}';
    }
    
}
