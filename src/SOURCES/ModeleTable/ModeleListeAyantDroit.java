/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.ModeleTable;

import BEAN_BARRE_OUTILS.Bouton;
import BEAN_MenuContextuel.RubriqueSimple;
import ICONES.Icones;
import SOURCES.Callback.EcouteurUpdateClose;
import SOURCES.Callback.EcouteurValeursChangees;
import SOURCES.Interfaces.InterfaceAyantDroit;
import SOURCES.Interfaces.InterfaceEleve;
import SOURCES.Interfaces.InterfaceFrais;
import SOURCES.Utilitaires.LiaisonEleveFrais;
import SOURCES.Utilitaires.Util;
import java.awt.Color;
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
    private Vector<InterfaceAyantDroit> listeDataExclus = new Vector<>();
    private Vector<InterfaceFrais> listeFrais = new Vector<>();
    private JScrollPane parent;
    private EcouteurValeursChangees ecouteurModele;
    private ModeleListeEleve modeleListeEleve;
    private EcouteurUpdateClose ecouteurClose;
    private Bouton btEnreg;
    private RubriqueSimple mEnreg;

    public ModeleListeAyantDroit(JScrollPane parent, Bouton btEnreg, RubriqueSimple mEnreg, Vector<InterfaceFrais> listeFrais, ModeleListeEleve modeleListeEleve, EcouteurValeursChangees ecouteurModele, EcouteurUpdateClose ecouteurClose) {
        this.parent = parent;
        this.ecouteurModele = ecouteurModele;
        this.listeFrais = listeFrais;
        this.modeleListeEleve = modeleListeEleve;
        this.ecouteurClose = ecouteurClose;
        this.mEnreg = mEnreg;
        this.btEnreg = btEnreg;
        //System.out.println(" * ModeleListeFrais");
    }

    public void chercher(Vector<InterfaceEleve> listeEleveFiltres) {
        //System.out.println("Taile Eleves: " + listeEleveFiltres.size());
        this.listeData.addAll(this.listeDataExclus);
        this.listeDataExclus.removeAllElements();
        for (InterfaceAyantDroit Iayant : this.listeData) {
            boolean canBL = true;
            for (InterfaceEleve Ieleve : listeEleveFiltres) {
                if (Iayant.getSignatureEleve() == Ieleve.getSignature()) {
                    canBL = false;
                }
            }
            if (canBL == true) {
                search_blacklister(Iayant);
            }
        }
        //En fin, on va nettoyer la liste - en enlevant tout objet qui a été black listé
        search_nettoyer();
    }

    private void search_blacklister(InterfaceAyantDroit Iayant) {
        if (Iayant != null && this.listeDataExclus != null) {
            if (!listeDataExclus.contains(Iayant)) {
                this.listeDataExclus.add(Iayant);
            }
        }
    }

    private void search_nettoyer() {
        if (this.listeDataExclus != null && this.listeData != null) {
            this.listeDataExclus.forEach((IeleveASupp) -> {
                this.listeData.removeElement(IeleveASupp);
            });
            redessinerTable();
        }
    }

    public void setListeAyantDroit(Vector<InterfaceAyantDroit> listeData) {
        this.listeData = listeData;
        redessinerTable();
    }

    private InterfaceEleve getEleve(long signatureEleve) {
        for (InterfaceEleve eleve : this.modeleListeEleve.getListeData()) {
            if (eleve.getSignature() == signatureEleve) {
                return eleve;
            }
        }
        return null;
    }

    private void updateEleve(InterfaceAyantDroit IayAyantDroit) {
        InterfaceEleve newEleve = getEleve(IayAyantDroit.getSignatureEleve());
        if (newEleve != null) {
            IayAyantDroit.setEleve(newEleve.getNom() + " " + newEleve.getPostnom() + " " + newEleve.getPrenom());
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
        this.listeData.add(0, newFrais);
        mEnreg.setCouleur(Color.blue);
        btEnreg.setCouleur(Color.blue);
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
            for (InterfaceFrais Ifrais : this.listeFrais) {
                String titre = Ifrais.getNom();
                String SmontantDefaut = Util.getMontantFrancais(Ifrais.getMontant_default());
                String monnaie = Ifrais.getMonnaie();
                if (10 < titre.trim().length()) {
                    titresCols.add(titre.substring(0, 7) + "...(" + SmontantDefaut + " " + monnaie + ")"); //j'ai l'itention de limité la taille de titre de la colonne
                } else {
                    titresCols.add(titre + " (" + SmontantDefaut + " " + monnaie + ")");
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

    private InterfaceAyantDroit getAyantDroit(long signatureEleve) {
        for (InterfaceAyantDroit IayantD : this.listeData) {
            if (IayantD.getSignatureEleve() == signatureEleve) {
                return IayantD;
            }
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        //{"N°", "Elève"};
        InterfaceAyantDroit IayantDroit = listeData.get(rowIndex);
        String avant = IayantDroit.toString() + "" + IayantDroit.getListeLiaisons().toString();
        //System.out.println("Avant: " + avant);
        boolean canAdd = false;
        if (columnIndex < 2) {
            switch (columnIndex) {
                case 1:
                    long signatureEleve = Long.parseLong(aValue + "");
                    //Contrôle anti doublon
                    InterfaceAyantDroit ayantDroiExistant = getAyantDroit(signatureEleve);
                    if (ayantDroiExistant == null) {
                        IayantDroit.setSignatureEleve(signatureEleve);
                        updateEleve(IayantDroit);
                        canAdd = true;
                    } else {
                        if (this.ecouteurClose != null) {
                            String message = "Désolé, " + (ayantDroiExistant.getEleve().trim()) + " figure déjà dans cette liste comme ayant-droit.";
                            this.ecouteurClose.onActualiser(message, new Icones().getAdministrateur_01());
                        }
                        canAdd = false;
                    }
                    break;
                default:
                    break;
            }
        } else {
            IayantDroit.getListeLiaisons().elementAt(columnIndex - 2).setMontant(Double.parseDouble(aValue + ""));
        }

        String apres = IayantDroit.toString() + "" + IayantDroit.getListeLiaisons().toString();
        //System.out.println("Après: " + apres);
        if (!avant.equals(apres)) {
            if (IayantDroit.getBeta() == InterfaceAyantDroit.BETA_EXISTANT) {
                IayantDroit.setBeta(InterfaceAyantDroit.BETA_MODIFIE);
                mEnreg.setCouleur(Color.blue);
                btEnreg.setCouleur(Color.blue);
            }
        }

        if (canAdd == true) {
            listeData.set(rowIndex, IayantDroit);
            ecouteurModele.onValeurChangee();
            fireTableDataChanged();
        }
    }

}
