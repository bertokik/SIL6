/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu;

import java.util.HashMap;
import java.util.Random;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author FDC1
 */
@XmlRootElement(name = "Partie")
public class Partie implements Runnable{
    private String nom;
    
    @XmlElement
    private Joueurs listeJoueurs = new Joueurs();
    
    private boolean attente;
    private String message;
    
    @XmlElement
    private static Cartes tasCarte;
    
    
//    Créer le jeu de 32 cartes
    static {
        tasCarte = new Cartes();
        
        tasCarte.liste.add(new Carte("7 Trèfle",1));
        tasCarte.liste.add(new Carte("7 Carreau",1));
        tasCarte.liste.add(new Carte("7 Coeur",1));
        tasCarte.liste.add(new Carte("7 Pique",1));
        
        tasCarte.liste.add(new Carte("8 Trèfle",2));
        tasCarte.liste.add(new Carte("8 Carreau",2));
        tasCarte.liste.add(new Carte("8 Coeur",2));
        tasCarte.liste.add(new Carte("8 Pique",2));
        
        tasCarte.liste.add(new Carte("9 Trèfle",3));
        tasCarte.liste.add(new Carte("9 Carreau",3));
        tasCarte.liste.add(new Carte("9 Coeur",3));
        tasCarte.liste.add(new Carte("9 Pique",3));
        
        tasCarte.liste.add(new Carte("10 Trèfle",4));
        tasCarte.liste.add(new Carte("10 Carreau",4));
        tasCarte.liste.add(new Carte("10 Coeur",4));
        tasCarte.liste.add(new Carte("10 Pique",4));
        
        tasCarte.liste.add(new Carte("Valet Trèfle",5));
        tasCarte.liste.add(new Carte("Valet Carreau",5));
        tasCarte.liste.add(new Carte("Valet Coeur",5));
        tasCarte.liste.add(new Carte("Valet Pique",5));
        
        tasCarte.liste.add(new Carte("Dame Trèfle",6));
        tasCarte.liste.add(new Carte("Dame Carreau",6));
        tasCarte.liste.add(new Carte("Dame Coeur",6));
        tasCarte.liste.add(new Carte("Dame Pique",6));
        
        tasCarte.liste.add(new Carte("Roi Trèfle",7));
        tasCarte.liste.add(new Carte("Roi Carreau",7));
        tasCarte.liste.add(new Carte("Roi Coeur",7));
        tasCarte.liste.add(new Carte("Roi Pique",7));
        
        tasCarte.liste.add(new Carte("As Trèfle",8));
        tasCarte.liste.add(new Carte("As Carreau",8));
        tasCarte.liste.add(new Carte("As Coeur",8));
        tasCarte.liste.add(new Carte("As Pique",8));
    }
    
    public Partie() {
        this.attente = true;
    }

    public Partie(String unNom) {
        this.nom = unNom;
        this.attente = true;
        this.message = "";
    }

    public Joueurs getListeJoueurs() {
        return listeJoueurs;
    }

    public void ajouterJoueur(Joueur j) {
        if(this.listeJoueurs.liste.size() <= 6) {
            this.listeJoueurs.liste.add(j);
            if(this.listeJoueurs.liste.size() >= 4) {
                setAttente(false);
            }
        }
    }
    
    public void retirerJoueur(Joueur j) {
        
        listeJoueurs.liste.remove(j);
        
        
        if(this.listeJoueurs.liste.size() < 4) {
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public void distribuer(Cartes cartes) {
        Random random = new Random();
        int hasard = 0;
        // vider les mains des joueurs avant de distribuer
        for(Joueur unJoueur : listeJoueurs.liste){
             unJoueur.clearMain();
        }
        // Commencer la distribution
        while (!cartes.liste.isEmpty()) {            
            for(Joueur unJoueur : listeJoueurs.liste){
                hasard = random.nextInt(33);
                // Ajouter la carte à la main du joueur
                unJoueur.addCarte(cartes.liste.get(hasard));
                // Retirer la carte de la pioche
                cartes.liste.remove(hasard);
            }
        }
    }
    
    // Méthode de déclenchement du jeu 
    public void run() {
        // Etape 1 : Distribuer les cartes au joueurs
        Cartes listeCartePartie = tasCarte;
        
        // Liste des joueurs qui n'ont pas perdus
        Joueurs listeJoueursEnCours = listeJoueurs;
        // Liste des gagnants 
        Joueurs listeGagnants = new Joueurs();
        
        // Plateau
        Cartes plateau = new Cartes();
        
        System.out.println("--- Tirage des cartes ---");
        distribuer(listeCartePartie);
        
        // Etape 2 : Débuter la partie
        System.out.println("--- Début de la partie ---");
        Random random = new Random();
        int hasard = 0;
        while(listeJoueursEnCours.liste.size() > 1) {
           // Vider la liste des gagnants et le plateau à chaque tour
           listeGagnants.liste.clear();
           plateau.liste.clear();
           
           Carte meilleur = new Carte();
           for(Joueur unJoueur : listeJoueursEnCours.liste){
               hasard = random.nextInt(unJoueur.getMainJoueur().liste.size()+1);

               // Affichage
               System.out.println(unJoueur.getPseudo() + " joue la carte : " + unJoueur.getMainJoueur().liste.get(hasard).getNom());
               plateau.liste.add(unJoueur.getMainJoueur().liste.get(hasard));
               // Retirer la carte jouée de la main du joueur
               unJoueur.removeCarte(unJoueur.getMainJoueur().liste.get(hasard));
               
               // Vérifier si la carte jouée est supérieur à la meilleure
               if (meilleur.getValue() < unJoueur.getMainJoueur().liste.get(hasard).getValue()) {
                   meilleur = unJoueur.getMainJoueur().liste.get(hasard);
                   // Vider les anciens gagnants dans la liste
                   listeGagnants.liste.clear();
                   // Ajouter le nouveau gagnant
                   listeGagnants.liste.add(unJoueur);
               // vérifier l'égalité
               } else if (meilleur.getValue() == unJoueur.getMainJoueur().liste.get(hasard).getValue()) {
                   // Rajouter un gagnant supplémentaire
                   listeGagnants.liste.add(unJoueur);
               }
           }
           
           // Répartir les cartes au gagnants
           for(Carte uneCarte : plateau.liste){
               hasard = random.nextInt(listeGagnants.liste.size()+1);
               listeGagnants.liste.get(hasard).addCarte(uneCarte); 
           }
           
           // Afficher les gagnants
           for(Joueur unJoueur : listeGagnants.liste) {
               System.out.println(unJoueur.getPseudo() + " remporte le tour");
           }
           
           // Vérifier si des joueurs n'ont plus de cartes, si c'est le cas les éliminer
           for(Joueur unJoueur : listeJoueursEnCours.liste) {
               if (unJoueur.getMainJoueur().liste.isEmpty()) {
                   System.out.println(unJoueur.getPseudo() + " n'a plus de cartes, il est eliminé !");
                   // Score du joueur + (nb de joueurs dans la partie - nb de joueurs actifs)
                   unJoueur.setScore(unJoueur.getScore() + (listeJoueurs.liste.size() - listeJoueursEnCours.liste.size()));
                   listeJoueursEnCours.liste.remove(unJoueur);
               }
           }
        }
        
        // Récupérer le nom du gagnant
        for (Joueur unJoueur : listeJoueursEnCours.liste) {
            System.out.println(unJoueur.getPseudo() + " gagne la partie, Félicitations !");
        }
        
        
        
        
        
    }
    
    
    
    
    
}
