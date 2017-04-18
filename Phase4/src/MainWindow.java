import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainWindow extends JFrame {
    private JTable regles;
    private String[] lines;
    private String[][] splittedLines;
    private JTextField searchField = new JTextField();
    private String[][] data;
    private String[] titles = {"X", "Occurences", "Y", "Je sais pas", "Lift"};
    private MyModel tableModel;
    private boolean ascending = true;
    private int col;

    public MainWindow() {
        super();
        setMinimumSize(new Dimension(800, 600));
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        //setResizable(false);
        setTitle("Règles d'associations");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Traitement
        readFile("regleDassoTextuelCourt.txt");
        splitLines();

        //Bouton et son action
        JButton searchButton = new JButton("Rechercher");
        searchButton.addActionListener(e -> {
            data = searchStr(searchField.getText());
            tableModel.setData(data);
            //regles.repaint();
        });
        searchField.setColumns(20);
        JPanel searchBar = new JPanel();
        searchBar.setLayout(new FlowLayout());
        searchBar.add(searchField);
        searchBar.add(searchButton);

        //Tableau des regles d'associations
        data = splittedLines;
        tableModel = new MyModel(data, titles);
        regles = new JTable(data, titles);
        regles.setModel(tableModel);
        //resizeColumnWidth(regles);


        //JPanel sortButtonsPanel = new JPanel();
        /*JButton sortByLiftButton = new JButton("Trier par Lift");
        sortByLiftButton.addActionListener(e -> {
            java.util.Arrays.sort(data, (a, b) -> {
                int cmp = a[4].compareTo(b[4]);
                return ascending ? cmp : -cmp;
            });
            ascending = !ascending;
            tableModel.setData(data);
        });*/
        //sortButtonsPanel.setLayout(new FlowLayout());
        //sortButtonsPanel.add(searchButton);
        //sortButtonsPanel.add(sortByLiftButton);

        //Trie par colonne en cliquant sur le titre de la colonne
        regles.getTableHeader().setReorderingAllowed(false);
        regles.getTableHeader().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (col != regles.getTableHeader().columnAtPoint(e.getPoint())) {
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

        //Menu
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Fichier");
        JMenuItem menuItem = new JMenuItem("Quitter");
        menuItem.addActionListener(e -> {
            //dispose();
            System.exit(0);
        });
        menu.add(menuItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        setLayout(new BorderLayout());
        add(new JScrollPane(regles), BorderLayout.CENTER);
        add(searchBar, BorderLayout.NORTH);
        //add(sortButtonsPanel, BorderLayout.SOUTH);

        setVisible(true);
    }


    public String readFile(String filePath) {
        String str = "";
        //lecture du fichier texte
        try{
            InputStream ips = new FileInputStream(filePath);
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);
            String ligne;
            while ((ligne = br.readLine()) != null){
                //System.out.println(ligne);
                str += ligne + "\n";
            }
            br.close();
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
        lines = str.split("\n");
        //System.out.println(lines.length);
        return str;
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
        foundLines = new Object[occurences][5];
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
        foundLines = new String[occurences][5];
        for (int i = 0 ; i < occurences ; ++i) {
            foundLines[i] = splittedLines[indices.get(i)];
        }
        return foundLines;
    }


    public void splitLines() {
        splittedLines = new String[lines.length][5];
        for (int i = 0 ; i < lines.length ; ++i) {
            lines[i] = lines[i].replaceAll(" : ", "\r");
            lines[i] = lines[i].replaceAll(" -> ", "\r");
            splittedLines[i] = lines[i].split("\r");
        }
    }


    /*private void resizeColumnWidth(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 15; // Min width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width +1 , width);
            }
            if(width > 300)
                width = 300;
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }*/


    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();
        PleaseWaitWindow pleaseWaitWindow = new PleaseWaitWindow("");
    }
}
