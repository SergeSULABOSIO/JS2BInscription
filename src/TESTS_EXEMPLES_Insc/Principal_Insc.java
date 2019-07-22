/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TESTS_EXEMPLES_Insc;

import SOURCES.Callback_Insc.EcouteurInscription;
import SOURCES.UI_Insc.PanelInscription;
import SOURCES.Utilitaires_Insc.DonneesInscription;
import SOURCES.Utilitaires_Insc.ParametreInscription;
import SOURCES.Utilitaires_Insc.SortiesInscription;
import SOURCES.Utilitaires_Insc.UtilInscription;
import Source.Callbacks.EcouteurCrossCanal;
import Source.Interface.InterfaceAyantDroit;
import Source.Interface.InterfaceClasse;
import Source.Interface.InterfaceEleve;
import Source.Interface.InterfaceExercice;
import Source.Interface.InterfaceMonnaie;
import Source.Objet.Ayantdroit;
import Source.Objet.Classe;
import Source.Objet.CouleurBasique;
import Source.Objet.Eleve;
import Source.Objet.Entreprise;
import Source.Objet.Exercice;
import Source.Objet.Frais;
import Source.Objet.LiaisonFraisClasse;
import Source.Objet.LiaisonFraisEleve;
import Source.Objet.LiaisonFraisPeriode;
import Source.Objet.Monnaie;
import java.awt.Color;
import static java.lang.Thread.sleep;
import java.util.Date;
import java.util.Vector;

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
    public int idUtilisateur = 1;
    public int idEntreprise = 1;
    public int idExercice = 1;
    public Entreprise entreprise = new Entreprise(1, "ECOLE CARESIENNE DE KINSHASA", "7e Rue Limeté Industrielle, Kinshasa/RDC", "+243844803514", "infos@cartesien.org", "wwww.cartesien.org", "logo.png", "RCCM/KD/CD/4513", "IDN00111454", "IMP00124100", "Equity Bank Congo SA", "AIB RDC Sarl", "000000121212400", "IBANNN0012", "SWIFTCDK");
    public Exercice anneescolaire = null;
    
    public Principal_Insc() {
        initComponents();
    }
    
    public ParametreInscription getParametres(){
        //Chargement des Frais
        this.listeMonnaies = new Vector<>();
        this.listeMonnaies.add(new Monnaie(12, idEntreprise, idUtilisateur, idExercice, "USD - Dollars", "USD", InterfaceMonnaie.NATURE_MONNAIE_ETRANGERE, 1620, 1010101010, InterfaceMonnaie.BETA_EXISTANT));
        this.listeMonnaies.add(new Monnaie(13, idEntreprise, idUtilisateur, idExercice, "CDF - Francs", "CDF", InterfaceMonnaie.NATURE_MONNAIE_LOCALE, 1, 0455, InterfaceMonnaie.BETA_EXISTANT));
        
        
        this.classe_G1 = new Classe(10, idUtilisateur, idEntreprise, idExercice, "G1", 50, "Local 2", UtilInscription.generateSignature(), InterfaceClasse.BETA_EXISTANT);
        this.classe_G2 = new Classe(11, idUtilisateur, idEntreprise, idExercice, "G2", 50, "Local 3", UtilInscription.generateSignature(), InterfaceClasse.BETA_EXISTANT);
        this.classe_G3 = new Classe(12, idUtilisateur, idEntreprise, idExercice, "G3", 50, "Local 4", UtilInscription.generateSignature(), InterfaceClasse.BETA_EXISTANT);
        this.classe_L1 = new Classe(13, idUtilisateur, idEntreprise, idExercice, "L2", 50, "Local 5", UtilInscription.generateSignature(), InterfaceClasse.BETA_EXISTANT);
        
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
    
        this.Frais_Inscription = new Frais(12, idUtilisateur, idEntreprise, idExercice, 10, 1010101010, UtilInscription.generateSignature(), "INSCRIPTION", "$", 1, liaisonsINSCRIPTION, new Vector<LiaisonFraisPeriode>(), 50, InterfaceEleve.BETA_EXISTANT);
        
        Vector<LiaisonFraisClasse> liaisonsMINERVALE = new Vector<>();
        liaisonsMINERVALE.add(new LiaisonFraisClasse(classe_G1.getId(), classe_G1.getNom(), classe_G1.getSignature(), 1500));
        liaisonsMINERVALE.add(new LiaisonFraisClasse(classe_G2.getId(), classe_G2.getNom(), classe_G2.getSignature(), 1500));
        liaisonsMINERVALE.add(new LiaisonFraisClasse(classe_G3.getId(), classe_G3.getNom(), classe_G3.getSignature(), 1500));
        liaisonsMINERVALE.add(new LiaisonFraisClasse(classe_L1.getId(), classe_L1.getNom(), classe_L1.getSignature(), 1500));
        
        this.Frais_Minervale = new Frais(1, idUtilisateur, idEntreprise, idExercice, 10, 1010101010, UtilInscription.generateSignature(), "MINERVALE", "$", 3, liaisonsMINERVALE, new Vector<LiaisonFraisPeriode>(), 1500, InterfaceEleve.BETA_EXISTANT);
        
        Vector<LiaisonFraisClasse> liaisonsTRAVAILMAN = new Vector<>();
        liaisonsTRAVAILMAN.add(new LiaisonFraisClasse(classe_G1.getId(), classe_G1.getNom(), classe_G1.getSignature(), 10));
        liaisonsTRAVAILMAN.add(new LiaisonFraisClasse(classe_G2.getId(), classe_G2.getNom(), classe_G2.getSignature(), 10));
        liaisonsTRAVAILMAN.add(new LiaisonFraisClasse(classe_G3.getId(), classe_G3.getNom(), classe_G3.getSignature(), 10));
        liaisonsTRAVAILMAN.add(new LiaisonFraisClasse(classe_L1.getId(), classe_L1.getNom(), classe_L1.getSignature(), 10));
        this.Frais_TravailManul = new Frais(51, idUtilisateur, idEntreprise, idExercice, 10, 1010101010, UtilInscription.generateSignature(), "TRAVAIL MANUEL", "$", 3, liaisonsTRAVAILMAN, new Vector<LiaisonFraisPeriode>(), 10, InterfaceEleve.BETA_EXISTANT);
        
        this.listeFraises.add(Frais_Inscription);
        this.listeFraises.add(Frais_Minervale);
        this.listeFraises.add(Frais_TravailManul);
        
        anneescolaire = new Exercice(12, entreprise.getId(), idUtilisateur, "Année 2019-2020", new Date(), UtilInscription.getDate_AjouterAnnee(new Date(), 1), InterfaceExercice.BETA_EXISTANT);
        
        ParametreInscription parametres = new ParametreInscription(listeMonnaies, listeClasses, listeFraises, entreprise, anneescolaire, idUtilisateur, "Serge SULA BOSIO");
        return parametres;
    }
    
    
    public DonneesInscription getDonnees(){
        Vector<Eleve> listeElevesExistants = new Vector<>();
        long signEle = (new Date().getTime());
        listeElevesExistants.add(new Eleve(1, entreprise.getId(), idUtilisateur, anneescolaire.getId(), 10, signEle, "G1", "", "(+243)844803514", "SULA", "BOSIO", "SERGE", InterfaceEleve.STATUS_ACTIF, InterfaceEleve.SEXE_MASCULIN, new Date(), InterfaceEleve.BETA_EXISTANT));
        listeElevesExistants.add(new Eleve(2, entreprise.getId(), idUtilisateur, anneescolaire.getId(), 10, new Date().getTime()+1, "G1", "", "(+243)844803514", "MAKULA", "BOFANDO", "ALAIN", InterfaceEleve.STATUS_ACTIF, InterfaceEleve.SEXE_MASCULIN, new Date(), InterfaceEleve.BETA_EXISTANT));
        listeElevesExistants.add(new Eleve(3, entreprise.getId(), idUtilisateur, anneescolaire.getId(), 10, new Date().getTime()+2, "G1", "", "(+243)844803514", "MUTA", "KANKU", "CHRISTIAN", InterfaceEleve.STATUS_ACTIF, InterfaceEleve.SEXE_MASCULIN, new Date(), InterfaceEleve.BETA_EXISTANT));
        listeElevesExistants.add(new Eleve(4, entreprise.getId(), idUtilisateur, anneescolaire.getId(), 10, new Date().getTime()+3, "G1", "", "(+243)844803514", "SULA", "OKONDJI", "HERMINE", InterfaceEleve.STATUS_ACTIF, InterfaceEleve.SEXE_FEMININ, new Date(), InterfaceEleve.BETA_EXISTANT));
        
        
        Vector<Ayantdroit> listeAyantDroitsExistants = new Vector<>();
        Vector<LiaisonFraisEleve> liaison = new Vector<>();
        liaison.add(new LiaisonFraisEleve(new Date().getTime(), Frais_Inscription.getSignature(), 12, 0, 3, "$"));
        liaison.add(new LiaisonFraisEleve(new Date().getTime(), Frais_Minervale.getSignature(), 1, 0, 3, "$"));
        liaison.add(new LiaisonFraisEleve(new Date().getTime(), Frais_TravailManul.getSignature(), 51, 0, 3, "$"));
        
        listeAyantDroitsExistants.add(new Ayantdroit(1, entreprise.getId(), idUtilisateur, anneescolaire.getId(), 1, "SULA", liaison, new Date().getTime(), signEle, InterfaceAyantDroit.BETA_EXISTANT));
        
        DonneesInscription donnees = new DonneesInscription(listeElevesExistants, listeAyantDroitsExistants);
        return donnees;
    }

    private void initParametres() {
        ParametreInscription parametre = getParametres();
        DonneesInscription donnees = getDonnees();
        
        CouleurBasique couleurs = new CouleurBasique();
        couleurs.setCouleur_background_normal(Color.white);
        couleurs.setCouleur_background_selection(UtilInscription.COULEUR_BLEU);
        couleurs.setCouleur_encadrement_selection(UtilInscription.COULEUR_ORANGE);
        couleurs.setCouleur_foreground_objet_existant(UtilInscription.COULEUR_BLEU);
        couleurs.setCouleur_foreground_objet_modifie(UtilInscription.COULEUR_BLEU_CLAIRE_1);
        couleurs.setCouleur_foreground_objet_nouveau(UtilInscription.COULEUR_ROUGE);
        
        this.gestionnaireExercice = new PanelInscription(couleurs, this.tabPrincipale, donnees, parametre, new EcouteurInscription() {
            @Override
            public void onDetruitExercice(int idExercice) {
                System.out.println("RAS=" + idExercice);
            }
            

            @Override
            public void onDetruitElements(int idElement, int index) {
                System.out.println("Element supprimé = " + idElement + ", indice = " + index);
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
                                if(Oeleve.getBeta() == InterfaceEleve.BETA_MODIFIE || Oeleve.getBeta() == InterfaceEleve.BETA_NOUVEAU){
                                    System.out.println(" * " + Oeleve.toString());
                                    
                                    //Après enregistrement
                                    Oeleve.setBeta(InterfaceEleve.BETA_EXISTANT);
                                }
                            });
                          
                            sortiesEleveAyantDroit.getListeAyantDroit().forEach((Oeleve) -> {
                                if(Oeleve.getBeta() == InterfaceAyantDroit.BETA_MODIFIE || Oeleve.getBeta() == InterfaceAyantDroit.BETA_NOUVEAU){
                                    System.out.println(" * " + Oeleve.toString() + " : ");
                                    
                                    //Après enregistrement
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
        }, new EcouteurCrossCanal() {
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

        initParametres();
        
        //Chargement du gestionnaire sur l'onglet
        tabPrincipale.addTab("Fiche d'inscription", gestionnaireExercice);
        
        //On séléctionne l'onglet actuel
        tabPrincipale.setSelectedComponent(gestionnaireExercice);


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
