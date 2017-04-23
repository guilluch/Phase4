package fr.amu_univ.etu;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Hello world!
 *
 */
public class App
{
    public static void extraireRegleAssociation(BufferedReader out) throws IOException{
        Scanner sc = new Scanner(System.in);
        System.out.println("Veuillez saisir la confiance minimum : ");
        double minConfiance = sc.nextDouble();
        List<Motif> lignes;
        PrintWriter sortie = new PrintWriter(new BufferedWriter(new FileWriter("regleAssociation.csv")));// gjhdkj
        try{
            lignes = out.lines().map(Motif::new).collect(Collectors.toList());
            List<RegleAsso> regles = new ArrayList<>();
            for(Motif motifY:lignes) {
                for (Motif motifX : lignes) {
                    if (motifX.estSousEnsembleDe(motifY) && !motifX.equals(motifY)) {
                        RegleAsso regleAsso = new RegleAsso(motifX, motifY);
                        if (regleAsso.getConfiance() >= minConfiance && regleAsso.getConfiance() <= 1) {
                            regles.add(regleAsso);
                        }
                    }
                }
            }
            regles.stream().sorted(Comparator.comparingDouble(RegleAsso::getLift).reversed()).forEach(sortie::println);

        } catch (Exception e){

        }
    }
    public static void main( String[] args ) throws IOException
    {
        try(BufferedReader buff = new BufferedReader(new FileReader("out"))) {
            extraireRegleAssociation(buff);
        }
    }
}
