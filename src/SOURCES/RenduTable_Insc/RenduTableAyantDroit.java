/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.RenduTable_Insc;


import SOURCES.ModeleTable_Insc.ModeleListeAyantDroit;
import SOURCES.ModeleTable_Insc.ModeleListeEleve;
import SOURCES.Utilitaires_Insc.UtilInscription;
import Source.GestionEdition;
import Source.Interface.InterfaceAyantDroit;
import Source.Interface.InterfaceEleve;
import Source.Objet.Ayantdroit;
import Source.Objet.CouleurBasique;
import Source.UI.CelluleTableauSimple;
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
    private GestionEdition gestionEdition;
    
    public RenduTableAyantDroit(GestionEdition gestionEdition, CouleurBasique couleurBasique, ImageIcon iconeEdition, ModeleListeEleve modeleListeEleve, ModeleListeAyantDroit modeleListeAyantDroit) {
        this.couleurBasique = couleurBasique;
        this.iconeEdition = iconeEdition;
        this.modeleListeEleve = modeleListeEleve;
        this.modeleListeAyantDroit = modeleListeAyantDroit;
        this.gestionEdition = gestionEdition;
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
        CelluleTableauSimple cellule = null;
        
        ImageIcon icone = null;
        if(gestionEdition != null){
            Ayantdroit Ieleve = this.modeleListeAyantDroit.getAyantDroit(row);
            if(Ieleve != null){
                if(gestionEdition.isEditable(Ieleve.getId(), 1)){
                    icone = iconeEdition;
                }
            }
        }
        
        
        switch (column) {
            case 0:
                cellule = new CelluleTableauSimple(couleurBasique, " " + value + " ", CelluleTableauSimple.ALIGNE_CENTRE, null);
                break;
            case 1:
                cellule = new CelluleTableauSimple(couleurBasique, " " + getEleve(Long.parseLong(value+"")) + " ", CelluleTableauSimple.ALIGNE_GAUCHE, icone);
                break;
            default:
                double mont = Double.parseDouble(value+"");
                cellule = new CelluleTableauSimple(couleurBasique, " " + UtilInscription.getMontantFrancais(mont) + " ", CelluleTableauSimple.ALIGNE_DROITE, icone);
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









