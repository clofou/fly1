package models;

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
	
}
