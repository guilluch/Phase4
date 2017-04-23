import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Hello world!
 *
 */
public class Phase3Lift
{
    public static void extraireRegleAssociation(BufferedReader out) throws IOException{
        //Scanner sc = new Scanner(System.in);
        //System.out.println("Veuillez saisir la confiance minimum : ");
        //double minConfiance = sc.nextDouble();
        double minConfiance;
        minConfiance = Double.parseDouble(JOptionPane.showInputDialog(null, "Veuillez saisir la confiance minimum : ", "Confiance", JOptionPane.PLAIN_MESSAGE));
        List<MotifPhase3> lignes;
        PrintWriter sortie = new PrintWriter(new BufferedWriter(new FileWriter("regleAssociation.csv")));// gjhdkj
        try{
            lignes = out.lines().map(MotifPhase3::new).collect(Collectors.toList());
            List<RegleAssoLift> regles = new ArrayList<>();
            for(MotifPhase3 motifY:lignes) {
                for (MotifPhase3 motifX : lignes) {
                    if (motifX.estSousEnsembleDe(motifY) && !motifX.equals(motifY)) {
                        RegleAssoLift regleAsso = new RegleAssoLift(motifX, motifY);
                        if (regleAsso.getConfiance() >= minConfiance && regleAsso.getConfiance() <= 1) {
                            regles.add(regleAsso);
                        }
                    }
                }
            }
            regles.stream().sorted(Comparator.comparingDouble(RegleAssoLift::getLift).reversed()).forEach(sortie::println);

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
