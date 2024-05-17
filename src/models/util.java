package models;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mindrot.jbcrypt.BCrypt;

public class util {

    //Methode pour obtenir la date sous format AAAA-MM-JJ
    public static String Date(){
        Scanner entree = new Scanner(System.in);
        //Jour de naissance
        boolean isIntJour;
        int jourNaissance = 0;
        do {
            System.out.println("Veuillez donner le jour :");
            if (entree.hasNextInt()) {
                jourNaissance = entree.nextInt();
                isIntJour = true;
                if (jourNaissance > 31) {
                    System.out.println("Veuillez donner un nombre inférieur ou égal à 31");
                    isIntJour = false;
                }
            } else {
                System.out.println("Veuillez donner un entier");
                entree.next(); // Consomme l'entrée invalide pour éviter une boucle infinie
                isIntJour = false;
            }
        } while (!isIntJour);
        //Mois de naissance
        boolean isIntMois;
        int moisNaissance = 0;
        do {
            System.out.println("Veuillez donner le mois :");
            if (entree.hasNextInt()) {
                moisNaissance = entree.nextInt();
                isIntMois = true;
                if (moisNaissance > 12) {
                    System.out.println("Veuillez donner un nombre inférieur ou égal à 12");
                    isIntMois = false;
                }
            } else {
                System.out.println("Veuillez donner un entier");
                entree.next(); // Consomme l'entrée invalide pour éviter une boucle infinie
                isIntMois = false;
            }
        } while (!isIntMois);

        //Annee de naissance
        boolean isIntAnnee;
        int anneeNaissance = 0;
        do {
            System.out.println("Veuillez donner l'année :");
            if (entree.hasNextInt()) {
                anneeNaissance = entree.nextInt();
                isIntAnnee = true;
                if (anneeNaissance > 3000) {
                    System.out.println("Veuillez donner un nombre inférieur ou égal à 3000");
                    isIntAnnee = false;
                }
            } else {
                System.out.println("Veuillez donner un entier");
                entree.next(); // Consomme l'entrée invalide pour éviter une boucle infinie
                isIntAnnee = false;
            }
        } while (!isIntAnnee);
        //Fusion Date
        return anneeNaissance+"-"+moisNaissance+"-"+jourNaissance;
    }

    // Methode pour hasher le mote de passe
    public static String hasherMotDePasse(){
        Scanner entree = new Scanner(System.in);
        String motDePasse = entree.nextLine();
        return BCrypt.hashpw(motDePasse, BCrypt.gensalt());
    }

    //Recupération de l'ID de la dernière personne insérée
    public static int recupererValeurUnique(Connection connection) throws SQLException {
        // Préparer la requête SQL
        String sql = "SELECT idPersonne FROM Personne ORDER BY idPersonne DESC LIMIT 1";
        int idPersonne = 0;
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            // Vérifier si le ResultSet contient des données
            if (resultSet.next()) {
                // Récupérer la valeur unique
                idPersonne = resultSet.getInt("idPersonne");

                // Utiliser la valeur récupérée
                // ... votre code ici pour utiliser la valeur 'nomUtilisateur'
            } else {
                System.out.println("Aucune donnée trouvée.");
            }
        }
        return idPersonne;
    }


    // Méthode pour vérifier si l'email est valide
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (email == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
