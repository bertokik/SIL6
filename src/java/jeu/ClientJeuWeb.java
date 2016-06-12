package jeu;

import java.io.StringReader;
import java.util.Scanner;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author Kik
 */
public class ClientJeuWeb {
    
    private static WebTarget serviceJeu = null;
    
    public static void main(String args[]) throws Exception {

        /*
       ** Initialisation du stub pour interagir avec le service web REST
         */
        serviceJeu = ClientBuilder.newClient().target("http://localhost:8080/Projet_SIL06");
        
        boolean jeuConnexion = true;
        boolean connect = false;
        Joueur joueur = null;

//        Interface de connexion
        while (jeuConnexion) {
            
            System.out.println("----------------------------------");
            System.out.println("|           Bonjour              |");
            System.out.println("----------------------------------");
            System.out.println("|       1- Se connecter          |");
            System.out.println("|       2- S'inscrire            |");
            System.out.println("|       3- Quitter               |");
            System.out.println("----------------------------------");
            System.out.print("Entrez une réponse : ");
            Scanner sc = new Scanner(System.in);
            String rep = sc.nextLine();
            
            switch (rep) {
                case "1":
                    connect = true;
                    break;
                case "2":
                    connect = true;
                    break;
                case "3":
                    jeuConnexion = false;
                    break;
                default:
                    System.out.println("Ceci n'est pas une réponse valable");
            }
            
            if (connect) {
                System.out.print("Entrez votre pseudo : ");
                String pseudo = sc.nextLine();
                System.out.print("Entrez votre mot de passe : ");
                String mdp = sc.nextLine();
                
                joueur = new Joueur(pseudo, mdp);
                
                joueur = inscription(joueur);
                if (joueur.isConnecte()) {
                    System.out.println("Vous êtes bien connecté");
                }
                connect = joueur.isConnecte();
                jeuConnexion = true;
            }
//            Interface d'accueil
            while (connect) {
                System.out.println("");
                System.out.println("Bonjour " + joueur.getPseudo() + "      Score : " + joueur.getScore());
                System.out.println("-----------------------------------------");
                System.out.println("|       1- Voir les parties disponibles |");
                System.out.println("|       2- Voir les joueurs connectés   |");
                System.out.println("|       3- Se déconnecter               |");
                System.out.println("-----------------------------------------");
                System.out.print("Entrez une réponse : ");
                rep = sc.nextLine();
                
                switch (rep) {
                    case "1":
                        
                        break;
                    case "2":
                        
                        break;
                    case "3":
                        connect = false;
                        joueur.setConnecte(connect);
                        break;
                    default:
                        System.out.println("Ceci n'est pas une réponse valable");
                }                
            }
        }
        
    }
    
    private static Joueur inscription(Joueur joueur) throws Exception {
        String reponse;
        StringBuffer xmlStr;
        JAXBContext context;
        JAXBElement<Joueur> root;
        Unmarshaller unmarshaller;
        
        context = JAXBContext.newInstance(Joueur.class);
        unmarshaller = context.createUnmarshaller();
        
        reponse = serviceJeu.path("inscription").request().put(Entity.xml(joueur)).readEntity(String.class);
        
        xmlStr = new StringBuffer(reponse);
        root = unmarshaller.unmarshal(new StreamSource(new StringReader(xmlStr.toString())), Joueur.class);
        
        return root.getValue();
    }
    
}
