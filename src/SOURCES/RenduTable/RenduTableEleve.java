/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.RenduTable;

import SOURCES.Interfaces.InterfaceClasse;
import SOURCES.Interfaces.InterfaceEleve;
import SOURCES.UI.CelluleSimpleTableau;
import SOURCES.Utilitaires.Util;
import java.awt.Component;
import java.util.Date;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author user
 */
public class RenduTableEleve implements TableCellRenderer {
    
    private ImageIcon iconeEdition;
    private Vector<InterfaceClasse> listeClasses;
    
    public RenduTableEleve(ImageIcon iconeEdition, Vector<InterfaceClasse> listeClasses) {
        this.iconeEdition = iconeEdition;
        this.listeClasses = listeClasses;
    }
    
    private String getSexe(Object value) {
        String Ssexe = "MASCULIN";
        if ((Integer.parseInt(value + "")) == InterfaceEleve.SEXE_FEMININ) {
            Ssexe = "FEMININ";
        }
        return Ssexe;
    }
    
    private String getStatus(Object value) {
        String Status = "REGULIER(E)";
        if ((Integer.parseInt(value + "")) == InterfaceEleve.STATUS_INACTIF) {
            Status = "EXCLU(E)";
        }
        return Status;
    }
    
    private String getClasse(int idClasse) {
        for (InterfaceClasse clss : this.listeClasses) {
            if (clss.getId() == idClasse) {
                return clss.getNom();
            }
        }
        return "";
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        //{"N°", "Nom", "Postnom", "Prénom", "Sexe", "Classe", "Date naiss.", "Lieu de naiss.", "Téléphone (parents)"}
        CelluleSimpleTableau cellule = null;
        switch (column) {
            case 0: //N°
                cellule = new CelluleSimpleTableau(" " + value + " ", CelluleSimpleTableau.ALIGNE_CENTRE, null);
                break;
            case 1: //Nom
                cellule = new CelluleSimpleTableau(" " + value + " ", CelluleSimpleTableau.ALIGNE_GAUCHE, iconeEdition);
                break;
            case 2: //Postnom
                cellule = new CelluleSimpleTableau(" " + value + " ", CelluleSimpleTableau.ALIGNE_GAUCHE, iconeEdition);
                break;
            case 3: //Prenom
                cellule = new CelluleSimpleTableau(" " + value + " ", CelluleSimpleTableau.ALIGNE_GAUCHE, iconeEdition);
                break;
            case 4: //Sexe
                cellule = new CelluleSimpleTableau(" " + getSexe(value) + " ", CelluleSimpleTableau.ALIGNE_GAUCHE, iconeEdition);
                break;
            case 5: //Classe
                cellule = new CelluleSimpleTableau(" " + getClasse(Integer.parseInt(value + "")) + " ", CelluleSimpleTableau.ALIGNE_GAUCHE, iconeEdition);
                break;
            case 6: //Date de naissance
                cellule = new CelluleSimpleTableau(" " + Util.getDateFrancais((Date) value) + " ", CelluleSimpleTableau.ALIGNE_GAUCHE, iconeEdition);
                break;
            case 7: //Status
                cellule = new CelluleSimpleTableau(" " + getStatus(value) + " ", CelluleSimpleTableau.ALIGNE_GAUCHE, iconeEdition);
                break;
            case 8: //Téléphone
                cellule = new CelluleSimpleTableau(" " + value + " ", CelluleSimpleTableau.ALIGNE_GAUCHE, iconeEdition);
                break;
        }
        cellule.ecouterSelection(isSelected, row);
        return cellule;
    }
}
