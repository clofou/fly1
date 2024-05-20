package models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.mindrot.jbcrypt.BCrypt;
import utils.Color;

public class Passager extends Personne {
    private int idPassager;

    // Constructeur
    public Passager(String nom, String prenom, String email, String numeroDeTelephone, String dateDeNaissance, String motDePasse) {
        super(nom, prenom, email, numeroDeTelephone, dateDeNaissance, motDePasse);
    }

    // Getter et Setter spécifique à Passager
    public int getIdPassager() {
        idPassager = -1;
        try {
            idPassager = getPassager(Connexion.con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idPassager;

    }

    public void setIdPassager(int idPassager) {
        this.idPassager = idPassager;
    }
    private int getPassager(Connection connection) throws SQLException{
        // Préparer la requête SQL
        String sql = "SELECT idPersonne FROM Personne WHERE email='"+getEmail()+"'";
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

    public int inscription() throws SQLException {
        String insertionPersonneQuery = "INSERT INTO Personne (nom, prenom, email, numeroDeTelephone, dateDeNaissance, motDePasse) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        String insertionPassagerQuery = "INSERT INTO Passager (idPassager, idPersonne) VALUES (?, ?)";

        try (
                PreparedStatement personneStatement = Connexion.con.prepareStatement(insertionPersonneQuery);
                PreparedStatement passagerStatement = Connexion.con.prepareStatement(insertionPassagerQuery)) {

            // Insérer dans la table Personne
            personneStatement.setString(1, getNom());
            personneStatement.setString(2, getPrenom());
            personneStatement.setString(3, getEmail());
            personneStatement.setString(4, getNumeroDeTelephone());
            personneStatement.setString(5, getDateDeNaissance());
            personneStatement.setString(6, getMotDePasse());

            personneStatement.executeUpdate();
            //System.out.println(util.recupererValeurUnique(Connexion.con));

            // Insérer dans la table Passager
            try {
                passagerStatement.setInt(1, util.recupererValeurUnique(Connexion.con));
                passagerStatement.setInt(2, util.recupererValeurUnique(Connexion.con));
            } catch (SQLException e) {
                e.printStackTrace();
            }


            passagerStatement.executeUpdate();

            System.out.println("\n!!! Passager inscrit avec succès !!!");
            return util.recupererValeurUnique(Connexion.con);
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'inscription du passager : " + e.getMessage());
            throw e; // Propagation de l'exception pour une gestion supérieure
        }
    }

    //Methode faireReservation

    public void faireReservation(Date dateReservation, int nombreDePassager, String statut) throws SQLException {
        String insereReservation = "INSERT INTO Reservation (idPassager, dateReservation, nombreDePassager, status) " +
                "VALUES (?, ?, ?, ?)";

        try (
             PreparedStatement reservationStatement = Connexion.con.prepareStatement(insereReservation)) {

            // Insérer la réservation dans la base de données
            reservationStatement.setInt(1, getIdPassager());
            reservationStatement.setDate(2, dateReservation);
            reservationStatement.setInt(3, nombreDePassager);
            reservationStatement.setString(4, statut);

            reservationStatement.executeUpdate();

            System.out.println("Réservation effectuée avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la réservation : " + e.getMessage());
            throw e; // Propagation de l'exception pour une gestion supérieure
        }
    }

    //Methode modifierReservation

    public void annulerReservation(int idReservation) throws SQLException {
        String modificationQuery = "UPDATE Reservation SET statut = ? WHERE idReservation = ? AND idPassager = ?";

        try (
             PreparedStatement statement = Connexion.con.prepareStatement(modificationQuery)) {

            // Paramètres pour la modification de la réservation
            statement.setString(1, "annulé");
            statement.setInt(2, idReservation);
            statement.setInt(3, getIdPassager());

            // Exécution de la requête de mise à jour
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Réservation annulée avec succès !");
            } else {
                System.out.println("Aucune réservation trouvée avec l'ID : " + idReservation);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification de la réservation : " + e.getMessage());
            throw e; // Propagation de l'exception pour une gestion supérieure
        }
    }

    //Methode verifierStatutReservation

    public void verifierStatutReservation(int idReservation) throws SQLException {
        String verificationQuery = "SELECT status FROM Reservation WHERE idReservation = ? AND idPassager = ?";

        try (
             PreparedStatement statement = Connexion.con.prepareStatement(verificationQuery)) {

            // Paramètres pour la vérification du statut de la réservation
            statement.setInt(1, idReservation);
            statement.setInt(2, getIdPassager());

            // Exécution de la requête de sélection
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String statut = resultSet.getString("status");
                    System.out.println("Statut de la réservation (ID " + idReservation + ") pour ce passager : " + statut);
                } else {
                    System.out.println("Aucune réservation trouvée avec l'ID : " + idReservation);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la vérification du statut de la réservation : " + e.getMessage());
            throw e; // Propagation de l'exception pour une gestion supérieure
        }
    }

    public int seConnecter(String email, String motDePasse) {
        String selectPassagerQuery = "SELECT idPersonne, motDePasse FROM Personne WHERE email = ?";

        try (
             PreparedStatement statement = Connexion.con.prepareStatement(selectPassagerQuery)) {

            // Paramètre pour la requête SELECT
            statement.setString(1, email);

            // Exécution de la requête SELECT
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String motDePasseBD = resultSet.getString("motDePasse");
                    int idPersonne = resultSet.getInt("IdPersonne");

                    // Vérifier le mot de passe haché avec BCrypt
                    if (BCrypt.checkpw(motDePasse, motDePasseBD)) {
                        int idPersonneBD = resultSet.getInt("idPersonne");
                        BienvenuePassager(Connexion.con, email);
                        return idPersonneBD; // Connexion réussie
                    } else {
                        System.out.println(Color.ANSI_RED+"⚠️Identifiants incorrects. Connexion échouée.\n"+Color.ANSI_RESET);
                        return -1; // Mot de passe incorrect
                    }
                } else {
                    System.out.println(Color.ANSI_RED+"⚠️Adresse e-mail non trouvée. Connexion échouée.\n"+Color.ANSI_RESET);
                    return -1; // Adresse e-mail non trouvée dans la base de données
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la connexion : " + e.getMessage());
            return -1; // Connexion échouée en raison d'une exception SQL
        }
    }

    public static boolean isEmailExist(Connection connection,String email) throws SQLException{
        // Préparer la requête SQL
        String sql = "SELECT idPersonne FROM Personne WHERE email='"+email+"'";
        boolean idPersonne = false;
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            // Vérifier si le ResultSet contient des données
            if (resultSet.next()) {
                idPersonne = true;
            }
        }
        return idPersonne;
    }

    private void BienvenuePassager(Connection connection,String email) throws SQLException{
        // Préparer la requête SQL
        String sql = "SELECT nom, prenom FROM Personne WHERE email='"+email+"'";
        int idPersonne = 0;
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            // Vérifier si le ResultSet contient des données
            if (resultSet.next()) {
                // Récupérer la valeur unique
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");

                System.out.println("\nBIENVENUE,"+ Color.ANSI_GREEN + prenom.toUpperCase() + " " + nom.toUpperCase()+ Color.ANSI_RESET+"\n");
            }
        }
    }
}
