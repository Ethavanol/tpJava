import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class AlgorithmeIP extends Algorithme {


    public AlgorithmeIP(HashMap<List<Agent>, Integer> valuePartitions) {
        super(valuePartitions);
    }

    @Override
    public List<List<Agent>> findCoalition() {
        HashMap<List<Integer>, Integer> hashMap = getListPartitionsWithMax();
        List<HashMap.Entry<List<Integer>, Integer>> sortedEntries = new ArrayList<>(hashMap.entrySet());
        sortedEntries.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        int maxRealValue = Integer.MIN_VALUE;
        HashMap<List<List<Agent>>, Integer> combinationsWithTotalValues = new HashMap<>();
        // Parcourir les partitions triées en fonction de la valeur théorique maximale
        for (HashMap.Entry<List<Integer>, Integer> entry : sortedEntries) {
            List<Integer> key = entry.getKey();
            int theoreticalMaxValue = entry.getValue();
            if(theoreticalMaxValue < maxRealValue){
                break;
            }

            // Explorer la partition pour obtenir la valeur maximale réelle
            exploringPartition(key, combinationsWithTotalValues);

            // Trouver la valeur réelle maximale dans les combinaisons explorées
            int realMaxValue = Collections.max(combinationsWithTotalValues.values());
            List<List<Agent>> combination = Collections.max(combinationsWithTotalValues.entrySet(), HashMap.Entry.comparingByValue()).getKey();

            // Si la valeur réelle maximale est supérieure à toutes les autres valeurs théoriques, on arrête
            if (realMaxValue > maxRealValue) {
                maxRealValue = realMaxValue;
            } else {
                // Si la valeur réelle maximale est inférieure ou égale à la valeur maximale théorique
                // et qu'il y a d'autres partitions à explorer, on passe à la suivante
            }
        }

        // Afficher la plus grande valeur réelle trouvée
        System.out.println("La plus grande valeur réelle trouvée : " + maxRealValue);
        List<List<Agent>> combination = Collections.max(combinationsWithTotalValues.entrySet(), HashMap.Entry.comparingByValue()).getKey();
        System.out.println("Valeur réelle maximale actuelle : " + maxRealValue + " " + combination);
        return combination;
    }

    // Retournes pour chaque taille de partitionnement (1,2,3,4), la valeur maximale associée
    private HashMap<Integer, Integer> getMaxForIndexes() {
        HashMap<Integer, Integer> hashMapMax = new HashMap<>();
        valuePartitions.forEach((agents, value) -> {
            int size = agents.size();  // Taille de la liste d'agents
            hashMapMax.merge(size, value, Integer::max);  // Mettre à jour avec la valeur maximale
        });
        return hashMapMax;
    }

    // Retournes la liste des partitions possibles ([1,1,1,1], [1,1,2], [1,3], ..) avec la somme des valeurs maximales associées
    private HashMap<List<Integer>, Integer> getListPartitionsWithMax() {
        List<List<Integer>> partitions = listPartitions();
        HashMap<List<Integer>, Integer> listPartitionsWithMax = new HashMap<>();
        HashMap<Integer, Integer> maxIndexes = getMaxForIndexes();
        for (List<Integer> partition : partitions) {
            int total = 0;
            for (Integer index : partition) {
                total += maxIndexes.get(index);
            }
            listPartitionsWithMax.put(partition, total);
        }
        return listPartitionsWithMax;
    }

    private void exploringPartition(List<Integer> partitionSize, HashMap<List<List<Agent>>, Integer> combinationsWithTotalValues) {
        List<Agent> agents = getAgentList();

        List<List<List<Agent>>> partitions = new ArrayList<>();
        generatePartitions(agents, partitionSize, new ArrayList<>(), partitions);

        // Afficher toutes les partitions générées
        for (List<List<Agent>> partition : partitions) {
            int totalValue = 0;
            for (List<Agent> subset : partition) {
                totalValue += this.valuePartitions.get(subset);
            }
            combinationsWithTotalValues.put(partition, totalValue);
        }
    }

    private void generatePartitions(List<Agent> agents, List<Integer> partitionSize, List<List<Agent>> currentPartition, List<List<List<Agent>>> partitions) {
        if (partitionSize.isEmpty()) {
            partitions.add(new ArrayList<>(currentPartition));
            return;
        }
        int size = partitionSize.get(0);

        List<List<Agent>> combinations = combinations(agents, size);

        for (List<Agent> combination : combinations) {
            // Créer une nouvelle liste d'agents sans ceux déjà choisis
            List<Agent> remainingAgents = new ArrayList<>(agents);
            remainingAgents.removeAll(combination);

            // Ajouter la combinaison à la partition courante
            currentPartition.add(combination);

            // Appel récursif pour générer le reste des partitions
            generatePartitions(remainingAgents, partitionSize.subList(1, partitionSize.size()), currentPartition, partitions);

            // Retirer la combinaison de la partition courante avant de tester d'autres
            currentPartition.remove(currentPartition.size() - 1);
        }
    }

    protected List<List<Agent>> combinations(List<Agent> agents, int size) {
        List<List<Agent>> result = new ArrayList<>();
        combine(agents, size, 0, new ArrayList<>(), result);
        return result;
    }

    private void combine(List<Agent> agents, int size, int start, List<Agent> current, List<List<Agent>> result) {
        if (current.size() == size) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (int i = start; i < agents.size(); i++) {
            current.add(agents.get(i));
            combine(agents, size, i + 1, current, result);
            current.remove(current.size() - 1);
        }
    }
}
