import data.ParserEtudiant;
import data.Ecole;
import data.Etudiant;
import algorithme.HungarianAlgorithm;

public class Assign {
	
	public static double[][] createCostMatrix(Etudiant[] francais, Etudiant[] etrangers) {
		double[][] cost = new double[francais.length][etrangers.length];
		
		final int COUT_EGAL = 0,
				COUT_PROCHES = 1,
				COUT_LOIN = 10;
		
		Ecole f,e;
		for(int i = 0; i < francais.length; i++) {
			for(int j = 0; j < etrangers.length; j++) {
				f = francais[i].ecole();
				e = etrangers[j].ecole();
				if(f == e)
					cost[i][j] = COUT_EGAL;
				else if(Ecole.sontProches(f,e))
					cost[i][j] = COUT_PROCHES;
				else
					cost[i][j] = COUT_LOIN;
			}
		}
		return cost;
	}
	public static void main(String[] args) {
		String file_etrangers = null,file_francais = null;
		String out = null;
		
		file_etrangers = "./files/etrangers.xls";
		file_francais = "./files/francais.xls";
		out = "./files/out.xlsx";

		System.out.println("Processing...");
		ParserEtudiant parserEtrangers = new ParserEtudiant(file_etrangers);
		ParserEtudiant parserFrancais = new ParserEtudiant(file_francais,2,3,1,4,6);
		try {
			
			Etudiant[] etrangers = parserEtrangers.parse();
			Etudiant[] francais = parserFrancais.parse();
			
			double[][] cost = createCostMatrix(francais,etrangers);
			HungarianAlgorithm algorithm = new HungarianAlgorithm(cost);
			
			int[] assignment = algorithm.execute();
			
			for(int i = 0; i < francais.length; i++) {
				System.out.println(francais[i].nom() + " -> " + etrangers[assignment[i]].nom());
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("Done !");
	}
}
