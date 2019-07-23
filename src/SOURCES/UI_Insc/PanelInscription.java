/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.UI_Insc;

import BEAN_BARRE_OUTILS.BarreOutils;
import BEAN_BARRE_OUTILS.Bouton;
import BEAN_BARRE_OUTILS.BoutonListener;
import BEAN_MenuContextuel.MenuContextuel;
import BEAN_MenuContextuel.RubriqueListener;
import BEAN_MenuContextuel.RubriqueSimple;
import ICONES.Icones;
import SOURCES.Callback_Insc.EcouteurAjoutInscription;
import SOURCES.Callback_Insc.EcouteurInscription;
import SOURCES.EditeurTable_Insc.EditeurClasse;
import SOURCES.EditeurTable_Insc.EditeurDate;
import SOURCES.EditeurTable_Insc.EditeurEleve;
import SOURCES.EditeurTable_Insc.EditeurSexe;
import SOURCES.EditeurTable_Insc.EditeurStatus;
import SOURCES.GenerateurPDF_Insc.DocumentPDFInscription;
import SOURCES.ModeleTable_Insc.ModeleListeAyantDroit;
import SOURCES.ModeleTable_Insc.ModeleListeEleve;
import SOURCES.MoteurRecherche_Insc.MoteurRecherche;
import SOURCES.RenduComboBox_Insc.RenduCombo;
import SOURCES.RenduTable_Insc.RenduTableAyantDroit;
import SOURCES.RenduTable_Insc.RenduTableEleve;
import SOURCES.Utilitaires_Insc.DonneesInscription;
import SOURCES.Utilitaires_Insc.ParametreInscription;
import SOURCES.Utilitaires_Insc.SortiesInscription;
import SOURCES.Utilitaires_Insc.UtilInscription;
import Source.Callbacks.EcouteurCrossCanal;
import Source.Callbacks.EcouteurEnregistrement;
import Source.Callbacks.EcouteurSuppressionElement;
import Source.Callbacks.EcouteurUpdateClose;
import Source.Callbacks.EcouteurValeursChangees;
import Source.Interface.InterfaceAyantDroit;
import Source.Interface.InterfaceClasse;
import Source.Interface.InterfaceEleve;
import Source.Interface.InterfaceEntreprise;
import Source.Objet.Ayantdroit;
import Source.Objet.CouleurBasique;
import Source.Objet.Eleve;
import Source.Objet.LiaisonFraisEleve;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

/**
 *
 * @author HP Pavilion
 */
public class PanelInscription extends javax.swing.JPanel {

    /**
     * Creates new form Panel
     */
    public int indexTabSelected = 0;
    private Icones icones = null;
    private final JTabbedPane parent;
    private PanelInscription moi = null;
    private EcouteurUpdateClose ecouteurClose = null;
    private EcouteurAjoutInscription ecouteurAjout = null;
    private Bouton btEnregistrer, btAjouter, btSupprimer, btVider, btImprimer, btPDF, btFermer, btActualiser, btPaiement, btLitiges;
    private RubriqueSimple mEnregistrer, mAjouter, mSupprimer, mVider, mImprimer, mPDF, mFermer, mActualiser, mPaiement, mLitige;
    private MenuContextuel menuContextuel = null;
    private BarreOutils bOutils = null;
    private EcouteurInscription ecouteurInscription = null;

    private ModeleListeEleve modeleListeEleve;
    private ModeleListeAyantDroit modeleListeAyantDroit;
    private EditeurEleve editeurEleve = null;
    private MoteurRecherche gestionnaireRecherche;

    public DonneesInscription donneesInscription;
    public ParametreInscription parametreInscription;
    private CouleurBasique couleurBasique;
    private EcouteurCrossCanal ecouteurCrossCanal;
    public Eleve selectedEleve = null;

    public PanelInscription(CouleurBasique couleurBasique, JTabbedPane parent, DonneesInscription donneesInscription, ParametreInscription parametreInscription, EcouteurInscription ecouteurInscription, EcouteurCrossCanal ecouteurCrossCanal) {
        this.initComponents();
        this.ecouteurCrossCanal = ecouteurCrossCanal;
        this.parent = parent;
        this.couleurBasique = couleurBasique;
        this.init();
        this.donneesInscription = donneesInscription;
        this.parametreInscription = parametreInscription;
        this.ecouteurInscription = ecouteurInscription;

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

    private void setTaille(TableColumn column, int taille, boolean fixe, TableCellEditor editor) {
        column.setPreferredWidth(taille);
        if (fixe == true) {
            column.setMaxWidth(taille);
            column.setMinWidth(taille);
        }
        if (editor != null) {
            column.setCellEditor(editor);
        }
    }

    private void fixerColonnesTableEleves(boolean resizeTable) {
        this.tableListeEleves.setDefaultRenderer(Object.class, new RenduTableEleve(couleurBasique, icones.getModifier_01(), this.modeleListeEleve, this.parametreInscription.getListeClasses()));
        this.tableListeEleves.setRowHeight(25);

        //{"N°", "Nom", "Postnom", "Prénom", "Sexe", "Classe", "Date naiss.", "Lieu de naiss.", "Téléphone (parents)", "Adresse"}
        setTaille(this.tableListeEleves.getColumnModel().getColumn(0), 40, true, null);
        setTaille(this.tableListeEleves.getColumnModel().getColumn(1), 150, false, null);
        setTaille(this.tableListeEleves.getColumnModel().getColumn(2), 150, false, null);
        setTaille(this.tableListeEleves.getColumnModel().getColumn(3), 150, false, null);
        setTaille(this.tableListeEleves.getColumnModel().getColumn(4), 140, true, new EditeurSexe());
        setTaille(this.tableListeEleves.getColumnModel().getColumn(5), 90, true, new EditeurClasse(this.parametreInscription.getListeClasses()));
        setTaille(this.tableListeEleves.getColumnModel().getColumn(6), 150, false, new EditeurDate());
        setTaille(this.tableListeEleves.getColumnModel().getColumn(7), 120, false, new EditeurStatus());
        setTaille(this.tableListeEleves.getColumnModel().getColumn(8), 200, false, null);
        setTaille(this.tableListeEleves.getColumnModel().getColumn(9), 300, false, null);

        //On écoute les sélction
        this.tableListeEleves.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting() == false) {
                    if (modeleListeEleve != null) {
                        selectedEleve = modeleListeEleve.getEleve(tableListeEleves.getSelectedRow());
                        if (selectedEleve != null && ecouteurClose != null) {
                            //Paiement
                            btPaiement.appliquerDroitAccessDynamique(true);
                            mPaiement.appliquerDroitAccessDynamique(true);
                            //Litige
                            btLitiges.appliquerDroitAccessDynamique(true);
                            mLitige.appliquerDroitAccessDynamique(true);
                            
                            ecouteurClose.onActualiser(modeleListeEleve.getRowCount() + " élement(s).", icones.getClient_01());
                        }
                    }
                }
            }
        });

        if (resizeTable == true) {
            this.tableListeEleves.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        }
    }

    private void initModelTableEleves() {
        this.modeleListeEleve = new ModeleListeEleve(couleurBasique, scrollListeEleves, btEnregistrer, mEnregistrer, this.parametreInscription.getListeClasses(), new EcouteurValeursChangees() {
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
    }

    private void chargerDataTableEleves() {
        //On charge les données existantes (le cas échéant)
        if (this.donneesInscription != null) {
            if (!this.donneesInscription.getListeEleves().isEmpty()) {
                this.modeleListeEleve.setListeEleves(this.donneesInscription.getListeEleves());
            }
        }
    }

    private void parametrerTableEleves() {
        initModelTableEleves();
        chargerDataTableEleves();
        fixerColonnesTableEleves(true);
    }

    private void initModelTableAyantDroit() {
        this.modeleListeAyantDroit = new ModeleListeAyantDroit(parametreInscription, couleurBasique, scrollListeAyantDroit, btEnregistrer, mEnregistrer, this.parametreInscription.getListeFraises(), this.modeleListeEleve, new EcouteurValeursChangees() {
            @Override
            public void onValeurChangee() {

            }
        }, this.ecouteurClose);

        //Parametrage du modele contenant les données de la table
        this.tableListeAyantDroit.setModel(this.modeleListeAyantDroit);
    }

    private void chargerDataTableAyantDroit() {
        //On charge les données existantes (le cas échéant)
        if (this.donneesInscription != null) {
            if (!this.donneesInscription.getListeAyantDroit().isEmpty()) {
                this.modeleListeAyantDroit.setListeAyantDroit(this.donneesInscription.getListeAyantDroit());
            }
        }
    }

    private void fixerColonnesTableAyantDroit(boolean resizeTable) {
        this.editeurEleve = new EditeurEleve(this.modeleListeEleve, this.modeleListeAyantDroit);

        //Parametrage du rendu de la table
        this.tableListeAyantDroit.setDefaultRenderer(Object.class, new RenduTableAyantDroit(couleurBasique, icones.getModifier_01(), this.modeleListeEleve, modeleListeAyantDroit));
        this.tableListeAyantDroit.setRowHeight(25);

        //{"N°", "Eleve", ...liste des frais}
        setTaille(this.tableListeAyantDroit.getColumnModel().getColumn(0), 40, true, null);
        setTaille(this.tableListeAyantDroit.getColumnModel().getColumn(1), 150, false, editeurEleve);

        //On écoute les sélction
        this.tableListeAyantDroit.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting() == false) {
                    if (modeleListeAyantDroit != null) {
                        Ayantdroit artcl = modeleListeAyantDroit.getAyantDroit(tableListeAyantDroit.getSelectedRow());
                        if (artcl != null && ecouteurClose != null) {
                            ecouteurClose.onActualiser(modeleListeAyantDroit.getRowCount() + " élement(s).", icones.getAdministrateur_01());
                            
                            selectedEleve = modeleListeEleve.getEleve_signature(artcl.getSignatureEleve());
                            //Paiement
                            btPaiement.appliquerDroitAccessDynamique(true);
                            mPaiement.appliquerDroitAccessDynamique(true);
                            
                            //Litige
                            btLitiges.appliquerDroitAccessDynamique(true);
                            mLitige.appliquerDroitAccessDynamique(true);
                        }
                    }
                }
            }
        });

        if (resizeTable == true) {
            this.tableListeAyantDroit.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        }
    }

    private void parametrerTableAyantDroit() {
        initModelTableAyantDroit();
        chargerDataTableAyantDroit();
        fixerColonnesTableAyantDroit(false);
    }

    private void setBoutons() {
        btAjouter = new Bouton(12, "Ajouter", "Ajouter", false, icones.getAjouter_02(), new BoutonListener() {
            @Override
            public void OnEcouteLeClick() {
                ajouter();
            }
        });

        btSupprimer = new Bouton(12, "Supprimer", "Supprimer", false, icones.getSupprimer_02(), new BoutonListener() {
            @Override
            public void OnEcouteLeClick() {
                supprimer();
            }
        });

        btEnregistrer = new Bouton(12, "Enregistrer", "Enregistrer", false, icones.getEnregistrer_02(), new BoutonListener() {
            @Override
            public void OnEcouteLeClick() {
                enregistrer();
            }
        });
        btEnregistrer.setGras(true);

        btVider = new Bouton(12, "Vider", "Vider le contenu de cette liste", false, icones.getAnnuler_02(), new BoutonListener() {
            @Override
            public void OnEcouteLeClick() {
                vider();
            }
        });

        btImprimer = new Bouton(12, "Imprimer", "Imprimer le contenu de cette liste", false, icones.getImprimer_02(), new BoutonListener() {
            @Override
            public void OnEcouteLeClick() {
                imprimer();
            }
        });

        btFermer = new Bouton(12, "Fermer", "Fermer cette fenêtre", false, icones.getFermer_02(), new BoutonListener() {
            @Override
            public void OnEcouteLeClick() {
                fermer();
            }
        });

        btPDF = new Bouton(12, "Exp. en PDF", "Produire un contenu PDF imprimable", false, icones.getPDF_02(), new BoutonListener() {
            @Override
            public void OnEcouteLeClick() {
                exporterPDF();
            }
        });

        btActualiser = new Bouton(12, "Actualiser", "Actualiser cette liste", false, icones.getSynchroniser_02(), new BoutonListener() {
            @Override
            public void OnEcouteLeClick() {
                actualiser();
            }
        });

        btPaiement = new Bouton(12, "Paiement", "Ouvrir la fiche de paiement", false, icones.getCaisse_02(), new BoutonListener() {
            @Override
            public void OnEcouteLeClick() {
                if (ecouteurCrossCanal != null) {
                    ecouteurCrossCanal.onOuvrirPaiements(selectedEleve);
                }
            }
        });
        btPaiement.appliquerDroitAccessDynamique(false);
        
        
        btLitiges = new Bouton(12, "Litiges", "Ouvrir les litiges", false, icones.getFournisseur_02(), new BoutonListener() {
            @Override
            public void OnEcouteLeClick() {
                if (ecouteurCrossCanal != null) {
                    ecouteurCrossCanal.onOuvrirLitiges(selectedEleve);
                }
            }
        });
        btLitiges.appliquerDroitAccessDynamique(false);

        bOutils = new BarreOutils(barreOutils);
        bOutils.AjouterBouton(btEnregistrer);
        bOutils.AjouterBouton(btPaiement);
        bOutils.AjouterBouton(btLitiges);
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

        this.ecouteurAjout = new EcouteurAjoutInscription() {
            @Override
            public void setAjoutEleve(ModeleListeEleve modeleListeEleve) {
                if (modeleListeEleve != null) {
                    int index = (modeleListeEleve.getRowCount() + 1);
                    Date date = new Date();
                    modeleListeEleve.AjouterEleve(new Eleve(-1, parametreInscription.getEntreprise().getId(), parametreInscription.getIdUtilisateur(), parametreInscription.getExercice().getId(), -1, UtilInscription.generateSignature(), "", "", "(+243)", "Eleve_" + index, "", "", InterfaceEleve.STATUS_ACTIF, InterfaceEleve.SEXE_MASCULIN, date, InterfaceEleve.BETA_NOUVEAU));
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
                            modeleListeAyantDroit.AjouterAyantDroit(new Ayantdroit(-1, parametreInscription.getEntreprise().getId(), parametreInscription.getIdUtilisateur(), parametreInscription.getExercice().getId(), -1, "", new Vector<LiaisonFraisEleve>(), UtilInscription.generateSignature(), -1, InterfaceAyantDroit.BETA_NOUVEAU));
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
                modeleListeEleve.SupprimerEleve(tableListeEleves.getSelectedRow(), new EcouteurSuppressionElement() {
                    @Override
                    public void onSuppressionConfirmee(int idElement) {
                        ecouteurInscription.onDetruitElements(idElement, indexTabSelected);
                    }
                });
                break;
            case 1: //Tab ayantdroit
                modeleListeAyantDroit.SupprimerAyantDroit(tableListeAyantDroit.getSelectedRow(), new EcouteurSuppressionElement() {
                    @Override
                    public void onSuppressionConfirmee(int idElement) {
                        ecouteurInscription.onDetruitElements(idElement, indexTabSelected);
                    }
                });
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
        mAjouter = new RubriqueSimple("Ajouter", 12, false, icones.getAjouter_01(), new RubriqueListener() {
            @Override
            public void OnEcouterLaSelection() {
                ajouter();
            }
        });

        mSupprimer = new RubriqueSimple("Supprimer", 12, false, icones.getSupprimer_01(), new RubriqueListener() {
            @Override
            public void OnEcouterLaSelection() {
                supprimer();
            }
        });

        mEnregistrer = new RubriqueSimple("Enregistrer", 12, true, icones.getEnregistrer_01(), new RubriqueListener() {
            @Override
            public void OnEcouterLaSelection() {
                enregistrer();
            }
        });

        mVider = new RubriqueSimple("Vider", 12, false, icones.getAnnuler_01(), new RubriqueListener() {
            @Override
            public void OnEcouterLaSelection() {
                vider();
            }
        });

        mImprimer = new RubriqueSimple("Imprimer", 12, false, icones.getImprimer_01(), new RubriqueListener() {
            @Override
            public void OnEcouterLaSelection() {
                imprimer();
            }
        });

        mFermer = new RubriqueSimple("Fermer", 12, false, icones.getFermer_01(), new RubriqueListener() {
            @Override
            public void OnEcouterLaSelection() {
                fermer();
            }
        });

        mPDF = new RubriqueSimple("Export. PDF", 12, false, icones.getPDF_01(), new RubriqueListener() {
            @Override
            public void OnEcouterLaSelection() {
                exporterPDF();
            }
        });

        mActualiser = new RubriqueSimple("Actualiser", 12, false, icones.getSynchroniser_01(), new RubriqueListener() {
            @Override
            public void OnEcouterLaSelection() {
                actualiser();
            }
        });
        
        mPaiement = new RubriqueSimple("Paiement", 12, false, icones.getCaisse_01(), new RubriqueListener() {
            @Override
            public void OnEcouterLaSelection() {
                if(ecouteurCrossCanal != null){
                    ecouteurCrossCanal.onOuvrirPaiements(selectedEleve);
                }
            }
        });
        mPaiement.appliquerDroitAccessDynamique(false);
        
        mLitige = new RubriqueSimple("Litiges", 12, false, icones.getFournisseur_01(), new RubriqueListener() {
            @Override
            public void OnEcouterLaSelection() {
                if(ecouteurCrossCanal != null){
                    ecouteurCrossCanal.onOuvrirLitiges(selectedEleve);
                }
            }
        });

        menuContextuel = new MenuContextuel();
        menuContextuel.Ajouter(mEnregistrer);
        menuContextuel.Ajouter(mPaiement);
        menuContextuel.Ajouter(mLitige);
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
            if (Iayant.getBeta() == InterfaceAyantDroit.BETA_MODIFIE || Iayant.getBeta() == InterfaceAyantDroit.BETA_NOUVEAU) {
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
                this.ecouteurInscription.onEnregistre(getSortieInscription(btEnregistrer, mEnregistrer));
                this.ecouteurClose.onFermer();
            } else if (dialogResult == JOptionPane.NO_OPTION) {
                this.ecouteurClose.onFermer();
            }
        } else {
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
                SortiesInscription sortie = getSortieInscription(btImprimer, mImprimer);
                DocumentPDFInscription documentPDF = new DocumentPDFInscription(this, DocumentPDFInscription.ACTION_IMPRIMER, sortie);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getNomfichierPreuve() {
        return "Output.pdf";
    }

    private SortiesInscription getSortieInscription(Bouton boutonDeclencheur, RubriqueSimple rubriqueDeclencheur) {
        SortiesInscription sortieEA = new SortiesInscription(
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
                if (modeleListeEleve != null) {
                    modeleListeEleve.redessinerTable();
                }
                if (modeleListeAyantDroit != null) {
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
        if (this.ecouteurInscription != null) {
            btEnregistrer.setForeground(Color.black);
            mEnregistrer.setCouleur(Color.BLACK);

            SortiesInscription sortie = getSortieInscription(btEnregistrer, mEnregistrer);
            this.ecouteurInscription.onEnregistre(sortie);
        }
    }

    public void exporterPDF() {
        int dialogResult = JOptionPane.showConfirmDialog(this, "Voulez-vous les exporter dans un fichier PDF?", "Avertissement", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {
            try {
                SortiesInscription sortie = getSortieInscription(btPDF, mPDF);
                DocumentPDFInscription docpdf = new DocumentPDFInscription(this, DocumentPDFInscription.ACTION_OUVRIR, sortie);
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
        btPaiement.appliquerDroitAccessDynamique(false);
        mPaiement.appliquerDroitAccessDynamique(false);
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
        barreOutils.setFloatable(false);
        barreOutils.setRollover(true);
        barreOutils.setAutoscrolls(true);
        barreOutils.setPreferredSize(new java.awt.Dimension(59, 61));

        jButton5.setBackground(new java.awt.Color(255, 255, 255));
        jButton5.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG_Insc/Facture02.png"))); // NOI18N
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

        labInfos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG_Insc/Facture01.png"))); // NOI18N
        labInfos.setText("Prêt.");

        chRecherche.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG_Insc/Facture01.png"))); // NOI18N
        chRecherche.setTextInitial("Recherche");

        chClasse.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        chClasse.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chClasseItemStateChanged(evt);
            }
        });

        chSexe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TOUT GENRE", "MASCULIN", "FEMININ" }));
        chSexe.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chSexeItemStateChanged(evt);
            }
        });

        chStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TOUT STATUS", "EL. ACTIF", "EL. INACTIF" }));
        chStatus.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chStatusItemStateChanged(evt);
            }
        });

        btCriteres.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG_Insc/Facture01.png"))); // NOI18N
        btCriteres.setSelected(true);
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

    private void chClasseItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chClasseItemStateChanged
        // TODO add your handling code here:
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            System.out.println("Combo: Selection - " + evt.getItem());
            gestionnaireRecherche.chercher(chRecherche.getText());
        }
    }//GEN-LAST:event_chClasseItemStateChanged

    private void chSexeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chSexeItemStateChanged
        // TODO add your handling code here:
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            System.out.println("Combo: Selection - " + evt.getItem());
            gestionnaireRecherche.chercher(chRecherche.getText());
        }
    }//GEN-LAST:event_chSexeItemStateChanged

    private void chStatusItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chStatusItemStateChanged
        // TODO add your handling code here:
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            System.out.println("Combo: Selection - " + evt.getItem());
            gestionnaireRecherche.chercher(chRecherche.getText());
        }
    }//GEN-LAST:event_chStatusItemStateChanged


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
