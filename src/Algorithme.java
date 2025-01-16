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

    public List<List<Integer>> listPartitions() {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> currentPartition = new ArrayList<>();
        int maxSize = getMaxListSize();
        generatePartitions(maxSize, 1, currentPartition, result);
        return result;
    }

    private void generatePartitions(int n, int start, List<Integer> currentPartition, List<List<Integer>> result) {
        if (n == 0) {
            // Si la somme est atteinte, ajouter la partition
            result.add(new ArrayList<>(currentPartition));
            return;
        }
        for (int i = start; i <= n; i++) {
            currentPartition.add(i); // Ajouter le nombre courant à la partition
            generatePartitions(n - i, i, currentPartition, result); // Appel récursif avec la réduction de n
            currentPartition.remove(currentPartition.size() - 1); // Enlever le dernier élément pour tester une autre partition
        }
    }

    public abstract void findCoalition();

}
