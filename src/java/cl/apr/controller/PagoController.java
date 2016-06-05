package cl.apr.controller;

import cl.apr.entity.Pago;
import cl.apr.controller.util.JsfUtil;
import cl.apr.controller.util.JsfUtil.PersistAction;
import cl.apr.entity.DetalleAvisoCobro;
import cl.apr.entity.PagoTipoCobro;
import cl.apr.entity.PagoTipoCobroPK;
import cl.apr.entity.Periodo;
import cl.apr.entity.TipoCobro;
import cl.apr.facade.PagoFacade;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
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
import javax.faces.application.FacesMessage;
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
    
    
    private boolean esSeleccionTipo = false;
    private List<Pago> items = null;
    private Pago selected;
    private TipoCobro tipoCobroSelected;
    private List<DetalleAvisoCobro> itemsDetalleAvisos = null;
    private List<DetalleAvisoCobro> detalleAvisoPagos = null;
    private List<PagoTipoCobro> pagoTipoCobro=null;
    private List<TipoCobro> listaTipoCobros = null;
    private int total;
    private int dias;
    private int valorCodigo;
    private Date fechaInicio;
    private Date fechaFin;
   

    @Inject
    private CuentaController cuentaController;
    @Inject
    private PeriodoController periodoController;
    @Inject
    private TipoCobroController tipoCobroController;
    
    private int interes;
    private int subtotal;

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
    
    public void limpiaFecha(){
        fechaFin=null;
    }
    
    public void filtroRango(){
        items= new ArrayList<>();
        if(fechaInicio!=null&&fechaFin==null){
            items=ejbFacade.findByRange(fechaInicio, fechaInicio);
        }   
        if(fechaInicio!=null&&fechaFin!=null){
            items=ejbFacade.findByRange(fechaInicio, fechaFin);
        }
        fechaFin=null;
        fechaInicio=null;
     }
    
    public int getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }  
    

    public int getInteres() {
        return interes;
    }

    public void setInteres(int interes) {
        this.interes = interes;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }
    

    public List<DetalleAvisoCobro> getDetalleAvisoPagos() {
        return detalleAvisoPagos;
    }

    public void setDetalleAvisoPagos(List<DetalleAvisoCobro> detalleAvisoPagos) {
        this.detalleAvisoPagos = detalleAvisoPagos;
    }

    final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000;

    public List<TipoCobro> getListaTipoCobros() {
        if(listaTipoCobros == null){
            listaTipoCobros = tipoCobroController.getItems();       
        }
        Collections.sort(listaTipoCobros, Comparator.comparing(TipoCobro::getNombre));
        
        if(!esSeleccionTipo && listaTipoCobros != null && listaTipoCobros.size() > 0 && selected!=null){
             selected.setSubtotal( listaTipoCobros.get(0).getValor());             
        }
        
        
        esSeleccionTipo = false;
        return listaTipoCobros;
    }
   
   
    public List<DetalleAvisoCobro> getItemsDetalleAvisos() {
        if(itemsDetalleAvisos == null){
            itemsDetalleAvisos= ejbFacadeDetalleAviso.getDetalleAvisoCobroDisponibles(cuentaController.getSelected().getIdCuenta());
            
        }
        return itemsDetalleAvisos;
    }

    public TipoCobro getTipoCobroSelected() {
        return tipoCobroSelected;
    }

    public void setTipoCobroSelected(TipoCobro tipoCobroSelected) {
        //selected.setSubtotal(tipoCobroSelected.getValor());
        this.tipoCobroSelected = tipoCobroSelected;
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
        pagoTipoCobro=new ArrayList<>();
        tipoCobroSelected = null;
        listaTipoCobros = null;
        initializeEmbeddableKey();
        itemsDetalleAvisos = null;
        detalleAvisoPagos=null;
        setDias(0);
        return selected;
    }

    public void create() {
        
        selected.setIdCuenta(cuentaController.getSelected());
        selected.setDetalleAvisoCobroList(detalleAvisoPagos);
        boolean nDocumentoEnBD=getFacade().existeNumeroDocumento(selected.getNumeroDocumento());
        /*
        for (Pago item : items) {
            if(item.getNumeroDocumento().equals(selected.getNumeroDocumento())){
            nDocumentoEnBD=true;
            } 
        }
        */
         
                
        if(nDocumentoEnBD==false && selected.getSubtotal()>=0){
            BigInteger idPago = getFacade().getNextIdPago();
            if(idPago != null){
                int idpagoInt = idPago.intValue();
                selected.setIdPago(idpagoInt);                 
                persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PagoCreated"));
                for(int i=0;i<detalleAvisoPagos.size();i++){
                    detalleAvisoPagos.get(i).setPagado(true);
                    ejbFacadeDetalleAviso.edit(detalleAvisoPagos.get(i));
                }

                if (!JsfUtil.isValidationFailed()) {
                    items = null;    // Invalidate list of items to trigger re-query.
                }
            }else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al generar pago.",null));
            }
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: Debe elegir un Item o N° Documento ya Existe",null));
        }
    }
    public void getAgregarItem(){   
        PagoTipoCobro cc=new PagoTipoCobro();
        PagoTipoCobroPK pk=new PagoTipoCobroPK();
        pk.setIdTipoCobro(tipoCobroSelected.getIdTipoCobro());
        cc.setTipoCobro(tipoCobroSelected);
        cc.setPagoTipoCobroPK(pk);
        cc.setTotal(selected.getSubtotal());        
        pagoTipoCobro.add(cc);
        getTotalPagoTipoCobro();
        //selected.setSubtotal(0);
        
        listaTipoCobros.remove(tipoCobroSelected);
        
        
    }
    public void getTotalPagoTipoCobro(){
        selected.setTotal(0);
        for (PagoTipoCobro itemT  : pagoTipoCobro) {
            selected.setTotal(itemT.getTotal()+selected.getTotal());
        }
    }
    public void cambioEnDatos(){
        //System.out.println("Actualizar valor: "+ tipoCobroSelected.getNombre());
        selected.setSubtotal(tipoCobroSelected.getValor());
        esSeleccionTipo = true;
    }
    public void eliminarItem(PagoTipoCobro cc){ 
        System.out.println("Eliminar : "+cc.getTotal());
        if(!pagoTipoCobro.isEmpty()){
           
            for (PagoTipoCobro pTipoCobro : pagoTipoCobro) {
                if(pTipoCobro.getTipoCobro().getIdTipoCobro() == cc.getTipoCobro().getIdTipoCobro()){
                     selected.setTotal(selected.getTotal()-cc.getTotal());
                     pagoTipoCobro.remove(pTipoCobro);
                     listaTipoCobros.add(cc.getTipoCobro());
                     break;
                }                
            }
        }        
    }
          
          
    
    
    public void createPagoExtraordinario() {
        selected.setInteres(0); 
        selected.setSubtotal(0);
        selected.setIdCuenta(cuentaController.getSelected());
        
        
        boolean nDocumentoEnBD=getFacade().existeNumeroDocumento(selected.getNumeroDocumento());
        /*
        for (Pago item : items) {
            if(item.getNumeroDocumento().equals(selected.getNumeroDocumento())){
            nDocumentoEnBD=true;
            } 
        }
        */
        if(nDocumentoEnBD==false && selected.getTotal()>=0){
            BigInteger idPago = getFacade().getNextIdPago();
            if(idPago != null){
                int idpagoInt = idPago.intValue();
                selected.setIdPago(idpagoInt);                
                
               // List <PagoTipoCobro> listaPagoTipoCobro = selected.getPagoTipoCobroList();
               // selected.setPagoTipoCobroList(new ArrayList<>());
                /*
                for (PagoTipoCobro item : selected.getPagoTipoCobroList()) {
                    item.getPagoTipoCobroPK().setIdPago(idPago.intValue());
                }
                */        

            persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PagoCreated"));
             
            if (!JsfUtil.isValidationFailed()) {
                items = null;    // Invalidate list of items to trigger re-query.
            }
            
          }          
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "N° Documento ya Existe",null));
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

    public List<PagoTipoCobro> getPagoTipoCobro() {
        return pagoTipoCobro;
    }

    public void setPagoTipoCobro(List<PagoTipoCobro> pagoTipoCobro) {
        this.pagoTipoCobro = pagoTipoCobro;
    }



    public List<Pago> getItems() {
        /*if (items == null) {
            items = getFacade().findAll();
        }*/
        return items;
    }
    public boolean permitePagar(){
            if(periodoController.getUltimoPeriodo()!=null){
                if(periodoController.getUltimoPeriodo().getFechaEmision().getTime() <= new Date().getTime()){
                    return false;
                } 
            }
        return true;
    }
    public void limpiarDetalles(AjaxBehaviorEvent event){
        itemsDetalleAvisos = null;
        detalleAvisoPagos = null; 
        
        selected.setFechaCreacion(new Date());
        setSubtotal(0);
            for(int e=0;e<tipoCobroController.getItems().size();e++){
              if(tipoCobroController.getItems().get(e).getCodigoTipoCobro().equalsIgnoreCase("INTERES")){
                   valorCodigo=tipoCobroController.getItems().get(e).getValor();
               }
            }
            System.out.println("valor interes tipo de cobro" + valorCodigo);
            
            
            if(!cuentaController.getSelected().getEsInstitucion()){
                //setInteres((ejbFacade.obtenerInteres(cuentaController.getSelected().getIdCuenta(),new Date()))*valorCodigo);
                setDias(ejbFacade.obtenerInteres(cuentaController.getSelected().getIdCuenta(),new Date()));
                setInteres((getDias()*valorCodigo));
                System.out.println("EL INTERES ES : "+interes+" FECHA :"+new Date());
            }else{
                setDias(0);
                setInteres(0);
            }
        
    }
    public void calculaInteresManual(){
        
        if(cuentaController.getSelected().getIdCuenta()!=null && !cuentaController.getSelected().getEsInstitucion() ){
            //total=(selected.getSubtotal()+selected.getInteres()); 
            
            if(getDias()>=0 && selected!=null){
                total=(selected.getSubtotal()+getDias()*valorCodigo);
                
                selected.setInteres(getDias()*valorCodigo);
                selected.setTotal(total);
            }else{
                setDias(0);
                setInteres(0);
            }            
        }else{
            setDias(0);
            setInteres(0);
        }
    }
    public void calculaItems(){ 

        int subtotal=0; 
        int total=getInteres();    
        int interes=getInteres();
        
        selected.setSubtotal(subtotal);
        selected.setInteres(interes);
        selected.setTotal(total);      
            
            if(!detalleAvisoPagos.isEmpty()){
            int id_periodo=detalleAvisoPagos.get(0).getAvisoCobro().getAvisoCobroPK().getIdPeriodo();
            for(int i=0;i< detalleAvisoPagos.size();i++){                                
                subtotal=subtotal+detalleAvisoPagos.get(i).getTotal();             
            } 
            selected.setSubtotal(subtotal);
            selected.setTotal(subtotal+interes);
        }
        //Periodo periodo=periodoController.getPeriodo(id_periodo);
        
//        cal2.setTime(selected.getFechaCreacion());
//        cal1.setTime(periodo.getFechaVencimiento());
//        if(selected.getFechaCreacion().getTime()>periodoController.getPeriodo(id_periodo).getFechaVencimiento().getTime()){
//            long milis1 = cal1.getTimeInMillis();
//            long milis2 = cal2.getTimeInMillis();
//            long diff = milis2 - milis1;
//            diffDays = diff / (24 * 60 * 60 * 1000);
//            System.out.println("En dias: " + diffDays + " dias.");
//        }
            
            
        
        //System.out.println(ejbFacade.obtenerInteres(1,new Date()));
        
        //selected.setInteres(ejbFacade.obtenerInteres(selected.getIdCuenta().getIdCuenta(), new Date()));
        
        
        
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    for (PagoTipoCobro item : pagoTipoCobro) {
                           item.getPagoTipoCobroPK().setIdPago(selected.getIdPago());
                    }
                    selected.setPagoTipoCobroList(pagoTipoCobro);
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
                ex.printStackTrace();
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
    
    
     @FacesConverter(forClass=PagoTipoCobro.class)
    public static class PagoTipoCobroControllerConverter implements Converter {

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
