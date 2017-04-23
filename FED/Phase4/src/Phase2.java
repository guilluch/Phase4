import javax.swing.*;
import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Phase2
{
    public static void extraireRegleAssociation(BufferedReader out) throws IOException{
        //Scanner sc = new Scanner(System.in);
        //System.out.println("Veuillez saisir la confiance minimum : ");
        //double minConfiance = sc.nextDouble();
        double minConfiance;
        minConfiance = Double.parseDouble(JOptionPane.showInputDialog(null, "Veuillez saisir la confiance minimum : ", "Confiance", JOptionPane.PLAIN_MESSAGE));
        List<MotifPhase2> lignes;
        PrintWriter sortie = new PrintWriter(new BufferedWriter(new FileWriter("regleAssociation.csv")));
        try{
            lignes = out.lines().map(MotifPhase2::new).collect(Collectors.toList());
            for(MotifPhase2 motifY:lignes) {
                for (MotifPhase2 motifX : lignes) {
                    if (motifX.estSousEnsembleDe(motifY) && !motifX.equals(motifY)) {
                        double confiance = motifY.getSupport() / (motifX.getSupport() * 1.0d);
                        if (confiance >= minConfiance) {
                            MotifPhase2 XMinY = motifY.priveDe(motifX);
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
