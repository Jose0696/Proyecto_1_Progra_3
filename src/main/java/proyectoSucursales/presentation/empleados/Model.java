package proyectoSucursales.presentation.empleados;

import proyectoSucursales.logic.Empleado;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

public class Model extends java.util.Observable{
    List<Empleado> ListaEmpleados;

    public Model() {
        this.setListaEmpleados(new ArrayList<Empleado>());
    }

    public void setListaEmpleados(List<Empleado> listaEmpleados){
        this.ListaEmpleados = listaEmpleados;
    }

    public List<Empleado> getListaEmpleados() {
        return ListaEmpleados;
    }

    @Override
    public void addObserver(Observer o) {
        super.addObserver(o);
        commit();
    }

    public void commit(){
        setChanged();
        notifyObservers(null);
    }
}
