package proyectoSucursales.logic;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlID;

@XmlAccessorType(XmlAccessType.FIELD)
public class Sucursal {
    @XmlID
    String codigo;
    String referencia;
    String direccion;
    Double zonaje;
    int coordenadaX;
    int coordenadaY;

    public Sucursal(String codigo, String referencia, String direccion, Double zonaje, int coordenadaX, int coordenadaY) {
        this.codigo = codigo;
        this.referencia = referencia;
        this.direccion = direccion;
        this.zonaje = zonaje;
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
    }

    public Sucursal() {
        this("","","",0.0,0,0);
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Double getZonaje() {
        return zonaje;
    }

    public void setZonaje(Double zonaje) {
        this.zonaje = zonaje;
    }

    public int getCoordenadaX() {
        return coordenadaX;
    }

    public void setCoordenadaX(int coordenadaX) {
        this.coordenadaX = coordenadaX;
    }

    public int getCoordenadaY() {
        return coordenadaY;
    }

    public void setCoordenadaY(int coordenadaY) {
        this.coordenadaY = coordenadaY;
    }

    public String getData() {
        return referencia +", Código: "+codigo+", "+"Dirección: "+direccion+", "+"Zonaje: "+zonaje+".";
    }
}
