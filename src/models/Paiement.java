package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Paiement {
    

    public static void ajouterPaiement(Connection connection, Scanner scanner){
        System.out.println("Veuillez saisir les informations de paiement :");


        System.out.println("montant : ");
        int Montant = scanner.nextInt();
        
        System.out.println("mode de paiement : ");
        String Modepaiement = scanner.nextLine();
        scanner.nextLine();
        
        System.out.println("date paiement (au format YYYY-MM-DD) : ");
        String date_paiement = scanner.nextLine();
        

        System.out.println("id reservation  : ");
        int id_reservation = scanner.nextInt();

        String sql = "INSERT INTO paiement (montant,  modePaiement, datePaiement, idReservation ) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, Montant);
            statement.setString(2, Modepaiement);
            statement.setString(3, date_paiement);
            statement.setInt(4, id_reservation);

            // Exécutez la requête
            int lignesModifiees = statement.executeUpdate();
            if (lignesModifiees > 0) {
                System.out.println("Les données ont été ajoutées avec succès !");
            } else {
                System.out.println("Erreur lors de l'ajout des données.");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
