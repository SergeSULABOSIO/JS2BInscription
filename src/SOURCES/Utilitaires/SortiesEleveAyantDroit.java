/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.Utilitaires;

import SOURCES.Callback.EcouteurEnregistrement;
import SOURCES.Interfaces.InterfaceAyantDroit;
import SOURCES.Interfaces.InterfaceEleve;
import java.util.Vector;

/**
 *
 * @author user
 */

public class SortiesEleveAyantDroit {
    private EcouteurEnregistrement ecouteurEnregistrement;
    private Vector<InterfaceEleve> listeEleves;
    private Vector<InterfaceAyantDroit> listeAyantDroit;

    public SortiesEleveAyantDroit(Vector<InterfaceEleve> listeEleves, Vector<InterfaceAyantDroit> listeAyantDroit, EcouteurEnregistrement ecouteurEnregistrement) {
        this.ecouteurEnregistrement = ecouteurEnregistrement;
        this.listeEleves = listeEleves;
        this.listeAyantDroit = listeAyantDroit;
    }

    public EcouteurEnregistrement getEcouteurEnregistrement() {
        return ecouteurEnregistrement;
    }

    public void setEcouteurEnregistrement(EcouteurEnregistrement ecouteurEnregistrement) {
        this.ecouteurEnregistrement = ecouteurEnregistrement;
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
        return "SortiesEleveAyantDroit{" + "ecouteurEnregistrement=" + ecouteurEnregistrement + ", listeEleves=" + listeEleves + ", listeAyantDroit=" + listeAyantDroit + '}';
    }
}
