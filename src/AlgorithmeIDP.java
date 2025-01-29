import java.util.*;

public class AlgorithmeIDP extends Algorithme {

    private HashMap<List<Agent>, Integer> table = new HashMap<List<Agent>, Integer>();

    public AlgorithmeIDP(HashMap<List<Agent>, Integer> valuePartitions) {
        super(valuePartitions);
    }

    @Override
    public List<List<Agent>> findCoalition() {
        HashMap<List<Agent>, Map.Entry<List<List<Agent>>,Integer>> combinationsValues = calculateAllEntries();
//        for(Map.Entry<List<Agent>, Map.Entry<List<List<Agent>>, Integer>> entry : combinationsValues.entrySet()) {
//            System.out.println(entry.getKey() + "  :  " + entry.getValue());
//        }
        List<Agent> largestKey = null;
        int maxSize = -1;

        for (Map.Entry<List<Agent>, Map.Entry<List<List<Agent>>, Integer>> entry : combinationsValues.entrySet()) {
            List<Agent> key = entry.getKey();
            int keySize = key.size();
            if (keySize > maxSize) {
                largestKey = key;
                maxSize = keySize;
            }
        }
        List<List<Agent>> combination = combinationsValues.get(largestKey).getKey();
        return combination;
    }

    public HashMap<List<Agent>, Map.Entry<List<List<Agent>>,Integer>> calculateAllEntries() {
        List<List<Agent>> sortedEntries = new ArrayList<>(this.valuePartitions.keySet());
        sortedEntries.sort((entry1, entry2) -> Integer.valueOf(entry1.size()).compareTo(Integer.valueOf(entry2.size())));
        HashMap<List<Agent>, Map.Entry<List<List<Agent>>,Integer>> combinationsValues = new HashMap<>();
        for (List<Agent> entry: sortedEntries){
            combinationsValues.put(entry, new AbstractMap.SimpleEntry<>(List.of(entry), this.valuePartitions.get(entry)));
        }
        for (List<Agent> entry: sortedEntries){
            generateAllCombinationsFromEntry(entry, combinationsValues);
        }
        return combinationsValues;
    }

    public void generateAllCombinationsFromEntry(List<Agent> entry, HashMap<List<Agent>, Map.Entry<List<List<Agent>>,Integer>> combinationsValues){
        int entrySize = entry.size();
        List<List<List<Agent>>> listCombinations = new ArrayList<>();
        for (int i = 1; i <= (entrySize / 2); i++) {
            List<Integer> newPartition = new ArrayList<>();
            newPartition.add(i);
            newPartition.add(entrySize - i);
            generateAndCalculateCombinations(entry, entry, newPartition, new ArrayList<>(), listCombinations, combinationsValues);
        }
    }

    // Génères toutes les combinaisons pour une liste d'agents et un partitionnement donné
    protected void generateAndCalculateCombinations(List<Agent> agents, List<Agent> partitionning, List<Integer> partitionSize, List<List<Agent>> currentCombination, List<List<List<Agent>>> allCombinations, HashMap<List<Agent>, Map.Entry<List<List<Agent>>,Integer>> combinationsValues) {
        if (partitionSize.isEmpty()) {
            allCombinations.add(new ArrayList<>(currentCombination));
            calculateCombination(partitionning, currentCombination, combinationsValues);
            return;
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
            generateAndCalculateCombinations(remainingAgents, partitionning, partitionSize.subList(1, partitionSize.size()), currentCombination, allCombinations, combinationsValues);

            // Retirer la combinaison de la partition courante avant de tester d'autres
            currentCombination.remove(currentCombination.size() - 1);
        }
    }

    private void calculateCombination(List<Agent> currentPartition, List<List<Agent>> currentCombination, HashMap<List<Agent>, Map.Entry<List<List<Agent>>,Integer>> combinationsValues){
        int totalValue = 0;
        for (List<Agent> combination: currentCombination){
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
            List<List<Agent>> combi = new ArrayList<>();
            combi.addAll(currentCombination);
            combinationsValues.replace(currentPartition, new AbstractMap.SimpleEntry<>(combi, totalValue));
        }
    }

}
