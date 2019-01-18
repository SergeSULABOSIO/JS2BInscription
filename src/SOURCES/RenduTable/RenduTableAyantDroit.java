/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.RenduTable;

import SOURCES.Interfaces.InterfaceEleve;
import SOURCES.Interfaces.InterfaceMonnaie;
import SOURCES.ModeleTable.ModeleListeAyantDroit;
import SOURCES.ModeleTable.ModeleListeEleve;
import SOURCES.UI.CelluleSimpleTableau;
import SOURCES.Utilitaires.Util;
import java.awt.Component;
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
    private ModeleListeEleve modeleListeEleve;
    
    public RenduTableAyantDroit(ImageIcon iconeEdition, ModeleListeEleve modeleListeEleve, ModeleListeAyantDroit modeleListeAyantDroit) {
        this.iconeEdition = iconeEdition;
        this.modeleListeEleve = modeleListeEleve;
    }
    
    private String getEleve(long signatureEleve) {
        for (InterfaceEleve eleve : this.modeleListeEleve.getListeData()) {
            if (eleve.getSignature() == signatureEleve) {
                return eleve.getNom() + " " + eleve.getPostnom() + " " + eleve.getPrenom()+" ("+eleve.getClasse()+")";
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
                cellule = new CelluleSimpleTableau(" " + getEleve(Long.parseLong(value+"")) + " ", CelluleSimpleTableau.ALIGNE_GAUCHE, iconeEdition);
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
