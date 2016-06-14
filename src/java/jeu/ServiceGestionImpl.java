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

    private ArrayList<Joueur> listeJoueurs = new ArrayList<>();
    
    @Override
    public Joueur donnerJoueur(String pseudo) throws RemoteException {
        Joueur joueur = new Joueur();
        for(int i = 0 ; i < listeJoueurs.size();i++) {
            if(pseudo.equals(listeJoueurs.get(i).getPseudo())) {
                joueur = listeJoueurs.get(i);
            } 
        }
        return joueur;
    }
    
    public static void main(String [] args) throws Exception
    {
        ServiceGestionImpl service = new ServiceGestionImpl();
        
        ServiceGestion stub;
        
        stub = (ServiceGestion) UnicastRemoteObject.exportObject(service,0);
     
        Registry registry = LocateRegistry.createRegistry(2000);
        registry.bind("PlayerList", stub);
        
        
        System.out.println("Serveur de données en route");
    }
    
}
