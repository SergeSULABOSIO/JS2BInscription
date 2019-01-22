/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TESTS_EXEMPLES;

import SOURCES.Callback.EcouteurEleveAyantDroit;
import SOURCES.Interfaces.InterfaceClasse;
import SOURCES.Interfaces.InterfaceFrais;
import SOURCES.UI.Panel;
import SOURCES.Utilitaires.LiaisonClasseFrais;
import SOURCES.Utilitaires.LiaisonEleveFrais;
import SOURCES.Utilitaires.SortiesEleveAyantDroit;
import static java.lang.Thread.sleep;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author HP Pavilion
 */
public class TEST_Principal extends javax.swing.JFrame {

    /**
     * Creates new form TEST_Principal
     */
    public Panel gestionnaireExercice = null;
    public Vector<InterfaceClasse> listeClasses = null;
    public Vector<InterfaceFrais> listeFraises = null;
    private TEST_Frais Frais_Inscription, Frais_Minervale, Frais_TravailManul = null;
    private TEST_Classe classe_G1, classe_G2, classe_G3, classe_L1 = null;
    public int idUtilisateur = 1;
    public int idEntreprise = 1;
    public int idExercice = 1;
    

    public TEST_Principal() {
        initComponents();
    }

    private void initParametres() {
        this.classe_G1 = new TEST_Classe(10, idUtilisateur, idEntreprise, idExercice, "G1", 50, "Local 2", new Date().getTime());
        this.classe_G2 = new TEST_Classe(11, idUtilisateur, idEntreprise, idExercice, "G2", 50, "Local 3", new Date().getTime()+1);
        this.classe_G3 = new TEST_Classe(12, idUtilisateur, idEntreprise, idExercice, "G3", 50, "Local 4", new Date().getTime()+2);
        this.classe_L1 = new TEST_Classe(13, idUtilisateur, idEntreprise, idExercice, "L2", 50, "Local 5", new Date().getTime()+3);
        
        //Chargement des classes
        this.listeClasses = new Vector<>();
        this.listeClasses.add(classe_G1);
        this.listeClasses.add(classe_G2);
        this.listeClasses.add(classe_G3);
        this.listeClasses.add(classe_L1);
        
        //Chargement des Frais
        this.listeFraises = new Vector<>();
        Vector<LiaisonClasseFrais> liaisonsINSCRIPTION = new Vector<>();
        liaisonsINSCRIPTION.add(new LiaisonClasseFrais(classe_G1, 50));
        liaisonsINSCRIPTION.add(new LiaisonClasseFrais(classe_G2, 50));
        liaisonsINSCRIPTION.add(new LiaisonClasseFrais(classe_G3, 50));
        liaisonsINSCRIPTION.add(new LiaisonClasseFrais(classe_L1, 50));
        this.Frais_Inscription = new TEST_Frais(12, idUtilisateur, idEntreprise, idExercice, 10, 1010101010, "INSCRIPTION", "$", 1, 50, liaisonsINSCRIPTION);
        
        Vector<LiaisonClasseFrais> liaisonsMINERVALE = new Vector<>();
        liaisonsMINERVALE.add(new LiaisonClasseFrais(classe_G1, 1500));
        liaisonsMINERVALE.add(new LiaisonClasseFrais(classe_G2, 1500));
        liaisonsMINERVALE.add(new LiaisonClasseFrais(classe_G3, 1500));
        liaisonsMINERVALE.add(new LiaisonClasseFrais(classe_L1, 1500));
        this.Frais_Minervale = new TEST_Frais(1, idUtilisateur, idEntreprise, idExercice, 10, 1010101010, "MINERVALE", "$", 3, 1500, liaisonsMINERVALE);
        
        Vector<LiaisonClasseFrais> liaisonsTRAVAILMAN = new Vector<>();
        liaisonsTRAVAILMAN.add(new LiaisonClasseFrais(classe_G1, 10));
        liaisonsTRAVAILMAN.add(new LiaisonClasseFrais(classe_G2, 10));
        liaisonsTRAVAILMAN.add(new LiaisonClasseFrais(classe_G3, 10));
        liaisonsTRAVAILMAN.add(new LiaisonClasseFrais(classe_L1, 10));
        this.Frais_TravailManul = new TEST_Frais(51, idUtilisateur, idEntreprise, idExercice, 10, 1010101010, "TRAVAIL MANUEL", "$", 3, 10, liaisonsTRAVAILMAN);
        
        this.listeFraises.add(Frais_Inscription);
        this.listeFraises.add(Frais_Minervale);
        this.listeFraises.add(Frais_TravailManul);
        
        
        this.gestionnaireExercice = new Panel(this.tabPrincipale, this.idUtilisateur, this.idEntreprise, this.idExercice, this.listeClasses, this.listeFraises, new EcouteurEleveAyantDroit() {
            @Override
            public void onEnregistre(SortiesEleveAyantDroit sortiesEleveAyantDroit) {
                
                
                Thread th = new Thread() {
                    @Override
                    public void run() {
                        try {
                            sortiesEleveAyantDroit.getEcouteurEnregistrement().onUploading("Chargement...");
                            sleep(5000);
                            
                            System.out.println("ELEVES :");
                            sortiesEleveAyantDroit.getListeEleves().forEach((Oeleve) -> {
                                System.out.println(" * " + Oeleve.getNom() + ", " + Oeleve.getPostnom()+", " + Oeleve.getPrenom()+", Classe : " + Oeleve.getClasse());
                            });
                            
                            System.out.println("ELEVES AYANT-DROIT :");
                            sortiesEleveAyantDroit.getListeAyantDroit().forEach((Oeleve) -> {
                                System.out.println(" * " + Oeleve.getEleve() + " : ");
                                for(LiaisonEleveFrais liaisonEleveFrais : Oeleve.getListeLiaisons()){
                                    System.out.println("\t * " + liaisonEleveFrais.getIdFrais()+" : " + liaisonEleveFrais.getMontant()+" " + liaisonEleveFrais.getMonnaie());
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
            java.util.logging.Logger.getLogger(TEST_Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TEST_Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TEST_Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TEST_Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TEST_Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JTabbedPane tabPrincipale;
    // End of variables declaration//GEN-END:variables
}
