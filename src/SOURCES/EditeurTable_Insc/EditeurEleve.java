/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.EditeurTable_Insc;


import SOURCES.Interface.InterfaceAyantDroit;
import SOURCES.Interface.InterfaceEleve;
import SOURCES.ModeleTable_Insc.ModeleListeAyantDroit;
import SOURCES.ModeleTable_Insc.ModeleListeEleve;
import java.awt.Component;
import java.util.Vector;
import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author user
 */
public class EditeurEleve extends AbstractCellEditor implements TableCellEditor {

    private JComboBox<String> champEditionCombo = new JComboBox();
    private ModeleListeEleve modeleListeEleve;
    private ModeleListeAyantDroit modeleListeAyantDroit;

    public EditeurEleve(ModeleListeEleve modeleListeEleve, ModeleListeAyantDroit modeleListeAyantDroit) {
        this.modeleListeEleve = modeleListeEleve;
        this.modeleListeAyantDroit = modeleListeAyantDroit;
        initCombo();
    }
    
    public int getTailleCombo(){
        return this.champEditionCombo.getItemCount();
    }
    

    public void initCombo() {
        this.champEditionCombo.removeAllItems();
        if (this.modeleListeEleve != null) {
            Vector<InterfaceEleve> listeEleves = this.modeleListeEleve.getListeData();
            if(listeEleves != null){
                for(InterfaceEleve eleve : listeEleves){
                    this.champEditionCombo.addItem(eleve.getNom() + " " + eleve.getPostnom()+ ", " + eleve.getPrenom()+" ("+eleve.getClasse()+")");
                }
            }
        }
    }
    
    private long getSignatureEleve(String eleve){
        for(InterfaceEleve interfaceEleve : this.modeleListeEleve.getListeData()){
            String Seleve = interfaceEleve.getNom()+ " " + interfaceEleve.getPostnom()+", " + interfaceEleve.getPrenom()+" ("+interfaceEleve.getClasse()+")";
            if(Seleve.trim().equals(eleve.trim())){
                return interfaceEleve.getSignature();
            }
        }
        return -1;
    }
    
    private String getEleve(long signatureEleve){
        //String eleve = "Inconnue";
        for(InterfaceEleve IeEleve : this.modeleListeEleve.getListeData()){
            if(IeEleve.getSignature() == signatureEleve){
                return IeEleve.getNom() + " " + IeEleve.getPostnom() +", " + IeEleve.getPrenom()+" ("+IeEleve.getClasse()+")";
            }
        }
        return "Inconnu";
    }

    @Override
    public Object getCellEditorValue() {
        //Après édition de l'utilisateur
        return getSignatureEleve(champEditionCombo.getSelectedItem() + "");
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        //Pendant édition de l'utilisateur
        initCombo();
        String DefautSelection = getEleve(Long.parseLong(value+""));
        champEditionCombo.setSelectedItem(DefautSelection);
        //System.out.println("DefautSelection = " + DefautSelection);
        return champEditionCombo;
    }

}
