package evaluateur;

@FunctionalInterface
public abstract interface Evaluateur<T> {
	public static final int COUT_FAIBLE = 1,
			COUT_MOYEN = 10,
			COUT_ELEVE = 100;
	public abstract double evaluerCoutAssignement(T ressource, T tache);
}
