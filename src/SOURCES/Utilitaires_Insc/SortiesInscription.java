/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.Utilitaires_Insc;

import SOURCES.Callback_Insc.EcouteurEnregistrement;
import SOURCES.Interface.InterfaceAyantDroit;
import SOURCES.Interface.InterfaceClasse;
import SOURCES.Interface.InterfaceEleve;
import SOURCES.Interface.InterfaceFrais;
import java.util.Vector;

/**
 *
 * @author user
 */

public class SortiesInscription {
    private EcouteurEnregistrement ecouteurEnregistrement;
    private Vector<InterfaceEleve> listeEleves;
    private Vector<InterfaceAyantDroit> listeAyantDroit;
    private Vector<InterfaceClasse> listeClasses;
    private Vector<InterfaceFrais> listeFrais;

    public SortiesInscription(Vector<InterfaceFrais> listeFrais, Vector<InterfaceClasse> listeClasses, Vector<InterfaceEleve> listeEleves, Vector<InterfaceAyantDroit> listeAyantDroit, EcouteurEnregistrement ecouteurEnregistrement) {
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

    public Vector<InterfaceClasse> getListeClasses() {
        return listeClasses;
    }
    
    public void setListeClasses(Vector<InterfaceClasse> listeClasses) {
        this.listeClasses = listeClasses;
    }

    public Vector<InterfaceFrais> getListeFrais() {
        return listeFrais;
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
