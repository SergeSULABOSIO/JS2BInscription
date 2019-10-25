/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.Utilitaires_Insc;


import Source.Objet.Classe;
import Source.Objet.Entreprise;
import Source.Objet.Annee;
import Source.Objet.Frais;
import Source.Objet.Monnaie;
import Source.Objet.Utilisateur;
import java.util.Vector;

/**
 *
 * @author user
 */
public class ParametreInscription {
    //Les données non modifiables, mais indispensables au bon fonctionnement de l'API
    public Vector<Classe> listeClasses;
    public Vector<Frais> listeFraises;
    public Vector<Monnaie> listeMonnaies;
    public Entreprise entreprise;
    public Annee exercice;
    public Utilisateur utilisateur;

    public ParametreInscription(Vector<Monnaie> listeMonnaies, Vector<Classe> listeClasses, Vector<Frais> listeFraises, Entreprise entreprise, Annee exercice, Utilisateur utilisateur) {
        this.listeMonnaies = listeMonnaies;
        this.listeClasses = listeClasses;
        this.listeFraises = listeFraises;
        this.entreprise = entreprise;
        this.exercice = exercice;
        this.utilisateur = utilisateur;
    }

    public Vector<Classe> getListeClasses() {
        return listeClasses;
    }

    public void setListeClasses(Vector<Classe> listeClasses) {
        this.listeClasses = listeClasses;
    }

    public Vector<Frais> getListeFraises() {
        return listeFraises;
    }

    public void setListeFraises(Vector<Frais> listeFraises) {
        this.listeFraises = listeFraises;
    }

    public Vector<Monnaie> getListeMonnaies() {
        return listeMonnaies;
    }

    public void setListeMonnaies(Vector<Monnaie> listeMonnaies) {
        this.listeMonnaies = listeMonnaies;
    }

    public Entreprise getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

    public Annee getExercice() {
        return exercice;
    }

    public void setExercice(Annee exercice) {
        this.exercice = exercice;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    @Override
    public String toString() {
        return "ParametreInscription{" + "listeClasses=" + listeClasses + ", listeFraises=" + listeFraises + ", listeMonnaies=" + listeMonnaies + ", entreprise=" + entreprise + ", exercice=" + exercice + ", utilisateur=" + utilisateur + '}';
    }
}





