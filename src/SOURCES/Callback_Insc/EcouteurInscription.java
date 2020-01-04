/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.Callback_Insc;

import SOURCES.Utilitaires_Insc.SortiesInscription;

/**
 *
 * @author HP Pavilion
 */


public abstract class EcouteurInscription {
    public abstract void onEnregistre(SortiesInscription sortiesFacture);
    public abstract void onDetruitExercice(int idExercice);
    public abstract void onDetruitElements(int idElement, int index, long signature);
    public abstract void onClose(); 
}

