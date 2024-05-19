import models.Admin;
import models.Connexion;
import org.mindrot.jbcrypt.BCrypt;
import utils.Date;

import java.sql.SQLException;
import java.util.Scanner;

import static models.util.isValidEmail;
import static models.util.isValidInternationalNumber;
import static utils.Date.lireDateValide;

public class MainAdmin {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connexion.seConecter();
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
        String email;

        while (true){
            System.out.print("Veuillez donner votre E-mail : ");
            email = entree.nextLine();
            if (isValidEmail(email)){
                break;
            } else {
                System.out.println("⚠️Email INVALIDE !!!");
            }
        }
        //Numero de telephone
        String numTelephone;

        while (true){
            System.out.print("Votre numero de telephone (Format International, Ex: +223 ... ) : ");
            numTelephone = entree.nextLine();
            if (isValidInternationalNumber(numTelephone)){
                break;
            } else {
                System.out.println("⚠️Numero Invalide !!!");
            }
        }
        //Date de Naissance
        String DateNaissance = new Date(lireDateValide()).formatAnglais();;
        //Mot de Passe
        while(true){
            System.out.print("Donnez un mot de passe :");
            String motDePasse = entree.nextLine();
            if (motDePasse.length() < 6){
                System.out.println("La longueur du mot de passe doit etre superieur a 6caracteres");
                continue;
            }
            break;
        }
        System.out.println("Donnez un mot de passe :");
        String motDePasse = entree.nextLine();
        String motDePasseHasher = BCrypt.hashpw(motDePasse, BCrypt.gensalt());
        Admin admin = new Admin(nom, prenom, email, numTelephone, DateNaissance, motDePasseHasher);
        //admin.inscription();
    }

    private static int ConnexionAdmin(){
        // Connexion de l'admin à la base de données
        Scanner entree = new Scanner(System.in);
        System.out.println("Pour vous connecter en tant que Admin...");
        //Email
        String email;

        while (true){
            System.out.print("Veuillez donner votre E-mail : ");
            email = entree.nextLine();
            if (isValidEmail(email)){
                break;
            } else {
                System.out.println("⚠️Email INVALIDE !!!");
            }
        }
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
