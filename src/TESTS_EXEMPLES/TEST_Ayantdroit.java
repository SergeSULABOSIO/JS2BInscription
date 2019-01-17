/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TESTS_EXEMPLES;

import SOURCES.Utilitaires.*;
import SOURCES.Interfaces.InterfaceAyantDroit;
import java.util.Vector;

/**
 *
 * @author HP Pavilion
 */
public class TEST_Ayantdroit implements InterfaceAyantDroit{
    public int id;
    public int idEntreprise;
    public int idUtilisateur;
    public int idExercice;
    public int idEleve;
    public Vector<LiaisonEleveFrais> listeLiaisons;
    public long signature;

    public TEST_Ayantdroit(int id, int idEntreprise, int idUtilisateur, int idExercice, int idEleve, Vector<LiaisonEleveFrais> listeLiaisons, long signature) {
        this.id = id;
        this.idEntreprise = idEntreprise;
        this.idUtilisateur = idUtilisateur;
        this.idExercice = idExercice;
        this.idEleve = idEleve;
        this.listeLiaisons = listeLiaisons;
        this.signature = signature;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdEntreprise() {
        return idEntreprise;
    }

    public void setIdEntreprise(int idEntreprise) {
        this.idEntreprise = idEntreprise;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public int getIdExercice() {
        return idExercice;
    }

    public void setIdExercice(int idExercice) {
        this.idExercice = idExercice;
    }

    public int getIdEleve() {
        return idEleve;
    }

    public void setIdEleve(int idEleve) {
        this.idEleve = idEleve;
    }

    public Vector<LiaisonEleveFrais> getListeLiaisons() {
        return listeLiaisons;
    }

    public void setListeLiaisons(Vector<LiaisonEleveFrais> listeLiaisons) {
        this.listeLiaisons = listeLiaisons;
    }

    public long getSignature() {
        return signature;
    }

    public void setSignature(long signature) {
        this.signature = signature;
    }

    @Override
    public String toString() {
        return "XX_Ayantdroit{" + "id=" + id + ", idEntreprise=" + idEntreprise + ", idUtilisateur=" + idUtilisateur + ", idExercice=" + idExercice + ", idEleve=" + idEleve + ", listeLiaisons=" + listeLiaisons + ", signature=" + signature + '}';
    }
}
