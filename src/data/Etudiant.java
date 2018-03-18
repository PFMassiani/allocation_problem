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
	public String prenom() {return prenom;}
	public String mail() {return mail;}
	public boolean jumelage() {return jumelage;}
	public Ecole ecole() {return ecole;}
	
	@Override
	public boolean equals(Object o) {
		if (o != null) {
			if (o instanceof Etudiant) {
				Etudiant e = (Etudiant) o;
				return nom().compareTo(e.nom()) == 0
						&& prenom().compareTo(e.prenom()) == 0
						&& mail().compareTo(e.mail()) == 0;
			}
		}
		return false;
	}
	@Override
	public int hashCode() {
		return (nom + " "
					+ prenom + " "
					+ mail)
				.hashCode();
	}
	@Override
	public String toString() {
		return prenom + " " + nom + " " + mail + ", " + ecole;
	}
}
