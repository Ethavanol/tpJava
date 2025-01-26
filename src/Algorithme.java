import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Algorithme {

    public HashMap<List<Agent>, Integer> valuePartitions;

    public Algorithme(HashMap<List<Agent>, Integer> valuePartitions){
        this.valuePartitions = valuePartitions;
    }

    public int getMaxListSize() {
        int maxSize = 0;
        for (List<Agent> agents : this.valuePartitions.keySet()) {
            maxSize = Math.max(maxSize, agents.size());
        }
        return maxSize;
    }


    public List<Agent> getAgentList() {
        List<Agent> longestList = null;

        // Parcourir toutes les entrées du HashMap
        for (HashMap.Entry<List<Agent>, Integer> entry : this.valuePartitions.entrySet()) {
            List<Agent> currentList = entry.getKey();
            if (longestList == null || currentList.size() > longestList.size()) {
                longestList = currentList;
            }
        }
        return longestList;
    }

    // Retournes la liste des formats de partitionnements possibles:
    // [1,1,1,1], [1,1,2], [1,3], [2,2], [4]
    public List<List<Integer>> listPartitions() {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> currentPartition = new ArrayList<>();
        int maxSize = getMaxListSize();
        generatePartitionnings(maxSize, 1, currentPartition, result);
        return result;
    }

    // Génères les formats de partionnements possibles
    private void generatePartitionnings(int n, int start, List<Integer> currentPartition, List<List<Integer>> result) {
        if (n == 0) {
            // Si la somme est atteinte, ajouter la partition
            result.add(new ArrayList<>(currentPartition));
            return;
        }
        for (int i = start; i <= n; i++) {
            currentPartition.add(i); // Ajouter le nombre courant à la partition
            generatePartitionnings(n - i, i, currentPartition, result); // Appel récursif avec la réduction de n
            currentPartition.remove(currentPartition.size() - 1); // Enlever le dernier élément pour tester une autre partition
        }
    }

    // Génères toutes les combinaison pour une liste d'agents et un partitionnement donné
    protected void generateCombinations(List<Agent> agents, List<Integer> partitionSize, List<List<Agent>> currentPartition, List<List<List<Agent>>> allCombinations) {
        if (partitionSize.isEmpty()) {
            allCombinations.add(new ArrayList<>(currentPartition));
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
            generateCombinations(remainingAgents, partitionSize.subList(1, partitionSize.size()), currentPartition, allCombinations);

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

    public abstract void findCoalition();

}
