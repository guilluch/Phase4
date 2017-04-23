import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;

public class MainWindow extends JFrame {
    private JPanel content;
    private JTable regles;
    private String[] lines;
    private String[][] splittedLines = new String[0][0];
    private JPanel searchBar;
    private JTextField searchField;
    private String[][] data;
    private String[] titles = {"X", "Occurences de X", "Y", "Occurences de Y", "Fréquence de Y privé de X", "Lift"};
    private MyModel tableModel;
    private boolean ascending = true;
    private int col;
    private int page = 1;

    public MainWindow() {
        super();
        setMinimumSize(new Dimension(800, 600));
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        //setResizable(false);
        setTitle("Règles d'associations");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        content = new JPanel();

        content = getContent("regleDassoTextuel.txt");

        creerMenu();

        setLayout(new BorderLayout());
        setContentPane(content);
        getContentPane().setVisible(false);
        //add(new JScrollPane(regles), BorderLayout.CENTER);
        //add(searchBar, BorderLayout.NORTH);

        setVisible(true);
    }


    public void creerMenu() {
        //Menu
        JMenuBar menuBar = new JMenuBar();
        JMenu fichierMenu = new JMenu("Fichier");
        JMenu executerMenu = new JMenu("Exécuter");
        JMenuItem openMenuItem = new JMenuItem("Ouvrir");
        JMenuItem quitterMenuItem = new JMenuItem("Quitter");
        JMenuItem phase1MenuItem = new JMenuItem("Csv vers Trans");
        JMenuItem aprioriMenuItem = new JMenuItem("Apriori");
        JMenuItem phase2MenuItem = new JMenuItem("Phase 2");
        JMenuItem motssInutilesMenuItem = new JMenuItem("Supprimer les mots inutiles");
        JMenuItem phase3MenuItem = new JMenuItem("Phase 3 Lift");
        JMenuItem traductionMenuItem = new JMenuItem("Traduire les règles d'associations");

        openMenuItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                //System.out.println("Selected file: " + selectedFile.getPath());
                traitement(selectedFile.getAbsolutePath());
                data = splittedLines;
                tableModel.setData(data);
                getContentPane().setVisible(true);

            }
        });
        quitterMenuItem.addActionListener(e -> {
            //dispose();
            System.exit(0);
        });
        phase1MenuItem.addActionListener(e -> {
            String filePath = ouvrirFichier();
            if (filePath != null) {
                try {
                    ProcessBuilder builder = new ProcessBuilder("bin/CsvToTrans.exe", filePath);
                    builder.redirectErrorStream(true);
                    Process process = builder.start();
                    System.out.println(convertStreamToString(process.getInputStream()));
                    process.waitFor();
                    JOptionPane.showMessageDialog(null, "Terminé");
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        });
        aprioriMenuItem.addActionListener(e -> {
            String filePath = ouvrirFichier();
            if (filePath != null) {
                try {
                    ProcessBuilder builder = new ProcessBuilder("bin/apriori.exe", filePath, JOptionPane.showInputDialog(null, "Veuillez saisir le support minimum : ", "Confiance", JOptionPane.PLAIN_MESSAGE), "../apriori.out");
                    builder.redirectErrorStream(true);
                    Process process = builder.start();
                    System.out.println(convertStreamToString(process.getInputStream()));
                    process.waitFor();
                    JOptionPane.showMessageDialog(null, "Terminé");
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        });
        phase2MenuItem.addActionListener(e -> {
            String filePath = ouvrirFichier();
            if (filePath != null) {
                try (BufferedReader buff = new BufferedReader(new FileReader(filePath))) {
                    Phase2.extraireRegleAssociation(buff);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                System.out.println("Phase 2 terminée sans échec");
            }
            JOptionPane.showMessageDialog(null, "Terminé");
        });
        motssInutilesMenuItem.addActionListener(e -> {
            String filePath = ouvrirFichier();
            try (BufferedReader buff = new BufferedReader(new FileReader(filePath))) {
                Phase3.supprimerMotsInutile(buff);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        phase3MenuItem.addActionListener(e -> {
            String filePath = ouvrirFichier();
            if (filePath != null) {
                try (BufferedReader buff = new BufferedReader(new FileReader(filePath))) {
                    Phase3Lift.extraireRegleAssociation(buff);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                System.out.println("Phase 3 terminée sans échec");
            }
            JOptionPane.showMessageDialog(null, "Terminé");
        });
        traductionMenuItem.addActionListener(e -> {
            String filePath = ouvrirFichier();
            if (filePath != null) {
                try {
                    ProcessBuilder builder = new ProcessBuilder("bin/TransToCsv.exe", filePath);
                    builder.redirectErrorStream(true);
                    Process process = builder.start();
                    System.out.println(convertStreamToString(process.getInputStream()));
                    process.waitFor();
                    JOptionPane.showMessageDialog(null, "Terminé");
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        });

        fichierMenu.add(openMenuItem);
        fichierMenu.add(quitterMenuItem);
        executerMenu.add(phase1MenuItem);
        executerMenu.add(aprioriMenuItem);
        executerMenu.add(phase2MenuItem);
        executerMenu.add(motssInutilesMenuItem);
        executerMenu.add(phase3MenuItem);
        executerMenu.add(traductionMenuItem);
        menuBar.add(fichierMenu);
        menuBar.add(executerMenu);
        setJMenuBar(menuBar);
    }


    public static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }


    public void traitement(String str) {
        //Traitement
        readFile(str);
        splitLines();
    }


    public void creerBarreDeRecherche() {
        //Bouton et son action
        JButton searchButton = new JButton("Rechercher");
        searchButton.addActionListener(e -> {
            data = searchStr(searchField.getText());
            tableModel.setData(data);
            //System.out.println("Caca");
        });
        searchField = new JTextField();
        searchField.setColumns(20);
        searchBar = new JPanel();
        searchBar.setLayout(new FlowLayout());
        searchBar.add(searchField);
        searchBar.add(searchButton);
    }


    public void creerTableau() {
        //Tableau des regles d'associations
        data = splittedLines;
        tableModel = new MyModel(data, titles);
        regles = new JTable(/*data, titles*/);
        regles.setModel(tableModel);
        //System.out.println(regles);

        //Trie par colonne en cliquant sur le titre de la colonne
        regles.getTableHeader().setReorderingAllowed(false);
        regles.getTableHeader().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (col != regles.getTableHeader().columnAtPoint(e.getPoint())) {
                    //System.out.println(regles.getTableHeader().columnAtPoint(e.getPoint()));
                    ascending = true;
                }
                col = regles.getTableHeader().columnAtPoint(e.getPoint());
                java.util.Arrays.sort(data, (a, b) -> {
                    int cmp = a[col].compareTo(b[col]);
                    return ascending ? cmp : -cmp;
                });
                ascending = !ascending;
                tableModel.setData(data);
            }
        });
    }


    public JPanel creerBoutonsPages() {
        JPanel panel = new JPanel();

        JButton boutonPrecedent = new JButton("Précédent");
        JButton boutonSuivant = new JButton("Suivant");
        boutonPrecedent.setEnabled(false);

        boutonPrecedent.addActionListener(e -> {
            if (page > 1) {
                --page;
            }
            if (page == 1) {
                boutonPrecedent.setEnabled(false);
            }
            System.out.println(page);
        });
        boutonSuivant.addActionListener(e -> {
            ++page;
            tableModel.changePage(page);

            boutonPrecedent.setEnabled(true);
            System.out.println(page);
        });

        panel.add(boutonPrecedent);
        panel.add(boutonSuivant);

        return panel;
    }


    public String ouvrirFichier() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            return selectedFile.getAbsolutePath();
        }
        return null;
    }


    public JPanel getContent(String str) {
        JPanel content = new JPanel();

        //traitement(str);

        creerTableau();

        creerBarreDeRecherche();

        content.setLayout(new BorderLayout());
        content.add(searchBar, BorderLayout.NORTH);
        content.add(new JScrollPane(regles), BorderLayout.CENTER);
        //content.add(creerBoutonsPages(), BorderLayout.SOUTH);

        return content;
    }


    public String readFile(String filePath) {
        StringBuilder str = new StringBuilder();
        //lecture du fichier texte
        try{
            InputStream ips = new FileInputStream(filePath);
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);
            String ligne;
            while ((ligne = br.readLine()) != null){
                //System.out.println(ligne);
                str.append(ligne).append("\n");
            }
            br.close();
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
        lines = str.toString().split("\n");
        //System.out.println(lines.length);
        return str.toString();
    }


    public Object[][] search(String str) {
        Object[][] foundLines;
        String[] recherche = str.split(" ");
        ArrayList <Integer> indices = new ArrayList<>();
        int occurences = 0;
        for (int i = 0 ; i < splittedLines.length ; ++i) {
            if (splittedLines[i][0].toLowerCase().contains(str.toLowerCase())) {
                ++occurences;
                indices.add(i);
                //System.out.println(i);
            }
        }
        foundLines = new Object[occurences][6];
        for (int i = 0 ; i < occurences ; ++i) {
            foundLines[i] = splittedLines[indices.get(i)];
            //System.out.println(foundLines[i][0]);
            //System.out.println(indices.get(i));
        }
        System.out.println(str);
        return foundLines;
    }


    public String[][] searchStr(String str) {
        String[][] foundLines;
        String[] recherche = str.split(" ");
        boolean found = true;
        ArrayList <Integer> indices = new ArrayList<>();
        int occurences = 0;
        for (int i = 0 ; i < splittedLines.length ; ++i) {
            for (int j = 0 ; j < recherche.length ; ++j) {
                if (!splittedLines[i][0].contains(recherche[j].toLowerCase())) {
                    found = false;
                    break;
                }
            }
            if (found) {
                ++occurences;
                indices.add(i);
            }
            found = true;
        }
        foundLines = new String[occurences][6];
        for (int i = 0 ; i < occurences ; ++i) {
            foundLines[i] = splittedLines[indices.get(i)];
        }
        return foundLines;
    }


    public void splitLines() {
        splittedLines = new String[lines.length][6];
        for (int i = 0 ; i < lines.length ; ++i) {
            lines[i] = lines[i].replaceAll(" : ", "\n");
            lines[i] = lines[i].replaceAll(" => ", "\n");
            splittedLines[i] = lines[i].split("\n");
            //System.out.println(splittedLines[0].length);
        }
    }


    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();
    }
}
