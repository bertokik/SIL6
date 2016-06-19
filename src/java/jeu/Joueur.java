/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author FDC1
 */
@XmlRootElement(name = "Joueur")
public class Joueur implements Serializable{

    
    private String pseudo;
    private String password;
    private int score;
    private boolean connecte;
    private String message;
//    private Partie partieEnCours;

//    public Partie getPartieEnCours() {
//        return partieEnCours;
//    }
//
//    public void setPartieEnCours(Partie partieEnCours) {
//        this.partieEnCours = partieEnCours;
//    }
    
    @XmlElement
    private Cartes mainJoueur;

    public Joueur() {
        mainJoueur = new Cartes();
    }

    
    public Joueur(String pseudo, String password) {
        this.pseudo = pseudo;
        this.password = password;
        this.connecte = false;
        this.score = 0;
        this.message = "";
        mainJoueur = new Cartes();
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isConnecte() {
        return connecte;
    }

    public void setConnecte(boolean connecte) {
        this.connecte = connecte;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public Cartes getMainJoueur() {
        return mainJoueur;
    }
    
    public void addCarte(Carte carte) {
        mainJoueur.liste.add(carte);
    }
    
    public void removeCarte(Carte carte) {
        mainJoueur.liste.remove(carte);
    }
    
    public void clearMain() {
        mainJoueur.liste.clear();
    }
    
}
