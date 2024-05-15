package models;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AeroportDAO {
    

    
    // Méthode pour ajouter les informations d'un aéroport dans la base de données
    public static void ajouterAeroport(Aeroport aeroport) {
        try {
            // Requête SQL d'insertion
            String query = "INSERT INTO Aeroport (idVille, nomAeroport) VALUES (?, ?)";
            
            // Crée un objet PreparedStatement avec la requête SQL
            PreparedStatement preparedStatement = Connexion.con.prepareStatement(query);
            
            // Définit les valeurs des paramètres dans la requête SQL
            preparedStatement.setInt(1, aeroport.getIdVille());
            preparedStatement.setString(2, aeroport.getNomAeroport());
            
            // Exécute la requête SQL
            preparedStatement.executeUpdate();
            
            // Ferme le PreparedStatement
            preparedStatement.close();
            
            System.out.println("Aéroport ajouté avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Méthode pour fermer la connexion à la base de données
    public static void disconnect() {
        try {
            if (Connexion.con != null && !Connexion.con.isClosed()) {
                Connexion.con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
