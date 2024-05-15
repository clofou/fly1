package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Vol {

    public static void volDispoAUneDate(Connection connection, String date, int nbreDePlace, String villeDeDepar, String villeDArriv) throws SQLException{
        
        // Create a statement object
        try (Statement statement = connection.createStatement();
             // Create the SQL query to retrieve the entire column list
             ResultSet resultSet = statement.executeQuery("SELECT idVol, villeDeDepart, villeDArrive, dateDeDepart, nombreDEscale, modele, tarif, capacite, immatriculation FROM Vol NATURAL JOIN Avion WHERE dateDeDepart='"+date+"' AND capacite>="+nbreDePlace+" AND villeDeDepart='"+villeDeDepar+"' AND villeDArrive='"+villeDArriv+"'")) {

            // Iterate through the result set and print each value
            
            

            while (resultSet.next()) {
                
                int idVol = resultSet.getInt("idVol");
                String villeDeDepart = resultSet.getString("villeDeDepart");
                String villeDArrive = resultSet.getString("villeDArrive");
                String dateDeDepart = resultSet.getString("dateDeDepart");
                int nombreDEscale = resultSet.getInt("nombreDEscale");
                int tarif = resultSet.getInt("tarif");
                String immatriculation = resultSet.getString("immatriculation");
                
                System.out.print("	");
                System.out.print("matricule: "+immatriculation + "  ");
                System.out.print("numeroVol: "+idVol + "  ");
                System.out.print(villeDeDepart + "-");
                System.out.print(villeDArrive + "  ");
                System.out.print(dateDeDepart + " ");
                System.out.print("nbreEscale:"+nombreDEscale + " ");
                System.out.println("tarif:"+tarif+"euros");
            }
        }
    }

    public static void ajouterVol(Connection connection, Scanner scanner){
        System.out.println("Veuillez saisir les informations de vol :");

        System.out.print("Ville de départ : ");
        String villeDepart = scanner.next();

        System.out.print("Ville d'arrivée : ");
        String villeArrivee = scanner.next();

        System.out.print("Date de départ (au format YYYY-MM-DD) : ");
        String dateDepart = scanner.next();

        System.out.print("Date d'arrivée (au format YYYY-MM-DD) : ");
        String dateArrivee = scanner.next();

        int nbr_de_escale = 0;
        boolean isInt = false;
        do {
            System.out.print("Nombre d'escale : ");
            if (scanner.hasNextInt()) {
                nbr_de_escale = scanner.nextInt();
                isInt = true;
            } else {
                System.out.println("Veuillez saisir un entier.");
                scanner.next(); // Pour vider le scanner et éviter une boucle infinie
            }
        } while (!isInt);

        System.out.print("Tarif : ");
        int tarif = scanner.nextInt();
        System.out.println("Liste des Avions:");
        try {
            Avion.listeDAvion(Connexion.con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.print("choisissez l'id de L'avion : ");
        String idAvion = scanner.next();
        
        

        String sql = "INSERT INTO vol (immatriculation, villeDeDepart, villeDArrive, dateDeDepart, dateDArrive, nombreDEscale, tarif ) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, idAvion);
            statement.setString(2, villeDepart);
            statement.setString(3, villeArrivee);
            statement.setString(4, dateDepart);
            statement.setString(5, dateArrivee);
            statement.setInt(6, nbr_de_escale);
            statement.setInt(7, tarif);

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

    public static void modifierVol(Connection connection, Scanner scanner) {
        System.out.println("Modification vol : ");
        
        
        System.out.print("Ville de départ : ");
        String villeDepart = scanner.nextLine();

        System.out.print("Ville d'arrivée : ");
        String villeArrivee = scanner.nextLine();

        System.out.print("Date de départ (au format YYYY-MM-DD) : ");
        String dateDepart = scanner.nextLine();

        System.out.print("Date d'arrivée (au format YYYY-MM-DD) : ");
        String dateArrivee = scanner.nextLine();

        int nbr_de_escale = 0;
        boolean isInt = false;
        do {
            System.out.print("Nombre d'escale : ");
            if (scanner.hasNextInt()) {
                nbr_de_escale = scanner.nextInt();
                isInt = true;
            } else {
                System.out.println("Veuillez saisir un entier.");
                scanner.next(); // Pour vider le scanner et éviter une boucle infinie
            }
        } while (!isInt);

        System.out.print("Tarif : ");
        int tarif = scanner.nextInt();


        int idVol = 0;
        do {
            System.out.println("ID Categorie : ");
            if (scanner.hasNextInt()) {
                idVol = scanner.nextInt();
                isInt = true;
            } else {
                System.out.println("Veuillez saisir un entier.");
                scanner.next(); // Pour vider le scanner et éviter une boucle infinie
            }
        } while (!isInt);
           
        String sql = "UPDATE vol SET villeDeDepart = ?, villeDArrive = ?, dateDeDepart = ?, dateDArrive = ?, nombreDEscale = ?, tarif = ? WHERE idVol = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, villeDepart);
            statement.setString(2, villeArrivee);
            statement.setString(3, dateDepart);
            statement.setString(4, dateArrivee);
            statement.setInt(5, nbr_de_escale);
            statement.setInt(6, tarif);
            statement.setInt(7, idVol);

            int lignesModifiees = statement.executeUpdate();
            if (lignesModifiees > 0) {
                System.out.println("Vol a été modifié avec succès !");
            } else {
                System.out.println("Erreur lors de la modification du vol.");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void supprimerVol(Connection connection, Scanner scanner) {
        System.out.println("Suppression vol : ");
        int idVol = 0;
        boolean isInt = false;
        do {
            System.out.println("ID vol à supprimer : ");
            if (scanner.hasNextInt()) {
                idVol = scanner.nextInt();
                isInt = true;
            } else {
                System.out.println("Veuillez saisir un entier.");
                scanner.next(); // Pour vider le scanner et éviter une boucle infinie
            }
        } while (!isInt);

        String sql = "DELETE FROM vol WHERE idVol = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idVol);

            int lignesModifiees = statement.executeUpdate();
            if (lignesModifiees > 0) {
                System.out.println("Vol a été supprimé avec succès !");
            } else {
                System.out.println("Erreur lors de la suppression du vol.");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
