import models.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.mindrot.jbcrypt.BCrypt;

public class Main {


    public static void main(String[] args) throws SQLException {
        Connexion.seConecter();

        Scanner scanner = new Scanner(System.in);
        VolEscale.ajouterVolEscale(Connexion.con,scanner);

    }
}