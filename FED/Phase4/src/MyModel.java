import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

public class MyModel extends AbstractTableModel {
    private static final long serialVersionUID = 8083956857937064934L;

    private String[] titles = {"X", "Occurences", "Y", "Fréquence de Y privé de X", "Lift"};
    private String[][] data;
    private String[][] dataPage;

    public MyModel(String[][] data, String[] titles) {
        super();
        this.data = data;
        this.titles = titles;
        dataPage = new String[10][5];
        changePage(1);
    }

    public String getValueAt(int arg0, int arg1) {
        return data[arg0][arg1];
    }

    public void setHeader(String[] newHeaders){
        titles = newHeaders;
    }

    public int getColumnCount() {
        return this.titles.length;
    }

    public String getColumnName(int column) {
        return this.titles[column];
    }

    public int getRowCount() {
        return this.data.length;
    }

    public boolean isCellEditable(int row, int column) {
        // Aucune cellule éditable
        return false;
    }

    //Fonction qui va s'occuper de remettre à jour tout mon tableau et qui va mettre
    //à jour aussi l'affichage
    public void setData(String[][] newData){
        data = newData;
        //changePage(1);
        super.fireTableDataChanged();
    }

    //Nouvelles fonctions de pagination
    public String[][] getDataPage() {
        return dataPage;
    }

    public void changePage(int page) {
        for (int i = 10*(page-1), j = 0 ; i < 10*page ; ++i , ++j) {
            try {
                dataPage[j] = data[i];
            } catch (Exception e) {
                break;
            }
        }
    }
}
