/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 *
 * @author FDC1
 */
public class ServiceGestionImpl implements ServiceGestion {

    private Joueurs listeJoueurs = new Joueurs();

    @Override
    public Joueur inscription(String pseudo, String password) throws RemoteException {
        Joueur joueur;
        boolean nouveau = true;
        int i = 0;

        while (i < listeJoueurs.liste.size() && nouveau) {
            if (pseudo.equals(listeJoueurs.liste.get(i).getPseudo())) {
                nouveau = false;
            }
            i++;
        }

        if (!nouveau) {
            joueur = new Joueur();
            joueur.setMessage("Le joueur existe déjà");
        } else {
            joueur = new Joueur(pseudo, password);
            listeJoueurs.liste.add(joueur);
        }
        return joueur;
    }

    @Override
    public Joueur authentification(String pseudo, String password) throws RemoteException {
        Joueur joueur = new Joueur();
        boolean trouve = false;
        int i = 0;

        while (i < listeJoueurs.liste.size() && !trouve) {
            if (pseudo.equals(listeJoueurs.liste.get(i).getPseudo())) {
                trouve = true;
                if (password.equals(listeJoueurs.liste.get(i).getPassword())) {
                    joueur = listeJoueurs.liste.get(i);
                } else {
                    joueur.setMessage("Mauvais mot de passe");
                }
            }
            i++;
        }

        if (!trouve) {
            joueur.setMessage("Ce joueur n'existe pas");
        }

        return joueur;
    }

    public static void main(String[] args) throws Exception {
        ServiceGestionImpl service = new ServiceGestionImpl();

        ServiceGestion stub;

        stub = (ServiceGestion) UnicastRemoteObject.exportObject(service, 0);

        Registry registry = LocateRegistry.createRegistry(2000);
        registry.bind("PlayerList", stub);

        System.out.println("Serveur de données en route");
    }

}
