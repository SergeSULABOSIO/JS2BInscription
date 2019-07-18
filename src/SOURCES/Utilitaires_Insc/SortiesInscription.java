/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.Utilitaires_Insc;

import Source.Callbacks.EcouteurEnregistrement;
import Source.Objet.Ayantdroit;
import Source.Objet.Classe;
import Source.Objet.Eleve;
import Source.Objet.Frais;
import java.util.Vector;

/**
 *
 * @author user
 */

public class SortiesInscription {
    private EcouteurEnregistrement ecouteurEnregistrement;
    private Vector<Eleve> listeEleves;
    private Vector<Ayantdroit> listeAyantDroit;
    private Vector<Classe> listeClasses;
    private Vector<Frais> listeFrais;

    public SortiesInscription(Vector<Frais> listeFrais, Vector<Classe> listeClasses, Vector<Eleve> listeEleves, Vector<Ayantdroit> listeAyantDroit, EcouteurEnregistrement ecouteurEnregistrement) {
        this.ecouteurEnregistrement = ecouteurEnregistrement;
        this.listeEleves = listeEleves;
        this.listeAyantDroit = listeAyantDroit;
        this.listeClasses = listeClasses;
        this.listeFrais = listeFrais;
    }
    

    public EcouteurEnregistrement getEcouteurEnregistrement() {
        return ecouteurEnregistrement;
    }

    public void setEcouteurEnregistrement(EcouteurEnregistrement ecouteurEnregistrement) {
        this.ecouteurEnregistrement = ecouteurEnregistrement;
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

    public Vector<Classe> getListeClasses() {
        return listeClasses;
    }

    public void setListeClasses(Vector<Classe> listeClasses) {
        this.listeClasses = listeClasses;
    }

    public Vector<Frais> getListeFrais() {
        return listeFrais;
    }

    public void setListeFrais(Vector<Frais> listeFrais) {
        this.listeFrais = listeFrais;
    }

    @Override
    public String toString() {
        return "SortiesInscription{" + "ecouteurEnregistrement=" + ecouteurEnregistrement + ", listeEleves=" + listeEleves + ", listeAyantDroit=" + listeAyantDroit + ", listeClasses=" + listeClasses + ", listeFrais=" + listeFrais + '}';
    }
}
