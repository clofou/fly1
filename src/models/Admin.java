package models;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Admin extends Personne {
    private int idAdmin;

    // Constructeur
    public Admin(String nom, String prenom, String email, String numeroDeTelephone, String dateDeNaissance, String motDePasse) {
        super(nom, prenom, email, numeroDeTelephone, dateDeNaissance, motDePasse);
    }

    // Getter et Setter spécifique à Admin
    public int getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }

    public void ajoutAdmin() throws SQLException {
        String insertionPersonneQuery = "INSERT INTO Personne (nom, prenom, email, numeroDeTelephone, dateDeNaissance, motDePasse) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        String insertionAdminQuery = "INSERT INTO Admin (idAdmin, idPersonne) VALUES (?, ?)";

        try (
                PreparedStatement personneStatement = Connexion.con.prepareStatement(insertionPersonneQuery);
                PreparedStatement adminStatement = Connexion.con.prepareStatement(insertionAdminQuery)) {

            // Insérer dans la table Personne
            personneStatement.setString(1, getNom());
            personneStatement.setString(2, getPrenom());
            personneStatement.setString(3, getEmail());
            personneStatement.setString(4, getNumeroDeTelephone());
            personneStatement.setString(5, getDateDeNaissance());
            personneStatement.setString(6, getMotDePasse());

            personneStatement.executeUpdate();
            System.out.println(util.recupererValeurUnique(Connexion.con));

            // Insérer dans la table Admin
            try {
                adminStatement.setInt(1, util.recupererValeurUnique(Connexion.con));
                adminStatement.setInt(2, util.recupererValeurUnique(Connexion.con));
            } catch (SQLException e) {
                e.printStackTrace();
            }

            adminStatement.executeUpdate();

            System.out.println("Bienvenu au nouveau Administrateur");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'inscription du nouveau administrateur : " + e.getMessage());
            throw e; // Propagation de l'exception pour une gestion supérieure
        }
    }

    public void ajouterCompagnie(CompagnieAerienne compagnie) throws SQLException {
        String ajoutComp = "INSERT INTO Compagnie (nomCompagnie, motDePasse, siteWeb, idAdmin) VALUES (?, ?, ?, ?)";
        try (Connection connection = Connexion.con;
            PreparedStatement statement = connection.prepareStatement(ajoutComp)) {
            statement.setString(1, compagnie.getNomCompagnie());
            statement.setString(3, compagnie.getMotDePasse());
            statement.setString(4, compagnie.getSiteWeb());
            statement.setInt(5, this.getIdAdmin()); // Utilise l'ID de l'admin courant
            statement.executeUpdate();
        }
    }

    public void supprimerCompagnie(int idCompagnie) throws SQLException {
        String supprComp = "DELETE FROM Compagnie WHERE idCompagnie = ?";
        try (Connection connection = Connexion.con;
            PreparedStatement statement = connection.prepareStatement(supprComp)) {
            statement.setInt(1, idCompagnie);
            statement.executeUpdate();
        }
    }

    public void modifierCompagnie(CompagnieAerienne compagnie) throws SQLException {
        String modifComp = "UPDATE Compagnie SET nomCompagnie = ?, motDePasse = ?, siteWeb = ? WHERE idCompagnie = ?";
        try (Connection connection = Connexion.con;
            PreparedStatement statement = connection.prepareStatement(modifComp)) {
            statement.setString(1, compagnie.getNomCompagnie());
            statement.setString(3, compagnie.getMotDePasse());
            statement.setString(4, compagnie.getSiteWeb());
            statement.setInt(5, compagnie.getIdAdmin());
            statement.executeUpdate();
        }
    }

    public int seConnecter(String email, String motDePasse) {
        String selectPassagerQuery = "SELECT idPersonne, motDePasse FROM Personne WHERE email = ?";

        try (Connection connection = Connexion.con;
             PreparedStatement statement = connection.prepareStatement(selectPassagerQuery)) {

            // Paramètre pour la requête SELECT
            statement.setString(1, email);

            // Exécution de la requête SELECT
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String motDePasseBD = resultSet.getString("motDePasse");

                    // Vérifier le mot de passe haché avec BCrypt
                    if (BCrypt.checkpw(motDePasse, motDePasseBD)) {
                        int idPersonne = resultSet.getInt("idPersonne");
                        System.out.println("Vous êtes connecté en tant qu'admin (ID : " + idPersonne + ")");
                        return true; // Connexion réussie
                    } else {
                        System.out.println("Identifiants incorrects. Connexion échouée.");
                        return false; // Mot de passe incorrect
                    }
                } else {
                    System.out.println("Adresse e-mail non trouvée. Connexion échouée.");
                    return false; // Adresse e-mail non trouvée dans la base de données
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la connexion : " + e.getMessage());
            return false; // Connexion échouée en raison d'une exception SQL
        }
    }
}
