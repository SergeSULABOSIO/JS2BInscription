/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.Utilitaires;

import SOURCES.Interfaces.InterfaceAyantDroit;
import SOURCES.Interfaces.InterfaceEleve;
import java.util.Vector;

/**
 *
 * @author user
 */
public class DonneesInscription {
    //les variables existantes déjà dans la base de données. Modifiables et qu'on peut y ajouter d'autres
    public Vector<InterfaceEleve> listeEleves;
    public Vector<InterfaceAyantDroit> listeAyantDroit;

    public DonneesInscription(Vector<InterfaceEleve> listeEleves, Vector<InterfaceAyantDroit> listeAyantDroit) {
        this.listeEleves = listeEleves;
        this.listeAyantDroit = listeAyantDroit;
    }

    public Vector<InterfaceEleve> getListeEleves() {
        return listeEleves;
    }

    public void setListeEleves(Vector<InterfaceEleve> listeEleves) {
        this.listeEleves = listeEleves;
    }

    public Vector<InterfaceAyantDroit> getListeAyantDroit() {
        return listeAyantDroit;
    }

    public void setListeAyantDroit(Vector<InterfaceAyantDroit> listeAyantDroit) {
        this.listeAyantDroit = listeAyantDroit;
    }

    @Override
    public String toString() {
        return "DonneesInscription{" + "listeEleves=" + listeEleves + ", listeAyantDroit=" + listeAyantDroit + '}';
    }
}
