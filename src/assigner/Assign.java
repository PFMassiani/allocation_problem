package assigner;
import evaluateur.Evaluateur;
import data.DataParser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import algorithme.HungarianAlgorithm;

public abstract class Assign<T> {
	protected DataParser<T> parserRessources,parserTaches;
	public Assign(DataParser<T> parserRessources,DataParser<T> parserTaches) {
		this.parserRessources = parserRessources;
		this.parserTaches = parserTaches;
	}
	
	public double[][] createCostMatrix(Evaluateur<T> eval,List<T> ressources,List<T> taches){
		double[][] cost = new double[ressources.size()][taches.size()];
		int i,j;
		for(T ress : ressources) {
			i = ressources.indexOf(ress);
			for(T tache : taches) {
				j = taches.indexOf(tache);
				cost[i][j] = eval.evaluerCoutAssignement(ress,tache);
			}
		}
		return cost;
	}
	
	public void assigner(Evaluateur<T> evaluateur) {
		System.out.println("Processing...");
		try {
			List<T> ressources = parserRessources.parse();
			List<T> taches = parserTaches.parse();
		
			Map<T,List<T>> assignation = assignerToutesLesTaches(evaluateur,ressources,taches);
			traiterResultats(assignation);
			System.out.println("Terminé");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected Map<T,List<T>> assignerToutesLesTaches(Evaluateur<T> evaluateur, List<T> ressources, List<T> taches){
		double[][] cost;
		HungarianAlgorithm algorithm;
		int[] assignmentMatrix;
		
		Map<T,List<T>> assignation = new HashMap<T,List<T>>();
		List<T> assignesAuTourCourant;
		Set<T> assignes;
		T tmp;
		int i, j;
		while(!taches.isEmpty()) {
			cost = createCostMatrix(evaluateur,ressources,taches);
			algorithm = new HungarianAlgorithm(cost);
			assignmentMatrix = algorithm.execute();
			
			assignes = new HashSet<T>();
			for (T fr : ressources) {
				if (assignation.get(fr) == null) {
					assignation.put(fr,new LinkedList<T>());
				}
				assignesAuTourCourant = assignation.get(fr);
				i = ressources.indexOf(fr);
				j = assignmentMatrix[i];
				if (j != -1) {
					tmp = taches.get(j);
					assignesAuTourCourant.add(tmp);
					assignes.add(tmp);
				}
				else
					System.out.println("Pas d'assignation pour cette ressource pour ce tour");
				
			}
			taches.removeAll(assignes);
			
			System.out.println("Tour effectué");
		}
		return assignation;
	}

	public abstract void traiterResultats(Map<T,List<T>> resultats);
	
}
