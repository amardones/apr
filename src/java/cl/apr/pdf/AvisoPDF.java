package cl.apr.pdf;

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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class AvisoPDF {
	
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
	    
	
	static public ByteArrayOutputStream crearPdf(List<AvisoCobro> avisos){
		
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
                        document.setPageSize(PageSize.LETTER.rotate());
                        //document.setMargins(30, 30, 35,30);
                        document.newPage();
                        //document.setMargins(30, 30, 35,30);
                        //int pagina = 1;

                        //System.out.println("Creando aviso 0");
                        int i=0;
                        while(i<avisos.size()){
                            PdfPTable tableDividePagina = new PdfPTable(2);
                            tableDividePagina.setWidthPercentage(100);
                            float[] columnWidths2 = new float[] {50f, 50f};
                            try {
                                tableDividePagina.setWidths(columnWidths2);
                            } catch (DocumentException e) {e.printStackTrace();}
                            
                            PdfPCell pCell = crearAvisos(avisos.get(i));
                            pCell.setBorder(Rectangle.RIGHT);
                            pCell.setFixedHeight(PageSize.LETTER.rotate().getHeight()-100);
                            pCell.setVerticalAlignment(Element.ALIGN_TOP);
                            pCell.setPaddingRight(30);
                            tableDividePagina.addCell(pCell);
                                 
                            if((i+1) < avisos.size()){                                  
                                 pCell = crearAvisos(avisos.get(i+1));
                                 pCell.setFixedHeight(PageSize.LETTER.rotate().getHeight()-80);
                                 pCell.setBorder(Rectangle.LEFT);
                                 pCell.setPaddingLeft(30);
                                 pCell.setVerticalAlignment(Element.ALIGN_TOP);
                                 tableDividePagina.addCell(pCell);
                                
                            }else{
                                 tableDividePagina.addCell(crearAvisos(null));
                            }
                            
                            document.add(tableDividePagina);
                            i=i+2;
                            if(i < avisos.size()){                                 
                                 document.newPage();
                            }
                            
                        }
                       
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
	
	 static private PdfPCell crearAvisos(AvisoCobro aviso) {
            System.out.println("Creando aviso 2");
            PdfPCell pCell = null;
            try {
               
                if(aviso!=null){
                    PdfPCell cTituloCuenta = new PdfPCell(new Phrase("Datos de cuenta",fCuerpoCabeceraTabla));
                    //cTituloCuenta.setBackgroundColor(colorBlueLigth);
                    cTituloCuenta.setBorderColor(borderColor);
                    cTituloCuenta.setBorder(Rectangle.LEFT | Rectangle.BOTTOM);
                    cTituloCuenta.setVerticalAlignment(Element.ALIGN_BOTTOM);
                    cTituloCuenta.setHorizontalAlignment(Element.ALIGN_LEFT);                    
                    cTituloCuenta.setFixedHeight(20);
                    cTituloCuenta.setPaddingBottom(4);
                    
                    PdfPCell cTituloPeriodo = new PdfPCell(new Phrase("Detalle de sus lecturas periodo: "+aviso.getRegistroEstado().getPeriodo().getNombre(),fCuerpoCabeceraTabla));
                    //cTituloLecturas.setBackgroundColor(colorBlueLigth);
                    cTituloPeriodo.setBorderColor(borderColor);
                    cTituloPeriodo.setBorder(Rectangle.LEFT | Rectangle.BOTTOM);
                    cTituloPeriodo.setVerticalAlignment(Element.ALIGN_BOTTOM);
                    cTituloPeriodo.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cTituloPeriodo.setFixedHeight(20);
                    cTituloPeriodo.setPaddingBottom(4);
                     
                    PdfPTable table = new PdfPTable(1);
                    table.setWidthPercentage(100);
                    table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                    table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                    table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
                    table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                    
                    //Titulo
                    PdfPTable tableTitulo   = new PdfPTable(2);
                    tableTitulo.setWidths(new float[] {20f, 80f}); 
                    tableTitulo.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                    tableTitulo.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tableTitulo.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                    tableTitulo.getDefaultCell().setFixedHeight(40);
                    tableTitulo.addCell(new Phrase("APR",fTitulo1));
                    tableTitulo.addCell(new Phrase("COMITE AGUA POTABLE QUILELTO EL MANZANAL",fTitulo2));
                    
                    //Cuenta
                    PdfPTable tableHeaderCuenta   = new PdfPTable(4);
                    tableHeaderCuenta.setWidths(new float[] {15f, 50f, 15f, 20f}); 
                    tableHeaderCuenta.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                    tableHeaderCuenta.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tableHeaderCuenta.getDefaultCell().setFixedHeight(13);
                   
                    tableHeaderCuenta.addCell(new Phrase("N° Cuenta",fCuerpoTabla));
                    tableHeaderCuenta.addCell(new Phrase(": "+aviso.getRegistroEstado().getCuenta().getIdCuenta(),fCuerpoTabla));
                    tableHeaderCuenta.addCell(new Phrase("F. Emisión",fCuerpoTabla));
                    tableHeaderCuenta.addCell(new Phrase(": "+EnumFormatoFechaHora.formatoDiaMesTextoCortoAnio.format(aviso.getRegistroEstado().getPeriodo().getFechaEmision()).toUpperCase(),fCuerpoTabla));
                    
                    tableHeaderCuenta.addCell(new Phrase("Cliente",fCuerpoTabla));
                    tableHeaderCuenta.addCell(new Phrase(": "+aviso.getRegistroEstado().getCuenta().getRut().getNombre() +" "+aviso.getRegistroEstado().getCuenta().getRut().getApellido(),fCuerpoTabla));
                    tableHeaderCuenta.addCell(new Phrase("N° Aviso",fCuerpoTabla));
                    tableHeaderCuenta.addCell(new Phrase(": ",fCuerpoTabla));   
                             
                    tableHeaderCuenta.addCell(new Phrase("N° Cliente",fCuerpoTabla));
                    tableHeaderCuenta.addCell(new Phrase(": "+aviso.getRegistroEstado().getCuenta().getRut().getRut(),fCuerpoTabla));
                    tableHeaderCuenta.addCell(new Phrase("N° Medidor",fCuerpoTabla));
                    tableHeaderCuenta.addCell(new Phrase(": "+aviso.getRegistroEstado().getCuenta().getNumeroMedidor().getNumeroMedidor(),fCuerpoTabla));
                    
                    tableHeaderCuenta.addCell(new Phrase("Dirección",fCuerpoTabla));
                    tableHeaderCuenta.addCell(new Phrase(": "+aviso.getRegistroEstado().getCuenta().getDireccion(),fCuerpoTabla));
                    tableHeaderCuenta.addCell(new Phrase("",fCuerpoTabla));
                    tableHeaderCuenta.addCell(new Phrase("",fCuerpoTabla));
                    
                    //tabla periodo y lectura
                    PdfPTable tablePeriodo   = new PdfPTable(4);
                    tablePeriodo.setWidths(new float[] {20f, 40f, 20f, 20f}); 
                    tablePeriodo.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                    tablePeriodo.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tablePeriodo.getDefaultCell().setFixedHeight(13);
                   
                    tablePeriodo.addCell(new Phrase("F. Periodo",fCuerpoTabla));
                    tablePeriodo.addCell(new Phrase(": "+EnumFormatoFechaHora.formatoDiaMesTextoCortoAnio.format(aviso.getRegistroEstado().getPeriodo().getFechaInicio()).toUpperCase() +" al "+EnumFormatoFechaHora.formatoDiaMesTextoCortoAnio.format(aviso.getRegistroEstado().getPeriodo().getFechaInicio()).toUpperCase(),fCuerpoTabla));
                    tablePeriodo.addCell(new Phrase("Valor cargo fijo",fCuerpoTabla));
                    tablePeriodo.addCell(new Phrase(": $ "+NumeroFormato.formatearNumeroPesos(aviso.getRegistroEstado().getPeriodo().getIdValoresParametricos().getValorCargoFijo()),fCuerpoTabla));
                    
                    tablePeriodo.addCell(new Phrase("Lectura actual",fCuerpoTabla));
                    tablePeriodo.addCell(new Phrase(": "+aviso.getRegistroEstado().getEstadoActual(),fCuerpoTabla));
                    tablePeriodo.addCell(new Phrase("MT3 fijos",fCuerpoTabla));
                    tablePeriodo.addCell(new Phrase(": "+aviso.getRegistroEstado().getPeriodo().getIdValoresParametricos().getM3Fijos(),fCuerpoTabla));   
                             
                    tablePeriodo.addCell(new Phrase("Lectura anterior",fCuerpoTabla));
                    tablePeriodo.addCell(new Phrase(": "+aviso.getRegistroEstado().getEstadoAnterior(),fCuerpoTabla));
                    tablePeriodo.addCell(new Phrase("Valor 1 MT3",fCuerpoTabla));
                    tablePeriodo.addCell(new Phrase(": $ "+NumeroFormato.formatearNumeroPesos(aviso.getRegistroEstado().getPeriodo().getIdValoresParametricos().getValorM3()),fCuerpoTabla));
                    int m3Adicionales = 0;
                    if(aviso.getRegistroEstado().getMetrosCubicos() > aviso.getRegistroEstado().getPeriodo().getIdValoresParametricos().getM3Fijos()){
                        m3Adicionales = aviso.getRegistroEstado().getMetrosCubicos() - aviso.getRegistroEstado().getPeriodo().getIdValoresParametricos().getM3Fijos();
                    }
                    tablePeriodo.addCell(new Phrase("Consumo (MT3)",fCuerpoTabla));
                    tablePeriodo.addCell(new Phrase(": "+aviso.getRegistroEstado().getMetrosCubicos(),fCuerpoTabla));
                    tablePeriodo.addCell(new Phrase("MT3 adicionales",fCuerpoTabla));
                    tablePeriodo.addCell(new Phrase(": "+m3Adicionales,fCuerpoTabla));
                    
                    
                    //tabla lista pagos
                    PdfPTable tablePagos   = new PdfPTable(3);
                    tablePagos.setWidths(new float[] {50f, 30f, 20f}); 
                    tablePagos.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                    tablePagos.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
                    //tablePagos.getDefaultCell().setFixedHeight(13);
                   
                    PdfPCell cT01 = new PdfPCell(new Phrase("Detalle pagos",fCuerpoCabeceraTabla));
                    //cT01.setBackgroundColor(colorBlueLigth);
                    cT01.setBorderColor(borderColor);
                    cT01.setBorder(Rectangle.LEFT | Rectangle.BOTTOM);
                    cT01.setVerticalAlignment(Element.ALIGN_BOTTOM);
                    cT01.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cT01.setFixedHeight(20);
                    cT01.setPaddingBottom(4);
                    
                    
                    PdfPCell cT02 = new PdfPCell(new Phrase("",fCuerpoCabeceraTabla));
                    //cT02.setBackgroundColor(colorBlueLigth);
                    cT02.setBorderColor(borderColor);
                    cT02.setBorder(Rectangle.BOTTOM);
                    cT02.setVerticalAlignment(Element.ALIGN_BOTTOM);
                    cT02.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cT02.setFixedHeight(20);
                    cT02.setPaddingBottom(4);
                    
                    PdfPCell cT03 = new PdfPCell(new Phrase("Monto ($)",fCuerpoCabeceraTabla));
                    //cT03.setBackgroundColor(colorBlueLigth);
                    cT03.setBorderColor(borderColor);
                    cT03.setBorder(Rectangle.BOTTOM);
                    cT03.setVerticalAlignment(Element.ALIGN_BOTTOM);
                    cT03.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    cT03.setFixedHeight(20);
                    cT03.setPaddingBottom(4);
                    
                    tablePagos.addCell(cT01);
                    tablePagos.addCell(cT02);
                    tablePagos.addCell(cT03);
                    
                    PdfPCell cPag01, cPag02, cPag03;
                    DetalleAvisoCobro det;
                    String info = "";
                    for(int i=0; i<aviso.getDetalleAvisoCobroList().size(); ++i){
                        info = "";
                        det = aviso.getDetalleAvisoCobroList().get(i);
                        cPag01 = new PdfPCell(new Phrase(det.getIdTipoCobro().getNombre(),fCuerpoTabla));
                        cPag01.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cPag01.setHorizontalAlignment(Element.ALIGN_LEFT);   
                        cPag01.setBorder(Rectangle.NO_BORDER);
                        //cPag01.setPadding(5);
                        
                        if(det.getIdDetalleAvisoCobroAnt() > 0){
                            info += "Pendiente ";
                        }
//                        if(det.getDescuento() > 0){
//                            info += "SubTotal: "+det.getSubTotal() + " | Descuento: "+det.getDescuento();
//                        }
                        
                        cPag02 = new PdfPCell(new Phrase(info,fCuerpoTabla));
                        cPag02.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cPag02.setHorizontalAlignment(Element.ALIGN_LEFT); 
                        cPag02.setBorder(Rectangle.NO_BORDER);
                        //cPag02.setPadding(5);
                        
                        cPag03 = new PdfPCell(new Phrase(""+NumeroFormato.formatearNumeroPesos(det.getSubTotal()),fCuerpoTabla));
                        cPag03.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cPag03.setHorizontalAlignment(Element.ALIGN_RIGHT);   
                        cPag03.setBorder(Rectangle.NO_BORDER);
                        cPag03.setPaddingRight(5);
                        //cPag03.setPadding(5);
                        
                        tablePagos.addCell(cPag01);
                        tablePagos.addCell(cPag02);
                        tablePagos.addCell(cPag03);
                    }
                    int maxRow = 12;
                    if(aviso.getDetalleAvisoCobroList().size() < maxRow){
                         //Espacios
                        for(int j=0; j <(maxRow-aviso.getDetalleAvisoCobroList().size()); ++j){
                            tablePagos.addCell(new Phrase(" ",fCuerpoTabla));
                            tablePagos.addCell(new Phrase(" ",fCuerpoTabla));
                            tablePagos.addCell(new Phrase(" ",fCuerpoTabla)); 
                        }
                    }
                    /*
                     //Espacios
                    tablePagos.addCell(new Phrase(" ",fCuerpoTabla));
                    tablePagos.addCell(new Phrase(" ",fCuerpoTabla));
                    tablePagos.addCell(new Phrase(" ",fCuerpoTabla));
                    */
                    
                    URL urlImagen;
                    Image imagen = null;
                    try {
                        BufferedImage bi = BarChartAviso.crearBarchart();
                                
                        //urlImagen = new URL("http://localhost/APR/resources/images/default_barchart.png");
                        //imagen = Image.getInstance(urlImagen);
                        
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ImageIO.write(bi, "png", baos);
                        imagen = Image.getInstance(baos.toByteArray());

                    } catch (MalformedURLException ex) {
                        ex.printStackTrace();
                    } catch (BadElementException ex) {
                          ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        //Logger.getLogger(AvisoPDF.class.getName()).log(Level.SEVERE, null, ex);
                    }
                   
                
                    /*
                    cPag01 = new PdfPCell(new Phrase("Detalle de sus consumos",fCuerpoCabeceraTabla));
                    cPag01.setBorderColor(borderColor);
                    cPag01.setVerticalAlignment(Element.ALIGN_BOTTOM);
                    cPag01.setHorizontalAlignment(Element.ALIGN_LEFT);   
                    cPag01.setBorder(Rectangle.LEFT | Rectangle.BOTTOM);
                    cPag01.setPaddingBottom(5);
                       
                    tablePagos.addCell(cPag01);
                    tablePagos.addCell(new Phrase(" ",fCuerpoTabla));
                    tablePagos.addCell(new Phrase(" ",fCuerpoTabla));
                    */
                    
                    PdfPCell cGrafico = new PdfPCell(imagen, true);
                    //cT03.setBackgroundColor(colorBlueLigth);
                    cGrafico.setBorderColor(borderColor);
                    cGrafico.setBorder(Rectangle.NO_BORDER);
                    cGrafico.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cGrafico.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cGrafico.setRowspan(10); 
                    cGrafico.setPadding(5);
                    
                    //total pendiente
                    cPag02 = new PdfPCell(new Phrase("TOTAL PENDIENTE",fCuerpoTabla));
                    cPag02.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cPag02.setHorizontalAlignment(Element.ALIGN_LEFT); 
                    cPag02.setBorder(Rectangle.NO_BORDER);
                    cPag02.setPadding(5);
                    cPag02.setPaddingLeft(15);
                        
                    cPag03 = new PdfPCell(new Phrase("$ "+NumeroFormato.formatearNumeroPesos(aviso.getTotalPendiente()),fCuerpoTabla));
                    cPag03.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cPag03.setHorizontalAlignment(Element.ALIGN_RIGHT);   
                    cPag03.setBorder(Rectangle.NO_BORDER);
                    cPag03.setPaddingRight(5);                    
                    
                    tablePagos.addCell(cGrafico);
                    tablePagos.addCell(cPag02);                   
                    tablePagos.addCell(cPag03);
                    
                     //total periodo
                    cPag02 = new PdfPCell(new Phrase("SUB TOTAL PERIODO",fCuerpoTabla));
                    cPag02.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cPag02.setHorizontalAlignment(Element.ALIGN_LEFT); 
                    cPag02.setBorder(Rectangle.NO_BORDER);
                    cPag02.setPaddingLeft(5);
                    cPag02.setPaddingLeft(15);
                        
                    cPag03 = new PdfPCell(new Phrase("$ "+NumeroFormato.formatearNumeroPesos(aviso.getSubTotalPeriodo()),fCuerpoTabla));
                    cPag03.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cPag03.setHorizontalAlignment(Element.ALIGN_RIGHT);   
                    cPag03.setBorder(Rectangle.NO_BORDER);
                    cPag03.setPaddingRight(5);
                        
                    tablePagos.addCell(cPag02);                   
                    tablePagos.addCell(cPag03); 
                    
                     //total periodo
                    cPag02 = new PdfPCell(new Phrase("DESCUENTO PERIODO",fCuerpoTabla));
                    cPag02.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cPag02.setHorizontalAlignment(Element.ALIGN_LEFT); 
                    cPag02.setBorder(Rectangle.NO_BORDER);
                    cPag02.setPaddingLeft(5);
                    cPag02.setPaddingLeft(15);
                        
                    cPag03 = new PdfPCell(new Phrase("$ "+NumeroFormato.formatearNumeroPesos(aviso.getDescuentoPeriodo()*-1),fCuerpoTabla));
                    cPag03.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cPag03.setHorizontalAlignment(Element.ALIGN_RIGHT);   
                    cPag03.setBorder(Rectangle.NO_BORDER);
                    cPag03.setPaddingRight(5);
                        
                    tablePagos.addCell(cPag02);                   
                    tablePagos.addCell(cPag03);
                    
                     //total periodo
                    cPag02 = new PdfPCell(new Phrase("TOTAL PERIODO",fCuerpoTabla));
                    cPag02.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cPag02.setHorizontalAlignment(Element.ALIGN_LEFT); 
                    cPag02.setBorder(Rectangle.NO_BORDER);
                    cPag02.setPaddingLeft(5);
                    cPag02.setPaddingLeft(15);
                        
                    cPag03 = new PdfPCell(new Phrase("$ "+NumeroFormato.formatearNumeroPesos(aviso.getTotalPeriodo()),fCuerpoTabla));
                    cPag03.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cPag03.setHorizontalAlignment(Element.ALIGN_RIGHT);   
                    cPag03.setBorder(Rectangle.NO_BORDER);
                    cPag03.setPaddingRight(5);
                        
                    tablePagos.addCell(cPag02);                   
                    tablePagos.addCell(cPag03); 
                    
                    tablePagos.addCell(new Phrase(" ",fCuerpoTabla));
                    tablePagos.addCell(new Phrase(" ",fCuerpoTabla));
                    
                    PdfPCell cTR01 = new PdfPCell(new Phrase("TOTAL A PAGAR",fCuerpoCabeceraTabla));
                    //cTR02.setBackgroundColor(colorBlueLigth);
                    cTR01.setBorderColor(borderColor);
                    cTR01.setBorder(Rectangle.LEFT | Rectangle.BOTTOM | Rectangle.TOP );
                    cTR01.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cTR01.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cTR01.setFixedHeight(20);
                    cTR01.setPadding(5);
                    cTR01.setPaddingLeft(15);
                    
                    PdfPCell cTR02 = new PdfPCell(new Phrase("$ "+NumeroFormato.formatearNumeroPesos(aviso.getTotal()),fCuerpoCabeceraTabla));
                    //cTR02.setBackgroundColor(colorBlueLigth);
                    cTR02.setBorderColor(borderColor);
                    cTR02.setBorder(Rectangle.BOTTOM | Rectangle.TOP | Rectangle.RIGHT);
                    cTR02.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cTR02.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    cTR02.setFixedHeight(20);
                    cTR02.setPadding(5);
                   
                    PdfPCell cTR03 = new PdfPCell(new Phrase("VENCIMIENTO",fCuerpoCabeceraTabla));
                    //cTR03.setBackgroundColor(colorBlueLigth);
                    cTR03.setBorderColor(borderColor);
                    cTR03.setBorder(Rectangle.LEFT | Rectangle.BOTTOM );
                    cTR03.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cTR03.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cTR03.setFixedHeight(20);
                    cTR03.setPadding(5);
                    cTR03.setPaddingLeft(15);
                    
                    PdfPCell cTR04 = new PdfPCell(new Phrase(""+EnumFormatoFechaHora.formatoDiaMesTextoCortoAnio.format(aviso.getRegistroEstado().getPeriodo().getFechaVencimiento()).toUpperCase(),fCuerpoCabeceraTabla));
                    //cTR04.setBackgroundColor(colorBlueLigth);
                    cTR04.setBorderColor(borderColor);
                    cTR04.setBorder(Rectangle.BOTTOM | Rectangle.RIGHT);
                    cTR04.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cTR04.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    cTR04.setFixedHeight(20);
                    cTR04.setPadding(5);
                    

                    tablePagos.addCell(cTR01);
                    tablePagos.addCell(cTR02);
                    tablePagos.addCell(cTR03);
                    tablePagos.addCell(cTR04);
                    
                    table.setExtendLastRow(true);
                    table.addCell(tableTitulo);
                    table.addCell(cTituloCuenta);                    
                    table.addCell(tableHeaderCuenta);                   
                    table.addCell(cTituloPeriodo);
                    table.addCell(tablePeriodo);
                    table.addCell(tablePagos); 
                    //table.addCell(tableResumen); 
                                        
                    table.addCell(new Phrase(" ",fCuerpoCabeceraTabla));                   
                    table.addCell(new Phrase("Fono Consultas 76310437 - 97667763 ",fCuerpoTabla));
                    table.addCell(new Phrase("Recuerde mantener sus cuentas al día para brindarle un mejor servicio",fCuerpoTabla));
                    table.addCell(new Phrase("COMITÉ AGUA RURAL QUILELTO EL MANZANAL ",fCuerpoTabla));
                    table.addCell(new Phrase("ATENCIÓN: LUNES, MIERCOLES Y VIERNES DE 09:00 a 13:00 HRS.",fCuerpoTablaBold));
                    table.addCell(new Phrase("RECUERDE PROTEGER SU MEDIDOR DE LAS HELADAS",fCuerpoTablaBold));
                    table.addCell(new Phrase("",fCuerpoTablaBold));
                                       //  
                    //return
                    pCell = new PdfPCell(table);
                }
            } catch (DocumentException ex) {
                Logger.getLogger(AvisoPDF.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if(pCell == null){
                pCell = new PdfPCell(new Phrase(""));
                pCell.setBorder(Rectangle.NO_BORDER);
            }
             return pCell;
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
