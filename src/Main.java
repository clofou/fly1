import models.*;
import java.sql.SQLException;
import java.util.Scanner;
import org.mindrot.jbcrypt.BCrypt;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        Connexion.seConecter();

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