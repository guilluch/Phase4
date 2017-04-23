package fr.amu_univ.etu;

import java.io.*;
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
        PrintWriter sortie = new PrintWriter(new BufferedWriter(new FileWriter("regleAssociation.csv")));
        try{
            lignes = out.lines().map(Motif::new).collect(Collectors.toList());
            for(Motif motifY:lignes) {
                for (Motif motifX : lignes) {
                    if (motifX.estSousEnsembleDe(motifY) && !motifX.equals(motifY)) {
                        double confiance = motifY.getSupport() / (motifX.getSupport() * 1.0d);
                        if (confiance >= minConfiance) {
                            Motif XMinY = motifY.priveDe(motifX);
                            System.out.println(motifX + " -> " + XMinY + " : " + confiance);
                            sortie.write(motifX + " -> " + XMinY + " : " + confiance + '\n');
                        }
                    }
                }
            }
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
