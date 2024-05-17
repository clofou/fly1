package models;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CompagnieAerienne {
	private String nomCompagnie;
	private String motDePasse;
	private String siteWeb;
	private String email;
	private int idAdmin;

	public String getNomCompagnie() {
		return nomCompagnie;
	}

	public void setNomCompagnie(String nomCompagnie) {
		this.nomCompagnie = nomCompagnie;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = hashPassword(motDePasse);
	}

	public String getSiteWeb() {
		return siteWeb;
	}

	public void setSiteWeb(String siteWeb) {
		this.siteWeb = siteWeb;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getIdAdmin() {
		return idAdmin;
	}

	public void setIdAdmin(int idAdmin) {
		this.idAdmin = idAdmin;
	}

	public static void main(String[] args) {
		CompagnieAerienne c = new CompagnieAerienne();
		c.AjouterCompagnie();
	}

	Scanner c = new Scanner(System.in);

	public void AjouterCompagnie() {

		System.out.println("Entrer le nom de la compagnie : ");
		setNomCompagnie(c.next());
		System.out.println("Saisissez le mot de passe : ");
		setMotDePasse(c.next());
		String site;
		do {
			System.out.println("Donnez votre site web : ");
			site = c.next();
			if (isValidUrl(site)) {
				setSiteWeb(site);
			} else {
				System.out.println("Erreur: Le site web n'est pas valide. Veuillez réessayer.");
			}
		} while (!isValidUrl(site));

		String email;
		do {
			System.out.println("Entrez votre adresse email : ");
			email = c.next();
			if (isValidEmail(email)) {
				setEmail(email);
			} else {
				System.out.println("Erreur: L'adresse email n'est pas valide. Veuillez réessayer.");
			}
		} while (!isValidEmail(email));

		System.out.println("Entrez l'identifiant de l'Admin :");
		setIdAdmin(c.nextInt());

		String sql = "INSERT INTO compagnie(nomCompagnie,motDePasse,siteWeb,email,idAdmin) values(?,?,?,?,?)";
		Connexion.seConecter();
		try {
			PreparedStatement ps = Connexion.con.prepareStatement(sql);
			ps.setString(1, getNomCompagnie());
			ps.setString(2, getMotDePasse());
			ps.setString(3, getSiteWeb()); // Utilisation correcte de setSiteWeb()
			ps.setString(4, getEmail());
			ps.setInt(5, getIdAdmin());
			ps.execute();
			System.out.println("Enregistrement effectué !");
			System.out.println("les données sont : " + "Nom complet est: "+ getNomCompagnie() + " " + "Mot de passe: " + getMotDePasse() + " " + "Lien site Web: " + getSiteWeb() + " " + "Email: " + getEmail() + " " + "idAdmin: " + getIdAdmin());

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// Méthode pour vérifier si l'URL est valide
	public static boolean isValidUrl(String url) {
		String urlRegex = "^(http://www\\.|https://www\\.|http://|https://)?[a-zA-Z0-9][-a-zA-Z0-9]+(\\.[a-zA-Z]{2,7})+(:\\d{1,5})?(/.*)?$";
		Pattern pattern = Pattern.compile(urlRegex);
		if (url == null) {
			return false;
		}
		Matcher matcher = pattern.matcher(url);
		return matcher.matches();
	}

	// Méthode pour vérifier si l'email est valide
	public static boolean isValidEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,7}$";
		Pattern pattern = Pattern.compile(emailRegex);
		if (email == null) {
			return false;
		}
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	// Fonction pour hacher le mot de passe avec SHA-256
	private String hashPassword(String password) {
		try {
			// Obtenez une instance de l'algorithme SHA-256 de la classe MessageDigest
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			// Calculez le hachage du mot de passe donné
			byte[] hashBytes = md.digest(password.getBytes());
			// Convertissez les octets de hachage en une représentation hexadécimale
			StringBuilder sb = new StringBuilder();
			for (byte b : hashBytes) {
				sb.append(String.format("%02x", b));
			}
			// Retourne la chaîne hexadécimale du mot de passe haché
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			// En cas d'exception (par exemple, si l'algorithme SHA-256 n'est pas disponible), imprimez la pile d'erreurs
			e.printStackTrace();
			return null;
		}
	}
}
