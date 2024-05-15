package models;

public abstract class Personne {
    private String nom;
    private String prenom;
    private String email;
    private String numeroDeTelephone;
    private String dateDeNaissance;
    private String motDePasse;

    // Constructeur
    public Personne(String nom, String prenom, String email, String numeroDeTelephone, String dateDeNaissance, String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.numeroDeTelephone = numeroDeTelephone;
        this.dateDeNaissance = dateDeNaissance;
        this.motDePasse = motDePasse;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumeroDeTelephone() {
        return numeroDeTelephone;
    }

    public void setNumeroDeTelephone(String numeroDeTelephone) {
        this.numeroDeTelephone = numeroDeTelephone;
    }

    public String getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setDateDeNaissance(String dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public abstract boolean seConnecter(String email, String motDePasse);
}
