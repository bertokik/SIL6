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
        boolean inscript = false;
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
                    inscript = true;
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

                joueur = authentification(joueur);
                if (joueur.isConnecte()) {
                    System.out.println("Vous êtes bien connecté");
                    jeuConnexion = true;
                } else {
                    System.out.println(joueur.getMessage());
                }
            } else if (inscript) {
                System.out.print("Entrez votre pseudo : ");
                String pseudo = sc.nextLine();
                System.out.print("Entrez votre mot de passe : ");
                String mdp = sc.nextLine();

                joueur = new Joueur(pseudo, mdp);

                joueur = inscription(joueur);
                if (joueur.isConnecte()) {
                    System.out.println("Vous êtes bien inscrit");
                    jeuConnexion = true;
                } else {
                    System.out.println(joueur.getMessage());
                }
              
            }
//            Interface d'accueil
            while (joueur.isConnecte()) {
                System.out.println("");
                System.out.println("Bonjour " + joueur.getPseudo() + "      Score : " + joueur.getScore());
                System.out.println("-----------------------------------------");
                System.out.println("|       1- Voir les parties disponibles |");
                System.out.println("|       2- Voir les joueurs connectés   |");
                System.out.println("|       3- Créer une nouvelle partie    |");
                System.out.println("|       4- Se déconnecter               |");
                System.out.println("-----------------------------------------");
                System.out.print("Entrez une réponse : ");
                rep = sc.nextLine();

                switch (rep) {
                    case "1":
                        Parties parties = listerParties();
                        for (int i = 1; i < parties.liste.size() + 1; i++) {
                            System.out.println(i + "- " + parties.liste.get(i - 1).getNom());
                        }
                        System.out.print("Choisir le numéro de la partie à rejoindre (0 pour quitter) : ");
                        rep = sc.nextLine();
                        if (rep.equals("0")) {

                        }
                        break;
                    case "2":
                        Joueurs joueurs = listerJoueurs();
                        for (int i = 0; i < joueurs.liste.size(); i++) {
                            System.out.println(joueurs.liste.get(i).getPseudo());
                        }
                        break;
                    case "3":
                        boolean partieCree = false;
                        while (!partieCree) {
                            System.out.print("Entrez un nom de partie : ");
                            rep = sc.nextLine();
                            Partie partie = new Partie(rep);
                            partie.ajouterJoueur(joueur);
                            partie = creerPartie(partie);

                            if (partie.getNom().equals("")) {
                                System.out.println(partie.getMessage());
                            } else {
                                partieCree = true;

                            }
                        }

                        break;
                    case "4":
                        deconnection(joueur);
                        connect = false;
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

    private static Joueur authentification(Joueur joueur) throws Exception {
        String reponse;
        StringBuffer xmlStr;
        JAXBContext context;
        JAXBElement<Joueur> root;
        Unmarshaller unmarshaller;

        context = JAXBContext.newInstance(Joueur.class);
        unmarshaller = context.createUnmarshaller();

        reponse = serviceJeu.path("authentification").request().put(Entity.xml(joueur)).readEntity(String.class);

        xmlStr = new StringBuffer(reponse);
        root = unmarshaller.unmarshal(new StreamSource(new StringReader(xmlStr.toString())), Joueur.class);

        return root.getValue();
    }

    private static Parties listerParties() throws Exception {
        String reponse;
        StringBuffer xmlStr;
        JAXBContext context;
        JAXBElement<Parties> root;
        Unmarshaller unmarshaller;

        context = JAXBContext.newInstance(Parties.class);
        unmarshaller = context.createUnmarshaller();

        reponse = serviceJeu.path("listerParties").request().get(String.class);

        xmlStr = new StringBuffer(reponse);
        root = unmarshaller.unmarshal(new StreamSource(new StringReader(xmlStr.toString())), Parties.class);

        return root.getValue();
    }

    private static Joueurs listerJoueurs() throws Exception {
        String reponse;
        StringBuffer xmlStr;
        JAXBContext context;
        JAXBElement<Joueurs> root;
        Unmarshaller unmarshaller;

        context = JAXBContext.newInstance(Joueurs.class);
        unmarshaller = context.createUnmarshaller();

        reponse = serviceJeu.path("listerJoueurs").request().get(String.class);

        xmlStr = new StringBuffer(reponse);
        root = unmarshaller.unmarshal(new StreamSource(new StringReader(xmlStr.toString())), Joueurs.class);

        return root.getValue();
    }

    private static Partie creerPartie(Partie partie) throws Exception {
        String reponse;
        StringBuffer xmlStr;
        JAXBContext context;
        JAXBElement<Partie> root;
        Unmarshaller unmarshaller;

        context = JAXBContext.newInstance(Partie.class);
        unmarshaller = context.createUnmarshaller();

        reponse = serviceJeu.path("creerPartie").request().put(Entity.xml(partie)).readEntity(String.class);

        xmlStr = new StringBuffer(reponse);
        root = unmarshaller.unmarshal(new StreamSource(new StringReader(xmlStr.toString())), Partie.class);

        return root.getValue();
    }

    private static void deconnection(Joueur joueur) throws Exception {
        serviceJeu.path("deconnection").request().put(Entity.xml(joueur)).readEntity(String.class);
        joueur.setConnecte(false);
    }

}
