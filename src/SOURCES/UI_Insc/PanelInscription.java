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
import SOURCES.RenduTable_Insc.RenduTableAyantDroit;
import SOURCES.RenduTable_Insc.RenduTableEleve;
import SOURCES.Utilitaires_Insc.DataInscription;
import SOURCES.Utilitaires_Insc.SortiesInscription;
import SOURCES.Utilitaires_Insc.UtilInscription;
import Source.Callbacks.EcouteurCrossCanal;
import Source.Callbacks.EcouteurEnregistrement;
import Source.Callbacks.EcouteurFreemium;
import Source.Callbacks.EcouteurSuppressionElement;
import Source.Callbacks.EcouteurUpdateClose;
import Source.Callbacks.EcouteurValeursChangees;
import Source.GestionClickDroit;
import Source.GestionEdition;
import Source.Interface.InterfaceAyantDroit;
import Source.Interface.InterfaceEleve;
import Source.Interface.InterfaceEntreprise;
import Source.Interface.InterfaceUtilisateur;
import Source.Objet.Ayantdroit;
import Source.Objet.Classe;
import Source.Objet.CouleurBasique;
import Source.Objet.Eleve;
import Source.Objet.LiaisonFraisEleve;
import Source.Objet.UtilObjet;
import Source.Objet.Utilisateur;
import Source.UI.NavigateurPages;
import java.awt.Color;
import java.util.Date;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
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
    private Bouton btEnregistrer, btAjouter, btSupprimer, btVider, btImprimer, btPDF, btFermer, btActualiser, btPaiement, btLitiges, btEdition;
    private RubriqueSimple mEnregistrer, mAjouter, mSupprimer, mVider, mImprimer, mPDF, mFermer, mActualiser, mPaiement, mLitige, mEdition;
    private MenuContextuel menuContextuel = null;
    private BarreOutils bOutils = null;
    private EcouteurInscription ecouteurInscription = null;
    private ModeleListeEleve modeleListeEleve;
    private ModeleListeAyantDroit modeleListeAyantDroit;
    private EditeurEleve editeurEleve = null;

    public DataInscription dataInscription;
    private CouleurBasique couleurBasique;
    private EcouteurCrossCanal ecouteurCrossCanal;
    public Eleve selectedEleve = null;
    public Ayantdroit selectedAyantDroit = null;
    public JProgressBar progress;
    private GestionEdition gestionEdition = new GestionEdition();
    private EcouteurFreemium ef = null;

    public PanelInscription(EcouteurFreemium ef, CouleurBasique couleurBasique, JTabbedPane parent, DataInscription dataInscription, JProgressBar progress, EcouteurInscription ecouteurInscription, EcouteurCrossCanal ecouteurCrossCanal) {
        this.initComponents();
        this.ef = ef;
        this.progress = progress;
        this.ecouteurCrossCanal = ecouteurCrossCanal;
        this.parent = parent;
        this.couleurBasique = couleurBasique;
        this.dataInscription = dataInscription;
        this.ecouteurInscription = ecouteurInscription;
        this.init();
        this.parametrerTableEleves();
        this.parametrerTableAyantDroit();
        this.setIconesTabs();

        //On ecoute les click droit afin d'afficher le menu contextuel
        new GestionClickDroit(menuContextuel, tableListeEleves, scrollListeEleves).init();
        new GestionClickDroit(menuContextuel, tableListeAyantDroit, scrollListeAyantDroit).init();
    }

    public DataInscription getDataInscription() {
        return dataInscription;
    }

    public NavigateurPages getNavigateur() {
        return navigateur;
    }

    public InterfaceEntreprise getEntreprise() {
        return dataInscription.getParametreInscription().getEntreprise();
    }

    public int getIndexTabSelected() {
        return indexTabSelected;
    }

    public String getNomUtilisateur() {
        return dataInscription.getParametreInscription().getUtilisateur().getNom();
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
        this.tableListeEleves.setDefaultRenderer(Object.class, new RenduTableEleve(gestionEdition, couleurBasique, icones.getModifier_01(), this.modeleListeEleve, this.dataInscription.getParametreInscription().getListeClasses()));
        this.tableListeEleves.setRowHeight(25);

        //{"N°", "Nom", "Postnom", "Prénom", "Sexe", "Classe", "Date naiss.", "Lieu de naiss.", "Téléphone (parents)", "Adresse"}
        setTaille(this.tableListeEleves.getColumnModel().getColumn(0), 40, true, null);
        setTaille(this.tableListeEleves.getColumnModel().getColumn(1), 150, false, null);
        setTaille(this.tableListeEleves.getColumnModel().getColumn(2), 150, false, null);
        setTaille(this.tableListeEleves.getColumnModel().getColumn(3), 150, false, null);
        setTaille(this.tableListeEleves.getColumnModel().getColumn(4), 140, true, new EditeurSexe());
        setTaille(this.tableListeEleves.getColumnModel().getColumn(5), 90, true, new EditeurClasse(this.dataInscription.getParametreInscription().getListeClasses()));
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
        this.modeleListeEleve = new ModeleListeEleve(gestionEdition, couleurBasique, scrollListeEleves, btEnregistrer, mEnregistrer, this.dataInscription.getParametreInscription().getListeClasses(), new EcouteurValeursChangees() {
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

    private void parametrerTableEleves() {
        initModelTableEleves();
        fixerColonnesTableEleves(true);
    }

    private void initModelTableAyantDroit() {
        this.modeleListeAyantDroit = new ModeleListeAyantDroit(gestionEdition, dataInscription.getParametreInscription(), couleurBasique, scrollListeAyantDroit, btEnregistrer, mEnregistrer, this.dataInscription.getParametreInscription().getListeFraises(), this.modeleListeEleve, new EcouteurValeursChangees() {
            @Override
            public void onValeurChangee() {

            }
        }, this.ecouteurClose);

        //Parametrage du modele contenant les données de la table
        this.tableListeAyantDroit.setModel(this.modeleListeAyantDroit);
    }

    public void reiniliserEleves() {
        if (modeleListeEleve != null) {
            modeleListeEleve.reinitialiserListe();
        }
    }

    public void reiniliserAyantDroit() {
        if (modeleListeAyantDroit != null) {
            modeleListeAyantDroit.reinitialiserListe();
        }
    }

    public void setDonneesAyantDroit(Ayantdroit data) {
        if (modeleListeAyantDroit != null) {
            modeleListeAyantDroit.addData(data);
        }
    }

    public void setDonneesEleves(Eleve data) {
        if (modeleListeEleve != null) {
            modeleListeEleve.addData(data);
        }
    }

    public int getTailleResultatEleves() {
        if (modeleListeEleve != null) {
            return modeleListeEleve.getListeData().size();
        }
        return 0;
    }

    public Classe getClasse(String nom) {
        for (Classe cc : dataInscription.getParametreInscription().getListeClasses()) {
            if (nom.equals(cc.getNom())) {
                return cc;
            }
        }
        return null;
    }

    private void fixerColonnesTableAyantDroit(boolean resizeTable) {
        this.editeurEleve = new EditeurEleve(this.modeleListeEleve, this.modeleListeAyantDroit);

        //Parametrage du rendu de la table
        this.tableListeAyantDroit.setDefaultRenderer(Object.class, new RenduTableAyantDroit(gestionEdition, couleurBasique, icones.getModifier_01(), this.modeleListeEleve, modeleListeAyantDroit));
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
                        selectedAyantDroit = modeleListeAyantDroit.getAyantDroit(tableListeAyantDroit.getSelectedRow());
                        if (selectedAyantDroit != null && ecouteurClose != null) {
                            ecouteurClose.onActualiser(modeleListeAyantDroit.getRowCount() + " élement(s).", icones.getAdministrateur_01());

                            selectedEleve = modeleListeEleve.getEleve_signature(selectedAyantDroit.getSignatureEleve());
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
        fixerColonnesTableAyantDroit(false);
    }

    public void setBtEnregistrerNouveau() {
        if (mEnregistrer != null && btEnregistrer != null) {
            mEnregistrer.setCouleur(couleurBasique.getCouleur_foreground_objet_nouveau());                                        //mEnreg.setCouleur(Color.blue);
            btEnregistrer.setForeground(couleurBasique.getCouleur_foreground_objet_nouveau());
        }
    }

    public void init() {
        this.icones = new Icones();
        this.moi = this;
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
                    modeleListeEleve.AjouterEleve(new Eleve(-1, dataInscription.getParametreInscription().getEntreprise().getId(), dataInscription.getParametreInscription().getUtilisateur().getId(), dataInscription.getParametreInscription().getExercice().getId(), -1, UtilInscription.generateSignature(), "", "", "(+243)", "Eleve_" + index, "", "", InterfaceEleve.STATUS_ACTIF, InterfaceEleve.SEXE_MASCULIN, date, InterfaceEleve.BETA_NOUVEAU));
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
                            modeleListeAyantDroit.AjouterAyantDroit(new Ayantdroit(-1, dataInscription.getParametreInscription().getEntreprise().getId(), dataInscription.getParametreInscription().getUtilisateur().getId(), dataInscription.getParametreInscription().getExercice().getId(), -1, "", new Vector<LiaisonFraisEleve>(), UtilInscription.generateSignature(), -1, InterfaceAyantDroit.BETA_NOUVEAU));
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
        if (modeleListeEleve != null && tableListeEleves != null && icones != null && modeleListeAyantDroit != null && tableListeAyantDroit != null) {
            switch (indexTabSelected) {
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

    }

    public void ajouter() {
        switch (indexTabSelected) {
            case 0: //Tab eleve
                if(ef != null){
                    if(ef.onVerifieNombre(UtilObjet.DOSSIER_ELEVE) == true){
                        this.ecouteurAjout.setAjoutEleve(modeleListeEleve);
                    }
                }
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
                    public void onSuppressionConfirmee(int idElement, long signature) {
                        ecouteurInscription.onDetruitElements(idElement, indexTabSelected, signature);
                    }
                });
                break;
            case 1: //Tab ayantdroit
                modeleListeAyantDroit.SupprimerAyantDroit(tableListeAyantDroit.getSelectedRow(), new EcouteurSuppressionElement() {
                    @Override
                    public void onSuppressionConfirmee(int idElement, long signature) {
                        ecouteurInscription.onDetruitElements(idElement, indexTabSelected, signature);
                    }
                });
                break;
        }
    }

    public void vider() {
        this.ecouteurClose.onActualiser("Vidé!", icones.getInfos_01());
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

        btEdition = new Bouton(12, "Edition", "", true, icones.getModifier_02(), new BoutonListener() {
            @Override
            public void OnEcouteLeClick() {
                setEditionMode();
            }
        });

        //Il faut respecter les droits d'accès attribué à l'utilisateur actuel!
        bOutils = new BarreOutils(barreOutils);
        if (dataInscription.getParametreInscription().getUtilisateur() != null) {
            Utilisateur user = dataInscription.getParametreInscription().getUtilisateur();

            if (user.getDroitInscription() == InterfaceUtilisateur.DROIT_CONTROLER) {
                bOutils.AjouterBouton(btEnregistrer);
                bOutils.AjouterBouton(btAjouter);
                bOutils.AjouterBouton(btEdition);
                bOutils.AjouterSeparateur();
                bOutils.AjouterBouton(btSupprimer);
                bOutils.AjouterBouton(btVider);
            }
            bOutils.AjouterBouton(btActualiser);
            if (user.getDroitFacture() != InterfaceUtilisateur.DROIT_PAS_ACCES) {
                bOutils.AjouterBouton(btPaiement);
            }
            if (user.getDroitLitige() != InterfaceUtilisateur.DROIT_PAS_ACCES) {
                bOutils.AjouterBouton(btLitiges);
            }
            bOutils.AjouterBouton(btImprimer);
            bOutils.AjouterBouton(btPDF);
            bOutils.AjouterSeparateur();
            bOutils.AjouterBouton(btFermer);
        }
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
                if (ecouteurCrossCanal != null) {
                    ecouteurCrossCanal.onOuvrirPaiements(selectedEleve);
                }
            }
        });
        mPaiement.appliquerDroitAccessDynamique(false);

        mLitige = new RubriqueSimple("Litiges", 12, false, icones.getFournisseur_01(), new RubriqueListener() {
            @Override
            public void OnEcouterLaSelection() {
                if (ecouteurCrossCanal != null) {
                    ecouteurCrossCanal.onOuvrirLitiges(selectedEleve);
                }
            }
        });

        mEdition = new RubriqueSimple("Editer", 12, false, icones.getModifier_01(), new RubriqueListener() {
            @Override
            public void OnEcouterLaSelection() {
                setEditionMode();
            }
        });

        //Il faut respecter les droits d'accès attribué à l'utilisateur actuel!
        menuContextuel = new MenuContextuel();
        if (dataInscription.getParametreInscription().getUtilisateur() != null) {
            Utilisateur user = dataInscription.getParametreInscription().getUtilisateur();

            if (user.getDroitInscription() == InterfaceUtilisateur.DROIT_CONTROLER) {
                menuContextuel.Ajouter(mEnregistrer);
                menuContextuel.Ajouter(mAjouter);
                menuContextuel.Ajouter(mEdition);
                menuContextuel.Ajouter(new JPopupMenu.Separator());
                menuContextuel.Ajouter(mSupprimer);
                menuContextuel.Ajouter(mVider);
            }
            menuContextuel.Ajouter(mActualiser);
            if (user.getDroitFacture() != InterfaceUtilisateur.DROIT_PAS_ACCES) {
                menuContextuel.Ajouter(mPaiement);
            }
            if (user.getDroitLitige() != InterfaceUtilisateur.DROIT_PAS_ACCES) {
                menuContextuel.Ajouter(mLitige);
            }
            menuContextuel.Ajouter(new JPopupMenu.Separator());
            menuContextuel.Ajouter(mImprimer);
            menuContextuel.Ajouter(mPDF);
            menuContextuel.Ajouter(new JPopupMenu.Separator());
            menuContextuel.Ajouter(mFermer);
        }

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
        if (ef != null) {
            if (ef.onVerifie() == true) {
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
        }

    }

    public String getNomfichierPreuve() {
        return "Output.pdf";
    }

    private SortiesInscription getSortieInscription(Bouton boutonDeclencheur, RubriqueSimple rubriqueDeclencheur) {
        SortiesInscription sortieEA = new SortiesInscription(
                this.dataInscription.getParametreInscription().getListeFraises(),
                this.dataInscription.getParametreInscription().getListeClasses(),
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

            actualiserEditeur();
        }
    }

    private void actualiserEditeur() {
        gestionEdition.reinitialiser();
        modeleListeEleve.actualiser();
        modeleListeAyantDroit.actualiser();
    }

    private void setEditionMode() {
        switch (indexTabSelected) {
            case 0:
                if (selectedEleve != null && gestionEdition != null) {
                    if (gestionEdition.isEditable(selectedEleve.getId(), indexTabSelected)) {
                        gestionEdition.setModeEdition(selectedEleve.getId(), indexTabSelected, false);
                    } else {
                        gestionEdition.setModeEdition(selectedEleve.getId(), indexTabSelected, true);
                    }
                    modeleListeEleve.actualiser();
                }
                break;
            case 1:
                if (selectedAyantDroit != null && gestionEdition != null) {
                    if (gestionEdition.isEditable(selectedAyantDroit.getId(), indexTabSelected)) {
                        gestionEdition.setModeEdition(selectedAyantDroit.getId(), indexTabSelected, false);
                    } else {
                        gestionEdition.setModeEdition(selectedAyantDroit.getId(), indexTabSelected, true);
                    }
                    modeleListeAyantDroit.actualiser();
                }
                break;
            default:
        }
    }

    public void exporterPDF() {
        if (ef != null) {
            if (ef.onVerifie() == true) {
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
        }

    }

    public void actualiser() {
        if (navigateur != null) {
            navigateur.reload();
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
        navigateur = new Source.UI.NavigateurPages();

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
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tableListeElevesMousePressed(evt);
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(barreOutils, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tabPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labInfos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(navigateur, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(barreOutils, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(navigateur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(tabPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(labInfos)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tableListeElevesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableListeElevesMouseClicked
        // TODO add your handling code here:
        //ecouterMenContA(evt, 0);
    }//GEN-LAST:event_tableListeElevesMouseClicked

    private void scrollListeElevesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_scrollListeElevesMouseClicked
        // TODO add your handling code here:
        //ecouterMenContA(evt, 0);
    }//GEN-LAST:event_scrollListeElevesMouseClicked

    private void tableListeAyantDroitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableListeAyantDroitMouseClicked
        // TODO add your handling code here:
        //ecouterMenContA(evt, 1);
    }//GEN-LAST:event_tableListeAyantDroitMouseClicked

    private void scrollListeAyantDroitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_scrollListeAyantDroitMouseClicked
        // TODO add your handling code here:
        //ecouterMenContA(evt, 1);
    }//GEN-LAST:event_scrollListeAyantDroitMouseClicked

    private void tabPrincipalStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tabPrincipalStateChanged
        // TODO add your handling code here:
        activerBoutons(((JTabbedPane) evt.getSource()).getSelectedIndex());
    }//GEN-LAST:event_tabPrincipalStateChanged

    private void tableListeElevesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableListeElevesMousePressed
        // TODO add your handling code here:

    }//GEN-LAST:event_tableListeElevesMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToolBar barreOutils;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel labInfos;
    private Source.UI.NavigateurPages navigateur;
    private javax.swing.JScrollPane scrollListeAyantDroit;
    private javax.swing.JScrollPane scrollListeEleves;
    private javax.swing.JTabbedPane tabPrincipal;
    private javax.swing.JTable tableListeAyantDroit;
    private javax.swing.JTable tableListeEleves;
    // End of variables declaration//GEN-END:variables
}
