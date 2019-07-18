/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.Callback_Insc;

import SOURCES.ModeleTable_Insc.ModeleListeAyantDroit;
import SOURCES.ModeleTable_Insc.ModeleListeEleve;

/**
 *
 * @author HP Pavilion
 */
public abstract class EcouteurAjoutInscription {
    public abstract void setAjoutEleve(ModeleListeEleve modeleListeEleve);
    public abstract void setAjoutAyantDroit(ModeleListeAyantDroit modeleListeAyantDroit);
}
