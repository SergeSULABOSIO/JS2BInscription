/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.ModeleTable;

import SOURCES.Callback.EcouteurValeursChangees;
import SOURCES.Interfaces.InterfaceEnseignant;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author HP Pavilion
 */
public class ModeleListeAyantDroit extends AbstractTableModel {

    private String[] titreColonnes = {"N°", "Nom", "Postnom", "Prénom", "Sexe", "Niveau d'étude"};
    private Vector<InterfaceEnseignant> listeData = new Vector<>();
    private JScrollPane parent;
    private EcouteurValeursChangees ecouteurModele;

    public ModeleListeAyantDroit(JScrollPane parent, EcouteurValeursChangees ecouteurModele) {
        this.parent = parent;
        this.ecouteurModele = ecouteurModele;
    }

    public void setListeClasses(Vector<InterfaceEnseignant> listeData) {
        this.listeData = listeData;
        redessinerTable();
    }

    public InterfaceEnseignant getEnseignant(int row) {
        if (row < listeData.size() && row != -1) {
            InterfaceEnseignant art = listeData.elementAt(row);
            if (art != null) {
                return art;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public InterfaceEnseignant getEnseignant_id(int id) {
        if (id != -1) {
            for (InterfaceEnseignant art : listeData) {
                if (id == art.getId()) {
                    return art;
                }
            }
        }
        return null;
    }
    
    public InterfaceEnseignant getEnseignant_signature(long signature) {
        if (signature != -1) {
            for (InterfaceEnseignant art : listeData) {
                if (signature == art.getSignature()) {
                    return art;
                }
            }
        }
        return null;
    }

    public Vector<InterfaceEnseignant> getListeData() {
        return this.listeData;
    }

    public void actualiser() {
        System.out.println("actualiser - Enseignant...");
        redessinerTable();
    }

    public void AjouterEnseignant(InterfaceEnseignant newEnseignant) {
        this.listeData.add(newEnseignant);
        ecouteurModele.onValeurChangee();
        redessinerTable();
    }

    public void SupprimerEnseignant(int row) {
        if (row < listeData.size() && row != -1) {
            InterfaceEnseignant articl = listeData.elementAt(row);
            if (articl != null) {
                int dialogResult = JOptionPane.showConfirmDialog(parent, "Etes-vous sûr de vouloir supprimer cette liste?", "Avertissement", JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    if (row <= listeData.size()) {
                        this.listeData.removeElementAt(row);
                    }
                    redessinerTable();
                }
            }
        }
    }

    public void viderListe() {
        int dialogResult = JOptionPane.showConfirmDialog(parent, "Etes-vous sûr de vouloir vider cette liste?", "Avertissement", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {
            this.listeData.removeAllElements();
            redessinerTable();
        }
    }

    private void redessinerTable() {
        ecouteurModele.onValeurChangee();
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return listeData.size();
    }

    @Override
    public int getColumnCount() {
        return titreColonnes.length;
    }

    @Override
    public String getColumnName(int column) {
        return titreColonnes[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        //{"N°", "Nom", "Postnom", "Prénom", "Sexe", "Niveau d'étude"};
        switch (columnIndex) {
            case 0:
                return (rowIndex + 1) + "";
            case 1:
                return listeData.elementAt(rowIndex).getNom();
            case 2:
                return listeData.elementAt(rowIndex).getPostnom();
            case 3:
                return listeData.elementAt(rowIndex).getPrenom();
            case 4:
                return listeData.elementAt(rowIndex).getSexe();
            case 5:
                return listeData.elementAt(rowIndex).getNiveauEtude();
            default:
                return "Null";
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        //{"N°", "Nom", "Postnom", "Prénom", "Sexe", "Niveau d'étude"};
        switch (columnIndex) {
            case 0:
                return String.class;//N°
            case 1:
                return String.class;//Nom
            case 2:
                return String.class;//postnom
            case 3:
                return String.class;//prenom
            case 4:
                return Integer.class;//sexe
            case 5:
                return Integer.class;//niveau d'étude
            default:
                return Object.class;
        }

    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if(columnIndex == 0){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        //{"N°", "Nom", "Postnom", "Prénom", "Sexe", "Niveau d'étude"};
        InterfaceEnseignant Iclasse = listeData.get(rowIndex);
        switch (columnIndex) {
            case 1:
                Iclasse.setNom(aValue + "");
                break;
            case 2:
                Iclasse.setPostnom(aValue + "");
                break;
            case 3:
                Iclasse.setPrenom(aValue + "");
                break;
            case 4:
                Iclasse.setSexe(Integer.parseInt(aValue + ""));
                break;
            case 5:
                Iclasse.setNiveauEtude(Integer.parseInt(aValue + ""));
                break;
            default:
                break;
        }
        listeData.set(rowIndex, Iclasse);
        ecouteurModele.onValeurChangee();
        fireTableDataChanged();
    }

}
