/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.apr.pdf.servlet;
import cl.apr.beans.BarChartItem;
import cl.apr.entity.AvisoCobro;
import cl.apr.entity.DatosComite;
import cl.apr.pdf.AvisoPDF;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author hmardones
 */
@WebServlet(name = "verAviso", urlPatterns = {"/verAviso"})
public class verAviso extends HttpServlet {

    @EJB
    private cl.apr.facade.AvisoCobroFacade ejbFacade;
    @EJB
    private cl.apr.facade.DatosComiteFacade comiteFacade; 
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
                Integer idPeriodo = (Integer)request.getAttribute("idPeriodo");
                Integer idCuenta = (Integer)request.getAttribute("idCuenta");
                 
                request.setCharacterEncoding("UTF-8");
                //String json = (String)request.getParameter("registro");
                //String nombre = (String)request.getParameter("nombre");
                //System.out.println("JSON:"+json);
                java.io.ByteArrayOutputStream baos=null;

                List<AvisoCobro> list = new ArrayList<AvisoCobro>();
                if(ejbFacade != null && idPeriodo != null){
                    if(idCuenta != null && idCuenta != -1){
                        list.add(ejbFacade.getAviso(idPeriodo, idCuenta));
                    }else{
                        list = ejbFacade.getAvisosPorPeriodo(idPeriodo);
                    }
                }
                List<DatosComite> datosList=new ArrayList<>();
                if(comiteFacade != null){
                    datosList=comiteFacade.findAll();
                }
//                list.add(new AvisoCobro());
//                list.add(new AvisoCobro());
//                list.add(new AvisoCobro());
//                list.add(new AvisoCobro());
//                list.add(new AvisoCobro());
                List<BarChartItem> barChartItems;
                        
                HashMap<Integer,List<BarChartItem>> hmapBarChartItems = new HashMap<Integer, List<BarChartItem> >();
                for (AvisoCobro aviso : list) {
                   barChartItems = ejbFacade.obtenerRegistroEstadoHistoricos(aviso.getAvisoCobroPK().getIdCuenta(), idPeriodo);
                   hmapBarChartItems.put(aviso.getAvisoCobroPK().getIdCuenta(), barChartItems);
                }
                baos =  AvisoPDF.crearPdf(list,datosList, hmapBarChartItems);

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
