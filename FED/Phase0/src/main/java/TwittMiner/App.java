package TwittMiner;

import twitter4j.*;

import java.io.BufferedWriter;
import java.io.FileWriter;// filewriter
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) throws TwitterException, IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Veuillez saisir un sujet :");
        String str = sc.nextLine();
        String insertion = " ";
        Twitter twitter = new TwitterFactory().getInstance();
        try (PrintWriter out = new PrintWriter( new BufferedWriter(new FileWriter("texte.csv")))){
            Query query = new Query(str);
            QueryResult result;
            do {
                result = twitter.search(query);
                List<Status> tweets = result.getTweets();
                //ENLEVER LES RETOURS CHARIOTS ET TAB
                tweets.stream().map((Status tweet) -> {
                    String chaineAlpha = Arrays.stream(tweet.getText().replaceAll("[;\"]", " ").split("[\\s]+")).map(s -> "\"" + s + "\"").sorted(String::compareToIgnoreCase).collect(Collectors.joining("; "));
                    String retour = "";
                    if(!tweet.getUser().getLocation().isEmpty()){
                        retour += "\"" +tweet.getUser().getLocation() + "\";";
                    }
                    if(tweet.getCreatedAt() != null){
                        retour += "\"" +tweet.getCreatedAt() + "\";";
                    }
                    if(tweet.getUser().getScreenName()!= null){
                        retour += "\"@" + tweet.getUser().getScreenName() + "\";";
                    }
                    if(!chaineAlpha.isEmpty()){
                        retour += chaineAlpha;
                    }
                    return retour;
                }).forEach(out::println);
            } while ((query = result.nextQuery()) != null);
            System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
        }
    }
} //App



