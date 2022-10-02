package proyectoSucursales.presentation.main;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

public class View implements Observer {
    private JTabbedPane panelTable;
    private JPanel panel1;

    public JTabbedPane getPanel() {
        return panelTable;
    }

    Controller controller;
    Model model;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setModel(Model model) {
        this.model = model;
        model.addObserver(this);
    }

    @Override
    public void update(Observable updatedModel, Object parametros) {

    }

}
