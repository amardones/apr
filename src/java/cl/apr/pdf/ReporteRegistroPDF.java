package cl.apr.pdf;

import cl.apr.beans.BarChartItem;
import cl.apr.beans.SubsidioReporte;
import cl.apr.entity.AvisoCobro;
import cl.apr.entity.DetalleAvisoCobro;
import cl.apr.entity.RegistroEstado;
import cl.apr.enums.EnumFormatoFechaHora;
import cl.apr.pdf.chart.BarChartAviso;
import cl.apr.util.NumeroFormato;
import com.itextpdf.text.BadElementException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class ReporteRegistroPDF {
	/*
	private static BaseColor colorRelleno = WebColors.getRGBColor("#eeeeee");
        private static BaseColor colorBlueLigth = WebColors.getRGBColor("#009edd"); 
	private static BaseColor colorBlue = WebColors.getRGBColor("#00008B"); 
	private static BaseColor borderColor = BaseColor.LIGHT_GRAY;
	
	private static Font fTitulo1 = new Font(Font.getFamily("ARIAL"),15,Font.NORMAL, colorBlue);
	private static Font fTitulo2 = new Font(Font.getFamily("ARIAL"),10,Font.NORMAL, colorBlue);
	private static Font fSeparador = new Font(Font.getFamily("ARIAL"),4,Font.NORMAL);
	//private static Font fTitulo = new Font(Font.getFamily("ARIAL"),14,Font.BOLD);
	//private static Font fTituloTabla = new Font(Font.getFamily("ARIAL"),11,Font.BOLD);
	//private static Font fSubTituloTabla = new Font(Font.getFamily("ARIAL"),8,Font.BOLD);//10
	private static Font fCuerpoCabeceraTabla= new Font(Font.getFamily("ARIAL"),7f,Font.BOLD);
	private static Font fCuerpoCabeceraTablaWhite= new Font(Font.getFamily("ARIAL"),7f,Font.BOLD, BaseColor.WHITE);
	private static Font fCuerpoTabla = new Font(Font.getFamily("ARIAL"),7f,Font.NORMAL, BaseColor.DARK_GRAY);
        private static Font fCuerpoTablaBold = new Font(Font.getFamily("ARIAL"),7f,Font.BOLD, BaseColor.DARK_GRAY);
	//private static BaseColor colorRellenoLightGray = BaseColor.LIGHT_GRAY;	
	
	private static Font fTextPlazo= new Font(Font.getFamily("ARIAL"),8,Font.BOLD | Font.UNDERLINE );
	    */
	
	static public ByteArrayOutputStream crearPdf(List<RegistroEstado> list){
		
		Document document =new Document();
		ByteArrayOutputStream baosPDF = new ByteArrayOutputStream();
		PdfContentByte pdfcb = null;

		try {			

                        PdfWriter pdfWriter =  PdfWriter.getInstance(document, baosPDF);
                        document.open();
                        document.setPageSize(PageSize.LETTER);
                        document.setMargins(UtilPDF.MARGIN_LEFT, UtilPDF.MARGIN_RIGHT,UtilPDF.MARGIN_TOP,UtilPDF.MARGIN_BOTTOM);
                        document.newPage();
                        document.setMargins(UtilPDF.MARGIN_LEFT, UtilPDF.MARGIN_RIGHT,UtilPDF.MARGIN_TOP,UtilPDF.MARGIN_BOTTOM);
                         URL urlImagen;
                        Image imagen = null;
                        try {
                            
                            urlImagen = new URL("http://localhost/APR/resources/images/marcanueva.png");
                            imagen = Image.getInstance(urlImagen);

                        } catch (MalformedURLException ex) {
                            ex.printStackTrace();
                        } catch (BadElementException ex) {
                              ex.printStackTrace();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            //Logger.getLogger(AvisoPDF.class.getName()).log(Level.SEVERE, null, ex);
                        }


                        //System.out.println("Creando aviso 0");
                        PdfPTable tablaTitulo   = new PdfPTable(3);
                        tablaTitulo.setWidthPercentage(100);
                        tablaTitulo.setWidths(new float[] {20f, 60f,20f});
                        tablaTitulo.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                        tablaTitulo.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
                        tablaTitulo.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                        tablaTitulo.getDefaultCell().setFixedHeight(40);
                        
                        tablaTitulo.addCell(imagen);
                        tablaTitulo.addCell(new Phrase("Reporte estado de Cuentas",UtilPDF.fTitulo1));
                        tablaTitulo.addCell(new Phrase(" ",UtilPDF.fTitulo1));
                        
                        PdfPTable tabla   = new PdfPTable(7);
                        tabla.setWidthPercentage(100);
                        tabla.setWidths(new float[] {8f,16f,20f, 14f, 14f, 14f,14f}); 
                        tabla.getDefaultCell().setBorder(Rectangle.BOX);
                        tabla.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
                        tabla.getDefaultCell().setFixedHeight(14);
                        
                        PdfPCell  c = new PdfPCell(new Phrase("N° Cuenta",UtilPDF.fCuerpoTablaBold));
                        c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        c.setHorizontalAlignment(Element.ALIGN_CENTER); 
                        c.setBorder(Rectangle.BOX);
                        c.setBackgroundColor(UtilPDF.borderColor);
                        c.setPadding(5);
                        tabla.addCell(c);
                        
                        c = new PdfPCell(new Phrase("Nombre",UtilPDF.fCuerpoTablaBold));
                        c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        c.setHorizontalAlignment(Element.ALIGN_CENTER); 
                        c.setBorder(Rectangle.BOX);
                        c.setBackgroundColor(UtilPDF.borderColor);
                        c.setPadding(5);
                        tabla.addCell(c);
                        
                        c = new PdfPCell(new Phrase("Apellido",UtilPDF.fCuerpoTablaBold));
                        c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        c.setHorizontalAlignment(Element.ALIGN_CENTER); 
                        c.setBorder(Rectangle.BOX);
                        c.setBackgroundColor(UtilPDF.borderColor);
                        c.setPadding(5);
                        tabla.addCell(c);
                        
                        c = new PdfPCell(new Phrase("N° Medidor",UtilPDF.fCuerpoTablaBold));
                        c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        c.setHorizontalAlignment(Element.ALIGN_CENTER); 
                        c.setBorder(Rectangle.BOX);
                        c.setBackgroundColor(UtilPDF.borderColor);
                        c.setPadding(5);
                        tabla.addCell(c);
                        
                        c = new PdfPCell(new Phrase("Estado Anterior",UtilPDF.fCuerpoTablaBold));
                        c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        c.setHorizontalAlignment(Element.ALIGN_CENTER); 
                        c.setBorder(Rectangle.BOX);
                        c.setBackgroundColor(UtilPDF.borderColor);
                        c.setPadding(5);
                        tabla.addCell(c);
                        
                         c = new PdfPCell(new Phrase("Estado Actual",UtilPDF.fCuerpoTablaBold));
                        c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        c.setHorizontalAlignment(Element.ALIGN_CENTER); 
                        c.setBorder(Rectangle.BOX);
                        c.setBackgroundColor(UtilPDF.borderColor);
                        c.setPadding(5);
                        tabla.addCell(c);
                        
                        c = new PdfPCell(new Phrase("Metros Cúbicos",UtilPDF.fCuerpoTablaBold));
                        c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        c.setHorizontalAlignment(Element.ALIGN_CENTER); 
                        c.setBorder(Rectangle.BOX);
                        c.setBackgroundColor(UtilPDF.borderColor);
                        c.setPadding(5);
                        tabla.addCell(c);
                        tabla.getDefaultCell().setFixedHeight(20);
                               
                
                        RegistroEstado av;
                        for(int i=0;i<list.size();i++){
                            av=list.get(i);                            
                            tabla.addCell(new Phrase(""+av.getCuenta().getIdCuenta(),UtilPDF.fCuerpoTabla));
                            tabla.addCell(new Phrase(av.getCuenta().getRut().getNombre(),UtilPDF.fCuerpoTabla));
                            tabla.addCell(new Phrase(av.getCuenta().getRut().getApellido(),UtilPDF.fCuerpoTabla));
                            tabla.addCell(new Phrase(""+av.getCuenta().getNumeroMedidor().getNumeroMedidor(),UtilPDF.fCuerpoTabla));
                            tabla.addCell(new Phrase(""+av.getEstadoAnterior(),UtilPDF.fCuerpoTabla));
                            if(av.getEstadoActual()!=0){
                                tabla.addCell(new Phrase(""+av.getEstadoActual(),UtilPDF.fCuerpoTabla));
                                tabla.addCell(new Phrase(""+av.getMetrosCubicos(),UtilPDF.fCuerpoTabla));
                            }else{
                                tabla.addCell(new Phrase("",UtilPDF.fCuerpoTabla));
                                tabla.addCell(new Phrase("",UtilPDF.fCuerpoTabla));
                            }
                        }
                              
                        document.add(tablaTitulo);
                        document.add(tabla);

                        try{
                                baosPDF.flush();
                                baosPDF.close();
                        }catch (IOException e){
                                e.printStackTrace();
                        }
                        document.close();
                    }catch (DocumentException e) {
			e.printStackTrace();
                    }
		
		return baosPDF;
		
	}
	
	
}
