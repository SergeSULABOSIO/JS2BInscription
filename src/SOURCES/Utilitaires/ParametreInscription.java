/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.Utilitaires;

import SOURCES.Interfaces.InterfaceClasse;
import SOURCES.Interfaces.InterfaceEntreprise;
import SOURCES.Interfaces.InterfaceExercice;
import SOURCES.Interfaces.InterfaceFrais;
import SOURCES.Interfaces.InterfaceMonnaie;
import java.util.Vector;

/**
 *
 * @author user
 */
public class ParametreInscription {
    //Les données non modifiables, mais indispensables au bon fonctionnement de l'API
    public Vector<InterfaceClasse> listeClasses;
    public Vector<InterfaceFrais> listeFraises;
    public Vector<InterfaceMonnaie> listeMonnaies;
    public InterfaceEntreprise entreprise;
    public InterfaceExercice exercice;
    public int idUtilisateur;
    public String nomUtilisateur;

    public ParametreInscription(Vector<InterfaceMonnaie> listeMonnaies, Vector<InterfaceClasse> listeClasses, Vector<InterfaceFrais> listeFraises, InterfaceEntreprise entreprise, InterfaceExercice exercice, int idUtilisateur, String nomUtilisateur) {
        this.listeMonnaies = listeMonnaies;
        this.listeClasses = listeClasses;
        this.listeFraises = listeFraises;
        this.entreprise = entreprise;
        this.exercice = exercice;
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

    public InterfaceExercice getAnneeScolaire() {
        return exercice;
    }

    public void setAnneeScolaire(InterfaceExercice exercice) {
        this.exercice = exercice;
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

    public Vector<InterfaceMonnaie> getListeMonnaies() {
        return listeMonnaies;
    }

    public void setListeMonnaies(Vector<InterfaceMonnaie> listeMonnaies) {
        this.listeMonnaies = listeMonnaies;
    }

    public InterfaceExercice getExercice() {
        return exercice;
    }

    public void setExercice(InterfaceExercice exercice) {
        this.exercice = exercice;
    }
    
    

    @Override
    public String toString() {
        return "ParametreInscription{" + "listeClasses=" + listeClasses + ", listeFraises=" + listeFraises + ", entreprise=" + entreprise + ", anneeScolaire=" + exercice + ", idUtilisateur=" + idUtilisateur + ", nomUtilisateur=" + nomUtilisateur + '}';
    }
    
}
