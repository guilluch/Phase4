import java.util.*;
import java.util.stream.Collectors;

public class MotifPhase3 {
    Set<Integer> items;
    private static Map<Set<Integer>, Integer> allItemSet = new HashMap();//ffg

    int support;
    public MotifPhase3(String ligne){
        String[] result = ligne.split("\\(");
        support = Integer.valueOf(result[1].substring(0, result[1].length()-1));
        items = Arrays.stream(result[0].split(" ")).filter(s -> !s.isEmpty()).map(Integer::valueOf).collect(Collectors.toSet());
        allItemSet.put(items, support);
    }

    public MotifPhase3() {
        items = new HashSet<>();
        support = allItemSet.get(items);
    }

    public Set<Integer> getItems() {
        return items;
    }

    public int getSupport() {
        return support;
    }

    public boolean estSousEnsembleDe(MotifPhase3 m2){
        return m2.items.containsAll(this.items);
    }

    @Override
    public String toString() {
        return  items + " : " + support;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MotifPhase3 motif = (MotifPhase3) o;

        if (support != motif.support) return false;
        return items != null ? items.equals(motif.items) : motif.items == null;
    }

    @Override
    public int hashCode() {
        int result = items != null ? items.hashCode() : 0;
        result = 31 * result + support;
        return result;
    }

    public MotifPhase3 priveDe(MotifPhase3 motifXY) {
        MotifPhase3 result = new MotifPhase3();
        result.items.addAll(this.items);
        result.items.removeAll(motifXY.items);
        result.support = allItemSet.get(result.items);
        return result;
    }
}
