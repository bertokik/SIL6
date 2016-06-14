/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author FDC1
 */
public interface ServiceGestion extends Remote {
    public Joueur inscription(String pseudo, String password) throws RemoteException;
    public Joueur authentification(String pseudo, String password) throws RemoteException;
}
