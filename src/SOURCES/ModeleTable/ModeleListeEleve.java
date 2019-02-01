/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.ModeleTable;

import SOURCES.Callback.EcouteurValeursChangees;
import SOURCES.Interfaces.InterfaceClasse;
import SOURCES.Interfaces.InterfaceEleve;
import SOURCES.Utilitaires.XX_Eleve;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author HP Pavilion
 */
public class ModeleListeEleve extends AbstractTableModel {

    private String[] titreColonnes = {"N°", "Nom", "Postnom", "Prénom", "Sexe", "Classe", "Date naiss.", "Status", "Téléphone"};
    private Vector<InterfaceEleve> listeData = new Vector<>();
    private JScrollPane parent;
    private EcouteurValeursChangees ecouteurModele;
    private Vector<InterfaceClasse> listeClasses = new Vector<>();
    private Vector<InterfaceEleve> listeDataExclus = new Vector<>();

    public ModeleListeEleve(JScrollPane parent, Vector<InterfaceClasse> listeClasses, EcouteurValeursChangees ecouteurModele) {
        this.parent = parent;
        this.ecouteurModele = ecouteurModele;
        this.listeClasses = listeClasses;
    }

    public void chercher(String motcle, int idclasse, int sexe, int status) {
        this.listeData.addAll(this.listeDataExclus);
        this.listeDataExclus.removeAllElements();
        for (InterfaceEleve Ieleve : this.listeData) {
            if (Ieleve != null) {
                search_verifier_status(motcle, status, idclasse, sexe, Ieleve);
            }
        }
        //En fin, on va nettoyer la liste - en enlevant tout objet qui a été black listé
        search_nettoyer();
    }

    private void search_verifier_motcle(String motcle, int idclasse, InterfaceEleve Ieleve) {
        if (Ieleve != null) {
            boolean isNeContientPasMotCle = (!(Ieleve.getNom().toLowerCase().contains(motcle.toLowerCase()))
                    && !(Ieleve.getPostnom().toLowerCase().contains(motcle.toLowerCase()))
                    && !(Ieleve.getPrenom().toLowerCase().contains(motcle.toLowerCase())));

            if (isNeContientPasMotCle == true) {
                search_blacklister(Ieleve);
            }
        }
    }

    private void search_verifier_status(String motcle, int status, int idclasse, int sexe, InterfaceEleve Ieleve) {
        if (Ieleve != null) {
            if (status == -1) {
                //On ne fait rien
            } else if (Ieleve.getStatus() != status) {
                search_blacklister(Ieleve);
            }
            search_verifier_sexe(motcle, idclasse, sexe, Ieleve);
        }
    }

    private void search_verifier_sexe(String motcle, int idclasse, int sexe, InterfaceEleve Ieleve) {
        if (Ieleve != null) {
            if (sexe == -1) {
                //On ne fait rien
            } else if (Ieleve.getSexe() != sexe) {
                search_blacklister(Ieleve);
            }
            search_verifier_classe(motcle, idclasse, Ieleve);
        }
    }

    private void search_verifier_classe(String motcle, int idclasse, InterfaceEleve Ieleve) {
        if (Ieleve != null) {
            if (idclasse == -1) {
                //On ne fait rien
            } else if (Ieleve.getIdClasse() != idclasse) {
                search_blacklister(Ieleve);
            }
            search_verifier_motcle(motcle, idclasse, Ieleve);
        }
    }

    private void search_blacklister(InterfaceEleve Ieleve) {
        if (Ieleve != null && this.listeDataExclus != null) {
            if (!listeDataExclus.contains(Ieleve)) {
                this.listeDataExclus.add(Ieleve);
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

    public void setListeEleves(Vector<InterfaceEleve> listeData) {
        this.listeData = listeData;
        redessinerTable();
    }

    public InterfaceEleve getEleve(int row) {
        if (row < listeData.size() && row != -1) {
            InterfaceEleve art = listeData.elementAt(row);
            if (art != null) {
                return art;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public InterfaceEleve getEleve_id(int id) {
        if (id != -1) {
            for (InterfaceEleve art : listeData) {
                if (id == art.getId()) {
                    return art;
                }
            }
        }
        return null;
    }

    public InterfaceEleve getEleve_signature(long signature) {
        if (signature != -1) {
            for (InterfaceEleve art : listeData) {
                if (signature == art.getSignature()) {
                    return art;
                }
            }
        }
        return null;
    }

    public Vector<InterfaceEleve> getListeData() {
        return this.listeData;
    }

    public void actualiser() {
        //System.out.println("actualiser - Enseignant...");
        redessinerTable();
    }

    public void AjouterEleve(InterfaceEleve newEleve) {
        this.listeData.add(0, newEleve);
        ecouteurModele.onValeurChangee();
        redessinerTable();
    }

    public void SupprimerEleve(int row) {
        if (row < listeData.size() && row != -1) {
            InterfaceEleve articl = listeData.elementAt(row);
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
        //{"N°", "Nom", "Postnom", "Prénom", "Sexe", "Classe", "Date naiss.", "Status", "Téléphone (parents)"}
        InterfaceEleve Ieleve = listeData.get(rowIndex);
        switch (columnIndex) {
            case 0: //N°
                return (rowIndex + 1) + "";
            case 1: //Nom
                return Ieleve.getNom();
            case 2: //Postnom
                return Ieleve.getPostnom();
            case 3: //Prenom
                return Ieleve.getPrenom();
            case 4: //Sexe
                return Ieleve.getSexe();
            case 5: //Classe
                return Ieleve.getIdClasse();
            case 6: //Date de naissance
                return Ieleve.getDateNaissance();
            case 7: //Status
                return Ieleve.getStatus();
            case 8: //Telephone
                return Ieleve.getTelephonesParents();
            default:
                return "Null";
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        //{"N°", "Nom", "Postnom", "Prénom", "Sexe", "Classe", "Date naiss.", "Status", "Téléphone (parents)"}
        switch (columnIndex) {
            case 0: //N°
                return String.class;
            case 1: //Nom
                return String.class;
            case 2: //Postnom
                return String.class;
            case 3: //Prenom
                return String.class;
            case 4: // Sexe
                return Integer.class;
            case 5: // Classe
                return Integer.class;
            case 6: // Date de naissance
                return Date.class;
            case 7: //Status
                return Integer.class;
            case 8: //Téléphone des parents
                return String.class;
            default:
                return Object.class;
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

    private InterfaceClasse getClasse(int idClasse) {
        for (InterfaceClasse clss : this.listeClasses) {
            if (clss.getId() == idClasse) {
                return clss;
            }
        }
        return null;
    }

    private void updateClasse(InterfaceEleve Ieleve) {
        InterfaceClasse newClasse = getClasse(Ieleve.getIdClasse());
        if (newClasse != null) {
            Ieleve.setClasse(newClasse.getNom());
        }
        redessinerTable();
    }
    
    

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        //{"N°", "Nom", "Postnom", "Prénom", "Sexe", "Classe", "Date naiss.", "Lieu de naiss.", "Téléphone"}
        InterfaceEleve Ieleve = listeData.get(rowIndex);
        String avant = Ieleve.toString();
        switch (columnIndex) {
            case 1: //Nom
                Ieleve.setNom(aValue + "");
                break;
            case 2: //Postnom
                Ieleve.setPostnom(aValue + "");
                break;
            case 3: //Prenom
                Ieleve.setPrenom(aValue + "");
                break;
            case 4: //Sexe
                Ieleve.setSexe(Integer.parseInt(aValue + ""));
                break;
            case 5: //Classe
                Ieleve.setIdClasse(Integer.parseInt(aValue + ""));
                updateClasse(Ieleve);
                break;
            case 6: //Date de naissance
                Ieleve.setDateNaissance((Date) aValue);
                break;
            case 7: //Status
                Ieleve.setStatus(Integer.parseInt(aValue + ""));
                break;
            case 8: //Téléphone des parents
                Ieleve.setTelephonesParents(aValue + "");
                break;
            default:
                break;
        }
        String apres = Ieleve.toString();
        if(!avant.equals(apres)){
            if(Ieleve.getId() != -1){
                Ieleve.setBeta(InterfaceEleve.BETA_MODIFIE);
            }
            //System.out.println(avant);
            //System.out.println(Ieleve.toString());
        }
        listeData.set(rowIndex, Ieleve);
        ecouteurModele.onValeurChangee();
        fireTableDataChanged();
    }

}
