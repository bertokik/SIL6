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
    
    private Joueurs listJoueurs = new Joueurs();
    private Parties listParties = new Parties();
    private Parties listPartiesLibre = new Parties();
    
    
    // Inscription // Connexion
    @PUT
    @Path("inscription")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Joueur inscription(JAXBElement<Joueur> j) throws Exception  {
        // ajouter condition 3 ème tier et ajout
        Registry registry = LocateRegistry.getRegistry("127.0.0.1",2000);
        ServiceGestion service;
        service = (ServiceGestion)registry.lookup("PlayerList");
        
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
    
    @PUT
    @Path("authentification")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Joueur authentification(JAXBElement<Joueur> j) throws Exception  {
        // ajouter condition 3 ème tier et ajout
        Registry registry = LocateRegistry.getRegistry("127.0.0.1",2000);
        ServiceGestion service;
        service = (ServiceGestion)registry.lookup("PlayerList");
        
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
                break;
            }
        }
        
        if (!exist) {
            listPartiesLibre.liste.add(partie);
        } else {
            
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
}
