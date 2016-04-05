/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.apr.pdf.servlet;
import cl.apr.beans.BarChartItem;
import cl.apr.beans.ItemReporte;
import cl.apr.beans.SubsidioReporte;
import cl.apr.controller.ReportesController;
import cl.apr.entity.AvisoCobro;
import cl.apr.pdf.AvisoPDF;
import cl.apr.pdf.ReporteIngresosPDF;
import cl.apr.pdf.ReporteSubsidioPDF;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author hmardones
 */
@WebServlet(name = "verReporteIngresosPDF", urlPatterns = {"/verReporteIngresosPDF"})
public class verReporteIngresosPDF extends HttpServlet {

    @Inject
    private ReportesController reportesController;
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
       String mensajeError = "SE PRODUJO UN ERROR AL INTENTAR DESCARGAR VISTA DE REGISTRO EN PDF.";
        try{
                
                 
                request.setCharacterEncoding("UTF-8");
                
                java.io.ByteArrayOutputStream baos=null;

                List<ItemReporte> list = reportesController.getItems();
                ItemReporte totalIngreso = new ItemReporte();
                totalIngreso=reportesController.getItemResumen();
               
                baos =  ReporteIngresosPDF.crearPdf(list,totalIngreso);

                if(baos != null)
                {
                        // setting some response headers
                        if(baos.size()==0){
                                PrintWriter out = response.getWriter();
                                out.println(mensajeError);
                                out.close();
                                return;
                        }
                        response.setHeader("Expires", "0");
                        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
                        response.setHeader("Pragma", "public");
                        response.setHeader("Content-Disposition","inline; filename=ver.pdf");
                        response.setContentType("application/pdf");
            // the contentlength
                        response.setContentLength(baos.size());
            // write ByteArrayOutputStream to the ServletOutputStream
            OutputStream os = response.getOutputStream();
            baos.writeTo(os);
            os.flush();
            os.close();
                }else{
                        PrintWriter out = response.getWriter();
                        out.println(mensajeError);
                        out.close();
                }			
        }catch(Exception e){	
                e.printStackTrace();
                PrintWriter out = response.getWriter();
                out.println(mensajeError);
                out.close();
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
