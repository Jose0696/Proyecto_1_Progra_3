package proyectoSucursales.presentation.sucursales;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class View implements Observer{
    private JPanel panel;
    private JTextField nombreLb;
    private JButton buscarBtn;
    private JButton agregarBtn;
    private JButton borrarBtn;
    private JButton reporteBtn;
    private JTable sucursalesTb;
    private JLabel mapaPanel;

    private List<Point> points;

    public View() {
        buscarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.buscar(nombreLb.getText());
            }
        });
        agregarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.preAgregar();
            }
        });
        borrarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row= sucursalesTb.getSelectedRow();
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
                        File myFile = new File("sucursales.pdf");
                        Desktop.getDesktop().open(myFile);
                    }
                } catch (Exception ex) { }
            }
        });
        sucursalesTb.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    createMapWithSelectedPin(sucursalesTb.getSelectedRow());
                }
                if (e.getClickCount() == 2) {
                    int row = sucursalesTb.getSelectedRow();
                    controller.editar(row);
                }
            }
        });
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
                if (detected==1){
                    ToolTipManager.sharedInstance().setEnabled(true);
                    mapaPanel.setToolTipText(detectedData);
                }else{
                    ToolTipManager.sharedInstance().setEnabled(false);
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
        int[] cols = {TableModel.CODIGO, TableModel.NOMBRE,TableModel.DIRECCION,TableModel.ZONAJE};
        sucursalesTb.setModel(new TableModel(cols, model.getListaSucursales()));
        sucursalesTb.setRowHeight(30);
        createMap();
        this.panel.revalidate();
    }

    public void createMap(){
        try {
            points = new ArrayList<Point>();
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

            for(int i = 0; i< points.size(); i++){
                g.drawImage(pin,(int)(points.get(i).getX()), (int)(points.get(i).getY()),32,32,null);
            }

            mapaPanel.setIcon(new ImageIcon(result));
        } catch (IOException e) {
            mapaPanel.setText("Ha ocurrido un error con la vista base.");
        }
    }

    public void createPins(){
        for(int i = 0; i<model.getListaSucursales().size(); i++) {
            points.add(new Point(model.getListaSucursales().get(i).getCoordenadaX(),model.getListaSucursales().get(i).getCoordenadaY()));
        }
    }

    public BufferedImage getMap() {
        try {
            points = new ArrayList<Point>();
            Image mapa;
            mapa = ImageIO.read(getClass().getResourceAsStream("../icons/mapa.jpg"));
            mapa.getScaledInstance(564, 521, Image.SCALE_SMOOTH);

            this.createPins();

            Image pin;
            pin = ImageIO.read(getClass().getResourceAsStream("../icons/Sucursal.png"));
            pin.getScaledInstance(32, 32, Image.SCALE_SMOOTH);

            BufferedImage result = new BufferedImage(564, 521, BufferedImage.TYPE_INT_BGR);
            Graphics g = result.getGraphics();
            g.drawImage(mapa, 0, 0, 564, 521, null);

            for (int i = 0; i < points.size(); i++) {
                g.drawImage(pin, (int) (points.get(i).getX()), (int) (points.get(i).getY()), 32, 32, null);
            }

            return result;
        } catch (IOException e) {
            return null;
        }
    }

    public void createMapWithSelectedPin(int index){
        try {
            points = new ArrayList<Point>();
            Image mapa;
            mapa = ImageIO.read(getClass().getResourceAsStream("../icons/mapa.jpg"));
            mapa.getScaledInstance(564,521, Image.SCALE_SMOOTH);

            this.createPins();

            Image pin;
            pin = ImageIO.read(getClass().getResourceAsStream("../icons/Sucursal.png"));
            pin.getScaledInstance(32,32, Image.SCALE_SMOOTH);

            Image pinSelected;
            pinSelected = ImageIO.read(getClass().getResourceAsStream("../icons/SucursalSel.png"));
            pinSelected.getScaledInstance(32,32, Image.SCALE_SMOOTH);

            BufferedImage result=new BufferedImage(564,521,BufferedImage.TYPE_INT_BGR);
            Graphics g=result.getGraphics();
            g.drawImage(mapa,0,0,564,521,null);

            for(int i = 0; i< points.size(); i++){
                if(i==index){
                    g.drawImage(pinSelected,(int)(points.get(i).getX()), (int)(points.get(i).getY()),32,32,null);
                }else{
                    g.drawImage(pin,(int)(points.get(i).getX()), (int)(points.get(i).getY()),32,32,null);
                }
            }

            mapaPanel.setIcon(new ImageIcon(result));
        } catch (IOException e) {
            mapaPanel.setText("Ha ocurrido un error con la vista base.");
        }
    }
}
