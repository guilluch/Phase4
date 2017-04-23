package fr.amu_univ.etu;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class App
{
    public static void main( String[] args )
    {
        try {
            List<String> motsInutiles = new ArrayList<>();
            BufferedReader buff = new BufferedReader(new FileReader("motsinutiles.txt"));
            motsInutiles = buff.lines().collect(Collectors.toList());
            BufferedReader buf = new BufferedReader(new FileReader("science-en.csv"));
            BufferedWriter writer = new BufferedWriter(new FileWriter("clean"));
            List<String> mots = new ArrayList<>();
            String line;
            while((line = buf.readLine()) != null){
                for(String str : line.split(";")){
                    str = str.replaceAll("\"", "");
                    if(!motsInutiles.contains(str.toLowerCase())) {
                        System.out.println(str);
                        writer.write(str +  "\n");
                        mots.add(str);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}