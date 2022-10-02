package proyectoSucursales.presentation.empleados;

import proyectoSucursales.logic.Empleado;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableModel extends AbstractTableModel implements javax.swing.table.TableModel {
    List<Empleado> list;
    int[] columns;

    public TableModel(int[] cols, List<Empleado> rows){
        initColNames();
        this.columns =cols;
        this.list =rows;
    }

    public int getColumnCount() {
        return columns.length;
    }

    public String getColumnName(int col){
        return colNames[columns[col]];
    }

    public Class<?> getColumnClass(int col){
        switch (columns[col]){
            default: return super.getColumnClass(col);
        }
    }

    public int getRowCount() {
        return list.size();
    }

    public Object getValueAt(int row, int col) {
        Empleado empleado = list.get(row);
        switch (columns[col]){
            case CEDULA: return empleado.getCedula();
            case NOMBRE: return empleado.getNombre();
            case TELEFONO: return empleado.getTelefono();
            case SALARIOBASE: return empleado.getSalarioBase();
            case SUCURSAL: return empleado.getSucursal().getReferencia();
            case ZONAJE: return empleado.getSucursal().getZonaje();
            case SALARIOTOTAL: return empleado.calculaSalarioTotal(empleado.getSucursal().getZonaje());
            default: return "";
        }
    }

    public static final int CEDULA=0;
    public static final int NOMBRE=1;
    public static final int TELEFONO=2;
    public static final int SALARIOBASE=3;
    public static final int SUCURSAL=4;
    public static final int ZONAJE=5;
    public static final int SALARIOTOTAL=6;

    String[] colNames = new String[7];
    private void initColNames(){
        colNames[CEDULA]= "Cedula";
        colNames[NOMBRE]= "Nombre";
        colNames[TELEFONO]= "Telefono";
        colNames[SALARIOBASE]= "Salario Base";
        colNames[SUCURSAL]= "Sucursal";
        colNames[ZONAJE]= "%Zonaje";
        colNames[SALARIOTOTAL]= "Sal.Total";
    }

}
