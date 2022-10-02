package proyectoSucursales.presentation.sucursal;

import proyectoSucursales.logic.Sucursal;
import proyectoSucursales.Application;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

public class View implements Observer{
    private JPanel panel;
    private JLabel mapaPanel;
    private JTextField codigoTx;
    private JTextField nombreTx;
    private JTextField zonajeTx;
    private JButton agregarFld;
    private JButton cancelarFld;
    private JTextField direccionTx;
    private JLabel codigoLb;
    private JLabel nombreLb;
    private JLabel direccionLb;
    private JLabel zonajeLb;
    int xpoints;
    int ypoints;
    Polygon mapPoint; // https://docs.oracle.com/javase/7/docs/api/java/awt/Polygon.html

    public View() {
        agregarFld.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validate()) {
                    Sucursal n = take();
                    try {
                        controller.guardar(n);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(panel, ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        cancelarFld.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.hide();
            }
        });
        mapaPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==1){
                    createCustomMap(e.getX(),e.getY());
                }
            }
        });
        mapPoint =new Polygon();
        mapPoint.addPoint(32,-11);
        mapPoint.addPoint(49 ,-16);
        mapPoint.addPoint(148,33);
        mapPoint.addPoint(190 ,8);
        mapPoint.addPoint(246,30);
        mapPoint.addPoint(280 ,65);
        mapPoint.addPoint(300,63);
        mapPoint.addPoint(359 ,55);
        mapPoint.addPoint(545,274);
        mapPoint.addPoint(498 ,262);
        mapPoint.addPoint(483,352);
        mapPoint.addPoint(511 ,365);
        mapPoint.addPoint(475,495);
        mapPoint.addPoint(449 ,470);
        mapPoint.addPoint(438,432);
        mapPoint.addPoint(404 ,462);
        mapPoint.addPoint(349,420);
        mapPoint.addPoint(348 ,337);
        mapPoint.addPoint(295,302);
        mapPoint.addPoint(219 ,265);
        mapPoint.addPoint(202,266);
        mapPoint.addPoint(153 ,185);
        mapPoint.addPoint(101,253);
        mapPoint.addPoint(18 ,193);
        mapPoint.addPoint(-16,140);
        mapPoint.addPoint(-9 ,104);
        mapPoint.addPoint(-5,36);
        mapPoint.addPoint(32 ,-11);
        mapPoint.addPoint(185 ,237);
        mapPoint.addPoint(183,200);
        mapPoint.addPoint(99 ,151);
        mapPoint.addPoint(84,165);
        mapPoint.addPoint(141 ,211);
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
    public void update(Observable updatedModel, Object parameters) {
        Sucursal current = model.getSucursal();
        this.codigoTx.setEnabled(model.getModo() == Application.MODO_AGREGAR);
        this.codigoTx.setText(current.getCodigo());
        nombreTx.setText(current.getReferencia());
        direccionTx.setText(current.getDireccion());
        zonajeTx.setText(current.getZonaje().toString());

        if(current.getCoordenadaX()==0&&current.getCoordenadaY()==0) {
            this.createDefaultMap();
        }
        else{
            this.createCustomMap(current.getCoordenadaX(), current.getCoordenadaY());
        }

        this.panel.validate();
    }

    public void createDefaultMap(){
        try {
            Image mapa;
            mapa = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../icons/mapa.jpg")));
            mapa.getScaledInstance(564,521, Image.SCALE_SMOOTH);

            BufferedImage result=new BufferedImage(564,521,BufferedImage.TYPE_INT_BGR);
            Graphics g=result.getGraphics();
            g.drawImage(mapa,0,0,564,521,null);

            xpoints =0;
            ypoints =0;

            mapaPanel.setIcon(new ImageIcon(result));
        } catch (IOException e) {
            mapaPanel.setText("Error en la vista base.");
        }
    }

    public void createCustomMap(int x,int y){
        try {
            Image mapa;
            mapa = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../icons/mapa.jpg")));
            mapa.getScaledInstance(564,521, Image.SCALE_SMOOTH);

            Image image;
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../icons/SucursalSel.png")));
            image.getScaledInstance(32,32, Image.SCALE_SMOOTH);

            BufferedImage result=new BufferedImage(564,521,BufferedImage.TYPE_INT_BGR);
            Graphics g=result.getGraphics();
            g.drawImage(mapa,0,0,564,521,null);

            g.drawImage(image,x-16,y-16,32,32,null);
            xpoints =x-16;
            ypoints =y-16;

            mapaPanel.setIcon(new ImageIcon(result));
        } catch (IOException e) {
            mapaPanel.setText("Error en la vista base.");
        }
    }

    public Sucursal take() {
        Sucursal e = new Sucursal();
        e.setCodigo(codigoTx.getText());
        e.setReferencia(nombreTx.getText());
        e.setDireccion(direccionTx.getText());
        e.setZonaje(Double.parseDouble(zonajeTx.getText()));
        e.setCoordenadaX(xpoints);
        e.setCoordenadaY(ypoints);
        return e;
    }

    private boolean validate() {
        boolean validacion = true;
        if (codigoTx.getText().isEmpty()) {
            validacion = false;
            codigoLb.setBorder(Application.BORDER_ERROR);
            codigoLb.setToolTipText("Código requerido.");
        } else {
            codigoLb.setBorder(null);
            codigoLb.setToolTipText(null);
        }

        if (nombreTx.getText().length() == 0) {
            validacion = false;
            nombreLb.setBorder(Application.BORDER_ERROR);
            nombreLb.setToolTipText("Nombre requerido.");
        } else {
            nombreLb.setBorder(null);
            nombreLb.setToolTipText(null);
        }

        if (direccionTx.getText().length() == 0) {
            validacion = false;
            direccionLb.setBorder(Application.BORDER_ERROR);
            direccionLb.setToolTipText("Dirección requerida.");
        } else {
            direccionLb.setBorder(null);
            direccionLb.setToolTipText(null);
        }

        if (zonajeTx.getText().length() == 0) {
            validacion = false;
            zonajeLb.setBorder(Application.BORDER_ERROR);
            zonajeLb.setToolTipText("Zonaje requerido.");
        } else {
            zonajeLb.setBorder(null);
            zonajeLb.setToolTipText(null);
        }
        if ((ypoints ==0 && xpoints == 0)||(!mapPoint.contains(xpoints, ypoints))) {
            validacion = false;
            mapaPanel.setBorder(Application.BORDER_ERROR);
            ToolTipManager.sharedInstance().setEnabled(true);
            mapaPanel.setToolTipText("Requiere Ubicacion en el mapa. Favor hacer click dentro del mapa.");
        } else {
            ToolTipManager.sharedInstance().setEnabled(false);
            mapaPanel.setBorder(null);
            mapaPanel.setToolTipText(null);
        }
        return validacion;
    }
}
