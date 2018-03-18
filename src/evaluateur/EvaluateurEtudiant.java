package evaluateur;

import data.Ecole;
import data.Etudiant;

public class EvaluateurEtudiant implements Evaluateur<Etudiant> {
	public EvaluateurEtudiant() {}
	@Override
	public double evaluerCoutAssignement(Etudiant ressource, Etudiant tache) {
		Ecole f = ressource.ecole(),
				e = tache.ecole();
		int cout;
		if (f == e) cout = COUT_FAIBLE;
		else if (Ecole.sontProches(f, e)) cout = COUT_MOYEN;
		else cout = COUT_ELEVE;
		return cout;
	}

}
