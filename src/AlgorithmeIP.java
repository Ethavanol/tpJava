import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class AlgorithmeIP extends Algorithme {


    public AlgorithmeIP(HashMap<List<Agent>, Integer> valuePartitions) {
        super(valuePartitions);
    }

    @Override
    public void findCoalition() {
        HashMap<List<Integer>, Integer> hashMap = getListPartitionsWithMax();
        int max = Collections.max(hashMap.values());
        System.out.println(max);
    }

    public HashMap<Integer, Integer> getMaxForIndexes() {
        HashMap<Integer, Integer> hashMapMax = new HashMap<>();
        valuePartitions.forEach((agents, value) -> {
            int size = agents.size();  // Taille de la liste d'agents
            hashMapMax.merge(size, value, Integer::max);  // Mettre à jour avec la valeur maximale
        });
        return hashMapMax;
    }


    public HashMap<List<Integer>, Integer> getListPartitionsWithMax() {
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
}
