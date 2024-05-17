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


}