import java.util.*;

public class AlgorithmeIDP extends Algorithme {

    private HashMap<List<Agent>, Integer> table = new HashMap<List<Agent>, Integer>();

    public AlgorithmeIDP(HashMap<List<Agent>, Integer> valuePartitions) {
        super(valuePartitions);
    }

    public void findCoalition() {
        calculateAllEntries();
        //   calculateEntry();
    }

    public void calculateAllEntries() {
        List<List<Agent>> sortedEntries = new ArrayList<>(this.valuePartitions.keySet());
        sortedEntries.sort((entry1, entry2) -> Integer.valueOf(entry1.size()).compareTo(Integer.valueOf(entry2.size())));
        HashMap<List<Agent>, Map.Entry<List<List<Agent>>,Integer>> combinationsValues = new HashMap<>();
        for (List<Agent> entry: sortedEntries){
            if (entry.size() == 1){
                combinationsValues.put(entry, new AbstractMap.SimpleEntry<>(List.of(entry), this.valuePartitions.get(entry)));
            }
            combinationsValues = calculateEntry(entry, combinationsValues);
        }
        for(Map.Entry<List<Agent>, Map.Entry<List<List<Agent>>, Integer>> entry : combinationsValues.entrySet()) {
            System.out.println(entry.getKey() + "  :  " + entry.getValue());
            // do what you have to do here
            // In your case, another loop.
        }
    }

    public HashMap<List<Agent>, Map.Entry<List<List<Agent>>,Integer>> calculateEntry(List<Agent> entry, HashMap<List<Agent>, Map.Entry<List<List<Agent>>,Integer>> combinationsValues){
        combinationsValues = generateAllCombinationsFromEntry(entry, combinationsValues);
        return combinationsValues;
    }

    public HashMap<List<Agent>, Map.Entry<List<List<Agent>>,Integer>> generateAllCombinationsFromEntry(List<Agent> entry, HashMap<List<Agent>, Map.Entry<List<List<Agent>>,Integer>> combinationsValues){
        int entrySize = entry.size();
        List<List<List<Agent>>> listCombinations = new ArrayList<>();
        for (int i = 1; i <= (entrySize / 2); i++) {
            List<Integer> newPartition = new ArrayList<>();
            newPartition.add(i);
            newPartition.add(entrySize - i);
            System.out.println(newPartition);
            generateAndCalculateCombinations(entry, newPartition, new ArrayList<>(), listCombinations, combinationsValues);
        }
        return combinationsValues;
    }

    // Génères toutes les combinaisons pour une liste d'agents et un partitionnement donné
    protected HashMap<List<Agent>, Map.Entry<List<List<Agent>>,Integer>> generateAndCalculateCombinations(List<Agent> agents, List<Integer> partitionSize, List<List<Agent>> currentCombination, List<List<List<Agent>>> allCombinations, HashMap<List<Agent>, Map.Entry<List<List<Agent>>,Integer>> combinationsValues) {
        if (partitionSize.isEmpty()) {
            allCombinations.add(new ArrayList<>(currentCombination));
            combinationsValues = calculateCombination(agents, currentCombination, combinationsValues);
            return combinationsValues;
        }
        int size = partitionSize.get(0);

        List<List<Agent>> combinations = combinations(agents, size);

        for (List<Agent> combination : combinations) {
            // Créer une nouvelle liste d'agents sans ceux déjà choisis
            List<Agent> remainingAgents = new ArrayList<>(agents);
            remainingAgents.removeAll(combination);

            // Ajouter la combinaison à la partition courante
            currentCombination.add(combination);

            // Appel récursif pour générer le reste des partitions
            generateAndCalculateCombinations(remainingAgents, partitionSize.subList(1, partitionSize.size()), currentCombination, allCombinations, combinationsValues);

            // Retirer la combinaison de la partition courante avant de tester d'autres
            currentCombination.remove(currentCombination.size() - 1);
        }

        return combinationsValues;
    }

    private HashMap<List<Agent>, Map.Entry<List<List<Agent>>,Integer>> calculateCombination(List<Agent> currentPartition, List<List<Agent>> currentCombination, HashMap<List<Agent>, Map.Entry<List<List<Agent>>,Integer>> combinationsValues){
        int totalValue = 0;
        for (List<Agent> combination: currentCombination){
            System.out.println(combination + " /// " + combinationsValues);
            Map.Entry<List<List<Agent>>,Integer> value = combinationsValues.get(combination);
            if (value != null) {
                totalValue += value.getValue();
            } else {
                totalValue += this.valuePartitions.get(combination);
                System.out.println("Pas de valeurs trouvées pour : " + combination);
            }
        }
        Map.Entry<List<List<Agent>>,Integer> value = combinationsValues.get(currentPartition);
        if (value != null && value.getValue() < totalValue) {
            combinationsValues.replace(currentPartition, new AbstractMap.SimpleEntry<>(currentCombination, totalValue));
        } else {
            combinationsValues.put(currentPartition, new AbstractMap.SimpleEntry<>(currentCombination, totalValue));
        }
        return combinationsValues;
    }

}
