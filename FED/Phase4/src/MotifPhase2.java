import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by p14001575 on 03/04/17.
 */
public class MotifPhase2 {
    Set<Integer> items;
    int support;
    public MotifPhase2(String ligne){
        String[] result = ligne.split("\\(");
        support = Integer.valueOf(result[1].substring(0, result[1].length()-1));
        items = Arrays.stream(result[0].split(" ")).filter(s -> !s.isEmpty()).map(Integer::valueOf).collect(Collectors.toSet());
    }

    public MotifPhase2() {
        items = new HashSet<>();
    }

    public Set<Integer> getItems() {
        return items;
    }

    public int getSupport() {
        return support;
    }

    public boolean estSousEnsembleDe(MotifPhase2 m2){
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

        MotifPhase2 motif = (MotifPhase2) o;

        if (support != motif.support) return false;
        return items != null ? items.equals(motif.items) : motif.items == null;
    }

    @Override
    public int hashCode() {
        int result = items != null ? items.hashCode() : 0;
        result = 31 * result + support;
        return result;
    }

    public MotifPhase2 priveDe(MotifPhase2 motifXY)
    {
        MotifPhase2 result = new MotifPhase2();
        result.items.addAll(this.items);
        result.items.removeAll(motifXY.items);
        return result;

    }
}
