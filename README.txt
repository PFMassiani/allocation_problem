Pour utiliser cet algorithme :

0) Ajouter à votre classpath la librairie poi-3.17.jar, que vous trouverez à l'URL suivant : https://archive.apache.org/dist/poi/release/bin/
Le fichier poi-bin-3.17-20170915.[zip/tar.gz] contient cette librairie.
Pour ajouter cette librairie au projet, sous Eclipse, il suffit de faire un clic droit sur le projet -> Build Path -> Add external archive et de sélectionner le fichier .jar correspondant.

1) Créer un type dans le package data ayant les champs requis. Exemple : Etudiant.
    Attention : il FAUT réécrire les fonctions equals et getHash de la classe, sous peine d'avoir des comportements imprévisibles. Si vous ne savez pas comment faire, vous pouvez prendre les implémentations suivantes:

    public int getHash(){ return 0; }

    public boolean equals(Object o) {
	    	if (o != null) {
	    		if (o instanceof [MON_TYPE]) {
	    			[MON_TYPE] e = ([MON_TYPE]) o;
	    			return [COMPARAISON_CHAMPS_PERTINENTS];
	    		}
	    	}
	    	return false;
	    }

    où vous aurez remplacé:
        -[MON_TYPE] par le nom de votre classe
        -[COMPARAISON_CHAMPS_PERTINENTS] par la comparaison de champs pertinents pour la comparaison (par exemple, pour Etudiant, on peut prendre [COMPARAISON_CHAMPS_PERTINENTS] = nom.compareTo(e.nom) == 0)

2) Créer un parser pour votre type dans le package data et héritant de DataParser<T>, où T est le type que vous avez défini en 1). Ce parser doit spécifier l'implémentation de la méthode parse(). Vous pouvez vous inspirer de l'implémentation fournie par ParserEtudiant pour cette étape. Pour construire ce parser, vous aurez également besoin du fichier dans lequel se trouvent les données à parser.

3) Implémenter l'interface Evaluateur<T> en spécifiant la méthode int evaluerCoutAssignement(T ressource, T tache), qui doit renvoyer le coût d'assigner la tâche tache à la ressource ressource. Vous pouvez utiliser les coûts prédéfinis dans l'interface fonctionnelle Evaluateur<T>, ou bien en définir d'autres.
    Remarque : l'interface Evaluateur<T> est fonctionnelle. Si vous le désirez, vous pourrez redéfinir la méthode evaluerCoutAssignement comme une lambda expression au seul endroit où elle vous sera nécessaire, et donc vous passer de cette étape.

4) Définir un sous type du type abstrait Assign<T>, paramétré par le type du package data que vous avez créé. La classe ainsi créée doit spécifier les implémentations des méthodes suivantes:
    -un constructeur prenant en paramètres deux parsers définis en 2) : un pour les ressources, et un pour les tâches;
    -une méthode traiterResultats(Map<T,List<T>>) exlicitant ce que vous voulez faire avec les résultats. Les clés de la Map sont les ressources, et les valeurs sont les tâches assignées à chaque ressource. Vous pouvez vous inspirer de l'implémentation fournie par AssignEtudiant, qui écrit les résultats dans un fichier Excel.
    -une méthode main, qui construit une instance de la classe que vous avez définie dans l'étape 4, et qui appelle sa méthode assign.


IMPORTANT : cet algorithme ne peut lire que des fichiers au format XLS, et pas XLSX ! 
