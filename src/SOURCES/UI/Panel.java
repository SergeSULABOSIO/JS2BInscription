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
import SOURCES.Callback.EcouteurUpdateClose;
import SOURCES.ModeleTable.ModeleListeAyantDroit;
import SOURCES.ModeleTable.ModeleListeEleve;
import java.awt.event.MouseEvent;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;

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
    private JTabbedPane parent = null;
    private Panel moi = null;
    private EcouteurUpdateClose ecouteurClose = null;
    private EcouteurAjout ecouteurAjout = null;
    private Bouton btEnregistrer, btAjouter, btSupprimer, btVider, btImprimer, btPDF, btFermer, btActualiser;
    private RubriqueSimple mEnregistrer, mAjouter, mSupprimer, mVider, mImprimer, mPDF, mFermer, mActualiser;
    private MenuContextuel menuContextuel = null;
    private BarreOutils bOutils = null;

    public Panel(JTabbedPane parent) {
        initComponents();
        init(parent);
        setIconesTabs();
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
        bOutils.AjouterBouton(btAjouter);
        bOutils.AjouterBouton(btSupprimer);
        bOutils.AjouterBouton(btEnregistrer);
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
                    menuContextuel.afficher(scrollListeMonnaie, evt.getX(), evt.getY());
                    break;
                case 1: //Tab classe
                    menuContextuel.afficher(scrollListeClasse, evt.getX(), evt.getY());
                    break;
            }
        }
        switch (tab) {
            case 0://Tab Monnaie
                //InterfaceMonnaie artcl = modeleListeMonnaie.getMonnaie(tableListeMonnaie.getSelectedRow());
                //if (artcl != null) {
                //    this.ecouteurClose.onActualiser(artcl.getCode() + ", " + artcl.getNom() + ", Taux en Devise loacale : " + Util.getMontantFrancais(artcl.getTauxMonnaieLocale()), icones.getArgent_01());
                //}
                break;
            case 1://Tab classe
                //InterfaceClasse clss = modeleListeClasse.getClasse(tableListeClasses.getSelectedRow());
                //if (clss != null) {
                //    this.ecouteurClose.onActualiser(clss.getNom() + ", nombre des sièges : " + Util.getMontantFrancais((double) clss.getCapacite()), icones.getClasse_01());
                //}
                break;
        }

    }

    public void init(JTabbedPane parent) {
        this.icones = new Icones();
        this.moi = this;
        this.parent = parent;
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
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void setAjoutAyantDroit(ModeleListeAyantDroit modeleListeAyantDroit) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
                //this.ecouteurAjout.setAjoutMonnaie(modeleListeMonnaie);
                break;
            case 1: //Tab ayantdroit
                //this.ecouteurAjout.setAjoutClasse(modeleListeClasse);
                break;
        }
    }

    public void supprimer() {
        switch (indexTabSelected) {
            case 0: //Tab eleve
                //modeleListeMonnaie.SupprimerMonnaie(tableListeMonnaie.getSelectedRow());
                break;
            case 1: //Tab ayantdroit
                //modeleListeClasse.SupprimerClasse(tableListeClasses.getSelectedRow(), this.modeleListeFrais);
                break;
        }
    }

    public void vider() {
        this.ecouteurClose.onActualiser("Vidé!", icones.getInfos_01());
        this.chRecherche.setText("");
        Date date = new Date();
        switch (indexTabSelected) {
            case 0: //eleve
                //modeleListeMonnaie.viderListe();
                break;
            case 1: //ayantdroit
                //modeleListeClasse.viderListe();
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
        menuContextuel.Ajouter(mAjouter);
        menuContextuel.Ajouter(mSupprimer);
        menuContextuel.Ajouter(mEnregistrer);
        menuContextuel.Ajouter(mVider);
        menuContextuel.Ajouter(mActualiser);
        menuContextuel.Ajouter(new JPopupMenu.Separator());
        menuContextuel.Ajouter(mImprimer);
        menuContextuel.Ajouter(mPDF);
        menuContextuel.Ajouter(new JPopupMenu.Separator());
        menuContextuel.Ajouter(mFermer);
    }
    
    public void fermer() {
        int dialogResult = JOptionPane.showConfirmDialog(this, "Etes-vous sûr de vouloir fermer cette fenêtre?", "Avertissement", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {
            this.ecouteurClose.onFermer();
        }
    }

    public void imprimer() {
        int dialogResult = JOptionPane.showConfirmDialog(this, "Etes-vous sûr de vouloir imprimer ce document?", "Avertissement", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {
            try {
                //SortiesAnneeScolaire sortie = getSortieAnneeScolaire(btImprimer, mImprimer);
                //DocumentPDF documentPDF = new DocumentPDF(this, DocumentPDF.ACTION_IMPRIMER, sortie);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void enregistrer() {
        //if (this.ecouteurExercice != null) {
        //SortiesAnneeScolaire sortie = getSortieAnneeScolaire(btEnregistrer, mEnregistrer);
        //this.ecouteurExercice.onEnregistre(sortie);
        //this.chNom.setBackground(Color.WHITE);
        //}
    }

    public void exporterPDF() {
        int dialogResult = JOptionPane.showConfirmDialog(this, "Voulez-vous les exporter dans un fichier PDF?", "Avertissement", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {
            try {
                //SortiesAnneeScolaire sortie = getSortieAnneeScolaire(btPDF, mPDF);
                //DocumentPDF docpdf = new DocumentPDF(this, DocumentPDF.ACTION_OUVRIR, sortie);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void actualiser() {
        switch (indexTabSelected) {
            case 0: //Monnaie
                //modeleListeMonnaie.actualiser();
                break;
            case 1: //Classe
                //modeleListeClasse.actualiser();
                break;
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
        scrollListeMonnaie = new javax.swing.JScrollPane();
        tableListeMonnaie = new javax.swing.JTable();
        scrollListeClasse = new javax.swing.JScrollPane();
        tableListeClasses = new javax.swing.JTable();
        labInfos = new javax.swing.JLabel();
        chRecherche = new UI.JS2bTextField();

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

        scrollListeMonnaie.setBackground(new java.awt.Color(255, 255, 255));
        scrollListeMonnaie.setAutoscrolls(true);
        scrollListeMonnaie.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                scrollListeMonnaieMouseClicked(evt);
            }
        });

        tableListeMonnaie.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Article", "Qunatité", "Unités", "Prix Unitaire HT", "Tva %", "Tva", "Total TTC"
            }
        ));
        tableListeMonnaie.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableListeMonnaieMouseClicked(evt);
            }
        });
        scrollListeMonnaie.setViewportView(tableListeMonnaie);

        tabPrincipal.addTab("Elèves", scrollListeMonnaie);

        scrollListeClasse.setBackground(new java.awt.Color(255, 255, 255));
        scrollListeClasse.setAutoscrolls(true);
        scrollListeClasse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                scrollListeClasseMouseClicked(evt);
            }
        });

        tableListeClasses.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Article", "Qunatité", "Unités", "Prix Unitaire HT", "Tva %", "Tva", "Total TTC"
            }
        ));
        tableListeClasses.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableListeClassesMouseClicked(evt);
            }
        });
        scrollListeClasse.setViewportView(tableListeClasses);

        tabPrincipal.addTab("Ayant-droit", scrollListeClasse);

        labInfos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Facture01.png"))); // NOI18N
        labInfos.setText("Prêt.");

        chRecherche.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Facture01.png"))); // NOI18N
        chRecherche.setTextInitial("Recherche");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(barreOutils, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
            .addComponent(tabPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labInfos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(chRecherche, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(barreOutils, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chRecherche, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tabPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labInfos)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tableListeMonnaieMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableListeMonnaieMouseClicked
        // TODO add your handling code here:
        ecouterMenContA(evt, 0);
    }//GEN-LAST:event_tableListeMonnaieMouseClicked

    private void scrollListeMonnaieMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_scrollListeMonnaieMouseClicked
        // TODO add your handling code here:
        ecouterMenContA(evt, 0);
    }//GEN-LAST:event_scrollListeMonnaieMouseClicked

    private void tableListeClassesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableListeClassesMouseClicked
        // TODO add your handling code here:
        ecouterMenContA(evt, 1);
    }//GEN-LAST:event_tableListeClassesMouseClicked

    private void scrollListeClasseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_scrollListeClasseMouseClicked
        // TODO add your handling code here:
        ecouterMenContA(evt, 1);
    }//GEN-LAST:event_scrollListeClasseMouseClicked

    private void tabPrincipalStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tabPrincipalStateChanged
        // TODO add your handling code here:
        activerBoutons(((JTabbedPane) evt.getSource()).getSelectedIndex());
    }//GEN-LAST:event_tabPrincipalStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToolBar barreOutils;
    private UI.JS2bTextField chRecherche;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel labInfos;
    private javax.swing.JScrollPane scrollListeClasse;
    private javax.swing.JScrollPane scrollListeMonnaie;
    private javax.swing.JTabbedPane tabPrincipal;
    private javax.swing.JTable tableListeClasses;
    private javax.swing.JTable tableListeMonnaie;
    // End of variables declaration//GEN-END:variables
}
