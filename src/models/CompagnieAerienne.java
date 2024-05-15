package models;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;


public class CompagnieAerienne{
    private String nomCompagnie;
	private String motDePasse;
	private String siteWeb;
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
		this.motDePasse = motDePasse;
	}

	public String getSiteWeb() {
		return siteWeb;
	}

	public void setSiteWeb(String siteWeb) {
		this.siteWeb = siteWeb;
	}

	public int getIdAdmin() {
		return idAdmin;
	}

	public void setIdAdmin(int idAdmin) {
		this.idAdmin = idAdmin;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CompagnieAerienne c =new CompagnieAerienne();
		c.AjouterCompagnie();

	}
	Scanner c=new Scanner(System.in);
	public void AjouterCompagnie() {
		
		System.out.println("Entrer le nom de la compagnie : ");
		setNomCompagnie(c.next());
		System.out.println("Saisissez le mot de passe : ");
		setMotDePasse(c.next());
		System.out.println("Donnez votre site : ");
		setSiteWeb(c.next());
		System.out.println("Entrez l'identifiant de l'Admin :");
		setIdAdmin(c.nextInt());
		
		String sql="INSERT INTO compagnie(nomCompagnie,motDePasse,siteWeb,idAdmin) values(?,?,?,?)";
		Connexion.seConecter();
		try {
			PreparedStatement ps=Connexion.con.prepareStatement(sql);
			ps.setString(1, getNomCompagnie());
			ps.setString(2, getMotDePasse());
			ps.setString(3, getSiteWeb());
			ps.setInt(4, getIdAdmin());
			ps.execute();
			System.out.println("Enregistrement effectuée !!!!!");
			System.out.println("les données sont : "+getNomCompagnie()+" "+getMotDePasse()+" "+getSiteWeb()+" "+getIdAdmin());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
  



}
