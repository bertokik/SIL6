/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
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
    
    
    // Inscription // Connexion
    @PUT
    @Path("inscription")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Joueur inscription(JAXBElement<Joueur> j) {
        // ajouter condition 3 ème tier et ajout 
        Joueur joueur = j.getValue();
        joueur.setConnecte(true);
        listJoueurs.liste.add(joueur);
              
        return joueur;
    }
    
}
