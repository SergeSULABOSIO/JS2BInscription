/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.Utilitaires_Insc;

import Source.Interface.InterfaceAyantDroit;
import Source.Objet.Ayantdroit;
import Source.Objet.Eleve;
import java.util.Vector;

/**
 *
 * @author user
 */
public class DonneesInscription {
    //les variables existantes déjà dans la base de données. Modifiables et qu'on peut y ajouter d'autres
    public Vector<Eleve> listeEleves;
    public Vector<Ayantdroit> listeAyantDroit;

    public DonneesInscription(Vector<Eleve> listeEleves, Vector<Ayantdroit> listeAyantDroit) {
        this.listeEleves = listeEleves;
        this.listeAyantDroit = listeAyantDroit;
    }

    public Vector<Eleve> getListeEleves() {
        return listeEleves;
    }

    public void setListeEleves(Vector<Eleve> listeEleves) {
        this.listeEleves = listeEleves;
    }

    public Vector<Ayantdroit> getListeAyantDroit() {
        return listeAyantDroit;
    }

    public void setListeAyantDroit(Vector<Ayantdroit> listeAyantDroit) {
        this.listeAyantDroit = listeAyantDroit;
    }

    @Override
    public String toString() {
        return "DonneesInscription{" + "listeEleves=" + listeEleves + ", listeAyantDroit=" + listeAyantDroit + '}';
    }
}
