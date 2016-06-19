/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu;

import java.io.Serializable;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author FDC1
 */
@XmlRootElement(name = "Cartes")
public class Cartes implements Serializable {
   @XmlElement
    public ArrayList<Carte> liste = new ArrayList<Carte>(); 
}
