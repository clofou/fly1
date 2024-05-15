package models;
public class Aeroport {
    private int idVille;
    private String nomAeroport;
    
    // Constructeur
    public Aeroport(int idVille, String nomAeroport) {
        this.idVille = idVille;
        this.nomAeroport = nomAeroport;
    }
    
    
    public int getIdVille() {
        return idVille;
    }
    
    public void setIdVille(int idVille) {
        this.idVille = idVille;
    }
    
    public String getNomAeroport() {
        return nomAeroport;
    }
    
    public void setNomAeroport(String nomAeroport) {
        this.nomAeroport = nomAeroport;
    }
    
    // Méthode pour afficher les informations de l'aéroport
    @Override
    public String toString() {
        return "idVille=" + idVille + ", nomAeroport=" + nomAeroport + "]";
    }

    
}
