
import models.*;
import org.mindrot.jbcrypt.BCrypt;
import utils.Date;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        Connexion.seConecter();

        String dateDepart = String.valueOf(new Date(Date.lireDateValide()));

    }

    private static void InscriptionPassager() throws SQLException {
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
        passager.inscription();


    }

    private static int ConnexionPassager(){
        // Connexion du passager à la base de données
        Scanner entree = new Scanner(System.in);
        System.out.println("Pour vous connecter Veuillez donner votre E-mail : ");
        String email = entree.nextLine();
        System.out.println("Donnez votre mot de passe : ");
        String motDePasse = entree.nextLine();
        String HashermotDePasseFourni = BCrypt.hashpw(motDePasse, BCrypt.gensalt());

        Passager passager = new Passager(null,null,email,null,null,HashermotDePasseFourni);
        return passager.seConnecter(email, motDePasse);
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

    private static int ConnexionAdmin(){
        // Connexion de l'admin à la base de données
        Scanner entree = new Scanner(System.in);
        System.out.println("Pour vous connecter en tant que Admin, veuillez donner votre E-mail : ");
        String email = entree.nextLine();
        System.out.println("Donnez votre mot de passe Admin : ");
        String motDePasse = entree.nextLine();
        String HashermotDePasseFourni = BCrypt.hashpw(motDePasse, BCrypt.gensalt());

        Admin admin = new Admin(null,null,email,null,null,HashermotDePasseFourni);
        //admin.seConnecter(email, motDePasse);
        int connexionAdminReussieIdPersonne = admin.seConnecter(email, motDePasse);
        if (connexionAdminReussieIdPersonne!=-1){
            return connexionAdminReussieIdPersonne;
        }else return -1;

    }
}