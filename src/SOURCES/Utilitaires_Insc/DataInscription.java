/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.Utilitaires_Insc;

/**
 *
 * @author user
 */
public class DataInscription {
    public ParametreInscription parametreInscription;

    public DataInscription(ParametreInscription parametreInscription) {
        this.parametreInscription = parametreInscription;
    }

    public ParametreInscription getParametreInscription() {
        return parametreInscription;
    }

    public void setParametreInscription(ParametreInscription parametreInscription) {
        this.parametreInscription = parametreInscription;
    }

    @Override
    public String toString() {
        return "DataInscription{" + "parametreInscription=" + parametreInscription + '}';
    }
}







