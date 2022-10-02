package proyectoSucursales.presentation.empleado;

import proyectoSucursales.Application;
import proyectoSucursales.logic.Empleado;
import proyectoSucursales.logic.Service;

import javax.swing.*;
import java.awt.*;

public class Controller {
    View view;
    Model model;

    public Controller(View view, Model model) {
        model.setEmpleado(new Empleado());
        model.setListaSucursales(Service.instance().sucursalesBusqueda(""));

        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
    }

    public void preAgregar(){
        model.setModo(Application.MODO_AGREGAR);
        model.setEmpleado(new Empleado());
        model.commit();
        this.show();
    }

    JDialog dialog;
    public void show(){
        dialog = new JDialog(Application.window,"Empleado", true);
        dialog.setSize(500+340,220+360);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setContentPane(view.getPanel());
        Point location = Application.window.getLocation();
        dialog.setLocation( location.x+400,location.y+100);
        dialog.setVisible(true);
    }

    public void hide(){
        dialog.dispose();
    }

    public void show1(){
        Application.window.setContentPane(view.getPanel());
        Application.window.revalidate();
    }
    public void hide1(){
        Application.mainController.show();
    }

    public void guardar(Empleado empleado) throws Exception {
        switch (model.getModo()) {
            case Application.MODO_AGREGAR:
                Service.instance().empleadoAdd(empleado);
                model.setEmpleado(new Empleado());
                break;
            case Application.MODO_EDITAR:
                Service.instance().empleadoUpdate(empleado);
                model.setEmpleado(empleado);
                break;
        }
        Service.instance().store();
        Application.empleadosController.buscar("");
        Application.sucursalesController.buscar("");
        model.commit();
    }

    public void editar(Empleado empleado){
        model.setModo(Application.MODO_EDITAR);
        model.setEmpleado(empleado);
        model.commit();
        this.show();
    }
    public void refresh(){
        model.setListaSucursales(Service.instance().sucursalesBusqueda(""));
    }
    public int getSucursalIndex(String cod) throws Exception {
        return Service.instance().sucursalesBusqueda("").indexOf(Service.instance().sucursalGet(cod));
    }
}