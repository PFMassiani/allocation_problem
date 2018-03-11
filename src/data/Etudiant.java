package data;

public class Etudiant {
	private String nom,prenom,mail;
	private Ecole ecole;
	private boolean jumelage;
	
	public Etudiant(String prenom, String nom, String mail, boolean jumelage, Ecole ecole) {
		this.nom = nom;
		this.prenom = prenom;
		this.mail = mail;
		this.jumelage = jumelage;
		this.ecole = ecole;
	}
	public String nom() {return nom;}
	public String prnom() {return prenom;}
	public String mail() {return mail;}
	public boolean jumelage() {return jumelage;}
	public Ecole ecole() {return ecole;}
}
