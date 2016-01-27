/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.apr.xls;

import cl.apr.beans.SubsidioReporte;
import cl.apr.controller.ReportesController;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
 
import jxl.read.biff.BiffException;
import jxl.write.Boolean;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;


/**
 *
 * @author hmardones
 */
@WebServlet(name = "verReporteSubsidios", urlPatterns = {"/verReporteSubsidios"})
public class ReporteLibroSubsidioXLS extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    @Inject
    private ReportesController reportesController;
     
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("[ReporteLibroSubsidiosXLS] - inicio Servlet ReporteLibroSubsidiosXLS Excel...");
                
        ServletOutputStream outputStream    = response.getOutputStream();
        String RUTA_REPORTES                = getServletConfig().getServletContext().getRealPath(File.separator  + "plantillas");
        //String RUTA_REPORTES                = getServletConfig().getServletContext().getRealPath(File.separator + "WEB-INF" + File.separator + "Plantillas");
        try{
            
                    
            outputStream = response.getOutputStream();
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            WorkbookSettings wbSettings = new WorkbookSettings();
            WritableCell cell;

            wbSettings.setPropertySets(true);

            Workbook workbook = Workbook.getWorkbook(new File(RUTA_REPORTES+ File.separator +"reporteSubsidios.xls"));

            WritableWorkbook copy = Workbook.createWorkbook(output, workbook, wbSettings);                    
            WritableSheet sheet = copy.getSheet(0);

            WritableFont fuente  = new WritableFont(WritableFont.ARIAL);
            WritableFont fuenteEnc  = new WritableFont(WritableFont.ARIAL);
            fuenteEnc.setBoldStyle(WritableFont.BOLD);

            //formato texto             
            WritableCellFormat formatoTexto = new WritableCellFormat(fuente);
            formatoTexto.setBackground(Colour.WHITE);
            formatoTexto.setAlignment(Alignment.CENTRE);
            formatoTexto.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);

            //formato encabezado             
            WritableCellFormat formatoEnc = new WritableCellFormat(fuenteEnc);
            formatoEnc.setBackground(Colour.GRAY_25);
            formatoEnc.setAlignment(jxl.format.Alignment.LEFT);
            formatoEnc.setBorder(Border.NONE, BorderLineStyle.NONE);

            //formato cantidad
            WritableCellFormat formatoQty = new WritableCellFormat(fuente);
            formatoQty.setBackground(Colour.WHITE);
            formatoQty.setAlignment(Alignment.RIGHT);
            formatoQty.setBorder(Border.ALL, BorderLineStyle.THIN);   

            String value= new String();
            Number number = null;
            Number number2 = null;
            Label label =null;



           int k=0;
           
           List<SubsidioReporte> items = this.reportesController.getItemsSubsudioReporte();
            

            if(items != null){  
                SubsidioReporte ir;            
                for (int i = 0; i < items.size(); i++) {
                  ir = items.get(i);
                    k=0;
                    label = new Label(k, 1+i,""+(i+1),formatoTexto); 
                    sheet.addCell(label);

                    label = new Label(++k, 1+i,ir.getIdPeriodo(),formatoTexto); 
                    sheet.addCell(label);
                    
                    label = new Label(++k, 1+i,ir.getIdcuenta(),formatoTexto); 
                    sheet.addCell(label);
                    
//                     label = new Label(++k, 1+i,ir.getIdPago().toString(),formatoTexto); 
//                    sheet.addCell(label);
                    
                    label = new Label(++k, 1+i,ir.getFechaCreacion().toString(),formatoTexto); 
                    sheet.addCell(label);
                    
                    label = new Label(++k, 1+i,ir.getDescuento_periodo().toString(),formatoTexto); 
                    sheet.addCell(label);
                    
                    
                }
            }
           
            copy.write();
            copy.close();
            workbook.close();

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH_mm");

            String reportDate = df.format(new Date());


            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=\"Report_"+reportDate+".xls\"");

            outputStream = response.getOutputStream();
            outputStream.write(output.toByteArray());

            outputStream.flush();
            outputStream.close();

            output.close();
            output.flush();

            return;
        }catch (IOException ex) {
            System.out.println("Error al crear el fichero.");
            response.getOutputStream().write(("Error download report").getBytes());
            return;
        }catch (WriteException ex) {
            System.out.println("Error al escribir el fichero.");
             response.getOutputStream().write(("Error download report").getBytes());
            return;
        }catch (BiffException e) {
            System.out.println("Error al escribir el fichero." + e.toString());
             response.getOutputStream().write(("Error download report").getBytes());
            return;
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
