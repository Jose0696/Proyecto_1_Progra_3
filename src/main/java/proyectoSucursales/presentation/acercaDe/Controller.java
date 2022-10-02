package proyectoSucursales.presentation.acercaDe;

public class Controller {
    proyectoSucursales.presentation.acercaDe.Model model;
    proyectoSucursales.presentation.acercaDe.View view;

    public Controller(View view, Model model) {
        this.model = model;
        this.view = view;
        view.setModel(model);
        view.setController(this);
    }

    public void show(){
        view.crearLogo();
    }
}
