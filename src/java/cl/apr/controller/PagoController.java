package cl.apr.controller;

import cl.apr.entity.Pago;
import cl.apr.controller.util.JsfUtil;
import cl.apr.controller.util.JsfUtil.PersistAction;
import cl.apr.entity.DetalleAvisoCobro;
import cl.apr.entity.Periodo;
import cl.apr.facade.PagoFacade;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;


@Named("pagoController")
@SessionScoped
public class PagoController implements Serializable {


    @EJB private cl.apr.facade.PagoFacade ejbFacade;
    @EJB private cl.apr.facade.DetalleAvisoCobroFacade ejbFacadeDetalleAviso;
    
    
     
    private List<Pago> items = null;
    private Pago selected;    
    private List<DetalleAvisoCobro> itemsDetalleAvisos = null;
    private List<DetalleAvisoCobro> detalleAvisoPagos = null;
    private int total;
    

    @Inject
    private CuentaController cuentaController;
    @Inject
    private PeriodoController periodoController;
    @Inject
    private TipoCobroController tipoCobroController;

    public List<DetalleAvisoCobro> getDetalleAvisoPagos() {
        return detalleAvisoPagos;
    }

    public void setDetalleAvisoPagos(List<DetalleAvisoCobro> detalleAvisoPagos) {
        this.detalleAvisoPagos = detalleAvisoPagos;
    }

    final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000;
   
   
    public List<DetalleAvisoCobro> getItemsDetalleAvisos() {
        if(itemsDetalleAvisos == null){
            itemsDetalleAvisos= ejbFacadeDetalleAviso.getDetalleAvisoCobroDisponibles(cuentaController.getSelected().getIdCuenta());
            for(int i=0;i<itemsDetalleAvisos.size();i++){
                System.out.println("item  :"+itemsDetalleAvisos.get(i).getIdTipoCobro().getNombre());;
            }
        }
        return itemsDetalleAvisos;
    }

    public PagoController() {
    }

    public Pago getSelected() {
        return selected;
    }

    public void setSelected(Pago selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {        
        cuentaController.setSelected(null);
        selected = new Pago();
        selected.setFechaCreacion(new Date());
    }
    

    private PagoFacade getFacade() {
        return ejbFacade;
    }
//    public List<DetalleAvisoCobro> getDetalleAvisoCobroDisponibles() {        
//        return ejbFacade.getDetalleAvisoCobroDisponibles(selected.getIdCuenta().getIdCuenta());
//    }
    

    public Pago prepareCreate() {
        selected = new Pago();
        initializeEmbeddableKey();
        itemsDetalleAvisos = null;
        detalleAvisoPagos=null;
        return selected;
    }

    public void create() {
        selected.setIdCuenta(cuentaController.getSelected());
        selected.setDetalleAvisoCobroList(detalleAvisoPagos);
        
        for(int i=0;i<detalleAvisoPagos.size();i++){
            detalleAvisoPagos.get(i).setPagado(true);
            //detalleAvisoPagos.get(i).getCobroCuotaList().get(i).
            ejbFacadeDetalleAviso.edit(detalleAvisoPagos.get(i));
            
        }
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PagoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PagoUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("PagoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Pago> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }
    
    public void limpiarDetalles(AjaxBehaviorEvent event){
        itemsDetalleAvisos = null;
        detalleAvisoPagos = null;
    }
    public void calculaInteresManual(){
        total=(selected.getSubtotal()+selected.getInteres());
        selected.setTotal(total);
    }
    public void calculaItems(){ 
//        Calendar cal1 = Calendar.getInstance();
//        Calendar cal2 = Calendar.getInstance();
//        long diffDays=0;     
//        int valorCodigo=0;
        System.out.println("Entro"); 
        int subtotal=0; 
        int total=0;
        int interes=0;
        selected.setFechaCreacion(new Date());
        selected.setSubtotal(subtotal);
        selected.setInteres(interes);
        selected.setTotal(total);
        System.out.println(total+"-"+interes+"-"+subtotal);      
        
        if(!detalleAvisoPagos.isEmpty()){
        int id_periodo=detalleAvisoPagos.get(0).getAvisoCobro().getAvisoCobroPK().getIdPeriodo(); 
        System.out.println(id_periodo);
        Periodo periodo=periodoController.getPeriodo(id_periodo);
        System.out.println(periodo.getFechaVencimiento());
//        cal2.setTime(selected.getFechaCreacion());
//        cal1.setTime(periodo.getFechaVencimiento());
//        if(selected.getFechaCreacion().getTime()>periodoController.getPeriodo(id_periodo).getFechaVencimiento().getTime()){
//            long milis1 = cal1.getTimeInMillis();
//            long milis2 = cal2.getTimeInMillis();
//            long diff = milis2 - milis1;
//            diffDays = diff / (24 * 60 * 60 * 1000);
//            System.out.println("En dias: " + diffDays + " dias.");
//        }
//        for(int e=0;e<tipoCobroController.getItems().size();e++){
//            if(tipoCobroController.getItems().get(e).getCodigoTipoCobro().equalsIgnoreCase("INTERES")){
//                valorCodigo=tipoCobroController.getItems().get(e).getValor();
//            }
//        }

            for(int i=0;i< detalleAvisoPagos.size();i++){                
                System.out.println("estado :"+ detalleAvisoPagos.get(i).getPagado()+"de :"+ detalleAvisoPagos.get(i).getIdTipoCobro().getNombre());                
                subtotal=subtotal+detalleAvisoPagos.get(i).getTotal();             
            }
            
        System.out.println("total: "+total+"  interes :"+interes+" subtotal: "+subtotal); 
        interes=ejbFacade.obtenerInteres(1,new Date());
        }
        //System.out.println(ejbFacade.obtenerInteres(1,new Date()));
        
        //selected.setInteres(ejbFacade.obtenerInteres(selected.getIdCuenta().getIdCuenta(), new Date()));
        
        selected.setSubtotal(subtotal);
        selected.setTotal(subtotal+interes);
        
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Pago getPago(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Pago> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Pago> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass=Pago.class)
    public static class PagoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PagoController controller = (PagoController)facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "pagoController");
            return controller.getPago(getKey(value));
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
            if (object instanceof Pago) {
                Pago o = (Pago) object;
                return getStringKey(o.getIdPago());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Pago.class.getName()});
                return null;
            }
        }

    }

}
