/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.EditeurTable_Insc;



import Source.Objet.Classe;
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
public class EditeurClasse extends AbstractCellEditor implements TableCellEditor {

    private JComboBox<String> champEditionCombo = new JComboBox();
    private Vector<Classe> listeClasses;

    public EditeurClasse(Vector<Classe> listeClasses) {
        this.listeClasses = listeClasses;
        initCombo();
    }

    public void initCombo() {
        this.champEditionCombo.removeAllItems();
        if (this.listeClasses != null) {
            for (Classe classe : listeClasses) {
                this.champEditionCombo.addItem(classe.getNom());
            }
        }
    }

    private String getClasse(int idClasse) {
        String classe = "Inconnue";
        for (Classe Iclasse : this.listeClasses) {
            if (Iclasse.getId() == idClasse) {
                classe = Iclasse.getNom();
                break;
            }
        }
        return classe;
    }

    private int getIdClasse(String nomClasse) {
        int id = -1;
        for (Classe Iclasse : this.listeClasses) {
            if (Iclasse.getNom().trim().equals(nomClasse.trim())) {
                id = Iclasse.getId();
            }
        }
        return id;
    }

    @Override
    public Object getCellEditorValue() {
        //Après édition de l'utilisateur
        return getIdClasse(champEditionCombo.getSelectedItem() + "");
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        //Pendant édition de l'utilisateur
        initCombo();
        String DefautSelection = getClasse(Integer.parseInt(value + ""));
        champEditionCombo.setSelectedItem(DefautSelection);
        //System.out.println("DefautSelection = " + DefautSelection);
        return champEditionCombo;
    }

}
