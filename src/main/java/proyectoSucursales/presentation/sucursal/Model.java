package proyectoSucursales.presentation.sucursal;

import proyectoSucursales.logic.Sucursal;

public class Model extends java.util.Observable{
    Sucursal sucursal;
    int modo;

    public Model() {
    }

    public int getModo() {
        return modo;
    }

    public void setModo(int modo) {
        this.modo = modo;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    @Override
    public void addObserver(java.util.Observer o) {
        super.addObserver(o);
        this.commit();
    }

    public void commit(){
        setChanged();
        notifyObservers(null);
    }
}

