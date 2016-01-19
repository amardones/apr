package cl.apr.controller;

import cl.apr.entity.DetalleAvisoCobro;
import cl.apr.controller.util.JsfUtil;
import cl.apr.controller.util.JsfUtil.PersistAction;
import cl.apr.facade.DetalleAvisoCobroFacade;
import cl.apr.beans.ItemReporte;
import cl.apr.beans.SubsidioReporte;
import cl.apr.entity.Cuenta;
import com.sun.jmx.remote.internal.ArrayQueue;
import java.io.IOException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Named("reportesController")
@SessionScoped
public class ReportesController implements Serializable {

    @EJB
    private cl.apr.facade.DetalleAvisoCobroFacade ejbFacade;
    @EJB
    private cl.apr.facade.PeriodoFacade pFacade;
    @EJB
    private cl.apr.facade.CuentaFacade cFacade;
    @EJB
    private cl.apr.facade.PagoFacade pagoFacade;
    private List<ItemReporte> items = null;
    private ItemReporte itemResumen=new ItemReporte();
    private List<SubsidioReporte> itemsSubsudioReporte=null;
    private SubsidioReporte subsidioReporte=new SubsidioReporte();
    private Date fechaInicio;
    private Date fechaFin;
    private int totalSubsidio;
    
    
    public void limpiaFecha(){
        fechaFin=null;
    }
    public String buscaIdCuenta(Integer id){  
        return cFacade.find(id).getIdCuenta()+":"+
        cFacade.find(id).getRut().getNombre()+" "+
        cFacade.find(id).getRut().getApellido();
    }
    public String buscaIdPeriodo(Integer id){
        return pFacade.find(id).getNombre();
    }

    public List<SubsidioReporte> getItemsSubsudioReporte() {
        return itemsSubsudioReporte;
    }

    public void setItemsSubsudioReporte(List<SubsidioReporte> itemsSubsudioReporte) {
        this.itemsSubsudioReporte = itemsSubsudioReporte;
    }

    public SubsidioReporte getSubsidioReporte() {
        return subsidioReporte;
    }

    public void setSubsidioReporte(SubsidioReporte subsidioReporte) {
        this.subsidioReporte = subsidioReporte;
    }

    public int getTotalSubsidio() {
        return totalSubsidio;
    }

    public void setTotalSubsidio(int totalSubsidio) {
        this.totalSubsidio = totalSubsidio;
    }
    

    public List<ItemReporte> getItems() {
//        items= new ArrayList<>();
//        itemResumen=new ItemReporte();
//        if(fechaInicio!=null&&fechaFin==null){
//            items= pagoFacade.reporteLibroIngreso(fechaInicio , fechaInicio);
//        }
//        if(fechaInicio!=null&&fechaFin!=null){
//            System.out.println("Fecha: "+fechaInicio);
//            items= pagoFacade.reporteLibroIngreso(fechaInicio , fechaFin);
//        }  
//        for (ItemReporte item : items) {
//            itemResumen.setConsumoAgua(itemResumen.getConsumoAgua()+item.getConsumoAgua());
//            itemResumen.setCorteReposicion(itemResumen.getCorteReposicion()+item.getCorteReposicion());
//            itemResumen.setCuotaSocial(itemResumen.getCuotaSocial()+item.getCuotaSocial());
//            itemResumen.setDerechoIncorporacion(itemResumen.getDerechoIncorporacion()+item.getDerechoIncorporacion());
//            itemResumen.setInteres(itemResumen.getInteres()+item.getInteres());
//            itemResumen.setMultas(itemResumen.getMultas()+item.getMultas());
//            itemResumen.setOtrosCobros(itemResumen.getOtrosCobros()+item.getOtrosCobros());
//            itemResumen.setTotalItem(itemResumen.getTotalItem()+item.getTotalItem());
//        }
        return items;
    }

    
     public void buscarIngresos() {
        items= new ArrayList<>();
        itemResumen=new ItemReporte();
        if(fechaInicio!=null&&fechaFin==null){
            items= pagoFacade.reporteLibroIngreso(fechaInicio , fechaInicio);
        }
        if(fechaInicio!=null&&fechaFin!=null){
            System.out.println("Fecha: "+fechaInicio);
            items= pagoFacade.reporteLibroIngreso(fechaInicio , fechaFin);
        }  
        for (ItemReporte item : items) {
            itemResumen.setConsumoAgua(itemResumen.getConsumoAgua()+item.getConsumoAgua());
            itemResumen.setCorteReposicion(itemResumen.getCorteReposicion()+item.getCorteReposicion());
            itemResumen.setCuotaSocial(itemResumen.getCuotaSocial()+item.getCuotaSocial());
            itemResumen.setDerechoIncorporacion(itemResumen.getDerechoIncorporacion()+item.getDerechoIncorporacion());
            itemResumen.setInteres(itemResumen.getInteres()+item.getInteres());
            itemResumen.setMultas(itemResumen.getMultas()+item.getMultas());
            itemResumen.setOtrosCobros(itemResumen.getOtrosCobros()+item.getOtrosCobros());
            itemResumen.setTotalItem(itemResumen.getTotalItem()+item.getTotalItem());
        }
        fechaFin=null;
        fechaInicio=null;
    }
     public void reporteSubsidios(){
        itemsSubsudioReporte= new ArrayList<>();
        totalSubsidio=0;
        if(fechaInicio!=null&&fechaFin==null){
            itemsSubsudioReporte= pagoFacade.reporteLibroSubsidio(fechaInicio , fechaInicio);
        }
        if(fechaInicio!=null&&fechaFin!=null){
            System.out.println("Fecha: "+fechaInicio);
            itemsSubsudioReporte= pagoFacade.reporteLibroSubsidio(fechaInicio , fechaFin);
        }
        for (SubsidioReporte item : itemsSubsudioReporte) {
            totalSubsidio=totalSubsidio+item.getDescuento_periodo();
        }
        fechaFin=null;
        fechaInicio=null;
     }
     /*public Integer totalSubsidio(){
         for (SubsidioReporte item : itemsSubsudioReporte) {
            subsidioReporte.setDescuento_periodo(subsidioReporte.getDescuento_periodo()+item.getDescuento_periodo());
        }
         return subsidioReporte.getDescuento_periodo();
     }*/
     
     public List<ItemReporte> getItemsBusqueda() {
         return items;
     }
     
    public void setItems(List<ItemReporte> items) {
        this.items = items;
    }

    public ItemReporte getItemResumen() {
        return itemResumen;
    }

    public void setItemResumen(ItemReporte itemResumen) {
        this.itemResumen = itemResumen;
    }
    
    
    public boolean permiteExportarExcel(){        
        if( items != null &&  items.size()>0)
            return true;
        
        return false;
    }
    
    public boolean permiteExportarExcelSubsidio(){        
        if( itemsSubsudioReporte != null &&  itemsSubsudioReporte.size()>0)
            return true;
        
        return false;
    }
    public ReportesController() {
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

   

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }
    
    
//    private DetalleAvisoCobroFacade getFacade() {
//        return ejbFacade;
//    }
//  

//    public List<itemReporte> getItems() {
//        if (items == null) {
//            items = getFacade().findAll();
//        }
//        return items;
//    }

//    
//    public DetalleAvisoCobro getDetalleAvisoCobro(java.lang.Integer id) {
//        return getFacade().find(id);
//    }
//
//    public List<DetalleAvisoCobro> getItemsAvailableSelectMany() {
//        return getFacade().findAll();
//    }

//    public List<itemReporte> getItemsAvailableSelectOne() {
//        return getFacade().findAll();
//    }
   

  /* @FacesConverter(forClass = DetalleAvisoCobro.class)
    public static class DetalleAvisoCobroControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ReportesController controller = (ReportesController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "detalleAvisoCobroController");
            return controller.getDetalleAvisoCobro(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof DetalleAvisoCobro) {
                DetalleAvisoCobro o = (DetalleAvisoCobro) object;
                return getStringKey(o.getIdDetalleAvisoCobro());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), DetalleAvisoCobro.class.getName()});
                return null;
            }
        }

    }*/
    
    
      public void verReporteIngresos() {
        try {
            if(items != null && items.size() > 0){
                FacesContext ctx = FacesContext.getCurrentInstance();
                ExternalContext ectx = ctx.getExternalContext();
                HttpServletRequest request = (HttpServletRequest) ectx.getRequest();
                HttpServletResponse response = (HttpServletResponse) ectx.getResponse();
                RequestDispatcher dispatcher = request.getRequestDispatcher("/verReporteIngresos");
               // request.setAttribute("idPeriodo", periodoController.getSelected().getIdPeriodo());
                dispatcher.forward(request, response);
                ctx.responseComplete();
                System.out.println("call servlet");
            }
        } catch (ServletException ex) {
            Logger.getLogger(AvisoCobroController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AvisoCobroController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      
      public void verReporteSubsidios() {
        try {
            if(itemsSubsudioReporte != null && itemsSubsudioReporte.size() > 0){
                FacesContext ctx = FacesContext.getCurrentInstance();
                ExternalContext ectx = ctx.getExternalContext();
                HttpServletRequest request = (HttpServletRequest) ectx.getRequest();
                HttpServletResponse response = (HttpServletResponse) ectx.getResponse();
                RequestDispatcher dispatcher = request.getRequestDispatcher("/verReporteSubsidios");
               // request.setAttribute("idPeriodo", periodoController.getSelected().getIdPeriodo());
                dispatcher.forward(request, response);
                ctx.responseComplete();
                System.out.println("call servlet");
            }
        } catch (ServletException ex) {
            Logger.getLogger(AvisoCobroController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AvisoCobroController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
