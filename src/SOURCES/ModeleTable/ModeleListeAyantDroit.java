/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.ModeleTable;

import SOURCES.Callback.EcouteurValeursChangees;
import SOURCES.Interfaces.InterfaceAyantDroit;
import SOURCES.Interfaces.InterfaceClasse;
import SOURCES.Interfaces.InterfaceEleve;
import SOURCES.Interfaces.InterfaceFrais;
import SOURCES.Utilitaires.LiaisonEleveFrais;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author HP Pavilion
 */


public class ModeleListeAyantDroit extends AbstractTableModel {

    private String[] titreColonnes = null;
    private Vector<InterfaceAyantDroit> listeData = new Vector<>();
    private Vector<InterfaceFrais> listeFrais = new Vector<>();
    private JScrollPane parent;
    private EcouteurValeursChangees ecouteurModele;
    private ModeleListeEleve modeleListeEleve;

    public ModeleListeAyantDroit(JScrollPane parent, Vector<InterfaceFrais> listeFrais, ModeleListeEleve modeleListeEleve, EcouteurValeursChangees ecouteurModele) {
        this.parent = parent;
        this.ecouteurModele = ecouteurModele;
        this.listeFrais = listeFrais;
        this.modeleListeEleve = modeleListeEleve;
        //System.out.println(" * ModeleListeFrais");
    }

    public void setListeAyantDroit(Vector<InterfaceAyantDroit> listeData) {
        this.listeData = listeData;
        redessinerTable();
    }
    
    
    private InterfaceEleve getEleve(long signatureEleve){
        for(InterfaceEleve eleve : this.modeleListeEleve.getListeData()){
            if(eleve.getSignature() == signatureEleve){
                return eleve;
            }
        }
        return null;
    }
    
    
    private void updateEleve(InterfaceAyantDroit IayAyantDroit){
        InterfaceEleve newEleve = getEleve(IayAyantDroit.getSignatureEleve());
        if(newEleve != null){
            IayAyantDroit.setEleve(newEleve.getNom()+" " + newEleve.getPostnom()+" " + newEleve.getPrenom());
        }
        redessinerTable();
    }

    public InterfaceAyantDroit getAyantDroit(int row) {
        if (row < listeData.size() && row != -1) {
            InterfaceAyantDroit art = listeData.elementAt(row);
            if (art != null) {
                return art;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public InterfaceAyantDroit getAyantDroit_id(int id) {
        if (id != -1) {
            for (InterfaceAyantDroit art : listeData) {
                if (id == art.getId()) {
                    return art;
                }
            }
        }
        return null;
    }

    public Vector<InterfaceAyantDroit> getListeData() {
        return this.listeData;
    }

    public void AjouterAyantDroit(InterfaceAyantDroit newFrais) {
        this.chargerLiaisons(newFrais);
        this.listeData.add(newFrais);
        redessinerTable();
        //lister();
    }

    private void chargerLiaisons(InterfaceAyantDroit newAyantDroit) {
        //On charge d'abord les liaisons possibles
        this.listeFrais.forEach((frais) -> {
            double montantRabais = 0;
            LiaisonEleveFrais liaison = new LiaisonEleveFrais(newAyantDroit.getSignatureEleve(), frais.getId(), montantRabais, frais.getIdMonnaie(), frais.getMonnaie());
            newAyantDroit.ajouterLiaisons(liaison);
        });
    }

    public void SupprimerAyantDroit(int row) {
        if (row < listeData.size() && row != -1) {
            InterfaceAyantDroit articl = listeData.elementAt(row);
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

    public void redessinerTable() {
        ecouteurModele.onValeurChangee();
        fireTableDataChanged();
    }

    public void actualiser() {
        //System.out.println("actualiser - Ayant-Droit...");
        redessinerTable();
        //lister();
    }

    @Override
    public int getRowCount() {
        return listeData.size();
    }

    private void initTitresColonnes() {
        Vector titresCols = new Vector();
        titresCols.add("N°");
        titresCols.add("Elève");
        if (this.listeFrais != null) {
            for (InterfaceFrais classe : this.listeFrais) {
                String titre = classe.getNom();
                if(10<titre.trim().length()){
                    titresCols.add(titre.substring(0, 7)+"..."); //j'ai l'itention de limité la taille de titre de la colonne
                }else{
                    titresCols.add(titre);
                }
            }
        }

        //On verse les titres dans le tableau static
        this.titreColonnes = new String[titresCols.size()];
        for (int i = 0; i < titreColonnes.length; i++) {
            this.titreColonnes[i] = titresCols.elementAt(i) + "";
        }
    }


    @Override
    public int getColumnCount() {
        initTitresColonnes();
        return titreColonnes.length;
    }

    @Override
    public String getColumnName(int column) {
        initTitresColonnes();
        return titreColonnes[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        //{"N°", "Elève"};
        if (columnIndex < 2) {
            switch (columnIndex) {
                case 0:
                    return (rowIndex + 1) + "";
                case 1:
                    return listeData.elementAt(rowIndex).getSignatureEleve();
                default:
                    return "Nullo";
            }
        } else {
            Vector<LiaisonEleveFrais> liaisons = listeData.elementAt(rowIndex).getListeLiaisons();
            if (!liaisons.isEmpty()) {
                return listeData.elementAt(rowIndex).getListeLiaisons().elementAt(columnIndex - 2).getMontant();
            } else {
                return "RAS";
            }
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        //{"N°", "Elève"};
        switch (columnIndex) {
            case 0://N°
                return String.class;
            case 1://Elève
                return String.class;
            default:
                return Double.class;
        }

    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return false;
        } else {
            return true;
        }
    }

    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        //{"N°", "Elève"};
        InterfaceAyantDroit IayantDroit = listeData.get(rowIndex);
        if (columnIndex < 2) {
            switch (columnIndex) {
                case 1:
                    long signatureEleve = Long.parseLong(aValue + "");
                    IayantDroit.setSignatureEleve(signatureEleve);
                    updateEleve(IayantDroit);
                    break;
                default:
                    break;
            }
        } else {
            listeData.elementAt(rowIndex).getListeLiaisons().elementAt(columnIndex - 2).setMontant(Double.parseDouble(aValue + ""));
        }
        listeData.set(rowIndex, IayantDroit);
        ecouteurModele.onValeurChangee();
        fireTableDataChanged();
    }

}
