package proyectoSucursales.presentation.empleados;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

public class View implements Observer {
    private JPanel panel;
    private JButton borrarBtn;
    private JButton reporteBtn;
    private JTextField nombreTx;
    private JButton buscarBtn;
    private JButton agregarBtn;
    private JTable empleadosTb;
    private JLabel nombreLb;


    public View() {
        buscarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.buscar(nombreTx.getText());
            }
        });
        agregarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.preAgregar();
            }
        });
        empleadosTb.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = empleadosTb.getSelectedRow();
                    controller.editar(row);
                }
            }
        });
        borrarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row= empleadosTb.getSelectedRow();
                if(row!=-1){
                    controller.borrar(row);
                }
            }
        });
        reporteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.imprimir();
                    if (Desktop.isDesktopSupported()) {
                        File myFile = new File("empleados.pdf");
                        Desktop.getDesktop().open(myFile);
                    }
                } catch (Exception ex) { }
            }
        });
    }

    public JPanel getPanel() {
        return panel;
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
        int[] cols = {TableModel.CEDULA, TableModel.NOMBRE, TableModel.TELEFONO, TableModel.SALARIOBASE,TableModel.SUCURSAL,TableModel.ZONAJE,TableModel.SALARIOTOTAL};
        empleadosTb.setModel(new TableModel(cols, model.getListaEmpleados()));
        empleadosTb.setRowHeight(30);
        this.panel.revalidate();
    }
}

