package proyectoSucursales;

import proyectoSucursales.presentation.sucursales.Controller;
import proyectoSucursales.presentation.sucursales.Model;
import proyectoSucursales.presentation.sucursales.View;


import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Application {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");}
        catch (Exception ex) {};

        Model sucursalesModel= new Model();
        View sucursalesView = new View();
        sucursalesController = new Controller(sucursalesView,sucursalesModel);

        proyectoSucursales.presentation.empleados.Model empleadosModel= new proyectoSucursales.presentation.empleados.Model();
        proyectoSucursales.presentation.empleados.View empleadosView = new proyectoSucursales.presentation.empleados.View();
        empleadosController = new proyectoSucursales.presentation.empleados.Controller(empleadosView,empleadosModel);


        proyectoSucursales.presentation.acercaDe.Model aboutModel = new proyectoSucursales.presentation.acercaDe.Model();
        proyectoSucursales.presentation.acercaDe.View aboutView = new proyectoSucursales.presentation.acercaDe.View();
        aboutController = new proyectoSucursales.presentation.acercaDe.Controller(aboutView,aboutModel);

        proyectoSucursales.presentation.empleado.Model empleadoModel= new proyectoSucursales.presentation.empleado.Model();
        proyectoSucursales.presentation.empleado.View empleadoView = new proyectoSucursales.presentation.empleado.View();
        empleadoController = new proyectoSucursales.presentation.empleado.Controller(empleadoView,empleadoModel);


        proyectoSucursales.presentation.sucursal.Model sucursalModel= new proyectoSucursales.presentation.sucursal.Model();
        proyectoSucursales.presentation.sucursal.View sucursalView = new proyectoSucursales.presentation.sucursal.View();
        sucursalController = new proyectoSucursales.presentation.sucursal.Controller(sucursalView,sucursalModel);

        proyectoSucursales.presentation.main.Model mainModel= new proyectoSucursales.presentation.main.Model();
        proyectoSucursales.presentation.main.View mainView = new proyectoSucursales.presentation.main.View();
        mainController = new proyectoSucursales.presentation.main.Controller(mainView, mainModel);

        mainView.getPanel().add("Empleados",empleadosView.getPanel());
        mainView.getPanel().add("Sucursales",sucursalesView.getPanel());
        mainView.getPanel().add("Acerca de",aboutView.getPanel());

        window = new JFrame();
        window.setSize(400,300);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setTitle("SISE: Sistema de Sucursales y Empleados");
        window.setVisible(true);
        aboutController.show();
        mainController.show();
    }

    public static Controller sucursalesController;
    public static proyectoSucursales.presentation.empleados.Controller empleadosController;

    public static proyectoSucursales.presentation.empleado.Controller empleadoController;

    public static proyectoSucursales.presentation.acercaDe.Controller aboutController;

    public static proyectoSucursales.presentation.sucursal.Controller sucursalController;

    public static proyectoSucursales.presentation.main.Controller mainController;

    public static JFrame window;

    public static  final int  MODO_AGREGAR=0;
    public static final int MODO_EDITAR=1;

    public static Border BORDER_ERROR = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.RED);

}

