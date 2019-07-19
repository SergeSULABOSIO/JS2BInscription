/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.ModeleTable_Insc;

import BEAN_BARRE_OUTILS.Bouton;
import BEAN_MenuContextuel.RubriqueSimple;
import SOURCES.Utilitaires_Insc.UtilInscription;
import Source.Callbacks.EcouteurSuppressionElement;
import Source.Callbacks.EcouteurValeursChangees;
import Source.Interface.InterfaceClasse;
import Source.Interface.InterfaceEleve;
import Source.Objet.Classe;
import Source.Objet.CouleurBasique;
import Source.Objet.Eleve;
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
    private Vector<Eleve> listeData = new Vector<>();
    private JScrollPane parent;
    private EcouteurValeursChangees ecouteurModele;
    private Vector<Classe> listeClasses = new Vector<>();
    private Vector<Eleve> listeDataExclus = new Vector<>();
    private Bouton btEnreg;
    private RubriqueSimple mEnreg;
    private CouleurBasique couleurBasique;

    public ModeleListeEleve(CouleurBasique couleurBasique, JScrollPane parent, Bouton btEnreg, RubriqueSimple mEnreg, Vector<Classe> listeClasses, EcouteurValeursChangees ecouteurModele) {
        this.parent = parent;
        this.couleurBasique = couleurBasique;
        this.ecouteurModele = ecouteurModele;
        this.listeClasses = listeClasses;
        this.mEnreg = mEnreg;
        this.btEnreg = btEnreg;
    }

    public void chercher(String motcle, int idclasse, int sexe, int status) {
        this.listeData.addAll(this.listeDataExclus);
        this.listeDataExclus.removeAllElements();
        for (Eleve Ieleve : this.listeData) {
            if (Ieleve != null) {
                search_verifier_status(motcle, status, idclasse, sexe, Ieleve);
            }
        }
        //En fin, on va nettoyer la liste - en enlevant tout objet qui a été black listé
        search_nettoyer();
    }

    private void search_verifier_motcle(String motcle, int idclasse, Eleve Ieleve) {
        if (Ieleve != null) {
            boolean isNeContientPasMotCle = (!(UtilInscription.contientMotsCles(Ieleve.getNom() + " " + Ieleve.getPostnom() + " " + Ieleve.getPrenom(), motcle)));
            if (isNeContientPasMotCle == true) {
                search_blacklister(Ieleve);
            }
        }
    }

    private void search_verifier_status(String motcle, int status, int idclasse, int sexe, Eleve Ieleve) {
        if (Ieleve != null) {
            if (status == -1) {
                //On ne fait rien
            } else if (Ieleve.getStatus() != status) {
                search_blacklister(Ieleve);
            }
            search_verifier_sexe(motcle, idclasse, sexe, Ieleve);
        }
    }

    private void search_verifier_sexe(String motcle, int idclasse, int sexe, Eleve Ieleve) {
        if (Ieleve != null) {
            if (sexe == -1) {
                //On ne fait rien
            } else if (Ieleve.getSexe() != sexe) {
                search_blacklister(Ieleve);
            }
            search_verifier_classe(motcle, idclasse, Ieleve);
        }
    }

    private void search_verifier_classe(String motcle, int idclasse, Eleve Ieleve) {
        if (Ieleve != null) {
            if (idclasse == -1) {
                //On ne fait rien
            } else if (Ieleve.getIdClasse() != idclasse) {
                search_blacklister(Ieleve);
            }
            search_verifier_motcle(motcle, idclasse, Ieleve);
        }
    }

    private void search_blacklister(Eleve Ieleve) {
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

    public void setListeEleves(Vector<Eleve> listeData) {
        this.listeData = listeData;
        redessinerTable();
    }

    public Eleve getEleve(int row) {
        if (row < listeData.size() && row != -1) {
            Eleve art = listeData.elementAt(row);
            if (art != null) {
                return art;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public Eleve getEleve_id(int id) {
        if (id != -1) {
            for (Eleve art : listeData) {
                if (id == art.getId()) {
                    return art;
                }
            }
        }
        return null;
    }

    public Eleve getEleve_signature(long signature) {
        if (signature != -1) {
            for (Eleve art : listeData) {
                if (signature == art.getSignature()) {
                    return art;
                }
            }
        }
        return null;
    }

    public Vector<Eleve> getListeData() {
        return this.listeData;
    }

    public void actualiser() {
        //System.out.println("actualiser - Enseignant...");
        redessinerTable();
    }

    public void AjouterEleve(Eleve newEleve) {
        this.listeData.add(0, newEleve);
        ecouteurModele.onValeurChangee();
        mEnreg.setCouleur(couleurBasique.getCouleur_foreground_objet_nouveau());                                        //mEnreg.setCouleur(Color.blue);
        btEnreg.setForeground(couleurBasique.getCouleur_foreground_objet_nouveau());                                   //btEnreg.setForeground(Color.blue);
        redessinerTable();
    }

    public void SupprimerEleve(int row, EcouteurSuppressionElement ecouteurSuppressionElement) {
        if (row < listeData.size() && row != -1) {
            Eleve articl = listeData.elementAt(row);
            if (articl != null) {
                int idASupp = articl.getId();
                int dialogResult = JOptionPane.showConfirmDialog(parent, "Etes-vous sûr de vouloir supprimer cette liste?", "Avertissement", JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    if (row <= listeData.size()) {
                        this.listeData.removeElementAt(row);
                        ecouteurSuppressionElement.onSuppressionConfirmee(idASupp);
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
        Eleve Ieleve = listeData.get(rowIndex);
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
        if (!avant.equals(apres)) {
            if (Ieleve.getBeta() == InterfaceEleve.BETA_EXISTANT) {
                Ieleve.setBeta(InterfaceEleve.BETA_MODIFIE);
                mEnreg.setCouleur(couleurBasique.getCouleur_foreground_objet_nouveau());                                        //mEnreg.setCouleur(Color.blue);
                btEnreg.setForeground(couleurBasique.getCouleur_foreground_objet_nouveau());                                   //btEnreg.setForeground(Color.blue);
            }
        }
        listeData.set(rowIndex, Ieleve);
        ecouteurModele.onValeurChangee();
        fireTableDataChanged();
    }

}