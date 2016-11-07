package cl.apr.pdf;

import cl.apr.beans.BarChartItem;
import cl.apr.beans.ItemReporte;
import cl.apr.beans.SubsidioReporte;
import cl.apr.entity.AvisoCobro;
import cl.apr.entity.DetalleAvisoCobro;
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

public class ReporteIngresosPDF {
	
	
	
	static public ByteArrayOutputStream crearPdf(List<ItemReporte> list, ItemReporte totalIngreso){
		
		Document document =new Document();
		ByteArrayOutputStream baosPDF = new ByteArrayOutputStream();
		PdfContentByte pdfcb = null;

		try {			
                        //System.out.println("System.out.println(PageSize.A4.getHeight()): "+PageSize.A4.getHeight());
                        //System.out.println("System.out.println(PageSize.LETTER.getWidth()): "+PageSize.LETTER.getWidth());
                        //System.out.println("System.out.println(PageSize.LETTER.getHeight()): "+PageSize.LETTER.getHeight());
                        PdfWriter pdfWriter =  PdfWriter.getInstance(document, baosPDF);
                        document.open();
                        //final Rectangle OFICIO = new Rectangle(612,934);
                        //document.setPageSize(OFICIO);
                        document.setPageSize(PageSize.LETTER);
                        document.setMargins(UtilPDF.MARGIN_LEFT, UtilPDF.MARGIN_RIGHT,UtilPDF.MARGIN_TOP,UtilPDF.MARGIN_BOTTOM);
                        document.newPage();
                        document.setMargins(UtilPDF.MARGIN_LEFT, UtilPDF.MARGIN_RIGHT,UtilPDF.MARGIN_TOP,UtilPDF.MARGIN_BOTTOM);
                        //int pagina = 1;
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
                        tablaTitulo.addCell(new Phrase("Reporte Ingresos",UtilPDF.fTitulo1));
                        tablaTitulo.addCell(new Phrase(" ",UtilPDF.fTitulo1));
                        
                        PdfPTable tabla   = new PdfPTable(11);
                        tabla.setWidthPercentage(100);
                        tabla.setWidths(new float[] {18f, 8f, 8f, 8f,8f, 8f, 8f, 8f,8f, 8f, 10f}); 
                        tabla.getDefaultCell().setBorder(Rectangle.BOX);
                        tabla.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
                        tabla.getDefaultCell().setFixedHeight(14);
                        
                        PdfPCell  c = new PdfPCell(new Phrase("Cuenta",UtilPDF.fCuerpoTablaBold));
                        c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        c.setHorizontalAlignment(Element.ALIGN_CENTER); 
                        c.setBorder(Rectangle.BOX);
                        c.setBackgroundColor(UtilPDF.borderColor);
                        c.setPadding(5);
                        tabla.addCell(c);
                        
                        c = new PdfPCell(new Phrase("N° Docuemento",UtilPDF.fCuerpoTablaBold));
                        c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        c.setHorizontalAlignment(Element.ALIGN_CENTER); 
                        c.setBorder(Rectangle.BOX);
                        c.setBackgroundColor(UtilPDF.borderColor);
                        c.setPadding(5);
                        tabla.addCell(c);
                        
                        c = new PdfPCell(new Phrase("Tarifa A.P.R",UtilPDF.fCuerpoTablaBold));
                        c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        c.setHorizontalAlignment(Element.ALIGN_CENTER); 
                        c.setBorder(Rectangle.BOX);
                        c.setBackgroundColor(UtilPDF.borderColor);
                        c.setPadding(5);
                        tabla.addCell(c);
                        
                        c = new PdfPCell(new Phrase("Cuota Social",UtilPDF.fCuerpoTablaBold));
                        c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        c.setHorizontalAlignment(Element.ALIGN_CENTER); 
                        c.setBorder(Rectangle.BOX);
                        c.setBackgroundColor(UtilPDF.borderColor);
                        c.setPadding(5);
                        tabla.addCell(c);
                        
                        c = new PdfPCell(new Phrase("Interés y Reajuste",UtilPDF.fCuerpoTablaBold));
                        c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        c.setHorizontalAlignment(Element.ALIGN_CENTER); 
                        c.setBorder(Rectangle.BOX);
                        c.setBackgroundColor(UtilPDF.borderColor);
                        c.setPadding(5);
                        tabla.addCell(c);
                        
                        c = new PdfPCell(new Phrase("Multas",UtilPDF.fCuerpoTablaBold));
                        c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        c.setHorizontalAlignment(Element.ALIGN_CENTER); 
                        c.setBorder(Rectangle.BOX);
                        c.setBackgroundColor(UtilPDF.borderColor);
                        c.setPadding(5);
                        tabla.addCell(c);
                        
                        c = new PdfPCell(new Phrase("Corte y reposición",UtilPDF.fCuerpoTablaBold));
                        c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        c.setHorizontalAlignment(Element.ALIGN_CENTER); 
                        c.setBorder(Rectangle.BOX);
                        c.setBackgroundColor(UtilPDF.borderColor);
                        c.setPadding(5);
                        tabla.addCell(c);
                        
                        c = new PdfPCell(new Phrase("Derecho Incorporación",UtilPDF.fCuerpoTablaBold));
                        c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        c.setHorizontalAlignment(Element.ALIGN_CENTER); 
                        c.setBorder(Rectangle.BOX);
                        c.setBackgroundColor(UtilPDF.borderColor);
                        c.setPadding(5);
                        tabla.addCell(c);
                        
                        c = new PdfPCell(new Phrase("Otras",UtilPDF.fCuerpoTablaBold));
                        c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        c.setHorizontalAlignment(Element.ALIGN_CENTER); 
                        c.setBorder(Rectangle.BOX);
                        c.setBackgroundColor(UtilPDF.borderColor);
                        c.setPadding(5);
                        tabla.addCell(c);
                        
                        c = new PdfPCell(new Phrase("Total",UtilPDF.fCuerpoTablaBold));
                        c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        c.setHorizontalAlignment(Element.ALIGN_CENTER); 
                        c.setBorder(Rectangle.BOX);
                        c.setBackgroundColor(UtilPDF.borderColor);
                        c.setPadding(5);
                        tabla.addCell(c);
                        
                        c = new PdfPCell(new Phrase("Fecha Creación",UtilPDF.fCuerpoTablaBold));
                        c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        c.setHorizontalAlignment(Element.ALIGN_CENTER); 
                        c.setBorder(Rectangle.BOX);
                        c.setBackgroundColor(UtilPDF.borderColor);
                        c.setPadding(5);
                        tabla.addCell(c);
                    
                    /*
                        tabla.addCell(new Phrase("Periodo",UtilPDF.fCuerpoTablaBold));
                        tabla.addCell(new Phrase("Cuenta",UtilPDF.fCuerpoTablaBold));
                        tabla.addCell(new Phrase("Fecha Creación",UtilPDF.fCuerpoTablaBold));
                        tabla.addCell(new Phrase("Valor Subsidiado",UtilPDF.fCuerpoTablaBold));
                        */
                        
                        ItemReporte av;
                        for(int i=0;i<list.size();i++){
                            av=list.get(i);
                            tabla.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                            tabla.addCell(new Phrase(av.getCuenta(),UtilPDF.fCuerpoTabla));
                            tabla.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
                            tabla.addCell(new Phrase(av.getNumeroDocumento(),UtilPDF.fCuerpoTabla));
                            tabla.addCell(new Phrase(NumeroFormato.formatearNumeroPesos(av.getConsumoAgua()),UtilPDF.fCuerpoTabla));
                            tabla.addCell(new Phrase(NumeroFormato.formatearNumeroPesos(av.getCuotaSocial()),UtilPDF.fCuerpoTabla));
                            tabla.addCell(new Phrase(NumeroFormato.formatearNumeroPesos(av.getInteres()),UtilPDF.fCuerpoTabla));
                            tabla.addCell(new Phrase(NumeroFormato.formatearNumeroPesos(av.getMultas()),UtilPDF.fCuerpoTabla));
                            tabla.addCell(new Phrase(NumeroFormato.formatearNumeroPesos(av.getCorteReposicion()),UtilPDF.fCuerpoTabla));
                            tabla.addCell(new Phrase(NumeroFormato.formatearNumeroPesos(av.getDerechoIncorporacion()),UtilPDF.fCuerpoTabla));
                            tabla.addCell(new Phrase(NumeroFormato.formatearNumeroPesos(av.getOtrosCobros()),UtilPDF.fCuerpoTabla));
                            tabla.addCell(new Phrase(NumeroFormato.formatearNumeroPesos(av.getTotalItem()),UtilPDF.fCuerpoTabla));
                            tabla.addCell(new Phrase(av.getFechaCreacion(),UtilPDF.fCuerpoTabla));
                        }
                            tabla.addCell(new Phrase("",UtilPDF.fCuerpoTablaBold));
                            tabla.addCell(new Phrase("TOTAL: ",UtilPDF.fCuerpoTablaBold));
                            tabla.addCell(new Phrase(NumeroFormato.formatearNumeroPesos(totalIngreso.getConsumoAgua()),UtilPDF.fCuerpoTablaBold));
                            tabla.addCell(new Phrase(NumeroFormato.formatearNumeroPesos(totalIngreso.getCuotaSocial()),UtilPDF.fCuerpoTablaBold));
                            tabla.addCell(new Phrase(NumeroFormato.formatearNumeroPesos(totalIngreso.getInteres()),UtilPDF.fCuerpoTablaBold));
                            tabla.addCell(new Phrase(NumeroFormato.formatearNumeroPesos(totalIngreso.getMultas()),UtilPDF.fCuerpoTablaBold));
                            tabla.addCell(new Phrase(NumeroFormato.formatearNumeroPesos(totalIngreso.getCorteReposicion()),UtilPDF.fCuerpoTablaBold));
                            tabla.addCell(new Phrase(NumeroFormato.formatearNumeroPesos(totalIngreso.getDerechoIncorporacion()),UtilPDF.fCuerpoTablaBold));
                            tabla.addCell(new Phrase(NumeroFormato.formatearNumeroPesos(totalIngreso.getOtrosCobros()),UtilPDF.fCuerpoTablaBold));
                            tabla.addCell(new Phrase(NumeroFormato.formatearNumeroPesos(totalIngreso.getTotalItem()),UtilPDF.fCuerpoTablaBold));
                            tabla.addCell(new Phrase("",UtilPDF.fCuerpoTablaBold));
                        document.add(tablaTitulo);
                        document.add(tabla);
                       
                        // document.add(new Phrase("FIN",UtilPDF.fTitulo1));
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
