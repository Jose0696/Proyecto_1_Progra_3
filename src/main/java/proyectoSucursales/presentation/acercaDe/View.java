package proyectoSucursales.presentation.acercaDe;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

public class View implements Observer {

    private JLabel logoLb;
    private JPanel panel;

    public void crearLogo(){
        try {
            Image logo;
            logo = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../icons/SISE.jpg")));
            logo.getScaledInstance(800,600, Image.SCALE_SMOOTH);

            BufferedImage result=new BufferedImage(800,600,BufferedImage.TYPE_INT_BGR);
            Graphics g=result.getGraphics();
            g.drawImage(logo,0,0,800,600,null);

            logoLb.setIcon(new ImageIcon(result));
        } catch (IOException e) {
            logoLb.setText("Ha ocurrido un error con la vista base.");
        }
    }

    public JPanel getPanel() {
        return panel;
    }

    proyectoSucursales.presentation.acercaDe.Controller controller;
    proyectoSucursales.presentation.acercaDe.Model model;

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
