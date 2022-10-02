package proyectoSucursales.presentation.sucursales;

import proyectoSucursales.logic.Sucursal;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableModel extends AbstractTableModel implements javax.swing.table.TableModel {
    List<Sucursal> list;
    int[] cols;

    public TableModel(int[] cols, List<Sucursal> rows){
        initColNames();
        this.cols=cols;
        this.list =rows;
    }

    public int getColumnCount() {
        return cols.length;
    }

    public String getColumnName(int col){
        return colNames[cols[col]];
    }

    public Class<?> getColumnClass(int col){
        switch (cols[col]){
            default: return super.getColumnClass(col);
        }
    }

    public int getRowCount() {
        return list.size();
    }

    public Object getValueAt(int row, int col) {
        Sucursal sucursal = list.get(row);
        switch (cols[col]){
            case CODIGO: return sucursal.getCodigo();
            case NOMBRE: return sucursal.getReferencia();
            case DIRECCION: return sucursal.getDireccion();
            case ZONAJE: return sucursal.getZonaje();
            default: return "";
        }
    }

    public static final int CODIGO=0;
    public static final int NOMBRE=1;
    public static final int DIRECCION=2;
    public static final int ZONAJE=3;

    String[] colNames = new String[4];
    private void initColNames(){
        colNames[CODIGO]= "Código";
        colNames[NOMBRE]= "Referencia";
        colNames[DIRECCION]= "Ubicación";
        colNames[ZONAJE]= "%Zonaje";
    }

}
