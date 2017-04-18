import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

public class MyModel extends AbstractTableModel {
    private static final long serialVersionUID = 8083956857937064934L;

    private String[] titles = {"X", "Occurences", "Y", "Je sais pas", "Lift"};
    private Object[][] data = new Object[0][0];

    public MyModel(Object[][] data, String[] titles) {
        super();
        this.data = data;
        this.titles = titles;
    }

    public Object getValueAt(int arg0, int arg1) {

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

    //Fonction qui va s'occupper de remettre à jour tout mon tableau et qui va mettre
    //à jour aussi l'affichage
    public void setData(Object[][] newData){
        data = newData;
        super.fireTableDataChanged();
    }
}
