/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.RenduTable;

import SOURCES.Interface.InterfaceAyantDroit;
import SOURCES.Interface.InterfaceEleve;
import SOURCES.ModeleTable.ModeleListeAyantDroit;
import SOURCES.ModeleTable.ModeleListeEleve;
import SOURCES.UI.CelluleSimpleTableau;
import SOURCES.Utilitaires.CouleurBasique;
import SOURCES.Utilitaires.UtilInscription;
import java.awt.Component;
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
    private ModeleListeAyantDroit modeleListeAyantDroit;
    private CouleurBasique couleurBasique;
    
    public RenduTableAyantDroit(CouleurBasique couleurBasique, ImageIcon iconeEdition, ModeleListeEleve modeleListeEleve, ModeleListeAyantDroit modeleListeAyantDroit) {
        this.couleurBasique = couleurBasique;
        this.iconeEdition = iconeEdition;
        this.modeleListeEleve = modeleListeEleve;
        this.modeleListeAyantDroit = modeleListeAyantDroit;
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
                cellule = new CelluleSimpleTableau(couleurBasique, " " + value + " ", CelluleSimpleTableau.ALIGNE_CENTRE, null);
                break;
            case 1:
                cellule = new CelluleSimpleTableau(couleurBasique, " " + getEleve(Long.parseLong(value+"")) + " ", CelluleSimpleTableau.ALIGNE_GAUCHE, iconeEdition);
                break;
            default:
                double mont = Double.parseDouble(value+"");
                cellule = new CelluleSimpleTableau(couleurBasique, " " + UtilInscription.getMontantFrancais(mont) + " ", CelluleSimpleTableau.ALIGNE_DROITE, iconeEdition);
                break;
        }
        cellule.ecouterSelection(isSelected, row, getBeta(row), hasFocus);
        return cellule;
    }
    
    private int getBeta(int row) {
        if (this.modeleListeAyantDroit != null) {
            InterfaceAyantDroit Ieleve = this.modeleListeAyantDroit.getAyantDroit(row);
            if (Ieleve != null) {
                return Ieleve.getBeta();
            }
        }
        return InterfaceEleve.BETA_NOUVEAU;
    }
}
