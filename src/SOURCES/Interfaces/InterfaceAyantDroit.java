/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.Interfaces;

import SOURCES.Utilitaires.LiaisonEleveFrais;
import java.util.Vector;

/**
 *
 * @author HP Pavilion
 */
public interface InterfaceAyantDroit {
    //les getters //ModeleListeAyantDroit
    public abstract int getId();
    public abstract int getIdEntreprise();
    public abstract int getIdUtilisateur();
    public abstract int getIdExercice();
    public abstract int getIdEleve();
    public abstract String getEleve();
    public abstract Vector<LiaisonEleveFrais> getListeLiaisons();
    public abstract long getSignature();
    public abstract long getSignatureEleve();
    //Stters
    public abstract void setId(int id);
    public abstract void setIdEntreprise(int idEntreprise);
    public abstract void setIdUtilisateur(int idUtilisateur);
    public abstract void setIdExercice(int idExercice);
    public abstract void setIdEleve(int idEleve);
    public abstract void setListeLiaisons(Vector<LiaisonEleveFrais>  listeLiaisons);
    public abstract void ajouterLiaisons(LiaisonEleveFrais newLiaison);
    public abstract void viderLiaisons();
    public abstract void setLiaisons(int idFrais, double montant);
    public abstract void setSignature(long signature);
    public abstract void setSignatureEleve(long signature);
    public abstract void setEleve(String eleve);
}
