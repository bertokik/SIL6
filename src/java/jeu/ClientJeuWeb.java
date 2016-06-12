package jeu;

import java.io.StringReader;
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

    private static WebTarget serviceMeteo = null;

    public static void main(String args[]) throws Exception {
        Joueur joueur = new Joueur("Paul", "pass");

        /*
       ** Initialisation du stub pour interagir avec le service web REST
         */
        serviceMeteo = ClientBuilder.newClient().target("http://localhost:8080/Projet_SIL06");

        System.out.println("Connecté : " + joueur.isConnecte());

        joueur = inscription(joueur);
        System.out.println("Connecté : " + joueur.isConnecte());
    }

    private static Joueur inscription(Joueur joueur) throws Exception {
        String reponse;
        StringBuffer xmlStr;
        JAXBContext context;
        JAXBElement<Joueur> root;
        Unmarshaller unmarshaller;

        context = JAXBContext.newInstance(Joueur.class);
        unmarshaller = context.createUnmarshaller();

        reponse = serviceMeteo.path("inscription").request().put(Entity.xml(joueur)).readEntity(String.class);

        xmlStr = new StringBuffer(reponse);
        root = unmarshaller.unmarshal(new StreamSource(new StringReader(xmlStr.toString())), Joueur.class);

        return root.getValue();
    }

}
