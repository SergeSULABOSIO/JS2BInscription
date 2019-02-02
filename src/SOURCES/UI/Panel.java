/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.UI;

import BEAN_BARRE_OUTILS.BarreOutils;
import BEAN_BARRE_OUTILS.Bouton;
import BEAN_BARRE_OUTILS.BoutonListener;
import BEAN_MenuContextuel.MenuContextuel;
import BEAN_MenuContextuel.RubriqueListener;
import BEAN_MenuContextuel.RubriqueSimple;
import ICONES.Icones;
import SOURCES.Callback.EcouteurAjout;
import SOURCES.Callback.EcouteurEleveAyantDroit;
import SOURCES.Callback.EcouteurEnregistrement;
import SOURCES.Callback.EcouteurUpdateClose;
import SOURCES.Callback.EcouteurValeursChangees;
import SOURCES.EditeurTable.EditeurClasse;
import SOURCES.EditeurTable.EditeurDate;
import SOURCES.EditeurTable.EditeurEleve;
import SOURCES.EditeurTable.EditeurSexe;
import SOURCES.EditeurTable.EditeurStatus;
import SOURCES.GenerateurPDF.DocumentPDF;
import SOURCES.Interfaces.InterfaceAyantDroit;
import SOURCES.Interfaces.InterfaceClasse;
import SOURCES.Interfaces.InterfaceEleve;
import SOURCES.Interfaces.InterfaceEntreprise;
import SOURCES.ModeleTable.ModeleListeAyantDroit;
import SOURCES.ModeleTable.ModeleListeEleve;
import SOURCES.RenduTable.RenduTableAyantDroit;
import SOURCES.RenduTable.RenduTableEleve;
import SOURCES.MoteurRecherche.MoteurRecherche;
import SOURCES.RenduComboBox.RenduCombo;
import SOURCES.Utilitaires.DonneesInscription;
import SOURCES.Utilitaires.LiaisonEleveFrais;
import SOURCES.Utilitaires.ParametreInscription;
import SOURCES.Utilitaires.SortiesEleveAyantDroit;
import SOURCES.Utilitaires.XX_Ayantdroit;
import SOURCES.Utilitaires.XX_Eleve;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.table.TableColumn;

/**
 *
 * @author HP Pavilion
 */
public class Panel extends javax.swing.JPanel {

    /**
     * Creates new form Panel
     */
    public int indexTabSelected = 0;
    private Icones icones = null;
    private final JTabbedPane parent;
    private Panel moi = null;
    private EcouteurUpdateClose ecouteurClose = null;
    private EcouteurAjout ecouteurAjout = null;
    private Bouton btEnregistrer, btAjouter, btSupprimer, btVider, btImprimer, btPDF, btFermer, btActualiser;
    private RubriqueSimple mEnregistrer, mAjouter, mSupprimer, mVider, mImprimer, mPDF, mFermer, mActualiser;
    private MenuContextuel menuContextuel = null;
    private BarreOutils bOutils = null;
    private EcouteurEleveAyantDroit ecouteurEleveAyantDroit = null;

    private ModeleListeEleve modeleListeEleve;
    private ModeleListeAyantDroit modeleListeAyantDroit;
    private EditeurEleve editeurEleve = null;
    private MoteurRecherche gestionnaireRecherche;

    public DonneesInscription donneesInscription;
    public ParametreInscription parametreInscription;

    public Panel(JTabbedPane parent, DonneesInscription donneesInscription, ParametreInscription parametreInscription, EcouteurEleveAyantDroit ecouteurEleveAyantDroit) {
        this.initComponents();
        this.parent = parent;
        this.init();
        this.donneesInscription = donneesInscription;
        this.parametreInscription = parametreInscription;
        this.ecouteurEleveAyantDroit = ecouteurEleveAyantDroit;

        //Initialisaterus
        this.parametrerTableEleves();
        this.parametrerTableAyantDroit();
        this.setIconesTabs();
        this.activerMoteurRecherche();
        this.initCombos();
    }

    public InterfaceEntreprise getEntreprise() {
        return this.parametreInscription.getEntreprise();
    }

    public int getIndexTabSelected() {
        return indexTabSelected;
    }

    public String getNomUtilisateur() {
        return this.parametreInscription.getNomUtilisateur();
    }

    public String getTitreDoc() {
        if (indexTabSelected == 0) {
            return "LISTE D'ELEVES";
        } else {
            return "LISTE D'ELEVES AYANT-DROITS";
        }
    }

    public Date getDateDocument() {
        return new Date();
    }

    private void initCombos() {
        //Les sexes
        chSexe.removeAllItems();
        chSexe.addItem("MASCULIN & FEMININ");
        chSexe.addItem("MASCULIN");
        chSexe.addItem("FEMININ");
        chSexe.setRenderer(new RenduCombo(icones.getClient_01()));

        //Les status
        chStatus.removeAllItems();
        chStatus.addItem("REGULIER(E) & EXCLU(E)");
        chStatus.addItem("REGULIER(E)");
        chStatus.addItem("EXCLU(E)");
        chStatus.setRenderer(new RenduCombo(icones.getAimer_01()));

        //Les calsses
        chClasse.removeAllItems();
        chClasse.addItem("TOUTES LES CLASSES");
        if (this.parametreInscription.getListeClasses() != null) {
            for (InterfaceClasse iClasse : this.parametreInscription.getListeClasses()) {
                chClasse.addItem(iClasse.getNom() + "");
            }
        }
        chClasse.setRenderer(new RenduCombo(icones.getClasse_01()));

        chRecherche.setTextInitial("Recherche : Saisissez votre mot clé ici, puis tapez ENTER");
        activerCriteres();
    }

    public String getCritereSexe() {
        return this.chSexe.getSelectedItem() + "";
    }

    public String getCritereClasse() {
        return this.chClasse.getSelectedItem() + "";
    }

    public String getCritereStatus() {
        return this.chStatus.getSelectedItem() + "";
    }

    private void activerMoteurRecherche() {
        gestionnaireRecherche = new MoteurRecherche(icones, chRecherche, ecouteurClose) {

            @Override
            public void chercher(String motcle) {
                //classe
                int idClasse = -1;
                for (InterfaceClasse iClasse : parametreInscription.getListeClasses()) {
                    if (iClasse.getNom().trim().equals(chClasse.getSelectedItem() + "")) {
                        idClasse = iClasse.getId();
                        break;
                    }
                }

                //Sexe
                int sexe = -1;
                switch (chSexe.getSelectedIndex()) {
                    case 1:
                        sexe = InterfaceEleve.SEXE_MASCULIN;
                        break;
                    case 2:
                        sexe = InterfaceEleve.SEXE_FEMININ;
                        break;
                    default:
                        sexe = -1;
                        break;
                }

                //status
                int status = -1;
                switch (chStatus.getSelectedIndex()) {
                    case 1:
                        status = InterfaceEleve.STATUS_ACTIF;
                        break;
                    case 2:
                        status = InterfaceEleve.STATUS_INACTIF;
                        break;
                    default:
                        status = -1;
                        break;
                }

                modeleListeEleve.chercher(motcle, idClasse, sexe, status);
                if (modeleListeAyantDroit != null) {
                    modeleListeAyantDroit.chercher(modeleListeEleve.getListeData());
                }
            }
        };
    }

    private void parametrerTableEleves() {
        this.modeleListeEleve = new ModeleListeEleve(scrollListeEleves, this.parametreInscription.getListeClasses(), new EcouteurValeursChangees() {
            @Override
            public void onValeurChangee() {
                if (modeleListeAyantDroit != null) {
                    modeleListeAyantDroit.actualiser();
                }
                if (ecouteurClose != null) {
                    ecouteurClose.onActualiser(modeleListeEleve.getRowCount() + " élement(s).", icones.getClient_01());
                }
            }
        });

        //Parametrage du modele contenant les données de la table
        this.tableListeEleves.setModel(this.modeleListeEleve);

        if (this.donneesInscription != null) {
            if (this.donneesInscription.getListeEleves().size() != 0) {
                this.modeleListeEleve.setListeEleves(this.donneesInscription.getListeEleves());
            }
        }

        //Parametrage du rendu de la table
        this.tableListeEleves.setDefaultRenderer(Object.class, new RenduTableEleve(icones.getModifier_01(), this.modeleListeEleve, this.parametreInscription.getListeClasses()));
        this.tableListeEleves.setRowHeight(25);

        //{"N°", "Nom", "Postnom", "Prénom", "Sexe", "Classe", "Date naiss.", "Lieu de naiss.", "Téléphone (parents)"}
        TableColumn col_No = this.tableListeEleves.getColumnModel().getColumn(0);
        col_No.setPreferredWidth(40);
        col_No.setMaxWidth(40);

        TableColumn colNom = this.tableListeEleves.getColumnModel().getColumn(1);
        colNom.setPreferredWidth(150);

        TableColumn colPostnom = this.tableListeEleves.getColumnModel().getColumn(2);
        colPostnom.setPreferredWidth(150);

        TableColumn colPrenom = this.tableListeEleves.getColumnModel().getColumn(3);
        colPrenom.setPreferredWidth(150);

        TableColumn colSexe = this.tableListeEleves.getColumnModel().getColumn(4);
        colSexe.setCellEditor(new EditeurSexe());
        colSexe.setPreferredWidth(140);
        colSexe.setMaxWidth(140);

        TableColumn colClasse = this.tableListeEleves.getColumnModel().getColumn(5);
        colClasse.setCellEditor(new EditeurClasse(this.parametreInscription.getListeClasses()));
        colClasse.setPreferredWidth(90);
        colClasse.setMaxWidth(90);

        TableColumn colDateNaissance = this.tableListeEleves.getColumnModel().getColumn(6);
        colDateNaissance.setCellEditor(new EditeurDate());
        colDateNaissance.setPreferredWidth(150);

        TableColumn colStatus = this.tableListeEleves.getColumnModel().getColumn(7);
        colStatus.setCellEditor(new EditeurStatus());
        colStatus.setPreferredWidth(120);

        TableColumn colTelephone = this.tableListeEleves.getColumnModel().getColumn(8);
        colTelephone.setPreferredWidth(200);
    }

    private void parametrerTableAyantDroit() {
        this.modeleListeAyantDroit = new ModeleListeAyantDroit(scrollListeAyantDroit, this.parametreInscription.getListeFraises(), this.modeleListeEleve, new EcouteurValeursChangees() {
            @Override
            public void onValeurChangee() {

            }
        }, this.ecouteurClose);

        //Parametrage du modele contenant les données de la table
        this.tableListeAyantDroit.setModel(this.modeleListeAyantDroit);

        if (this.donneesInscription != null) {
            if (this.donneesInscription.getListeAyantDroit().size() != 0) {
                this.modeleListeAyantDroit.setListeAyantDroit(this.donneesInscription.getListeAyantDroit());
            }
        }

        this.editeurEleve = new EditeurEleve(this.modeleListeEleve, this.modeleListeAyantDroit);

        //Parametrage du rendu de la table
        this.tableListeAyantDroit.setDefaultRenderer(Object.class, new RenduTableAyantDroit(icones.getModifier_01(), this.modeleListeEleve, modeleListeAyantDroit));
        this.tableListeAyantDroit.setRowHeight(25);

        //{"N°", "Eleve", ...liste des frais}
        TableColumn col_No = this.tableListeAyantDroit.getColumnModel().getColumn(0);
        col_No.setPreferredWidth(40);
        col_No.setMaxWidth(40);

        TableColumn colEleve = this.tableListeAyantDroit.getColumnModel().getColumn(1);
        colEleve.setCellEditor(this.editeurEleve);
        colEleve.setPreferredWidth(150);

        //Redimensionnement de la colonne des frais
        /*
        int index = 1;
        for (InterfaceFrais frais : this.parametreInscription.getListeFraises()) {
            TableColumn colFrais = this.tableListeAyantDroit.getColumnModel().getColumn(index);
            //colFrais.setPreferredWidth(40);
            //colFrais.setMaxWidth(40);
            index++;
        }
         */
    }

    private void setBoutons() {
        btAjouter = new Bouton(12, "Ajouter", icones.getAjouter_02(), new BoutonListener() {
            @Override
            public void OnEcouteLeClick() {
                ajouter();
            }
        });

        btSupprimer = new Bouton(12, "Supprimer", icones.getSupprimer_02(), new BoutonListener() {
            @Override
            public void OnEcouteLeClick() {
                supprimer();
            }
        });

        btEnregistrer = new Bouton(12, "Enregistrer", icones.getEnregistrer_02(), new BoutonListener() {
            @Override
            public void OnEcouteLeClick() {
                enregistrer();
            }
        });

        btVider = new Bouton(12, "Vider", icones.getAnnuler_02(), new BoutonListener() {
            @Override
            public void OnEcouteLeClick() {
                vider();
            }
        });

        btImprimer = new Bouton(12, "Imprimer", icones.getImprimer_02(), new BoutonListener() {
            @Override
            public void OnEcouteLeClick() {
                imprimer();
            }
        });

        btFermer = new Bouton(12, "Fermer", icones.getFermer_02(), new BoutonListener() {
            @Override
            public void OnEcouteLeClick() {
                fermer();
            }
        });

        btPDF = new Bouton(12, "Exp. en PDF", icones.getPDF_02(), new BoutonListener() {
            @Override
            public void OnEcouteLeClick() {
                exporterPDF();
            }
        });

        btActualiser = new Bouton(12, "Actualiser", icones.getSynchroniser_02(), new BoutonListener() {
            @Override
            public void OnEcouteLeClick() {
                actualiser();
            }
        });

        bOutils = new BarreOutils(barreOutils);
        bOutils.AjouterBouton(btEnregistrer);
        bOutils.AjouterSeparateur();
        bOutils.AjouterBouton(btAjouter);
        bOutils.AjouterBouton(btSupprimer);
        bOutils.AjouterBouton(btVider);
        bOutils.AjouterBouton(btActualiser);
        bOutils.AjouterSeparateur();
        bOutils.AjouterBouton(btImprimer);
        bOutils.AjouterBouton(btPDF);
        bOutils.AjouterSeparateur();
        bOutils.AjouterBouton(btFermer);
    }

    private void ecouterMenContA(java.awt.event.MouseEvent evt, int tab) {
        if (evt.getButton() == MouseEvent.BUTTON3) {
            switch (tab) {
                case 0: //Tab Monnaie
                    menuContextuel.afficher(scrollListeEleves, evt.getX(), evt.getY());
                    break;
                case 1: //Tab classe
                    menuContextuel.afficher(scrollListeAyantDroit, evt.getX(), evt.getY());
                    break;
            }
        }
        switch (tab) {
            case 0://Tab Eleve
                InterfaceEleve eleve = modeleListeEleve.getEleve(tableListeEleves.getSelectedRow());
                if (eleve != null) {
                    this.ecouteurClose.onActualiser(eleve.getNom() + " " + eleve.getPostnom() + " " + eleve.getPrenom() + ".", icones.getClient_01());
                }
                break;
            case 1://Tab Ayant-Droit
                InterfaceAyantDroit ayantD = modeleListeAyantDroit.getAyantDroit(tableListeAyantDroit.getSelectedRow());
                if (ayantD != null) {
                    this.ecouteurClose.onActualiser(ayantD.getEleve(), icones.getAdministrateur_01());
                }
                break;
        }

    }

    public void init() {
        this.icones = new Icones();
        this.moi = this;
        this.chRecherche.setIcon(icones.getChercher_01());
        this.labInfos.setIcon(icones.getInfos_01());
        this.labInfos.setText("Prêt.");

        this.ecouteurClose = new EcouteurUpdateClose() {
            @Override
            public void onFermer() {
                parent.remove(moi);
            }

            @Override
            public void onActualiser(String texte, ImageIcon icone) {
                labInfos.setText(texte);
                labInfos.setIcon(icone);
            }
        };

        this.ecouteurAjout = new EcouteurAjout() {
            @Override
            public void setAjoutEleve(ModeleListeEleve modeleListeEleve) {
                if (modeleListeEleve != null) {
                    int index = (modeleListeEleve.getRowCount() + 1);
                    Date date = new Date();
                    modeleListeEleve.AjouterEleve(new XX_Eleve(-1, parametreInscription.getEntreprise().getId(), parametreInscription.getIdUtilisateur(), parametreInscription.getAnneeScolaire().getId(), -1, date.getTime(), "", "", "(+243)", "Eleve_" + index, "", "", InterfaceEleve.STATUS_ACTIF, InterfaceEleve.SEXE_MASCULIN, date, InterfaceEleve.BETA_NOUVEAU));
                    //On sélectionne la première ligne
                    tableListeEleves.setRowSelectionAllowed(true);
                    tableListeEleves.setRowSelectionInterval(0, 0);
                }
            }

            @Override
            public void setAjoutAyantDroit(ModeleListeAyantDroit modeleListeAyantDroit) {
                if (modeleListeAyantDroit != null) {
                    if (editeurEleve != null) {
                        editeurEleve.initCombo();
                        if (editeurEleve.getTailleCombo() != 0) {
                            modeleListeAyantDroit.AjouterAyantDroit(new XX_Ayantdroit(-1, parametreInscription.getEntreprise().getId(), parametreInscription.getIdUtilisateur(), parametreInscription.getAnneeScolaire().getId(), -1, "", new Vector<LiaisonEleveFrais>(), (new Date()).getTime(), -1, InterfaceAyantDroit.BETA_NOUVEAU));
                            //On sélectionne la première ligne
                            tableListeAyantDroit.setRowSelectionAllowed(true);
                            tableListeAyantDroit.setRowSelectionInterval(0, 0);
                        } else {
                            JOptionPane.showMessageDialog(parent, "Désolé, il n'y a plus d'élève à ajouter dans cette liste.", "Pas d'élève à ajouter", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                }
            }
        };

        setBoutons();
        setMenuContextuel();
    }

    public void activerBoutons(int selectedTab) {
        this.indexTabSelected = selectedTab;
    }

    public void ajouter() {
        switch (indexTabSelected) {
            case 0: //Tab eleve
                this.ecouteurAjout.setAjoutEleve(modeleListeEleve);
                break;
            case 1: //Tab ayantdroit
                this.ecouteurAjout.setAjoutAyantDroit(modeleListeAyantDroit);
                break;
        }
    }

    public void supprimer() {
        switch (indexTabSelected) {
            case 0: //Tab eleve
                modeleListeEleve.SupprimerEleve(tableListeEleves.getSelectedRow());
                break;
            case 1: //Tab ayantdroit
                modeleListeAyantDroit.SupprimerAyantDroit(tableListeAyantDroit.getSelectedRow());
                break;
        }
    }

    public void vider() {
        this.ecouteurClose.onActualiser("Vidé!", icones.getInfos_01());
        this.chRecherche.setText("");
        Date date = new Date();
        switch (indexTabSelected) {
            case 0: //eleve
                modeleListeEleve.viderListe();
                break;
            case 1: //ayantdroit
                modeleListeAyantDroit.viderListe();
                break;
        }

    }

    private void setIconesTabs() {
        this.tabPrincipal.setIconAt(0, icones.getClient_01());  //Elève
        this.tabPrincipal.setIconAt(1, icones.getAdministrateur_01());  //Ayantdroit
    }

    private void setMenuContextuel() {
        mAjouter = new RubriqueSimple("Ajouter", icones.getAjouter_01(), new RubriqueListener() {
            @Override
            public void OnEcouterLaSelection() {
                ajouter();
            }
        });

        mSupprimer = new RubriqueSimple("Supprimer", icones.getSupprimer_01(), new RubriqueListener() {
            @Override
            public void OnEcouterLaSelection() {
                supprimer();
            }
        });

        mEnregistrer = new RubriqueSimple("Enregistrer", icones.getEnregistrer_01(), new RubriqueListener() {
            @Override
            public void OnEcouterLaSelection() {
                enregistrer();
            }
        });

        mVider = new RubriqueSimple("Vider", icones.getAnnuler_01(), new RubriqueListener() {
            @Override
            public void OnEcouterLaSelection() {
                vider();
            }
        });

        mImprimer = new RubriqueSimple("Imprimer", icones.getImprimer_01(), new RubriqueListener() {
            @Override
            public void OnEcouterLaSelection() {
                imprimer();
            }
        });

        mFermer = new RubriqueSimple("Fermer", icones.getFermer_01(), new RubriqueListener() {
            @Override
            public void OnEcouterLaSelection() {
                fermer();
            }
        });

        mPDF = new RubriqueSimple("Export. PDF", icones.getPDF_01(), new RubriqueListener() {
            @Override
            public void OnEcouterLaSelection() {
                exporterPDF();
            }
        });

        mActualiser = new RubriqueSimple("Actualiser", icones.getSynchroniser_01(), new RubriqueListener() {
            @Override
            public void OnEcouterLaSelection() {
                actualiser();
            }
        });

        menuContextuel = new MenuContextuel();
        menuContextuel.Ajouter(mEnregistrer);
        menuContextuel.Ajouter(new JPopupMenu.Separator());
        menuContextuel.Ajouter(mAjouter);
        menuContextuel.Ajouter(mSupprimer);
        menuContextuel.Ajouter(mVider);
        menuContextuel.Ajouter(mActualiser);
        menuContextuel.Ajouter(new JPopupMenu.Separator());
        menuContextuel.Ajouter(mImprimer);
        menuContextuel.Ajouter(mPDF);
        menuContextuel.Ajouter(new JPopupMenu.Separator());
        menuContextuel.Ajouter(mFermer);
    }

    private boolean mustBeSaved() {
        boolean rep = false;
        //On vérifie dans la liste d'élèves
        for (InterfaceEleve Ieleve : this.modeleListeEleve.getListeData()) {
            if (Ieleve.getBeta() == InterfaceEleve.BETA_MODIFIE || Ieleve.getBeta() == InterfaceEleve.BETA_NOUVEAU) {
                rep = true;
            }
        }

        //On vérifie aussi dans la liste d'ayant-droits
        for (InterfaceAyantDroit Iayant : this.modeleListeAyantDroit.getListeData()) {
            if (Iayant.getBeta() == InterfaceAyantDroit.BETA_MODIFIE || Iayant.getBeta() == InterfaceAyantDroit.BETA_MODIFIE) {
                rep = true;
            }
        }
        return rep;
    }

    public void fermer() {
        //Vérifier s'il n'y a rien à enregistrer
        if (mustBeSaved() == true) {
            int dialogResult = JOptionPane.showConfirmDialog(this, "Voulez-vous enregistrer les modifications et/ou ajouts apportés à ces données?", "Avertissement", JOptionPane.YES_NO_CANCEL_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                this.ecouteurEleveAyantDroit.onEnregistre(getSortieEleveAyantDroit(btEnregistrer, mEnregistrer));
                this.ecouteurClose.onFermer();
            }else if(dialogResult == JOptionPane.NO_OPTION){
                this.ecouteurClose.onFermer();
            }
        }else{
            int dialogResult = JOptionPane.showConfirmDialog(this, "Etes-vous sûr de vouloir fermer cette fenêtre?", "Avertissement", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                this.ecouteurClose.onFermer();
            }
        }
    }

    public void imprimer() {
        int dialogResult = JOptionPane.showConfirmDialog(this, "Etes-vous sûr de vouloir imprimer ce document?", "Avertissement", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {
            try {
                SortiesEleveAyantDroit sortie = getSortieEleveAyantDroit(btImprimer, mImprimer);
                DocumentPDF documentPDF = new DocumentPDF(this, DocumentPDF.ACTION_IMPRIMER, sortie);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getNomfichierPreuve() {
        return "FicheElevesS2B.pdf";
    }

    private SortiesEleveAyantDroit getSortieEleveAyantDroit(Bouton boutonDeclencheur, RubriqueSimple rubriqueDeclencheur) {
        SortiesEleveAyantDroit sortieEA = new SortiesEleveAyantDroit(
                this.parametreInscription.getListeFraises(),
                this.parametreInscription.getListeClasses(),
                this.modeleListeEleve.getListeData(),
                this.modeleListeAyantDroit.getListeData(),
                new EcouteurEnregistrement() {
            @Override
            public void onDone(String message) {
                ecouteurClose.onActualiser(message, icones.getAimer_01());
                if (boutonDeclencheur != null) {
                    boutonDeclencheur.appliquerDroitAccessDynamique(true);
                }
                if (rubriqueDeclencheur != null) {
                    rubriqueDeclencheur.appliquerDroitAccessDynamique(true);
                }
                
                //On redessine les tableau afin que les couleurs se réinitialisent / Tout redevient noire
                if(modeleListeEleve != null){
                    modeleListeEleve.redessinerTable();
                }
                if(modeleListeAyantDroit != null){
                    modeleListeAyantDroit.redessinerTable();
                }
            }

            @Override
            public void onError(String message) {
                ecouteurClose.onActualiser(message, icones.getAlert_01());
                if (boutonDeclencheur != null) {
                    boutonDeclencheur.appliquerDroitAccessDynamique(true);
                }
                if (rubriqueDeclencheur != null) {
                    rubriqueDeclencheur.appliquerDroitAccessDynamique(true);
                }
            }

            @Override
            public void onUploading(String message) {
                ecouteurClose.onActualiser(message, icones.getInfos_01());
                if (boutonDeclencheur != null) {
                    boutonDeclencheur.appliquerDroitAccessDynamique(false);
                }
                if (rubriqueDeclencheur != null) {
                    rubriqueDeclencheur.appliquerDroitAccessDynamique(false);
                }
            }
        });

        return sortieEA;
    }

    public void enregistrer() {
        if (this.ecouteurEleveAyantDroit != null) {
            SortiesEleveAyantDroit sortie = getSortieEleveAyantDroit(btEnregistrer, mEnregistrer);
            this.ecouteurEleveAyantDroit.onEnregistre(sortie);
        }
    }

    public void exporterPDF() {
        int dialogResult = JOptionPane.showConfirmDialog(this, "Voulez-vous les exporter dans un fichier PDF?", "Avertissement", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {
            try {
                SortiesEleveAyantDroit sortie = getSortieEleveAyantDroit(btPDF, mPDF);
                DocumentPDF docpdf = new DocumentPDF(this, DocumentPDF.ACTION_OUVRIR, sortie);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void actualiser() {
        switch (indexTabSelected) {
            case 0: //Monnaie
                modeleListeEleve.actualiser();
                break;
            case 1: //Classe
                modeleListeAyantDroit.actualiser();
                break;
        }
    }

    private void activerCriteres() {
        //System.out.println("btCriteres.isSelected() = " + btCriteres.isSelected());
        btCriteres.setIcon(icones.getParamètres_01());
        //On reinitialise les combo
        chClasse.setSelectedIndex(0);
        chSexe.setSelectedIndex(0);
        chStatus.setSelectedIndex(0);

        if (btCriteres.isSelected() == true) {
            chClasse.setVisible(true);
            chSexe.setVisible(true);
            chStatus.setVisible(true);
            btCriteres.setText("Critères [-]");
        } else {
            chClasse.setVisible(false);
            chSexe.setVisible(false);
            chStatus.setVisible(false);
            btCriteres.setText("Critères [+]");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        barreOutils = new javax.swing.JToolBar();
        jButton5 = new javax.swing.JButton();
        tabPrincipal = new javax.swing.JTabbedPane();
        scrollListeEleves = new javax.swing.JScrollPane();
        tableListeEleves = new javax.swing.JTable();
        scrollListeAyantDroit = new javax.swing.JScrollPane();
        tableListeAyantDroit = new javax.swing.JTable();
        labInfos = new javax.swing.JLabel();
        chRecherche = new UI.JS2bTextField();
        chClasse = new javax.swing.JComboBox<>();
        chSexe = new javax.swing.JComboBox<>();
        chStatus = new javax.swing.JComboBox<>();
        btCriteres = new javax.swing.JToggleButton();

        setBackground(new java.awt.Color(255, 255, 255));

        barreOutils.setBackground(new java.awt.Color(255, 255, 255));
        barreOutils.setRollover(true);
        barreOutils.setAutoscrolls(true);
        barreOutils.setPreferredSize(new java.awt.Dimension(59, 61));

        jButton5.setBackground(new java.awt.Color(255, 255, 255));
        jButton5.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Facture02.png"))); // NOI18N
        jButton5.setText("Ajouter");
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barreOutils.add(jButton5);

        tabPrincipal.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tabPrincipalStateChanged(evt);
            }
        });

        scrollListeEleves.setBackground(new java.awt.Color(255, 255, 255));
        scrollListeEleves.setAutoscrolls(true);
        scrollListeEleves.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                scrollListeElevesMouseClicked(evt);
            }
        });

        tableListeEleves.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Article", "Qunatité", "Unités", "Prix Unitaire HT", "Tva %", "Tva", "Total TTC"
            }
        ));
        tableListeEleves.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableListeElevesMouseClicked(evt);
            }
        });
        scrollListeEleves.setViewportView(tableListeEleves);

        tabPrincipal.addTab("Inscriptions", scrollListeEleves);

        scrollListeAyantDroit.setBackground(new java.awt.Color(255, 255, 255));
        scrollListeAyantDroit.setAutoscrolls(true);
        scrollListeAyantDroit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                scrollListeAyantDroitMouseClicked(evt);
            }
        });

        tableListeAyantDroit.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Article", "Qunatité", "Unités", "Prix Unitaire HT", "Tva %", "Tva", "Total TTC"
            }
        ));
        tableListeAyantDroit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableListeAyantDroitMouseClicked(evt);
            }
        });
        scrollListeAyantDroit.setViewportView(tableListeAyantDroit);

        tabPrincipal.addTab("Ayant-droit", scrollListeAyantDroit);

        labInfos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Facture01.png"))); // NOI18N
        labInfos.setText("Prêt.");

        chRecherche.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Facture01.png"))); // NOI18N
        chRecherche.setTextInitial("Recherche");

        chClasse.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        chSexe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TOUT GENRE", "MASCULIN", "FEMININ" }));

        chStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TOUT STATUS", "EL. ACTIF", "EL. INACTIF" }));

        btCriteres.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Facture01.png"))); // NOI18N
        btCriteres.setText("Critères++");
        btCriteres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCriteresActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(barreOutils, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tabPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labInfos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(chClasse, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chSexe, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chStatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(chRecherche, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btCriteres)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(barreOutils, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chRecherche, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btCriteres))
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chClasse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chSexe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labInfos)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tableListeElevesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableListeElevesMouseClicked
        // TODO add your handling code here:
        ecouterMenContA(evt, 0);
    }//GEN-LAST:event_tableListeElevesMouseClicked

    private void scrollListeElevesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_scrollListeElevesMouseClicked
        // TODO add your handling code here:
        ecouterMenContA(evt, 0);
    }//GEN-LAST:event_scrollListeElevesMouseClicked

    private void tableListeAyantDroitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableListeAyantDroitMouseClicked
        // TODO add your handling code here:
        ecouterMenContA(evt, 1);
    }//GEN-LAST:event_tableListeAyantDroitMouseClicked

    private void scrollListeAyantDroitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_scrollListeAyantDroitMouseClicked
        // TODO add your handling code here:
        ecouterMenContA(evt, 1);
    }//GEN-LAST:event_scrollListeAyantDroitMouseClicked

    private void tabPrincipalStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tabPrincipalStateChanged
        // TODO add your handling code here:
        activerBoutons(((JTabbedPane) evt.getSource()).getSelectedIndex());
    }//GEN-LAST:event_tabPrincipalStateChanged

    private void btCriteresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCriteresActionPerformed
        // TODO add your handling code here:
        activerCriteres();
    }//GEN-LAST:event_btCriteresActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToolBar barreOutils;
    private javax.swing.JToggleButton btCriteres;
    private javax.swing.JComboBox<String> chClasse;
    private UI.JS2bTextField chRecherche;
    private javax.swing.JComboBox<String> chSexe;
    private javax.swing.JComboBox<String> chStatus;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel labInfos;
    private javax.swing.JScrollPane scrollListeAyantDroit;
    private javax.swing.JScrollPane scrollListeEleves;
    private javax.swing.JTabbedPane tabPrincipal;
    private javax.swing.JTable tableListeAyantDroit;
    private javax.swing.JTable tableListeEleves;
    // End of variables declaration//GEN-END:variables
}
