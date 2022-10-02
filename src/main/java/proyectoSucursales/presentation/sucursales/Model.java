package proyectoSucursales.presentation.sucursales;

import proyectoSucursales.logic.Sucursal;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

public class Model extends java.util.Observable{
    List<Sucursal> listaSucursales;

    public Model() {
        this.setListaSucursales(new ArrayList<Sucursal>());
    }

    public void setListaSucursales(List<Sucursal> listaSucursales){
        this.listaSucursales = listaSucursales;
    }

    public List<Sucursal> getListaSucursales() {
        return listaSucursales;
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
