/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.Utilitaires_Insc;


import Source.Objet.Classe;
import Source.Objet.Entreprise;
import Source.Objet.Exercice;
import Source.Objet.Frais;
import Source.Objet.Monnaie;
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
    public Exercice exercice;
    public int idUtilisateur;
    public String nomUtilisateur;

    public ParametreInscription(Vector<Monnaie> listeMonnaies, Vector<Classe> listeClasses, Vector<Frais> listeFraises, Entreprise entreprise, Exercice exercice, int idUtilisateur, String nomUtilisateur) {
        this.listeMonnaies = listeMonnaies;
        this.listeClasses = listeClasses;
        this.listeFraises = listeFraises;
        this.entreprise = entreprise;
        this.exercice = exercice;
        this.idUtilisateur = idUtilisateur;
        this.nomUtilisateur = nomUtilisateur;
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

    public Exercice getExercice() {
        return exercice;
    }

    public void setExercice(Exercice exercice) {
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

    @Override
    public String toString() {
        return "ParametreInscription{" + "listeClasses=" + listeClasses + ", listeFraises=" + listeFraises + ", listeMonnaies=" + listeMonnaies + ", entreprise=" + entreprise + ", exercice=" + exercice + ", idUtilisateur=" + idUtilisateur + ", nomUtilisateur=" + nomUtilisateur + '}';
    }
}
