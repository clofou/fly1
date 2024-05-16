import models.*;
import java.sql.SQLException;
import java.util.Scanner;
import org.mindrot.jbcrypt.BCrypt;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        Connexion.seConecter();
        AdminAjoutCompagnie();
    }

    private void InscriptionPassager() throws SQLException {
        // Workflow pour l'inscription
        Scanner entree = new Scanner(System.in);
        System.out.println("Veuillez entrer vos informations :\n");
        //Nom Passager
        System.out.println("Votre nom de Famille :");
        String nom = entree.nextLine();
        //Prenom Passager
        System.out.println("Votre Prenom :");
        String prenom = entree.nextLine();
        //Email
        System.out.println("Votre Email :");
        String email = entree.nextLine();
        //Numero de telephone
        System.out.println("Votre numero de telephone :");
        String numTelephone = entree.nextLine();
        //Date de Naissance
        String DateNaissance = util.Date();
        //Mot de Passe
        System.out.println("Donnez un mot de passe :");
        String motDePasse = entree.nextLine();
        String motDePasseHasher = BCrypt.hashpw(motDePasse, BCrypt.gensalt());

        // Instanciation du passager
        Passager passager = new Passager(nom, prenom, email, numTelephone, DateNaissance, motDePasseHasher);
        //Ajout dans la base de données
        passager.inscription();
    }

    private void ConnexionPassager(){
        // Connexion du passager à la base de données
        Scanner entree = new Scanner(System.in);
        System.out.println("Pour vous connecter Veuillez donner votre E-mail : ");
        String email = entree.nextLine();
        System.out.println("Donnez votre mot de passe : ");
        String motDePasse = entree.nextLine();
        String HashermotDePasseFourni = BCrypt.hashpw(motDePasse, BCrypt.gensalt());

        Passager passager = new Passager(null,null,email,null,null,HashermotDePasseFourni);
        passager.seConnecter(email, motDePasse);
    }

    private static void ajouterUnAdministrateur() throws SQLException {
        //Ajout d'un administrateur

        Scanner entree = new Scanner(System.in);
        System.out.println("Veuillez entrer les informations du nouveau administrateur :\n");
        //Nom Admin
        System.out.println("Votre nom de Famille :");
        String nom = entree.nextLine();
        //Prenom Admin
        System.out.println("Votre Prenom :");
        String prenom = entree.nextLine();
        //Email
        System.out.println("Votre Email :");
        String email = entree.nextLine();
        //Numero de telephone
        System.out.println("Votre numero de telephone :");
        String numTelephone = entree.nextLine();
        //Date de Naissance
        String DateNaissance = util.Date();
        //Mot de Passe
        System.out.println("Donnez un mot de passe :");
        String motDePasse = entree.nextLine();
        String motDePasseHasher = BCrypt.hashpw(motDePasse, BCrypt.gensalt());

        // Instanciation de l'admin
        Admin admin = new Admin(nom, prenom, email, numTelephone, DateNaissance, motDePasseHasher);
        //Ajout dans la base de données
        admin.ajoutAdmin();
    }

    private static boolean ConnexionAdmin(){
        // Connexion de l'admin à la base de données
        Scanner entree = new Scanner(System.in);
        System.out.println("Pour vous connecter en tant que Admin, veuillez donner votre E-mail : ");
        String email = entree.nextLine();
        System.out.println("Donnez votre mot de passe Admin : ");
        String motDePasse = entree.nextLine();
        String HashermotDePasseFourni = BCrypt.hashpw(motDePasse, BCrypt.gensalt());

        Admin admin = new Admin(null,null,email,null,null,HashermotDePasseFourni);
        //admin.seConnecter(email, motDePasse);
        if (admin.seConnecter(email, motDePasse)){
            return true;
        }else return false;

    }

    private static void AdminAjoutCompagnie() throws SQLException {
        // Connexion de l'admin à la base de données
        Scanner entree = new Scanner(System.in);
        System.out.println("Pour ajouter une compagnie , veuillez donner votre E-mail : ");
        String email = entree.nextLine();
        System.out.println("Donnez votre mot de passe Admin : ");
        String motDePasse = entree.nextLine();
        String HashermotDePasseFourni = BCrypt.hashpw(motDePasse, BCrypt.gensalt());

        Admin admin = new Admin(null,null,email,null,null,HashermotDePasseFourni);
        //admin.seConnecter(email, motDePasse);
        if (admin.seConnecter(email, motDePasse) == true){
            System.out.println("Entrer le nom de votre compagnie : ");
            String nomCompagnie = entree.nextLine();
            System.out.println("Donnez le mot de passe : ");
            String motDePasseHasher = util.hasherMotDePasse();
            System.out.println("Donnez votre site : ");
            String siteWeb = entree.nextLine();

            //Instancier la compagnie
            CompagnieAerienne compagnie = new CompagnieAerienne();
            compagnie.setNomCompagnie(nomCompagnie);
            compagnie.setMotDePasse(motDePasseHasher);
            compagnie.setSiteWeb(siteWeb);

            try {
                admin.ajouterCompagnie(compagnie);
                System.out.println("Compagnie ajoutée avec succès.");
            } catch (SQLException e) {
                System.err.println("Erreur lors de l'ajout de la compagnie : " + e.getMessage());
            }
        }else{
            System.out.println("Vous n'êtes pas autorisé à ajouter une compagnie");
        }

    }


}