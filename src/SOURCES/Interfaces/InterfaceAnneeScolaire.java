/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.Interfaces;

import java.util.Date;

/**
 *
 * @author HP Pavilion
 */
public interface InterfaceAnneeScolaire {
    
    public abstract int getId();
    public abstract int getIdEntreprise();
    public abstract int getIdUtilisateur();
    public abstract String getNom();
    public abstract Date getDebut();
    public abstract Date getFin();
    
    public abstract void setId(int id);
    public abstract void setIdEntreprise(int idEntreprise);
    public abstract void setIdUtilisateur(int idUtilisateur);
    public abstract void setNom(String nom);
    public abstract void setDebut(Date dateDebut);
    public abstract void setFin(Date dateFin);
    
    
}
