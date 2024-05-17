package models;

import com.sun.xml.internal.bind.v2.runtime.output.StAXExStreamWriterOutput;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Pays {
    // Cette fonction permet d'ajouter un nouveau pays a notre base de données
    public static void ajouterPays(Connection connection, Scanner scanner) {
        System.out.println("Ajout d'un pays : ");

        System.out.println("Nom du pays : ");
        String nom = scanner.nextLine();

        String sql = "INSERT INTO pays (nom) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nom);

            int lignesModifiees = statement.executeUpdate();
            if (lignesModifiees > 0) {
                System.out.println("Les données du pays ont été ajoutées avec succès !");
            } else {
                System.out.println("Erreur lors de l'ajout des données.");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    // Cette fonction permet de modifier le nom d'un pays existant
    public static void modifierNomPays(Connection connection, Scanner scanner) {
        System.out.println("Modification du nom d'un pays : ");

        System.out.println("ID du pays à modifier : ");
        int idPays = scanner.nextInt();
        scanner.nextLine(); // Pour consommer le retour à la ligne

        System.out.println("Nouveau nom du pays : ");
        String nouveauNom = scanner.nextLine();

        String sql = "UPDATE pays SET nom = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nouveauNom);
            statement.setInt(2, idPays);

            int lignesModifiees = statement.executeUpdate();
            if (lignesModifiees > 0) {
                System.out.println("Le nom du pays a été modifié avec succès !");
            } else {
                System.out.println("Erreur lors de la modification du nom du pays.");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    // Cette fonction permet de supprimer un pays de notre base
    public static void supprimerPays(Connection connection, Scanner scanner) {
        System.out.println("Suppression d'un pays : ");

        System.out.println("ID du pays à supprimer : ");
        int id = scanner.nextInt();

        String sql = "DELETE FROM pays WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            int lignesModifiees = statement.executeUpdate();
            if (lignesModifiees > 0) {
                System.out.println("Le pays a été supprimé avec succès !");
            } else {
                System.out.println("Erreur lors de la suppression du pays.");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    // Cette fonction retourne la liste de tout les pays dans ma base de données
    public static void listeDePays(Connection connection) throws SQLException {
        System.out.println("\n");
        // Créer un objet Statement
        try (Statement statement = connection.createStatement();
             // Créez la requête SQL pour récupérer la liste complète des colonnes
             ResultSet resultSet = statement.executeQuery("SELECT idPays, nom FROM Pays")) {

            // Parcourez l'ensemble de résultats et imprimez chaque valeur
            while (resultSet.next()) {
                String idPays = resultSet.getString("idPays");
                String nomPays = resultSet.getString("nom");

                System.out.print(idPays + "- ");
                System.out.println(nomPays);
            }
        }
        System.out.println("\n\n");
    }
    // Cette fonction retourne la liste de tout les Ids
    public static ArrayList<Integer> afficherTousLesIds(Connection connection) {
        String sql = "SELECT idPays FROM pays";
        ArrayList<Integer> listeIds = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("idPays");
                listeIds.add(id);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return listeIds;
    }
}
