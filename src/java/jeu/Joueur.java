/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author FDC1
 */
@XmlRootElement(name = "Joueur")
public class Joueur {

    private String pseudo;
    private String password;
    private int score;
    private boolean connecte;
    private String message;

    public Joueur() {
    }

    public Joueur(String pseudo, String password) {
        this.pseudo = pseudo;
        this.password = password;
        this.connecte = false;
        this.score = 0;
        this.message = "";
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
    
    
}
