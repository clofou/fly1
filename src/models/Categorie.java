package models;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Categorie {


    public static void listeDeCategorie(Connection connection) throws SQLException{
        System.out.print("\n");
        // Create a statement object
        try (Statement statement = connection.createStatement();
             // Create the SQL query to retrieve the entire column list
             ResultSet resultSet = statement.executeQuery("SELECT idCategorie, nom FROM Categorie")) {

            // Iterate through the result set and print each value
            while (resultSet.next()) {
                System.out.print("  ");
                String idCategorie = resultSet.getString("idCategorie");
                String nom = resultSet.getString("nom");

                System.out.print(idCategorie + "- ");
                System.out.println(nom);
            }
        }
        System.out.println("\n");
    }

    public static void ajouteCategorie(Connection connection, Scanner scanner){
        System.out.println("Les informations sur les categories : ");

        System.out.println("Nom categorie : ");
        String nom = scanner.nextLine();

        

       
        // int vol_id  = 0;
        // boolean isInt = false;
        // do {
        //     System.out.println("Vol id : ");
        //     if (scanner.hasNextInt()) {
        //         vol_id = scanner.nextInt();
        //         isInt = true;
        //     } else {
        //         System.out.println("Veuillez saisir un entier.");
        //         scanner.next(); // Pour vider le scanner et éviter une boucle infinie
        //     }
        // } while (!isInt);

        String sql = "INSERT INTO categorie (nom) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,nom);
            // statement.setInt(2,vol_id);

            int lignesModifiees = statement.executeUpdate();
            if (lignesModifiees > 0) {
                System.out.println("Les données du categorie ont été ajoutées avec succès !");
            } else {
                System.out.println("Erreur lors de l'ajout des données.");
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }

    

    public static void modifierCategorie(Connection connection, Scanner scanner) {
        System.out.println("Modification categorie : ");
        
        int idCategorie = 0;
        boolean isInt = false;
        do {
            System.out.println("ID Categorie : ");
            if (scanner.hasNextInt()) {
                idCategorie = scanner.nextInt();
                isInt = true;
            } else {
                System.out.println("Veuillez saisir un entier.");
                scanner.next(); // Pour vider le scanner et éviter une boucle infinie
            }
        } while (!isInt);
        
        scanner.nextLine();

        System.out.println("Nouveau categorie : ");
        String nouveauNom = scanner.nextLine();

        String sql = "UPDATE categorie SET nom = ? WHERE idCategorie = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nouveauNom);
            statement.setInt(2, idCategorie);

            int lignesModifiees = statement.executeUpdate();
            if (lignesModifiees > 0) {
                System.out.println("Le nom du categorie a été modifié avec succès !");
            } else {
                System.out.println("Erreur lors de la modification du nom du categorie.");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void supprimerCategorie(Connection connection, Scanner scanner) {
        System.out.println("Suppression categorie : ");
        int idCategorie = 0;
        boolean isInt = false;
        do {
            System.out.println("ID categorie à supprimer : ");
            if (scanner.hasNextInt()) {
                idCategorie = scanner.nextInt();
                isInt = true;
            } else {
                System.out.println("Veuillez saisir un entier.");
                scanner.next(); // Pour vider le scanner et éviter une boucle infinie
            }
        } while (!isInt);

        String sql = "DELETE FROM categorie WHERE idCategorie = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idCategorie);

            int lignesModifiees = statement.executeUpdate();
            if (lignesModifiees > 0) {
                System.out.println("Categorie a été supprimé avec succès !");
            } else {
                System.out.println("Erreur lors de la suppression categorie.");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
