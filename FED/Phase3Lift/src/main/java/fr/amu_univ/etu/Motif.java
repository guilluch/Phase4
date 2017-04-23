package fr.amu_univ.etu;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class Motif {
    Set<Integer> items;
    private static Map<Set<Integer>, Integer> allItemSet = new HashMap();//ffg

    int support;
    public Motif(String ligne){
        String[] result = ligne.split("\\(");
        support = Integer.valueOf(result[1].substring(0, result[1].length()-1));
        items = Arrays.stream(result[0].split(" ")).filter(s -> !s.isEmpty()).map(Integer::valueOf).collect(Collectors.toSet());
        allItemSet.put(items, support);
    }

    public Motif() {
        items = new HashSet<>();
        support = allItemSet.get(items);
    }

    public Set<Integer> getItems() {
        return items;
    }

    public int getSupport() {
        return support;
    }

    public boolean estSousEnsembleDe(Motif m2){
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

        Motif motif = (Motif) o;

        if (support != motif.support) return false;
        return items != null ? items.equals(motif.items) : motif.items == null;
    }

    @Override
    public int hashCode() {
        int result = items != null ? items.hashCode() : 0;
        result = 31 * result + support;
        return result;
    }

    public Motif priveDe(Motif motifXY) {
        Motif result = new Motif();
        result.items.addAll(this.items);
        result.items.removeAll(motifXY.items);
        result.support = allItemSet.get(result.items);
        return result;
    }
}
