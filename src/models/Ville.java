package models;

import utils.Color;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Ville {
    private int idVille;
    private String nom;

    public Ville(int idVille, String nom) {
        this.idVille = idVille;
        this.nom = nom;
    }

    public int getIdVille() {
        return idVille;
    }

    public void setIdVille(int idVille) {
        this.idVille = idVille;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public static void ajouterUneVille() {
        try (Scanner scanner = new Scanner(System.in)) {
			System.out.print("Entrez le nom de la ville: ");
			String nom = scanner.nextLine();
            System.out.println("Liste de Pays: ");
            try{
                Pays.listeDePays(Connexion.con);
            } catch(SQLException e){
                e.printStackTrace();
            }

            int idPays = 0;
            boolean isIdValid = false;
            while (!isIdValid) {
                System.out.print("Entrez l'id du Pays (entier uniquement): ");
                if (scanner.hasNextInt()) {
                    idPays = scanner.nextInt();
                    isIdValid = true;
                } else {
                    System.out.println("Veuillez entrer un entier valide.");
                    scanner.next(); // Consommer l'entrée invalide
                }
            }

            //String idPays = scanner.nextLine();

			String sql = "INSERT INTO ville(idPays, nom) VALUES(?, ?)";

			try {
			    Connection con = Connexion.con;
			    PreparedStatement ps = con.prepareStatement(sql);
			    ps.setString(1, String.valueOf(idPays));
			    ps.setString(2, nom);
			    ps.executeUpdate();
			    System.out.println("Ville ajoutée avec succès !");
			} catch (SQLException e) {
			    e.printStackTrace();
			}
		}
    }

    public static void modifierVilleParId() {
        try (Scanner scanner = new Scanner(System.in)) {
			System.out.print("Entrez l'ID de la ville à modifier: ");
			int idVille = scanner.nextInt();
			scanner.nextLine(); // Consume newline character

			System.out.print("Entrez le nouveau nom de la ville: ");
			String nouveauNom = scanner.nextLine();

			String sql = "UPDATE ville SET nom = ? WHERE idVille = ?";

			try {
			    Connection con = Connexion.con;
			    PreparedStatement ps = con.prepareStatement(sql);
			    ps.setString(1, nouveauNom);
			    ps.setInt(2, idVille);
			    int rowsAffected = ps.executeUpdate();
			    if (rowsAffected > 0) {
			        System.out.println("Ville modifiée avec succès !");
			    } else {
			        System.out.println("Aucune ville trouvée avec cet identifiant.");
			    }
			} catch (SQLException e) {
			    e.printStackTrace();
			}
		}
    }

    public static void afficherToutesLesVilles() {
        String sql = "SELECT * FROM ville";

        try {
            Connection con = Connexion.con;
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            System.out.println("Liste des villes:");
            while (rs.next()) {
                int idVille = rs.getInt("idVille");
                String nom = rs.getString("nom");
                System.out.println("ID: " + idVille + ", Nom: " + nom);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> listeDeVille(Connection connection) throws SQLException{
        System.out.print("\n");
        ArrayList<String> listeville = new ArrayList<>();
        // Create a statement object
        try (Statement statement = connection.createStatement();
             // Create the SQL query to retrieve the entire column list
             ResultSet resultSet = statement.executeQuery("SELECT nom FROM Ville")) {

            // Iterate through the result set and print each value
            System.out.print(Color.ANSI_YELLOW+"[ ");
            int counter = 0;
            int lastCounterValue = 0;
            while (resultSet.next()) {

                String nomVille = resultSet.getString("nom");
                listeville.add(nomVille.toLowerCase());
                if(counter>=lastCounterValue+8){
                    System.out.println(" ");
                    lastCounterValue = counter;
                }

                System.out.print(nomVille + ", ");
                counter++;
            }
            System.out.println(" ]\n"+Color.ANSI_RESET);
        }
        return listeville;
    }
    
}
