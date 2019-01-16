/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.Interfaces;

import java.util.Date;

/**
 *
 * @author user
 */
public interface InterfaceEleve {
    //Constantes - SEXE
    public static final int SEXE_MASCULIN = 0;
    public static final int SEXE_FEMININ = 1;
    //les getters
    public abstract int getId();
    public abstract int getIdEntreprise();
    public abstract int getIdUtilisateur();
    public abstract int getIdExercice();
    public abstract int getIdClasse();
    public abstract long getSignature();
    public abstract String getClasse();
    public abstract String getAdresse();
    public abstract String getTelephonesParents();
    public abstract String getNom();
    public abstract String getPostnom();
    public abstract String getPrenom();
    public abstract String getLieuNaissance();
    public abstract int getSexe();
    public abstract Date getDateNaissance();
    
    
    //Les setters
    public abstract void setId(int id);
    public abstract void setIdEntreprise(int idEntreprise);
    public abstract void setIdUtilisateur(int idUtilisateur);
    public abstract void setIdExercice(int idExercice);
    public abstract void setIdClasse(int idClasse);
    public abstract void setSignature(long signature);
    public abstract void setClasse(String classe);
    public abstract void setAdresse(String adresse);
    public abstract void setTelephonesParents(String telephonesParents);
    public abstract void setNom(String nom);
    public abstract void setPostnom(String postnom);
    public abstract void setPrenom(String prenom);
    public abstract void setSexe(int sexe);
    public abstract void setDateNaissance(Date dateNaissance);
    public abstract void setLieuNaissance(String lieuNaissance);
    
}
