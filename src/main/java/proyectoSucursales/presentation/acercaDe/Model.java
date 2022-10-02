package proyectoSucursales.presentation.acercaDe;

import java.util.Observable;

public class Model extends Observable{

    public Model() {

    }

    public void commit(){
        setChanged();
        notifyObservers(null);
    }

    @Override
    public void addObserver(java.util.Observer o) {
        super.addObserver(o);
        commit();
    }
}
