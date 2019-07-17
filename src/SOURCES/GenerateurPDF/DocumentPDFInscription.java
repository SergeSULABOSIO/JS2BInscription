/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.GenerateurPDF;

import SOURCES.Interface.InterfaceAyantDroit;
import SOURCES.Interface.InterfaceClasse;
import SOURCES.Interface.InterfaceEleve;
import SOURCES.Interface.InterfaceEntreprise;
import SOURCES.Interface.InterfaceFrais;
import SOURCES.Interface.InterfaceMonnaie;
import SOURCES.UI.PanelInscription;
import SOURCES.Utilitaires.LiaisonEleveFrais;
import SOURCES.Utilitaires.SortiesInscription;
import SOURCES.Utilitaires.UtilInscription;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author Gateway
 */
public class DocumentPDFInscription extends PdfPageEventHelper {

    private Document document = new Document(PageSize.A4);
    private Font Font_Titre1 = null;
    private Font Font_Titre2 = null;
    private Font Font_Titre3 = null;
    private Font Font_TexteSimple = null;
    private Font Font_TexteSimple_petit, Font_TexteSimple_petit_Gras = null;
    private Font Font_TexteSimple_Gras = null;
    private Font Font_TexteSimple_Italique = null;
    private Font Font_TexteSimple_Gras_Italique = null;
    public static final int ACTION_IMPRIMER = 0;
    public static final int ACTION_OUVRIR = 1;
    private SortiesInscription sortiesEleveAyantDroit = null;
    private PanelInscription gestionnaireInscription;
    private String monnaie = "";

    public DocumentPDFInscription(PanelInscription panel, int action, SortiesInscription sortiesEleveAyantDroit) {
        try {
            init(panel, action, sortiesEleveAyantDroit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init(PanelInscription panel, int action, SortiesInscription sortiesEleveAyantDroit) {
        this.gestionnaireInscription = panel;
        this.sortiesEleveAyantDroit = sortiesEleveAyantDroit;
        parametre_initialisation_fichier();
        parametre_construire_fichier();
        if (action == ACTION_OUVRIR) {
            parametres_ouvrir_fichier();
        } else if (action == ACTION_IMPRIMER) {
            parametres_imprimer_fichier();
        }
    }

    private void parametre_initialisation_fichier() {
        //Les titres du document
        this.Font_Titre1 = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD, BaseColor.DARK_GRAY);
        this.Font_Titre2 = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD, BaseColor.BLACK);
        this.Font_Titre3 = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL, BaseColor.BLACK);

        //Les textes simples
        this.Font_TexteSimple = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL, BaseColor.BLACK);
        this.Font_TexteSimple_petit = new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.NORMAL, BaseColor.BLACK);
        this.Font_TexteSimple_petit_Gras = new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.BOLD, BaseColor.BLACK);
        this.Font_TexteSimple_Gras = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.BOLD, BaseColor.BLACK);
        this.Font_TexteSimple_Italique = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.ITALIC, BaseColor.BLACK);
        this.Font_TexteSimple_Gras_Italique = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.BOLDITALIC, BaseColor.BLACK);
    }

    private void parametre_construire_fichier() {
        try {
            String nomFichier = this.gestionnaireInscription.getNomfichierPreuve();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(nomFichier));
            writer.setPageEvent(new MarqueS2B());
            this.document.open();
            setDonneesBibliographiques();
            setContenuDeLaPage();
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.gestionnaireInscription, "Impossible d'effectuer cette opération.\nAssurez vous qu'aucun fichier du même nom ne soit actuellement ouvert.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void parametres_ouvrir_fichier() {
        String nomFichier = "Output.pdf";
        if (this.gestionnaireInscription != null) {
            nomFichier = this.gestionnaireInscription.getNomfichierPreuve();
        }
        File fic = new File(nomFichier);
        if (fic.exists() == true) {
            try {
                Desktop.getDesktop().open(fic);
                if (sortiesEleveAyantDroit != null) {
                    sortiesEleveAyantDroit.getEcouteurEnregistrement().onDone("PDF ouvert avec succès!");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                String message = "Impossible d'ouvrir le fichier !";
                JOptionPane.showMessageDialog(this.gestionnaireInscription, message, "Erreur", JOptionPane.ERROR_MESSAGE);
                if (sortiesEleveAyantDroit != null) {
                    sortiesEleveAyantDroit.getEcouteurEnregistrement().onError(message);
                }
            }
        }
    }

    private void parametres_imprimer_fichier() {
        String nomFichier = "Output.pdf";
        if (this.gestionnaireInscription != null) {
            nomFichier = this.gestionnaireInscription.getNomfichierPreuve();
        }
        File fic = new File(nomFichier);
        if (fic.exists() == true) {
            try {
                Desktop.getDesktop().print(fic);
                if (sortiesEleveAyantDroit != null) {
                    sortiesEleveAyantDroit.getEcouteurEnregistrement().onDone("Impression effectuée avec succès!");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                String message = "Impossible d'imprimer les données ";
                JOptionPane.showMessageDialog(this.gestionnaireInscription, message, "Erreur", JOptionPane.ERROR_MESSAGE);
                if (sortiesEleveAyantDroit != null) {
                    sortiesEleveAyantDroit.getEcouteurEnregistrement().onError(message);
                }
            }
        }
    }

    private void setDonneesBibliographiques() {
        this.document.addTitle("Document généré par JS2BFacture");
        this.document.addSubject("Etat");
        this.document.addKeywords("Java, PDF, Facture");
        this.document.addAuthor("S2B. Simple.Intuitif");
        this.document.addCreator("SULA BOSIO Serge, S2B, sulabosiog@gmail.com");
    }

    private void ajouterLigne(int number) throws Exception {
        Paragraph paragraphe = new Paragraph();
        for (int i = 0; i < number; i++) {
            paragraphe.add(new Paragraph(" "));
        }
        this.document.add(paragraphe);
    }

    private void setTitreEtDateDocument() throws Exception {
        Paragraph preface = new Paragraph();
        String titre = this.gestionnaireInscription.getTitreDoc() + "";

        if (this.gestionnaireInscription != null) {
            preface.add(getParagraphe("Date: " + UtilInscription.getDateFrancais(this.gestionnaireInscription.getDateDocument()), Font_Titre3, Element.ALIGN_RIGHT));
            preface.add(getParagraphe(titre, Font_Titre1, Element.ALIGN_CENTER));
        } else {
            preface.add(getParagraphe("Date: " + UtilInscription.getDateFrancais(new Date()), Font_Titre3, Element.ALIGN_RIGHT));
            preface.add(getParagraphe("Facture n°XXXXXXXXX/2018", Font_Titre1, Element.ALIGN_CENTER));
        }
        this.document.add(preface);
    }

    private void setSignataire() throws Exception {
        if (this.gestionnaireInscription != null) {
            this.document.add(getParagraphe(""
                    + "Produit par " + this.gestionnaireInscription.getNomUtilisateur() + "\n"
                    + "Validé par :..............................................\n\n"
                    + "Signature", Font_TexteSimple, Element.ALIGN_RIGHT));
        } else {
            this.document.add(getParagraphe(""
                    + "Produit par Serge SULA BOSIO\n"
                    + "Validé par :..............................................\n\n"
                    + "Signature", Font_TexteSimple, Element.ALIGN_RIGHT));
        }

    }

    private void setBasDePage() throws Exception {
        if (this.gestionnaireInscription != null) {
            InterfaceEntreprise entreprise = this.gestionnaireInscription.getEntreprise();
            if (entreprise != null) {
                this.document.add(getParagraphe(entreprise.getNom() + "\n" + entreprise.getAdresse() + " | " + entreprise.getTelephone() + " | " + entreprise.getEmail() + " | " + entreprise.getSiteWeb(), Font_TexteSimple, Element.ALIGN_CENTER));
            } else {
                addDefaultEntreprise();
            }
        } else {
            addDefaultEntreprise();
        }
    }

    private void addDefaultEntreprise() throws Exception {
        this.document.add(getParagraphe(""
                + "UAP RDC Sarl. Courtier d’Assurances n°0189\n"
                + "Prins van Luikschool, Av de la Gombe, Gombe, Kinshasa, DRC | (+243) 975 33 88 33 | info@aib-brokers.com", Font_TexteSimple, Element.ALIGN_CENTER));

    }

    private Paragraph getParagraphe(String texte, Font font, int alignement) {
        Paragraph par = new Paragraph(texte, font);
        par.setAlignment(alignement);
        return par;
    }

    private Phrase getPhrase(String texte, Font font) {
        Phrase phrase = new Phrase(texte, font);
        return phrase;
    }

    private void setLogoEtDetailsEntreprise() {
        try {
            PdfPTable tableauEnteteFacture = new PdfPTable(2);
            int[] dimensionsWidthHeight = {320, 1460};
            tableauEnteteFacture.setWidths(dimensionsWidthHeight);
            tableauEnteteFacture.setHorizontalAlignment(Element.ALIGN_LEFT);

            //CELLULE DU LOGO DE L'ENTREPRISE
            PdfPCell celluleLogoEntreprise = null;
            String logo = "";
            if (this.gestionnaireInscription != null) {
                logo = this.gestionnaireInscription.getEntreprise().getLogo();
                System.out.println("Fic logo: " + logo);
            }
            File ficLogo = new File(new File(logo).getName());
            System.out.println("Fichier Logo: " + ficLogo.getAbsolutePath());
            if (ficLogo.exists() == true) {
                System.out.println("Fichier Logo: " + ficLogo.getAbsolutePath()+ " - Trouvé!");
                //Chargement du logo et redimensionnement afin que celui-ci convienne dans l'espace qui lui est accordé
                Image Imglogo = Image.getInstance(ficLogo.getName());
                Imglogo.scaleAbsoluteWidth(70);
                Imglogo.scaleAbsoluteHeight(70);
                celluleLogoEntreprise = new PdfPCell(Imglogo);
            } else {
                celluleLogoEntreprise = new PdfPCell();
            }
            celluleLogoEntreprise.setPadding(2);
            celluleLogoEntreprise.setBorderWidth(0);
            celluleLogoEntreprise.setBorderColor(BaseColor.BLACK);
            tableauEnteteFacture.addCell(celluleLogoEntreprise);

            //CELLULE DES DETAILS SUR L'ENTREPRISE - TEXTE (Nom, Adresse, Téléphone, Email, etc)
            PdfPCell celluleDetailsEntreprise = new PdfPCell();
            celluleDetailsEntreprise.setPadding(2);
            celluleDetailsEntreprise.setPaddingLeft(5);
            celluleDetailsEntreprise.setBorderWidth(0);
            celluleDetailsEntreprise.setBorderWidthLeft(1);
            celluleDetailsEntreprise.setBorderColor(BaseColor.BLACK);
            celluleDetailsEntreprise.setHorizontalAlignment(Element.ALIGN_TOP);

            if (this.gestionnaireInscription != null) {
                InterfaceEntreprise entreprise = this.gestionnaireInscription.getEntreprise();
                if (entreprise != null) {
                    celluleDetailsEntreprise.addElement(getParagraphe(entreprise.getNom(), Font_Titre2, Element.ALIGN_LEFT));
                    celluleDetailsEntreprise.addElement(getParagraphe(entreprise.getAdresse(), Font_TexteSimple_petit, Element.ALIGN_LEFT));
                    celluleDetailsEntreprise.addElement(getParagraphe(entreprise.getSiteWeb() + " | " + entreprise.getEmail() + " | " + entreprise.getTelephone(), Font_TexteSimple_petit, Element.ALIGN_LEFT));
                    celluleDetailsEntreprise.addElement(getParagraphe("RCC : " + entreprise.getRccm()+ "\nID. NAT : " + entreprise.getIdnat()+ "\nNIF : " + entreprise.getNumeroImpot(), Font_TexteSimple_petit, Element.ALIGN_LEFT));
                }
            } else {
                celluleDetailsEntreprise.addElement(getParagraphe("UAP RDC Sarl, Courtier d'Assurances n°0189", Font_Titre2, Element.ALIGN_LEFT));
                celluleDetailsEntreprise.addElement(getParagraphe("Avenue de la Gombe, Kinshasa/Gombe", Font_TexteSimple_petit, Element.ALIGN_LEFT));
                celluleDetailsEntreprise.addElement(getParagraphe("https://www.aib-brokers.com | info@aib-brokers.com | (+243)84 480 35 14 - (+243)82 87 27 706", Font_TexteSimple_petit, Element.ALIGN_LEFT));
                celluleDetailsEntreprise.addElement(getParagraphe("RCC : CDF/KIN/2015-1245\nID. NAT : 0112487789\nNIF : 012245", Font_TexteSimple_petit, Element.ALIGN_LEFT));
            }
            tableauEnteteFacture.addCell(celluleDetailsEntreprise);

            //On insère le le tableau entete (logo et détails de l'entreprise) dans la page
            document.add(tableauEnteteFacture);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private PdfPCell getCelluleTableau(String texte, float BorderWidth, BaseColor background, BaseColor textColor, int alignement, Font font) {
        PdfPCell cellule = new PdfPCell();
        cellule.setBorderWidth(BorderWidth);
        if (background != null) {
            cellule.setBackgroundColor(background);
        } else {
            cellule.setBackgroundColor(BaseColor.WHITE);
        }
        if (textColor != null) {
            font.setColor(textColor);
        } else {
            font.setColor(BaseColor.BLACK);
        }
        cellule.setHorizontalAlignment(alignement);
        cellule.setPhrase(getPhrase(texte, font));
        return cellule;
    }

    private PdfPTable getTableau(float totalWidth, String[] titres, int[] widths, int alignement, float borderWidth) {
        try {
            PdfPTable tableau = new PdfPTable(widths.length);
            if (totalWidth != -1) {
                tableau.setTotalWidth(totalWidth);
            } else {
                tableau.setTotalWidth(PageSize.A4.getWidth() - 72);
            }
            tableau.setLockedWidth(true);
            tableau.setWidths(widths);
            tableau.setHorizontalAlignment(alignement);
            if (titres != null) {
                tableau.setSpacingBefore(3);
                for (String titre : titres) {
                    tableau.addCell(getCelluleTableau(titre, borderWidth, BaseColor.LIGHT_GRAY, null, Element.ALIGN_CENTER, Font_TexteSimple_Gras));
                }
            }

            return tableau;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private int getNBEleves(InterfaceClasse Iclasse) {
        int tot = 0;
        for (InterfaceEleve Ieleve : this.sortiesEleveAyantDroit.getListeEleves()) {
            if (Iclasse.getId() == Ieleve.getIdClasse()) {
                tot++;
            }
        }
        return tot;
    }

    private int getNBAyantDroits(InterfaceClasse Iclasse) {
        int tot = 0;
        for (InterfaceAyantDroit Iaya : this.sortiesEleveAyantDroit.getListeAyantDroit()) {
            for (InterfaceEleve Iele : this.sortiesEleveAyantDroit.getListeEleves()) {
                if (Iaya.getSignatureEleve() == Iele.getSignature() && Iele.getIdClasse() == Iclasse.getId()) {
                    tot++;
                }
            }

        }
        return tot;
    }

    private void setTableauEleves() {
        /* */
        //{"N°", "Nom", "Postnom", "Prénom", "Sexe", "Classe", "Date naiss.", "Status", "Téléphone"};
        try {
            if (this.sortiesEleveAyantDroit != null) {
                //Pour chaque Classe
                for (InterfaceClasse Iclasse : this.sortiesEleveAyantDroit.getListeClasses()) {
                    int taille = getNBEleves(Iclasse);
                    if (taille != 0) {
                        document.add(getParagraphe("LISTE D'ELEVES - CLASSE: " + Iclasse.getNom() + " (" + Iclasse.getNomLocal() + "), " + taille + " ELEVE(S).", Font_TexteSimple_Gras_Italique, Element.ALIGN_LEFT));
                        document.add(getParagraphe("SEXE: " + this.gestionnaireInscription.getCritereSexe() + " || CLASSE: " + this.gestionnaireInscription.getCritereClasse() + " || STATUS: " + this.gestionnaireInscription.getCritereStatus(), Font_TexteSimple_petit, Element.ALIGN_LEFT));
                        PdfPTable tableCharge = getTableau(
                                -1,
                                new String[]{"N°", "Nom", "Postnom", "Prénom", "Sexe", "Date naiss.", "Status", "Téléphone"},
                                new int[]{30, 100, 100, 100, 100, 100, 100, 100},
                                Element.ALIGN_CENTER,
                                0.2f
                        );
                        Vector<InterfaceEleve> listeEleves = this.sortiesEleveAyantDroit.getListeEleves();
                        int iEleve = 0;
                        for (InterfaceEleve Ieleve : listeEleves) {
                            if (Ieleve.getIdClasse() == Iclasse.getId()) {

                                //écriture dans chaque cellule de la ligne
                                tableCharge.addCell(getCelluleTableau("" + (iEleve + 1), 0.2f, BaseColor.WHITE, null, Element.ALIGN_RIGHT, Font_TexteSimple));
                                tableCharge.addCell(getCelluleTableau(Ieleve.getNom(), 0.2f, BaseColor.WHITE, null, Element.ALIGN_LEFT, Font_TexteSimple));
                                tableCharge.addCell(getCelluleTableau(Ieleve.getPostnom(), 0.2f, BaseColor.WHITE, null, Element.ALIGN_LEFT, Font_TexteSimple));
                                tableCharge.addCell(getCelluleTableau(Ieleve.getPrenom(), 0.2f, BaseColor.WHITE, null, Element.ALIGN_LEFT, Font_TexteSimple));
                                tableCharge.addCell(getCelluleTableau((Ieleve.getSexe() == InterfaceEleve.SEXE_MASCULIN ? "MASCULIN" : "FEMININ"), 0.2f, BaseColor.WHITE, null, Element.ALIGN_CENTER, Font_TexteSimple));
                                tableCharge.addCell(getCelluleTableau(UtilInscription.getDateFrancais(Ieleve.getDateNaissance()), 0.2f, BaseColor.WHITE, null, Element.ALIGN_CENTER, Font_TexteSimple));
                                tableCharge.addCell(getCelluleTableau(((Ieleve.getStatus() == InterfaceEleve.STATUS_ACTIF ? "REGULIER(E)" : "EXCLU(E)")), 0.2f, BaseColor.WHITE, null, Element.ALIGN_CENTER, Font_TexteSimple));
                                tableCharge.addCell(getCelluleTableau(Ieleve.getTelephonesParents(), 0.2f, BaseColor.WHITE, null, Element.ALIGN_LEFT, Font_TexteSimple));
                                //incrémentaion
                                iEleve++;
                            }
                        }
                        //La dernière ligne du table
                        document.add(tableCharge);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    private String getMonnaie(InterfaceFrais iff) {
        if (gestionnaireInscription.parametreInscription.getListeMonnaies() != null) {
            for (InterfaceMonnaie im : gestionnaireInscription.parametreInscription.getListeMonnaies()) {
                if (im.getSignature() == iff.getSignatureMonnaie()) {
                    return im.getCode();
                }
            }
        }
        return "";
    }

    private String[] getTabTitresColonnes() {
        Vector listTitre = new Vector();
        listTitre.add("N°");
        listTitre.add("Noms de l'élève");
        for (InterfaceFrais Ifrais : this.sortiesEleveAyantDroit.getListeFrais()) {
            String monnaie = getMonnaie(Ifrais);
            listTitre.add(Ifrais.getNom() + "(" + Ifrais.getMontantDefaut() + " " + monnaie + ")");
        }
        String[] tabTitres = new String[listTitre.size()];
        for (int i = 0; i < tabTitres.length; i++) {
            tabTitres[i] = listTitre.elementAt(i) + "";
        }
        return tabTitres;
    }

    private int[] getTabTailleColonnes() {
        Vector listTitre = new Vector();
        listTitre.add(30);
        listTitre.add(150);
        for (InterfaceFrais Ifrais : this.sortiesEleveAyantDroit.getListeFrais()) {
            listTitre.add(80);
        }
        int[] tabTitres = new int[listTitre.size()];
        for (int i = 0; i < tabTitres.length; i++) {
            tabTitres[i] = Integer.parseInt(listTitre.elementAt(i) + "");
        }
        return tabTitres;
    }

    private InterfaceEleve getEleve(long signatureEleve) {
        for (InterfaceEleve Iele : this.sortiesEleveAyantDroit.getListeEleves()) {
            if (Iele.getSignature() == signatureEleve) {
                return Iele;
            }
        }
        return null;
    }

    private void setTableauElevesAyant_Droit() {
        /* */
        //{"N°", "Nom", LISTE DES FRAIS [...]};
        try {
            if (this.sortiesEleveAyantDroit != null) {
                //Pour chaque Classe
                for (InterfaceClasse Iclasse : this.sortiesEleveAyantDroit.getListeClasses()) {
                    int taille = getNBAyantDroits(Iclasse);
                    if (taille != 0) {
                        document.add(getParagraphe("LISTE D'AYANT-DROITS - CLASSE: " + Iclasse.getNom() + " (" + Iclasse.getNomLocal() + "), " + taille + " ELEVE(S).", Font_TexteSimple_Gras_Italique, Element.ALIGN_LEFT));
                        document.add(getParagraphe("SEXE: " + this.gestionnaireInscription.getCritereSexe() + " || CLASSE: " + this.gestionnaireInscription.getCritereClasse() + " || STATUS: " + this.gestionnaireInscription.getCritereStatus(), Font_TexteSimple_petit, Element.ALIGN_LEFT));
                        PdfPTable tableAyantDroit = getTableau(
                                -1,
                                getTabTitresColonnes(),
                                getTabTailleColonnes(),
                                Element.ALIGN_CENTER,
                                0.2f
                        );
                        Vector<InterfaceAyantDroit> listeAyantDroit = this.sortiesEleveAyantDroit.getListeAyantDroit();
                        int iEleve = 0;
                        for (InterfaceAyantDroit Iaya : listeAyantDroit) {
                            InterfaceEleve Iele = getEleve(Iaya.getSignatureEleve());
                            if (Iele != null) {
                                if (Iele.getIdClasse() == Iclasse.getId()) {    //On regroupe les élèves selon les classes
                                    //écriture dans chaque cellule de la ligne
                                    tableAyantDroit.addCell(getCelluleTableau("" + (iEleve + 1), 0.2f, BaseColor.WHITE, null, Element.ALIGN_RIGHT, Font_TexteSimple));
                                    tableAyantDroit.addCell(getCelluleTableau(Iele.getNom() + " " + Iele.getPostnom() + " " + Iele.getPrenom() + " (" + Iele.getClasse() + ")", 0.2f, BaseColor.WHITE, null, Element.ALIGN_LEFT, Font_TexteSimple));
                                    for (LiaisonEleveFrais liaisonEF : Iaya.getListeLiaisons()) {
                                        tableAyantDroit.addCell(getCelluleTableau(liaisonEF.getMontant() + " " + liaisonEF.getMonnaie(), 0.2f, BaseColor.WHITE, null, Element.ALIGN_RIGHT, Font_TexteSimple));
                                    }
                                    //incrémentaion
                                    iEleve++;
                                }

                            }
                        }
                        //La dernière ligne du table
                        document.add(tableAyantDroit);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setLigneSeparateur() {
        try {
            Chunk linebreak = new Chunk(new DottedLineSeparator());
            document.add(linebreak);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setContenuDeLaPage() throws Exception {
        if (sortiesEleveAyantDroit != null) {
            sortiesEleveAyantDroit.getEcouteurEnregistrement().onUploading("Construction du contenu...");
        }
        setLogoEtDetailsEntreprise();
        setTitreEtDateDocument();
        //Corps
        if (this.gestionnaireInscription.getIndexTabSelected() == 0) {
            setTableauEleves();
        } else {
            setTableauElevesAyant_Droit();
        }
        //Fin du corps
        ajouterLigne(1);
        setSignataire();
        setBasDePage();
        if (sortiesEleveAyantDroit != null) {
            sortiesEleveAyantDroit.getEcouteurEnregistrement().onUploading("Finalisation...");
        }
    }

    public static void main(String[] a) {
        //Exemple
        DocumentPDFInscription docpdf = new DocumentPDFInscription(null, ACTION_OUVRIR, null);
    }

}
