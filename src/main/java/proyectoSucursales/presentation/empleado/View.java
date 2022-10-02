package proyectoSucursales.presentation.empleado;

import proyectoSucursales.Application;
import proyectoSucursales.logic.Empleado;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class View implements Observer {
    private JPanel panel;
    private JTextField telefonoTx;
    private JTextField nombreTx;
    private JTextField cedulaTx;
    private JButton guardarFld;
    private JButton cancelarFld;
    private JLabel nombreLb;
    private JLabel cedulaLb;
    private JLabel telefonoLb;
    private JLabel salBaseLb;
    private JTextField salBaseTx;
    private JLabel mapaPanel;
    private List<Point> pins;

    private int validSucursalValue;
    private int tooltipValidator;

    private int selectedSucursalIndex;

    public View() {
        guardarFld.addActionListener(e -> {
            if (validate()) {
                Empleado n = take();
                try {
                    controller.guardar(n);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        cancelarFld.addActionListener(e -> controller.hide());
        mapaPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Polygon rangePin;
                int detected=0;
                String detectedData="";
                for(int i = 0; i<model.getListaSucursales().size(); i++){
                    rangePin=new Polygon();
                    rangePin.addPoint(model.getListaSucursales().get(i).getCoordenadaX()-1,model.getListaSucursales().get(i).getCoordenadaY()+14);
                    rangePin.addPoint(model.getListaSucursales().get(i).getCoordenadaX()-7,model.getListaSucursales().get(i).getCoordenadaY()+3);
                    rangePin.addPoint(model.getListaSucursales().get(i).getCoordenadaX()-10,model.getListaSucursales().get(i).getCoordenadaY()-5);
                    rangePin.addPoint(model.getListaSucursales().get(i).getCoordenadaX()-8,model.getListaSucursales().get(i).getCoordenadaY()-13);
                    rangePin.addPoint(model.getListaSucursales().get(i).getCoordenadaX()-1,model.getListaSucursales().get(i).getCoordenadaY()-14);
                    rangePin.addPoint(model.getListaSucursales().get(i).getCoordenadaX()+7,model.getListaSucursales().get(i).getCoordenadaY()-10);
                    rangePin.addPoint(model.getListaSucursales().get(i).getCoordenadaX()+8,model.getListaSucursales().get(i).getCoordenadaY()-1);
                    rangePin.addPoint(model.getListaSucursales().get(i).getCoordenadaX()+2,model.getListaSucursales().get(i).getCoordenadaY()+8);
                    rangePin.addPoint(model.getListaSucursales().get(i).getCoordenadaX()-1,model.getListaSucursales().get(i).getCoordenadaY()+14);
                    if(rangePin.contains(new Point(e.getX(), e.getY()))) {
                        detected=1;
                        detectedData=model.getListaSucursales().get(i).getData();
                    }
                }
                if ((detected==1)&&(tooltipValidator==0)){
                    ToolTipManager.sharedInstance().setEnabled(true);
                    mapaPanel.setToolTipText(detectedData);
                }else{
                    ToolTipManager.sharedInstance().setEnabled(tooltipValidator == 1);
                }
            }
        });
        mapaPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==1){
                    Polygon rangePin;
                    int detected=0;
                    int detectedDataIndex=0;
                    for(int i = 0; i<model.getListaSucursales().size(); i++){
                        rangePin=new Polygon();
                        rangePin.addPoint(model.getListaSucursales().get(i).getCoordenadaX()-1,model.getListaSucursales().get(i).getCoordenadaY()+14);
                        rangePin.addPoint(model.getListaSucursales().get(i).getCoordenadaX()-7,model.getListaSucursales().get(i).getCoordenadaY()+3);
                        rangePin.addPoint(model.getListaSucursales().get(i).getCoordenadaX()-10,model.getListaSucursales().get(i).getCoordenadaY()-5);
                        rangePin.addPoint(model.getListaSucursales().get(i).getCoordenadaX()-8,model.getListaSucursales().get(i).getCoordenadaY()-13);
                        rangePin.addPoint(model.getListaSucursales().get(i).getCoordenadaX()-1,model.getListaSucursales().get(i).getCoordenadaY()-14);
                        rangePin.addPoint(model.getListaSucursales().get(i).getCoordenadaX()+7,model.getListaSucursales().get(i).getCoordenadaY()-10);
                        rangePin.addPoint(model.getListaSucursales().get(i).getCoordenadaX()+8,model.getListaSucursales().get(i).getCoordenadaY()-1);
                        rangePin.addPoint(model.getListaSucursales().get(i).getCoordenadaX()+2,model.getListaSucursales().get(i).getCoordenadaY()+8);
                        rangePin.addPoint(model.getListaSucursales().get(i).getCoordenadaX()-1,model.getListaSucursales().get(i).getCoordenadaY()+14);
                        if(rangePin.contains(new Point(e.getX(), e.getY()))) {
                            detected=1;
                            detectedDataIndex=i;
                        }
                    }
                    if (detected==1){
                        createMapWithSelectedPin(detectedDataIndex);
                        selectedSucursalIndex=detectedDataIndex;
                        validSucursalValue=1;
                    }else{
                        createMap();
                        validSucursalValue=0;
                    }
                }else{
                    validSucursalValue=0;
                }
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
        Empleado current = model.getEmpleado();
        this.cedulaTx.setEnabled(model.getModo() == Application.MODO_AGREGAR);
        this.cedulaTx.setText(current.getCedula());
        this.nombreTx.setText(current.getNombre());
        this.telefonoTx.setText(current.getTelefono());
        this.salBaseTx.setText(String.valueOf(current.getSalarioBase()));
        if (this.model.getModo()==Application.MODO_AGREGAR){
            createMap();
        }else if(this.model.getModo()==Application.MODO_EDITAR){
            try {
                createMapWithSelectedPin(controller.getSucursalIndex(current.getSucursal().getCodigo()));
                validSucursalValue=1;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        this.panel.validate();
    }

    public void createMap(){
        try {
            pins= new ArrayList<>();
            Image mapa;
            mapa = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../icons/mapa.jpg")));
            mapa.getScaledInstance(564,521, Image.SCALE_SMOOTH);

            this.createPins();

            Image pin;
            pin = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../icons/Sucursal.png")));
            pin.getScaledInstance(32,32, Image.SCALE_SMOOTH);

            BufferedImage result=new BufferedImage(564,521,BufferedImage.TYPE_INT_BGR);
            Graphics g=result.getGraphics();
            g.drawImage(mapa,0,0,564,521,null);

            for (Point point : pins) {
                g.drawImage(pin, (int) (point.getX()), (int) (point.getY()), 32, 32, null);
            }

            mapaPanel.setIcon(new ImageIcon(result));
        } catch (IOException e) {
            mapaPanel.setText("Error en la vista base.");
        }
    }

    public void createPins(){
        for(int i = 0; i<model.getListaSucursales().size(); i++) {
            pins.add(new Point(model.getListaSucursales().get(i).getCoordenadaX()-16,model.getListaSucursales().get(i).getCoordenadaY()-16));
        }
    }

    public void createMapWithSelectedPin(int index){
        try {
            pins= new ArrayList<>();
            Image mapa;
            mapa = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../icons/mapa.jpg")));
            mapa.getScaledInstance(564,521, Image.SCALE_SMOOTH);

            this.createPins();

            Image pin;
            pin = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../icons/Sucursal.png")));
            pin.getScaledInstance(32,32, Image.SCALE_SMOOTH);

            Image pinSelected;
            pinSelected = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../icons/SucursalSel.png")));
            pinSelected.getScaledInstance(32,32, Image.SCALE_SMOOTH);

            BufferedImage result=new BufferedImage(564,521,BufferedImage.TYPE_INT_BGR);
            Graphics g=result.getGraphics();
            g.drawImage(mapa,0,0,564,521,null);

            for(int i=0;i<pins.size();i++){
                if(i==index){
                    g.drawImage(pinSelected,(int)(pins.get(i).getX()), (int)(pins.get(i).getY()),32,32,null);
                }else{
                    g.drawImage(pin,(int)(pins.get(i).getX()), (int)(pins.get(i).getY()),32,32,null);
                }
            }

            mapaPanel.setIcon(new ImageIcon(result));
        } catch (IOException e) {
            mapaPanel.setText("Error en la vista base.");
        }
    }

    public Empleado take() {
        Empleado empleado = new Empleado();
        empleado.setCedula(cedulaTx.getText());
        empleado.setNombre(nombreTx.getText());
        empleado.setTelefono(telefonoTx.getText());
        empleado.setSalarioBase(Double.parseDouble(salBaseTx.getText()));
        empleado.setSucursal(model.getListaSucursales().get(selectedSucursalIndex));
        return empleado;
    }

    public static boolean isDouble(String text){
        double valor;
        try {
            valor=Double.parseDouble(text);
            return true;
        }catch (NumberFormatException ex){
            return false;
        }
    }

    private boolean validate() {
        boolean validacion = true;
        if (nombreTx.getText().isEmpty()) {
            validacion = false;
            nombreLb.setBorder(Application.BORDER_ERROR);
            nombreLb.setToolTipText("Id requerido");
        } else {
            nombreLb.setBorder(null);
            nombreLb.setToolTipText(null);
        }

        if (cedulaTx.getText().length() == 0) {
            validacion = false;
            cedulaLb.setBorder(Application.BORDER_ERROR);
            cedulaLb.setToolTipText("Cédula requerida");
        } else {
            cedulaLb.setBorder(null);
            cedulaLb.setToolTipText(null);
        }
        if (telefonoTx.getText().length() == 0) {
            validacion = false;
            telefonoLb.setBorder(Application.BORDER_ERROR);
            telefonoLb.setToolTipText("Teléfono requerido");
        } else {
            telefonoLb.setBorder(null);
            telefonoLb.setToolTipText(null);
        }
        if (!isDouble(salBaseTx.getText())) {
            validacion = false;
            salBaseLb.setBorder(Application.BORDER_ERROR);
            salBaseLb.setToolTipText("Salario base requerido");
        } else {
            salBaseLb.setBorder(null);
            salBaseLb.setToolTipText(null);
        }

        if (validSucursalValue==0) {
            validacion = false;
            mapaPanel.setBorder(Application.BORDER_ERROR);
            ToolTipManager.sharedInstance().setEnabled(true);
            mapaPanel.setToolTipText("Requiere Ubicacion. Introduzca un pin dentro del mapa");
            tooltipValidator=1;
        } else {
            ToolTipManager.sharedInstance().setEnabled(false);
            mapaPanel.setBorder(null);
            mapaPanel.setToolTipText(null);
            tooltipValidator=0;
        }
        return validacion;
    }
}
