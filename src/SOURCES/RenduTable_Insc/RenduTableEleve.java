/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.RenduTable_Insc;


import SOURCES.ModeleTable_Insc.ModeleListeEleve;
import SOURCES.Utilitaires_Insc.UtilInscription;
import Source.Interface.InterfaceEleve;
import Source.Objet.Classe;
import Source.Objet.CouleurBasique;
import Source.UI.CelluleTableauSimple;
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
    private Vector<Classe> listeClasses;
    private ModeleListeEleve modeleListeEleve;
    private CouleurBasique couleurBasique;

    public RenduTableEleve(CouleurBasique couleurBasique, ImageIcon iconeEdition, ModeleListeEleve modeleListeEleve, Vector<Classe> listeClasses) {
        this.couleurBasique = couleurBasique;
        this.iconeEdition = iconeEdition;
        this.listeClasses = listeClasses;
        this.modeleListeEleve = modeleListeEleve;
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
        for (Classe clss : this.listeClasses) {
            if (clss.getId() == idClasse) {
                return clss.getNom();
            }
        }
        return "";
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        //{"N°", "Nom", "Postnom", "Prénom", "Sexe", "Classe", "Date naiss.", "Lieu de naiss.", "Téléphone (parents)"}
        CelluleTableauSimple cellule = null;
        switch (column) {
            case 0: //N°
                cellule = new CelluleTableauSimple(couleurBasique, " " + value + " ", CelluleTableauSimple.ALIGNE_CENTRE, null);
                break;
            case 1: //Nom
                cellule = new CelluleTableauSimple(couleurBasique, " " + value + " ", CelluleTableauSimple.ALIGNE_GAUCHE, iconeEdition);
                break;
            case 2: //Postnom
                cellule = new CelluleTableauSimple(couleurBasique, " " + value + " ", CelluleTableauSimple.ALIGNE_GAUCHE, iconeEdition);
                break;
            case 3: //Prenom
                cellule = new CelluleTableauSimple(couleurBasique, " " + value + " ", CelluleTableauSimple.ALIGNE_GAUCHE, iconeEdition);
                break;
            case 4: //Sexe
                cellule = new CelluleTableauSimple(couleurBasique, " " + getSexe(value) + " ", CelluleTableauSimple.ALIGNE_GAUCHE, iconeEdition);
                break;
            case 5: //Classe
                cellule = new CelluleTableauSimple(couleurBasique, " " + getClasse(Integer.parseInt(value + "")) + " ", CelluleTableauSimple.ALIGNE_GAUCHE, iconeEdition);
                break;
            case 6: //Date de naissance
                cellule = new CelluleTableauSimple(couleurBasique, " " + UtilInscription.getDateFrancais((Date) value) + " ", CelluleTableauSimple.ALIGNE_GAUCHE, iconeEdition);
                break;
            case 7: //Status
                cellule = new CelluleTableauSimple(couleurBasique, " " + getStatus(value) + " ", CelluleTableauSimple.ALIGNE_GAUCHE, iconeEdition);
                break;
            case 8: //Téléphone
                cellule = new CelluleTableauSimple(couleurBasique, " " + value + " ", CelluleTableauSimple.ALIGNE_GAUCHE, iconeEdition);
                break;
        }

        cellule.ecouterSelection(isSelected, row, getBeta(row), hasFocus);
        return cellule;
    }

    private int getBeta(int row) {
        if (this.modeleListeEleve != null) {
            InterfaceEleve Ieleve = this.modeleListeEleve.getEleve(row);
            if (Ieleve != null) {
                return Ieleve.getBeta();
            }
        }
        return InterfaceEleve.BETA_NOUVEAU;
    }
}
