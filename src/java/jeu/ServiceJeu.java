/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;

/**
 * REST Web Service
 *
 * @author FDC1
 */
@Path("/")
public class ServiceJeu {

    private static final Joueurs listJoueurs = new Joueurs();
    private static final Joueurs listJoueursPartie = new Joueurs();
    private static final Parties listParties = new Parties();
    private static final Parties listPartiesLibre = new Parties();

    // Inscription 
    @PUT
    @Path("inscription")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Joueur inscription(JAXBElement<Joueur> j) throws Exception {
        // ajouter condition 3 ème tier et ajout
        Registry registry = LocateRegistry.getRegistry("127.0.0.1", 2000);
        ServiceGestion service;
        service = (ServiceGestion) registry.lookup("PlayerList");

        Joueur joueur = service.inscription(j.getValue().getPseudo(), j.getValue().getPassword());

        if (!joueur.getMessage().equals("")) {
//            System.out.println(joueur.getMessage());
            joueur.setConnecte(false);
        } else {
            joueur.setConnecte(true);
            listJoueurs.liste.add(joueur);
        }
//        Joueur joueur = j.getValue();  
        return joueur;
    }
    
// Connexion
    @PUT
    @Path("authentification")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Joueur authentification(JAXBElement<Joueur> j) throws Exception {
        // ajouter condition 3 ème tier et ajout
        Registry registry = LocateRegistry.getRegistry("127.0.0.1", 2000);
        ServiceGestion service;
        service = (ServiceGestion) registry.lookup("PlayerList");

        Joueur joueur = service.authentification(j.getValue().getPseudo(), j.getValue().getPassword());

        if (!joueur.getMessage().equals("")) {
//            System.out.println(joueur.getMessage());
            joueur.setConnecte(false);
        } else {
            joueur.setConnecte(true);
            listJoueurs.liste.add(joueur);

        }
//        Joueur joueur = j.getValue();  
        return joueur;
    }

    // ListerParties
    @GET
    @Path("listerParties")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Parties listerParties() {
        return listPartiesLibre;
    }

    // ListerJoueurs
    @GET
    @Path("listerJoueurs")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Joueurs listerJoueurs() {
        return listJoueurs;
    }
    
    // Retourne l'etat de la partie
    @PUT
    @Path("getPartie")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Partie getPartie(JAXBElement<Partie> p) {
        Partie partie = p.getValue();
        for(int i = 0; i < listParties.liste.size();i++) {
            if (partie.getNom().equals(listParties.liste.get(i).getNom())) {
               partie =  listParties.liste.get(i);
            }
        }
        
        return partie;
    }
    
  
    // Se connecter à une partie
    @PUT
    @Path("connexionPartie")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Partie connexionPartie(JAXBElement<Partie> p) {
        Partie partie = p.getValue();
        // Récupérer la partie dans la liste de parties
        for (int i=0; i < listParties.liste.size();i++) {
            if (partie.getNom().equals(listParties.liste.get(i).getNom())) {
                listParties.liste.set(i, partie);
                // Si la partie est pleine, la retirer de la liste de parties libres
                if (!partie.isAttente() && listPartiesLibre.liste.contains(partie)) {
                    listPartiesLibre.liste.remove(partie);
                }
                
            }
        }
        
        return partie;
    }
    
    // Quitter une partie
    @PUT
    @Path("quitterPartie")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Partie quitterPartie(JAXBElement<Partie> p) {
//        Joueur joueur = j.getValue();
//        Partie partie = joueur.getPartieEnCours();
//        partie.getListeJoueurs().liste.remove(joueur);
        Partie partie = p.getValue();
        boolean exist = false;
        // Récupérer la partie dans la liste de parties
        for (int i=0; i < listParties.liste.size();i++) {
            if (partie.getNom().equals(listParties.liste.get(i).getNom())) {           
                listParties.liste.set(i, partie);
                // Si la partie n'est plus pleine, l'ajouter dans la liste de parties libres
                if (partie.isAttente() && !listPartiesLibre.liste.contains(partie.getNom())) {
                    for (Partie unePartie : listPartiesLibre.liste) {
                        if (unePartie.getNom().equals(partie.getNom())) {
                            exist = true;
                        }
                    }
                    if (!exist) {
                        listPartiesLibre.liste.add(partie);
                    } 
                }
                // Si il n'y a plus de joueurs supprimmer la partie
                if (partie.getListeJoueurs().liste.isEmpty()) {
                    listParties.liste.remove(partie);
                    listPartiesLibre.liste.remove(partie);
                }
            }
        }
        
        return partie;
    }

    // creerPartie
    @PUT
    @Path("creerPartie")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Partie creerPartie(JAXBElement<Partie> p) {
        Partie partie = p.getValue();
        boolean exist = false;
        for (Partie unePartie : listParties.liste) {
            if (unePartie.getNom().equals(partie.getNom())) {
                exist = true;
                partie.setMessage("Une partie de même nom existe déjà");
                break;
            }
        }

        if (!exist) {
            listPartiesLibre.liste.add(partie);
            listParties.liste.add(partie);
            
        } 

        return partie;
    }

    @PUT
    @Path("deconnection")
    @Consumes(MediaType.APPLICATION_XML)
    public void deconnection(JAXBElement<Joueur> j) {
        Joueur joueur = j.getValue();

        for (Joueur aJoueur : listJoueurs.liste) {
            if (aJoueur.getPseudo().equals(joueur.getPseudo())) {
                listJoueurs.liste.remove(aJoueur);
                break;
            }
        }
    }
    
    // Affichage serveur
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String affichageServeur() {
        return "<server>Vous êtes bien connecté au serveur</server>";
    }
}
