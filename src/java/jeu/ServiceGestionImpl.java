/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author FDC1
 */
public class ServiceGestionImpl implements ServiceGestion {

    private Joueurs listeJoueurs = new Joueurs();

    @Override
    public Joueur inscription(String pseudo, String password) throws RemoteException {
        Joueur joueur = new Joueur();
        boolean nouveau = true;
        ObjectOutputStream oos = null;
        int i = 0;

        while (i < listeJoueurs.liste.size() && nouveau) {
            if (pseudo.equals(listeJoueurs.liste.get(i).getPseudo())) {
                nouveau = false;
            }
            i++;
        }

        if (!nouveau) {
            joueur.setMessage("Le joueur existe déjà");
        } else {
            // Sauvegarder l'objet dans un fichier
            File fichier = new File("C:\\Users\\FDC1\\Documents\\NetBeansProjects\\ProjetSIL06\\src\\jeu\\joueurs.ser");

//            try {
//                // ouverture d'un flux sur un fichier
//                oos = new ObjectOutputStream(new FileOutputStream(fichier));
                joueur = new Joueur(pseudo, password);
                listeJoueurs.liste.add(joueur);
//                // sérialization de l'objet
//                oos.writeObject(joueur);
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            } finally {
//                try {
//                    if (oos != null) {
//                        oos.flush();
//                        oos.close();
//                    }
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }
//            }

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
        ObjectInputStream ois = null;

        stub = (ServiceGestion) UnicastRemoteObject.exportObject(service, 0);

        Registry registry = LocateRegistry.createRegistry(2000);
        registry.bind("PlayerList", stub);

//         Récupération des objets sérializés
//        try {
//            final FileInputStream fichier = new FileInputStream("C:\\Users\\FDC1\\Documents\\NetBeansProjects\\ProjetSIL06\\src\\jeu\\joueurs.ser");
//            ois = new ObjectInputStream(fichier);
//            final Joueur joueur = (Joueur) ois.readObject();
//            service.listeJoueurs.liste.add(joueur);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        } finally {
//            try {
//                if(ois != null) {
//                    ois.close();
//                }
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
        

        System.out.println("Serveur de données en route");
    }

}
