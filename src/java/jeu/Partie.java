/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author FDC1
 */
@XmlRootElement(name = "Partie")
public class Partie {
    private String nom;
    private ArrayList<Joueur> listeJoueurs;
    private boolean attente;

    public Partie(String unNom) {
        this.nom = unNom;
        listeJoueurs = new ArrayList<>();
        this.attente = true;
    }

    public ArrayList<Joueur> getListeJoueurs() {
        return listeJoueurs;
    }

    public void ajouterJoueur(Joueur j) {
        if(this.listeJoueurs.size() <= 6) {
            this.listeJoueurs.add(j);
            if(this.listeJoueurs.size() >= 4) {
                setAttente(false);
            }
        }
    }
    
    public void retirerJoueur(Joueur j) {
        this.listeJoueurs.remove(j);
        if(this.listeJoueurs.size() < 4) {
                setAttente(true);
        }
    }
    
    public boolean isAttente() {
        return attente;
    }

    public void setAttente(boolean attente) {
        this.attente = attente;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
    
    
    
    
}
