package proyectoSucursales.presentation.sucursales;

import proyectoSucursales.Application;
import proyectoSucursales.logic.Sucursal;
import proyectoSucursales.logic.Service;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;

import javax.imageio.ImageIO;
import java.util.List;
import java.util.Objects;

public class Controller {
    View view;
    Model model;

    public Controller(View view, Model model) {
        model.setListaSucursales(Service.instance().sucursalesBusqueda(""));
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
    }

    public void buscar(String filtro){
        List<Sucursal> rows = Service.instance().sucursalesBusqueda(filtro);
        model.setListaSucursales(rows);
        model.commit();
    }

    public void preAgregar(){
        Application.sucursalController.preAgregar();
    }

    public void editar(int row){
        String cedula = model.getListaSucursales().get(row).getCodigo();
        Sucursal e=null;
        try {
            e= Service.instance().sucursalGet(cedula);
            Application.sucursalController.editar(e);
        } catch (Exception ex) {}
    }

    public void borrar(int row){
        Sucursal sucursal = model.getListaSucursales().get(row);
        try {
            Service.instance().sucursalDelete(sucursal);
            this.buscar("");
        } catch (Exception ex) {}
    }


    public void show(){
        Application.window.setContentPane(view.getPanel());
    }

    public void refresh(){
        model.setListaSucursales(Service.instance().sucursalesBusqueda(""));
    }

    private Cell getCell( Paragraph paragraph,TextAlignment alignment,boolean hasBorder) {
        Cell cell = new Cell().add(paragraph);
        cell.setPadding(0);
        cell.setTextAlignment(alignment);
        if(!hasBorder) cell.setBorder(Border.NO_BORDER);
        return cell;
    }

    private Cell getCell( Image image,HorizontalAlignment alignment,boolean hasBorder) {
        Cell cell = new Cell().add(image);
        image.setHorizontalAlignment(alignment);
        cell.setPadding(0);
        if(!hasBorder) cell.setBorder(Border.NO_BORDER);
        return cell;
    }

    public void imprimir()throws Exception{
        String dest="sucursales.pdf";
        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        java.awt.Image logo;
        logo = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../icons/logo.jpg")));

        Document document = new Document(pdf);
        document.setMargins(20, 20, 20, 20);

        Table header = new Table(1);
        header.setWidth(400);
        header.setHorizontalAlignment(HorizontalAlignment.CENTER);
        header.addCell(getCell(new Paragraph("Sistema Integrado SISE").setFont(font).setBold().setFontSize(20f), TextAlignment.CENTER,false));
        header.addCell(getCell(new Image(ImageDataFactory.create(logo,null)), HorizontalAlignment.CENTER,false));
        document.add(header);

        document.add(new Paragraph(""));document.add(new Paragraph(""));

        Color bkg = ColorConstants.RED;
        Color frg= ColorConstants.WHITE;
        Table body = new Table(4);
        body.setWidth(400);
        body.setHorizontalAlignment(HorizontalAlignment.CENTER);
        body.addCell(getCell(new Paragraph("Código").setBackgroundColor(bkg).setFontColor(frg),TextAlignment.CENTER,true));
        body.addCell(getCell(new Paragraph("Nombre").setBackgroundColor(bkg).setFontColor(frg),TextAlignment.CENTER,true));
        body.addCell(getCell(new Paragraph("Dirección").setBackgroundColor(bkg).setFontColor(frg),TextAlignment.CENTER,true));
        body.addCell(getCell(new Paragraph("Zonaje").setBackgroundColor(bkg).setFontColor(frg),TextAlignment.CENTER,true));
        for(Sucursal s: model.getListaSucursales()){
            body.addCell(getCell(new Paragraph(s.getCodigo()),TextAlignment.CENTER,true));
            body.addCell(getCell(new Paragraph(s.getReferencia()),TextAlignment.CENTER,true));
            body.addCell(getCell(new Paragraph(s.getDireccion()),TextAlignment.CENTER,true));
            body.addCell(getCell(new Paragraph(String.valueOf(s.getZonaje())),TextAlignment.CENTER,true));
        }
        document.add(body);
        document.close();
    }
}
