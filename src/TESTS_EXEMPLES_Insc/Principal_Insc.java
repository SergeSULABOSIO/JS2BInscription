/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TESTS_EXEMPLES_Insc;

import ICONES.Icones;
import SOURCES.Callback_Insc.EcouteurInscription;
import SOURCES.UI_Insc.PanelInscription;
import SOURCES.Utilitaires_Insc.DataInscription;
import SOURCES.Utilitaires_Insc.ParametreInscription;
import SOURCES.Utilitaires_Insc.SortiesInscription;
import SOURCES.Utilitaires_Insc.UtilInscription;
import Source.Callbacks.ConstructeurCriteres;
import Source.Callbacks.EcouteurCrossCanal;
import Source.Callbacks.EcouteurFreemium;
import Source.Callbacks.EcouteurNavigateurPages;
import Source.Interface.InterfaceAyantDroit;
import Source.Interface.InterfaceClasse;
import Source.Interface.InterfaceEleve;
import Source.Interface.InterfaceMonnaie;
import Source.Interface.InterfaceUtilisateur;
import Source.Objet.Ayantdroit;
import Source.Objet.Classe;
import Source.Objet.CouleurBasique;
import Source.Objet.Eleve;
import Source.Objet.Entreprise;
import Source.Objet.Annee;
import Source.Objet.Frais;
import Source.Objet.LiaisonFraisClasse;
import Source.Objet.LiaisonFraisEleve;
import Source.Objet.LiaisonFraisPeriode;
import Source.Objet.Monnaie;
import Source.Objet.UtilObjet;
import Source.Objet.Utilisateur;
import Source.UI.NavigateurPages;
import Sources.CHAMP_LOCAL;
import Sources.PROPRIETE;
import Sources.UI.JS2BPanelPropriete;
import static java.lang.Thread.sleep;
import java.util.Date;
import java.util.Vector;
import Source.Interface.InterfaceAnnee;

/**
 *
 * @author HP Pavilion
 */
public class Principal_Insc extends javax.swing.JFrame {

    /**
     * Creates new form TEST_Principal
     */
    public PanelInscription gestionnaireExercice = null;
    public Vector<Classe> listeClasses = null;
    public Vector<Frais> listeFraises = null;
    public Vector<Monnaie> listeMonnaies = null;

    private Frais Frais_Inscription, Frais_Minervale, Frais_TravailManul = null;
    private Classe classe_G1, classe_G2, classe_G3, classe_L1 = null;

    public Entreprise entreprise = new Entreprise(1, "ECOLE CARESIENNE DE KINSHASA", "7e Rue Limeté Industrielle, Kinshasa/RDC", "+243844803514", "infos@cartesien.org", "wwww.cartesien.org", "logo.png", "RCCM/KD/CD/4513", "IDN00111454", "IMP00124100", "Equity Bank Congo SA", "AIB RDC Sarl", "000000121212400", "IBANNN0012", "SWIFTCDK");
    public Utilisateur utilisateur = new Utilisateur(12, entreprise.getId(), "SULA", "BOSIO", "Serge", "sulabosiog@gmail.com", "abc", InterfaceUtilisateur.TYPE_ADMIN, UtilInscription.generateSignature(), InterfaceUtilisateur.DROIT_CONTROLER, InterfaceUtilisateur.DROIT_CONTROLER, InterfaceUtilisateur.DROIT_CONTROLER, InterfaceUtilisateur.DROIT_CONTROLER, InterfaceUtilisateur.DROIT_CONTROLER, InterfaceUtilisateur.DROIT_CONTROLER, InterfaceUtilisateur.DROIT_CONTROLER, InterfaceUtilisateur.BETA_EXISTANT);
    public Annee anneescolaire = new Annee(12, entreprise.getId(), utilisateur.getId(), "Année 2019-2020", new Date(), UtilInscription.getDate_AjouterAnnee(new Date(), 1), UtilObjet.getSignature(), InterfaceAnnee.BETA_EXISTANT);

    public Vector<Eleve> listeElevesExistants = new Vector<>();
    public Vector<Ayantdroit> listeAyantDroitsExistants = new Vector<>();

    public Principal_Insc() {
        initComponents();
        Icones icones = new Icones();
        this.setIconImage(icones.getAdresse_03().getImage());
    }

    public ParametreInscription getParametres() {
        //Chargement des Frais
        //Un très petit ajustement dd rien du tout.
        //RAS
        //ENCORE RIEN
        this.listeMonnaies = new Vector<>();
        this.listeMonnaies.add(new Monnaie(12, entreprise.getId(), utilisateur.getId(), anneescolaire.getId(), "USD - Dollars", "USD", InterfaceMonnaie.NATURE_MONNAIE_ETRANGERE, 1620, 1010101010, InterfaceMonnaie.BETA_EXISTANT));
        this.listeMonnaies.add(new Monnaie(13, entreprise.getId(), utilisateur.getId(), anneescolaire.getId(), "CDF - Francs", "CDF", InterfaceMonnaie.NATURE_MONNAIE_LOCALE, 1, 0455, InterfaceMonnaie.BETA_EXISTANT));

        this.classe_G1 = new Classe(10, utilisateur.getId(), entreprise.getId(), anneescolaire.getId(), "G1", 50, "Local 2", UtilInscription.generateSignature(), InterfaceClasse.BETA_EXISTANT);
        this.classe_G2 = new Classe(11, utilisateur.getId(), entreprise.getId(), anneescolaire.getId(), "G2", 50, "Local 3", UtilInscription.generateSignature(), InterfaceClasse.BETA_EXISTANT);
        this.classe_G3 = new Classe(12, utilisateur.getId(), entreprise.getId(), anneescolaire.getId(), "G3", 50, "Local 4", UtilInscription.generateSignature(), InterfaceClasse.BETA_EXISTANT);
        this.classe_L1 = new Classe(13, utilisateur.getId(), entreprise.getId(), anneescolaire.getId(), "L2", 50, "Local 5", UtilInscription.generateSignature(), InterfaceClasse.BETA_EXISTANT);

        //Chargement des classes
        this.listeClasses = new Vector<>();
        this.listeClasses.add(classe_G1);
        this.listeClasses.add(classe_G2);
        this.listeClasses.add(classe_G3);
        this.listeClasses.add(classe_L1);

        //Chargement des Frais
        this.listeFraises = new Vector<>();
        Vector<LiaisonFraisClasse> liaisonsINSCRIPTION = new Vector<>();
        liaisonsINSCRIPTION.add(new LiaisonFraisClasse(classe_G1.getId(), classe_G1.getNom(), classe_G1.getSignature(), 50));
        liaisonsINSCRIPTION.add(new LiaisonFraisClasse(classe_G2.getId(), classe_G2.getNom(), classe_G2.getSignature(), 50));
        liaisonsINSCRIPTION.add(new LiaisonFraisClasse(classe_G3.getId(), classe_G3.getNom(), classe_G3.getSignature(), 50));
        liaisonsINSCRIPTION.add(new LiaisonFraisClasse(classe_L1.getId(), classe_L1.getNom(), classe_L1.getSignature(), 50));
        //public TEST_Frais(int id, int idUtilisateur, int idEntreprise, int idExercice, int idMonnaie, long signatureMonnaie, String nom, String monnaie, int nbTranches, Vector<LiaisonClasseFrais> liaisonsClasses, Vector<LiaisonPeriodeFrais> liaisonsPeriodes, double montantDefaut, int beta) {

        this.Frais_Inscription = new Frais(12, utilisateur.getId(), entreprise.getId(), anneescolaire.getId(), 10, 1010101010, UtilInscription.generateSignature(), "INSCRIPTION", "$", 1, liaisonsINSCRIPTION, new Vector<LiaisonFraisPeriode>(), 50, InterfaceEleve.BETA_EXISTANT);

        Vector<LiaisonFraisClasse> liaisonsMINERVALE = new Vector<>();
        liaisonsMINERVALE.add(new LiaisonFraisClasse(classe_G1.getId(), classe_G1.getNom(), classe_G1.getSignature(), 1500));
        liaisonsMINERVALE.add(new LiaisonFraisClasse(classe_G2.getId(), classe_G2.getNom(), classe_G2.getSignature(), 1500));
        liaisonsMINERVALE.add(new LiaisonFraisClasse(classe_G3.getId(), classe_G3.getNom(), classe_G3.getSignature(), 1500));
        liaisonsMINERVALE.add(new LiaisonFraisClasse(classe_L1.getId(), classe_L1.getNom(), classe_L1.getSignature(), 1500));

        this.Frais_Minervale = new Frais(1, utilisateur.getId(), entreprise.getId(), anneescolaire.getId(), 10, 1010101010, UtilInscription.generateSignature(), "MINERVALE", "$", 3, liaisonsMINERVALE, new Vector<LiaisonFraisPeriode>(), 1500, InterfaceEleve.BETA_EXISTANT);

        Vector<LiaisonFraisClasse> liaisonsTRAVAILMAN = new Vector<>();
        liaisonsTRAVAILMAN.add(new LiaisonFraisClasse(classe_G1.getId(), classe_G1.getNom(), classe_G1.getSignature(), 10));
        liaisonsTRAVAILMAN.add(new LiaisonFraisClasse(classe_G2.getId(), classe_G2.getNom(), classe_G2.getSignature(), 10));
        liaisonsTRAVAILMAN.add(new LiaisonFraisClasse(classe_G3.getId(), classe_G3.getNom(), classe_G3.getSignature(), 10));
        liaisonsTRAVAILMAN.add(new LiaisonFraisClasse(classe_L1.getId(), classe_L1.getNom(), classe_L1.getSignature(), 10));
        this.Frais_TravailManul = new Frais(51, utilisateur.getId(), entreprise.getId(), anneescolaire.getId(), 10, 1010101010, UtilInscription.generateSignature(), "TRAVAIL MANUEL", "$", 3, liaisonsTRAVAILMAN, new Vector<LiaisonFraisPeriode>(), 10, InterfaceEleve.BETA_EXISTANT);

        this.listeFraises.add(Frais_Inscription);
        this.listeFraises.add(Frais_Minervale);
        this.listeFraises.add(Frais_TravailManul);

        return new ParametreInscription(listeMonnaies, listeClasses, listeFraises, entreprise, anneescolaire, utilisateur);
    }

    public void initDonnees() {
        Eleve elSULA = new Eleve(1, entreprise.getId(), utilisateur.getId(), anneescolaire.getId(), 10, UtilInscription.generateSignature(), "G1", "", "(+243)844803514", "SULA", "BOSIO", "SERGE", InterfaceEleve.STATUS_ACTIF, InterfaceEleve.SEXE_MASCULIN, new Date(), InterfaceEleve.BETA_EXISTANT);
        listeElevesExistants.removeAllElements();
        listeElevesExistants.add(elSULA);
        listeElevesExistants.add(new Eleve(2, entreprise.getId(), utilisateur.getId(), anneescolaire.getId(), 10, UtilInscription.generateSignature(), "G1", "", "(+243)844803514", "MAKULA", "BOFANDO", "ALAIN", InterfaceEleve.STATUS_ACTIF, InterfaceEleve.SEXE_MASCULIN, new Date(), InterfaceEleve.BETA_EXISTANT));
        listeElevesExistants.add(new Eleve(3, entreprise.getId(), utilisateur.getId(), anneescolaire.getId(), 10, UtilInscription.generateSignature(), "G1", "", "(+243)844803514", "MUTA", "KANKU", "CHRISTIAN", InterfaceEleve.STATUS_ACTIF, InterfaceEleve.SEXE_MASCULIN, new Date(), InterfaceEleve.BETA_EXISTANT));
        listeElevesExistants.add(new Eleve(4, entreprise.getId(), utilisateur.getId(), anneescolaire.getId(), 10, UtilInscription.generateSignature(), "G1", "", "(+243)844803514", "SULA", "OKONDJI", "HERMINE", InterfaceEleve.STATUS_ACTIF, InterfaceEleve.SEXE_FEMININ, new Date(), InterfaceEleve.BETA_EXISTANT));

        Vector<LiaisonFraisEleve> liaison = new Vector<>();
        liaison.add(new LiaisonFraisEleve(new Date().getTime(), Frais_Inscription.getSignature(), 12, 0, 3, "$"));
        liaison.add(new LiaisonFraisEleve(new Date().getTime(), Frais_Minervale.getSignature(), 1, 0, 3, "$"));
        liaison.add(new LiaisonFraisEleve(new Date().getTime(), Frais_TravailManul.getSignature(), 51, 0, 3, "$"));

        listeAyantDroitsExistants.removeAllElements();
        listeAyantDroitsExistants.add(new Ayantdroit(1, entreprise.getId(), utilisateur.getId(), anneescolaire.getId(), 1, "SULA", liaison, new Date().getTime(), elSULA.getSignature(), InterfaceAyantDroit.BETA_EXISTANT));
    }

    private void initParametres() {
        ParametreInscription parametre = getParametres();

        this.gestionnaireExercice = new PanelInscription(new EcouteurFreemium() {
            @Override
            public boolean onVerifie() {
                return true;
            }

            @Override
            public boolean onVerifieNombre(String nomTable) {
                return true;
            }
        }, new CouleurBasique(), this.tabPrincipale, new DataInscription(parametre), null, new EcouteurInscription() {
            @Override
            public void onDetruitExercice(int idExercice) {
                System.out.println("RAS=" + idExercice);
            }

            @Override
            public void onDetruitElements(int idElement, int index, long signature) {
                System.out.println("Element supprimé = " + idElement + ", indice = " + index + ", signature = " + signature);
            }

            @Override
            public void onEnregistre(SortiesInscription sortiesEleveAyantDroit) {
                Thread th = new Thread() {
                    @Override
                    public void run() {
                        try {
                            sortiesEleveAyantDroit.getEcouteurEnregistrement().onUploading("Chargement...");
                            sleep(10);

                            sortiesEleveAyantDroit.getListeEleves().forEach((Oeleve) -> {
                                if (Oeleve.getBeta() == InterfaceEleve.BETA_MODIFIE || Oeleve.getBeta() == InterfaceEleve.BETA_NOUVEAU) {
                                    System.out.println(" * " + Oeleve.toString());

                                    //Après enregistrement
                                    Oeleve.setId(new Date().getSeconds());
                                    Oeleve.setBeta(InterfaceEleve.BETA_EXISTANT);
                                }
                            });

                            sortiesEleveAyantDroit.getListeAyantDroit().forEach((Oeleve) -> {
                                if (Oeleve.getBeta() == InterfaceAyantDroit.BETA_MODIFIE || Oeleve.getBeta() == InterfaceAyantDroit.BETA_NOUVEAU) {
                                    System.out.println(" * " + Oeleve.toString() + " : ");

                                    //Après enregistrement
                                    Oeleve.setId(new Date().getSeconds());
                                    Oeleve.setBeta(InterfaceAyantDroit.BETA_EXISTANT);
                                }
                            });

                            sortiesEleveAyantDroit.getEcouteurEnregistrement().onDone("Enregistré!");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                th.start();

            }

            @Override
            public void onClose() {
                
            }
        }, new EcouteurCrossCanal() {
            @Override
            public void onOuvrirLitiges(Eleve eleve) {
                System.out.println("Ouverture des litiges de " + eleve.getNom());
            }

            @Override
            public void onOuvrirPaiements(Eleve eleve) {
                System.out.println("Ouverture des paiements de " + eleve.getNom());
            }

            @Override
            public void onOuvrirInscription(Eleve eleve) {
                System.out.println("Ouverture de la fiche d'inscription de " + eleve.getNom());
            }
        });

    }

    private boolean verifierNomEleve(String motCle, Eleve Ieleve) {
        boolean reponse = false;
        if (motCle.trim().length() == 0) {
            reponse = true;
        } else {
            reponse = ((UtilInscription.contientMotsCles(Ieleve.getNom() + " " + Ieleve.getPostnom() + " " + Ieleve.getPrenom(), motCle)));
        }
        return reponse;
    }

    private void chercherEleves(String motCle, int taillePage, JS2BPanelPropriete criteresAvances) {
        int index = 1;
        gestionnaireExercice.reiniliserEleves();
        gestionnaireExercice.reiniliserAyantDroit();

        for (Eleve ee : listeElevesExistants) {
            if (index == taillePage) {
                break;
            }
            boolean repMotCle = verifierNomEleve(motCle, ee);
            boolean repSexe = false;
            boolean repStat = false;
            boolean repClasse = false;

            if (criteresAvances != null) {
                for (PROPRIETE prop : criteresAvances.getListePro()) {
                    String nomCritere = prop.getNom();
                    Object valeur = prop.getValeurSelectionne();

                    System.out.println("Nom: " + prop.getNom() + ", val: " + valeur);

                    switch (nomCritere) {
                        case "Genre":
                            if (ee.getSexe() == InterfaceEleve.SEXE_MASCULIN && (valeur + "").equals("Masculin")) {
                                repSexe = true;
                            } else if (ee.getSexe() == InterfaceEleve.SEXE_FEMININ && (valeur + "").equals("Féminin")) {
                                repSexe = true;
                            } else if ((valeur + "").trim().length() == 0) {
                                repSexe = true;
                            } else {
                                repSexe = false;
                            }
                            break;
                        case "Statut":
                            if (ee.getStatus() == InterfaceEleve.STATUS_ACTIF && (valeur + "").equals("ACTIF")) {
                                repStat = true;
                            } else if (ee.getStatus() == InterfaceEleve.STATUS_INACTIF && (valeur + "").equals("INACTIF")) {
                                repStat = true;
                            } else if ((valeur + "").trim().length() == 0) {
                                repStat = true;
                            } else {
                                repStat = false;
                            }
                            break;
                        case "Classe":
                            if ((valeur + "").trim().length() == 0) {
                                repClasse = true;
                            } else {
                                Classe clss = gestionnaireExercice.getClasse(valeur + "");
                                if (clss != null) {
                                    if (clss.getId() == ee.getIdClasse()) {
                                        repClasse = true;
                                    }
                                }
                            }
                            break;
                        default:
                            break;
                    }
                }
            } else {
                repSexe = true;
                repStat = true;
                repClasse = true;
            }
            if (repMotCle == true && repSexe == true && repStat == true && repClasse == true) {
                gestionnaireExercice.setDonneesEleves(ee);
                for (Ayantdroit ayd : listeAyantDroitsExistants) {
                    if (ayd.getIdEleve() == ee.getId()) {
                        gestionnaireExercice.setDonneesAyantDroit(ayd);
                    }
                }

            }
            index++;
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

        jButton1 = new javax.swing.JButton();
        tabPrincipale = new javax.swing.JTabbedPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Ouvrir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addContainerGap(650, Short.MAX_VALUE))
            .addComponent(tabPrincipale, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabPrincipale, javax.swing.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Icones icones = new Icones();
        initParametres();
        initDonnees();
        if (gestionnaireExercice != null) {
            NavigateurPages navigateur = gestionnaireExercice.getNavigateur();
            navigateur.initialiser(this, new EcouteurNavigateurPages() {
                @Override
                public void onRecharge(String motCle, int pageActuelle, int taillePage, JS2BPanelPropriete criteresAvances) {
                    new Thread() {
                        public void run() {
                            navigateur.patienter(true, "Chargement...");
                            System.out.println("CHARGEMENT DE DONNEES...");
                            chercherEleves(motCle, taillePage, criteresAvances);
                            navigateur.setInfos(100, 10);
                            navigateur.patienter(false, "Prêt.");
                        }
                    }.start();
                }
            }, new ConstructeurCriteres() {
                @Override
                public JS2BPanelPropriete onInitialise() {
                    Vector listeClasses = new Vector();
                    listeClasses.add("TOUTES LES CLASSES");
                    for (Classe cl : gestionnaireExercice.getDataInscription().getParametreInscription().getListeClasses()) {
                        listeClasses.add(cl.getNom());
                    }

                    Vector listeGenre = new Vector();
                    listeGenre.add("TOUT GENRE");
                    listeGenre.add("Masculin");
                    listeGenre.add("Féminin");

                    Vector listeStatut = new Vector();
                    listeStatut.add("TOUT STATUT");
                    listeStatut.add("ACTIF");
                    listeStatut.add("INACTIF");

                    JS2BPanelPropriete panProp = new JS2BPanelPropriete(icones.getFiltrer_01(), "Critères avancés", true);
                    panProp.viderListe();
                    panProp.AjouterPropriete(new CHAMP_LOCAL(icones.getClasse_01(), "Classe", "cls", listeClasses, "", PROPRIETE.TYPE_CHOIX_LISTE), 0);
                    panProp.AjouterPropriete(new CHAMP_LOCAL(icones.getClient_01(), "Genre", "cls", listeGenre, "", PROPRIETE.TYPE_CHOIX_LISTE), 0);
                    panProp.AjouterPropriete(new CHAMP_LOCAL(icones.getAimer_01(), "Statut", "cls", listeStatut, "", PROPRIETE.TYPE_CHOIX_LISTE), 0);

                    return panProp;
                }
            });

            tabPrincipale.addTab("Fiche d'inscription", gestionnaireExercice);
            tabPrincipale.setSelectedComponent(gestionnaireExercice);

            navigateur.reload();
        }


    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Principal_Insc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal_Insc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal_Insc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal_Insc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal_Insc().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JTabbedPane tabPrincipale;
    // End of variables declaration//GEN-END:variables
}
