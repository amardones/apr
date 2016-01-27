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
	    
	
	static public ByteArrayOutputStream crearPdf(List<ItemReporte> list){
		
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
                        //document.setMargins(30, 30, 35,30);
                        document.newPage();
                        //document.setMargins(30, 30, 35,30);
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
                        tablaTitulo.setWidths(new float[] {20f, 60f,20f});
                        tablaTitulo.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                        tablaTitulo.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
                        tablaTitulo.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                        tablaTitulo.getDefaultCell().setFixedHeight(40);
                        
                        tablaTitulo.addCell(imagen);
                        tablaTitulo.addCell(new Phrase("Reporte Ingresos",fTitulo1));
                        tablaTitulo.addCell(new Phrase(" ",fTitulo1));
                        
                        PdfPTable tabla   = new PdfPTable(11);
                        tabla.setWidths(new float[] {9f, 9f, 7f, 8f,11f, 8f, 11f, 12f,8f, 8f, 10f}); 
                        tabla.getDefaultCell().setBorder(Rectangle.BOX);
                        tabla.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
                        tabla.getDefaultCell().setFixedHeight(14);
                        
                        PdfPCell  c = new PdfPCell(new Phrase("Cuenta",fCuerpoTablaBold));
                        c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        c.setHorizontalAlignment(Element.ALIGN_CENTER); 
                        c.setBorder(Rectangle.BOX);
                        c.setBackgroundColor(borderColor);
                        c.setPadding(5);
                        tabla.addCell(c);
                        
                        c = new PdfPCell(new Phrase("N° Docuemento",fCuerpoTablaBold));
                        c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        c.setHorizontalAlignment(Element.ALIGN_CENTER); 
                        c.setBorder(Rectangle.BOX);
                        c.setBackgroundColor(borderColor);
                        c.setPadding(5);
                        tabla.addCell(c);
                        
                        c = new PdfPCell(new Phrase("Tarifa A.P.R",fCuerpoTablaBold));
                        c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        c.setHorizontalAlignment(Element.ALIGN_CENTER); 
                        c.setBorder(Rectangle.BOX);
                        c.setBackgroundColor(borderColor);
                        c.setPadding(5);
                        tabla.addCell(c);
                        
                        c = new PdfPCell(new Phrase("Cuota Social",fCuerpoTablaBold));
                        c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        c.setHorizontalAlignment(Element.ALIGN_CENTER); 
                        c.setBorder(Rectangle.BOX);
                        c.setBackgroundColor(borderColor);
                        c.setPadding(5);
                        tabla.addCell(c);
                        
                        c = new PdfPCell(new Phrase("Interés y Reajuste",fCuerpoTablaBold));
                        c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        c.setHorizontalAlignment(Element.ALIGN_CENTER); 
                        c.setBorder(Rectangle.BOX);
                        c.setBackgroundColor(borderColor);
                        c.setPadding(5);
                        tabla.addCell(c);
                        
                        c = new PdfPCell(new Phrase("Multas",fCuerpoTablaBold));
                        c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        c.setHorizontalAlignment(Element.ALIGN_CENTER); 
                        c.setBorder(Rectangle.BOX);
                        c.setBackgroundColor(borderColor);
                        c.setPadding(5);
                        tabla.addCell(c);
                        
                        c = new PdfPCell(new Phrase("Corte y reposición",fCuerpoTablaBold));
                        c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        c.setHorizontalAlignment(Element.ALIGN_CENTER); 
                        c.setBorder(Rectangle.BOX);
                        c.setBackgroundColor(borderColor);
                        c.setPadding(5);
                        tabla.addCell(c);
                        
                        c = new PdfPCell(new Phrase("Derecho Incorporación",fCuerpoTablaBold));
                        c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        c.setHorizontalAlignment(Element.ALIGN_CENTER); 
                        c.setBorder(Rectangle.BOX);
                        c.setBackgroundColor(borderColor);
                        c.setPadding(5);
                        tabla.addCell(c);
                        
                        c = new PdfPCell(new Phrase("Otras",fCuerpoTablaBold));
                        c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        c.setHorizontalAlignment(Element.ALIGN_CENTER); 
                        c.setBorder(Rectangle.BOX);
                        c.setBackgroundColor(borderColor);
                        c.setPadding(5);
                        tabla.addCell(c);
                        
                        c = new PdfPCell(new Phrase("Total",fCuerpoTablaBold));
                        c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        c.setHorizontalAlignment(Element.ALIGN_CENTER); 
                        c.setBorder(Rectangle.BOX);
                        c.setBackgroundColor(borderColor);
                        c.setPadding(5);
                        tabla.addCell(c);
                        
                        c = new PdfPCell(new Phrase("Fecha Creación",fCuerpoTablaBold));
                        c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        c.setHorizontalAlignment(Element.ALIGN_CENTER); 
                        c.setBorder(Rectangle.BOX);
                        c.setBackgroundColor(borderColor);
                        c.setPadding(5);
                        tabla.addCell(c);
                    
                    /*
                        tabla.addCell(new Phrase("Periodo",fCuerpoTablaBold));
                        tabla.addCell(new Phrase("Cuenta",fCuerpoTablaBold));
                        tabla.addCell(new Phrase("Fecha Creación",fCuerpoTablaBold));
                        tabla.addCell(new Phrase("Valor Subsidiado",fCuerpoTablaBold));
                        */
                        ItemReporte av;
                        for(int i=0;i<list.size();i++){
                            av=list.get(i);
                            tabla.addCell(new Phrase(av.getCuenta(),fCuerpoTabla));
                            tabla.addCell(new Phrase(av.getNumeroDocumento(),fCuerpoTabla));
                            tabla.addCell(new Phrase(""+av.getConsumoAgua(),fCuerpoTabla));
                            tabla.addCell(new Phrase(""+av.getCuotaSocial(),fCuerpoTabla));
                            tabla.addCell(new Phrase(""+av.getInteres(),fCuerpoTabla));
                            tabla.addCell(new Phrase(""+av.getMultas(),fCuerpoTabla));
                            tabla.addCell(new Phrase(""+av.getCorteReposicion(),fCuerpoTabla));
                            tabla.addCell(new Phrase(""+av.getDerechoIncorporacion(),fCuerpoTabla));
                            tabla.addCell(new Phrase(""+av.getOtrosCobros(),fCuerpoTabla));
                            tabla.addCell(new Phrase(""+av.getTotalItem(),fCuerpoTabla));
                            tabla.addCell(new Phrase(av.getFechaCreacion(),fCuerpoTabla));
                        }
                        document.add(tablaTitulo);
                        document.add(tabla);
                       
                        // document.add(new Phrase("FIN",fTitulo1));
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
	

	
    /*	
    static private PdfPTable getHeader(String folio) {
       
    	PdfPTable table = new PdfPTable(3);
        try {
        	URL urlImagen = new URL(UtilSolicitud.URL_LOGOTIPO);
        	Image imagen = Image.getInstance(urlImagen);
        	
			table.setWidths(new float[] {15f, 70f, 15});
	       // table.setTotalWidth(527);
	       //table.setLockedWidth(true);
	       // table.getDefaultCell().setFixedHeight(80);
	        table.getDefaultCell().setBorder(Rectangle.NO_BORDER);            
	        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
	       // table.getDefaultCell().setPaddingTop(7.8f);
	        

	        PdfPCell cellTitulo1 = new PdfPCell(new Phrase(UtilSolicitud.TITULO_FORMULARIO1,fTitulo1));
	        cellTitulo1.setVerticalAlignment(Element.ALIGN_BOTTOM);
	        cellTitulo1.setHorizontalAlignment(Element.ALIGN_CENTER);
	        //cellTitulo.setFixedHeight(20);
	        cellTitulo1.setBorder(Rectangle.NO_BORDER);
	        cellTitulo1.setColspan(3);
	        
	        PdfPCell cellTitulo2 = new PdfPCell(new Phrase(UtilSolicitud.TITULO_FORMULARIO2,fTitulo2));
	        cellTitulo2.setVerticalAlignment(Element.ALIGN_BOTTOM);
	        cellTitulo2.setHorizontalAlignment(Element.ALIGN_CENTER);
	        //cellTitulo.setFixedHeight(20);
	        cellTitulo2.setBorder(Rectangle.NO_BORDER);
	        cellTitulo2.setColspan(3);
	        
	        PdfPCell cellLogo = new PdfPCell(imagen, true);
	        cellLogo.setPadding(3);
	        cellLogo.setFixedHeight(50);
	        cellLogo.setHorizontalAlignment(Element.ALIGN_CENTER);
	        cellLogo.setVerticalAlignment(Element.ALIGN_BOTTOM);
	        //cellLogo.setColspan(2);	       
	        cellLogo.setBorder(Rectangle.NO_BORDER);
	  
	        PdfPCell cellFolio = new PdfPCell(new Phrase(UtilSolicitud.NUM+"   "+folio,fTitulo2));
	        cellFolio.setVerticalAlignment(Element.ALIGN_MIDDLE);
	        cellFolio.setHorizontalAlignment(Element.ALIGN_RIGHT);
	        //cellTitulo.setFixedHeight(20);
	        cellFolio.setBorder(Rectangle.NO_BORDER);
	       
	        
	        table.addCell(cellTitulo1);
	        table.addCell(cellTitulo2);
	        table.addCell(new Phrase("",fTitulo1));
	        table.addCell(cellLogo);
	        table.addCell(cellFolio);
	        
        } catch (DocumentException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}      
        return table;
    }
        
        */
}
