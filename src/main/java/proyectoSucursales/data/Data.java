package proyectoSucursales.data;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import proyectoSucursales.logic.Sucursal;
import proyectoSucursales.logic.Empleado;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Data {
    private List<Sucursal> sucursales;
    private List<Empleado> empleados;

    public Data() {
        sucursales = new ArrayList<>();
        empleados = new ArrayList<>();
//        Empleado empleado= new Empleado();
//        sucursales.add(new Sucursal("001","Liberia","Guanacaste, Liberia",2.0));
//        sucursales.add(new Sucursal("002","Sabana","San Jose, San Jose",1.0));
//        sucursales.add(new Sucursal("003","Golfito","Puntarenas,Golfito",4.0));
//        sucursales.add(new Sucursal("004","Tortugero","Limon, Tortugero",4.0));
//        sucursales.add(new Sucursal("005","Cahuita","Limon, Cahuita",4.0));
//
//        empleados.add(new Empleado("111","Jose Sequeira","88513641",7500.0,0.0,sucursales.get(0)));
//        empleados.add(new Empleado("222","Jose Ruiz","85172707",8200.0,0.0,sucursales.get(1)));
//        empleados.add(new Empleado("333","Andre Morales","848660401",7800.0,0.0,sucursales.get(2)));


    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

    public List<Sucursal> getSucursales() {
        return sucursales;
    }

    public void setSucursales(List<Sucursal> sucursales) {
        this.sucursales = sucursales;
    }
}
