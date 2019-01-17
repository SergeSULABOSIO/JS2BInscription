/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.Interfaces;

/**
 *
 * @author HP Pavilion
 */
public interface InterfaceMonnaie {
    
    public static final int NATURE_MONNAIE_LOCALE = 0;
    public static final int NATURE_MONNAIE_ETRANGERE = 1;
    
    public abstract int getId();
    public abstract int getIdEntreprise();
    public abstract int getIdUtilisateur();
    public abstract int getIdExercice();
    public abstract String getNom();
    public abstract String getCode();
    public abstract int getNature();    //Monnaie locale = 0, Monnaie étrangère = 1; 
    public abstract double getTauxMonnaieLocale();
    public abstract long getSignature();
    
    public abstract void setId(int id);
    public abstract void setIdEntreprise(int idEntreprise);
    public abstract void setIdUtilisateur(int idUtilisateur);
    public abstract void setIdExercice(int idExercice);
    public abstract void setNom(String nom);
    public abstract void setCode(String code);
    public abstract void setNature(int nature);    //Monnaie locale = 0, Monnaie étrangère = 1; 
    public abstract void setTauxMonnaieLocale(double taux);
    public abstract void setSignature(long signature);
}
