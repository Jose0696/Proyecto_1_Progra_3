package proyectoSucursales.presentation.empleados;

import proyectoSucursales.Application;
import proyectoSucursales.logic.Empleado;
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
import java.time.LocalDate;

import javax.imageio.ImageIO;
import java.util.List;
import java.util.Objects;

public class Controller {
    View view;
    Model model;

    public Controller(View view, Model model) {
        model.setListaEmpleados(Service.instance().empleadosBusqueda(""));
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
    }

    public void buscar(String filtro) {
        List<Empleado> rows = Service.instance().empleadosBusqueda(filtro);
        model.setListaEmpleados(rows);
        model.commit();
    }

    public void preAgregar() {
        Application.empleadoController.preAgregar();
    }

    public void editar(int row) {
        String cedula = model.getListaEmpleados().get(row).getCedula();
        Empleado e = null;
        try {
            e = Service.instance().empleadoGet(cedula);
            Application.empleadoController.editar(e);
        } catch (Exception ex) {
        }
    }


    public void borrar(int row) {
        Empleado e = model.getListaEmpleados().get(row);
        try {
            Service.instance().empleadoDelete(e);
            this.buscar("");
        } catch (Exception ex) {
        }
    }

    public void show() {
        Application.window.setContentPane(view.getPanel());
    }


    private Cell getCell(Paragraph paragraph, TextAlignment alignment, boolean hasBorder) {
        Cell cell = new Cell().add(paragraph);
        cell.setPadding(0);
        cell.setTextAlignment(alignment);
        if (!hasBorder) cell.setBorder(Border.NO_BORDER);
        return cell;
    }

    private Cell getCell(Image image, HorizontalAlignment alignment, boolean hasBorder) {
        Cell cell = new Cell().add(image);
        image.setHorizontalAlignment(alignment);
        cell.setPadding(0);
        if (!hasBorder) cell.setBorder(Border.NO_BORDER);
        return cell;
    }

    public void refresh(){
        model.setListaEmpleados(Service.instance().empleadosBusqueda(""));
        Application.empleadoController.refresh();
    }
    public void imprimir() throws Exception {
        String dest = "empleados.pdf";
        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        java.awt.Image logo;
        logo = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../icons/logo.jpg")));

        LocalDate todaysDate = LocalDate.now();

        Document document = new Document(pdf);
        document.setMargins(20, 20, 20, 20);

        Table header = new Table(1);
        header.setWidth(600);
        header.setHorizontalAlignment(HorizontalAlignment.CENTER);
        header.addCell(getCell(new Paragraph("Sistema Integrado SISE").setFont(font).setBold().setFontSize(20f), TextAlignment.CENTER, false));
        header.addCell(getCell(new Paragraph("\nReporte Sucursales").setFont(font).setBold().setFontSize(16f), TextAlignment.CENTER,false));
        header.addCell(getCell(new Paragraph("\nFecha: "+ todaysDate).setFont(font).setBold().setFontSize(12f), TextAlignment.CENTER,false));
        header.addCell(getCell(new Image(ImageDataFactory.create(logo, null)), HorizontalAlignment.CENTER, false));
        document.add(header);

        document.add(new Paragraph(""));
        document.add(new Paragraph(""));

        Color bkg = ColorConstants.RED;
        Color frg = ColorConstants.WHITE;
        Table body = new Table(7);
        body.setWidth(450);
        body.setHorizontalAlignment(HorizontalAlignment.CENTER);
        body.addCell(getCell(new Paragraph("Cedula").setBackgroundColor(bkg).setFontColor(frg), TextAlignment.CENTER, true));
        body.addCell(getCell(new Paragraph("Nombre").setBackgroundColor(bkg).setFontColor(frg), TextAlignment.CENTER, true));
        body.addCell(getCell(new Paragraph("Telefono").setBackgroundColor(bkg).setFontColor(frg), TextAlignment.CENTER, true));
        body.addCell(getCell(new Paragraph("Sal. Base").setBackgroundColor(bkg).setFontColor(frg), TextAlignment.CENTER, true));
        body.addCell(getCell(new Paragraph("Sucursal").setBackgroundColor(bkg).setFontColor(frg), TextAlignment.CENTER, true));
        body.addCell(getCell(new Paragraph("%Zonaje").setBackgroundColor(bkg).setFontColor(frg), TextAlignment.CENTER, true));
        body.addCell(getCell(new Paragraph("Sal. Total").setBackgroundColor(bkg).setFontColor(frg), TextAlignment.CENTER, true));

        for (Empleado empleado : model.getListaEmpleados()) {
            body.addCell(getCell(new Paragraph(empleado.getCedula()), TextAlignment.CENTER, true));
            body.addCell(getCell(new Paragraph(empleado.getNombre()), TextAlignment.CENTER, true));
            body.addCell(getCell(new Paragraph(empleado.getTelefono()), TextAlignment.CENTER, true));
            body.addCell(getCell(new Paragraph(String.valueOf(empleado.getSalarioBase())), TextAlignment.CENTER, true));
            body.addCell(getCell(new Paragraph(empleado.getSucursal().getReferencia()), TextAlignment.CENTER, true));
            body.addCell(getCell(new Paragraph(empleado.getSucursal().getZonaje().toString()), TextAlignment.CENTER, true));
            body.addCell(getCell(new Paragraph(String.valueOf(empleado.calculaSalarioTotal(empleado.getSucursal().getZonaje()))), TextAlignment.CENTER, true));
        }
        document.add(body);
        document.close();
    }
}
