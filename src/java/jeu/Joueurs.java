/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Flo
 */
@XmlRootElement(name = "Joueurs")
public class Joueurs {
   @XmlElement
    public ArrayList<Joueur> liste = new ArrayList<Joueur>(); 
}
