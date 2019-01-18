/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.RenduTable;

import SOURCES.Interfaces.InterfaceAyantDroit;
import SOURCES.Interfaces.InterfaceClasse;
import SOURCES.Interfaces.InterfaceEleve;
import SOURCES.Interfaces.InterfaceMonnaie;
import SOURCES.ModeleTable.ModeleListeAyantDroit;
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
public class RenduTableAyantDroit implements TableCellRenderer {
    
    private ImageIcon iconeEdition;
    private Vector<InterfaceEleve> listeEleves;
    private Vector<InterfaceMonnaie> listeMonnaies;
    private ModeleListeAyantDroit modeleListeAyantDroit;
    
    public RenduTableAyantDroit(ImageIcon iconeEdition, Vector<InterfaceEleve> listeEleves, Vector<InterfaceMonnaie> listeMonnaies, ModeleListeAyantDroit modeleListeAyantDroit) {
        this.iconeEdition = iconeEdition;
        this.listeEleves = listeEleves;
        this.listeMonnaies = listeMonnaies;
        this.modeleListeAyantDroit = modeleListeAyantDroit;
    }
    
    private String getEleve(int idEleve) {
        for (InterfaceEleve eleve : this.listeEleves) {
            if (eleve.getId() == idEleve) {
                return eleve.getNom() + " " + eleve.getPostnom() + " " + eleve.getPrenom();
            }
        }
        return "";
    }
    
    private String getMonnaie(int idMonnaie) {
        for (InterfaceMonnaie monnaie : this.listeMonnaies) {
            if (monnaie.getId() == idMonnaie) {
                return monnaie.getCode();
            }
        }
        return "";
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        //{"N°", "Elève"}
        CelluleSimpleTableau cellule = null;
        switch (column) {
            case 0:
                cellule = new CelluleSimpleTableau(" " + value + " ", CelluleSimpleTableau.ALIGNE_CENTRE, null);
                break;
            case 1:
                cellule = new CelluleSimpleTableau(" " + getEleve(Integer.parseInt(value+"")) + " ", CelluleSimpleTableau.ALIGNE_GAUCHE, iconeEdition);
                break;
            default:
                double mont = Double.parseDouble(value+"");
                cellule = new CelluleSimpleTableau(" " + Util.getMontantFrancais(mont) + " ", CelluleSimpleTableau.ALIGNE_DROITE, iconeEdition);
                break;
        }
        cellule.ecouterSelection(isSelected, row);
        return cellule;
    }
}
