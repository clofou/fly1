package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Infopassager {
	private int idReservation;
	private int idVol ;
	private int idCategorie;
	private String nomPassagerEtranger;
	private String 	prenomPassagerEtranger;
	private String numeroPasseport;
	public int getIdReservation() {
		return idReservation;
	}
	public void setIdReservation(int idReservation) {
		this.idReservation = idReservation;
	}
	public int getIdVol() {
		return idVol;
	}
	public void setIdVol(int idVol) {
		this.idVol = idVol;
	}
	public int getIdCategorie() {
		return idCategorie;
	}
	public void setIdCategorie(int idCategorie) {
		this.idCategorie = idCategorie;
	}
	public String getNomPassagerEtranger() {
		return nomPassagerEtranger;
	}
	public void setNomPassagerEtranger(String nomPassagerEtranger) {
		this.nomPassagerEtranger = nomPassagerEtranger;
	}
	public String getPrenomPassagerEtranger() {
		return prenomPassagerEtranger;
	}
	public void setPrenomPassagerEtranger(String prenomPassagerEtranger) {
		this.prenomPassagerEtranger = prenomPassagerEtranger;
	}
	public String getNumeroPasseport() {
		return numeroPasseport;
	}
	public void setNumeroPasseport(String numeroPasseport) {
		this.numeroPasseport = numeroPasseport;
	}

	public static int recupererIdInfoPassager(Connection connection) throws SQLException {
		// Préparer la requête SQL
		String sql = "SELECT id FROM infopassager ORDER BY id DESC LIMIT 1";
		int id = 0;
		try (Statement statement = connection.createStatement();
			 ResultSet resultSet = statement.executeQuery(sql)) {

			// Vérifier si le ResultSet contient des données
			if (resultSet.next()) {
				// Récupérer la valeur unique
				id = resultSet.getInt("id");

				// Utiliser la valeur récupérée
				// ... votre code ici pour utiliser la valeur 'nomUtilisateur'
			} else {
				System.out.println("Aucune donnée trouvée.");
			}
		}
		return id;
	}
	
}
