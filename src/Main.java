package main;

import models.Pays;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
<<<<<<< HEAD
        Scanner scanner = new Scanner(System.in);
        Connexion.seConecter();
        //Pays.ajouterPays(Connexion.con, scanner);
        Ville.ajouterUneVille();
        System.out.println("Hello Amadou!");
=======
        // Définir les informations de connexion à la base de données
        String url = "jdbc:mysql://localhost:3306/gestiondevolgroupe1";
        String user = "root";
        String password = "";

        // Initialiser la connexion et le scanner
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Scanner scanner = new Scanner(System.in)) {

            // Appeler la fonction afficherTousLesIds
            Pays.afficherTousLesIds(connection);

            // Vous pouvez également appeler d'autres méthodes ici si nécessaire
            // Exemples:
            // Pays.ajouterPays(connection, scanner);
            // Pays.modifierNomPays(connection, scanner);
            // Pays.supprimerPays(connection, scanner);
            // Pays.listeDePays(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
>>>>>>> Branche_sermin
    }
}