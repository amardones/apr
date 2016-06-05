package cl.apr.controller;

import cl.apr.entity.RegistroCobro;
import cl.apr.controller.util.JsfUtil;
import cl.apr.controller.util.JsfUtil.PersistAction;
import cl.apr.entity.CobroCuota;
import cl.apr.entity.Pago;
import cl.apr.entity.CobroCuotaPK;
import cl.apr.entity.TipoCobro;
import cl.apr.enums.EnumFormatoFechaHora;
import cl.apr.facade.RegistroCobroFacade;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import javax.inject.Inject;

@Named("registroCobroController")
@SessionScoped
public class RegistroCobroController implements Serializable {

    @EJB
    private cl.apr.facade.RegistroCobroFacade ejbFacade;
    private List<RegistroCobro> items = null;
    private RegistroCobro selected;
    private List<CobroCuota> cuotas;
    private Date fechaInicio;
    private Date fechaFin;
    
    private int mes;
    private int anio;
    private int total;
    private List<Integer> valorCuota;
    private List<TipoCobro> listaTipoCobro;
    @Inject
    private CuentaController cuentaController;
    
    @Inject
    TipoCobroController tipoCobroController;

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
     
    
    
    public RegistroCobroController() {
    }

    public RegistroCobro getSelected() {
        return selected;
    }

    public void setSelected(RegistroCobro selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }
    public List<CobroCuota> getCuotas() {
        return cuotas;
    }

    public List<TipoCobro> getListaTipoCobro() {
        listaTipoCobro=tipoCobroController.getTiposCobroRegistranCobro();
        if(listaTipoCobro!=null && listaTipoCobro.size()>0){
            selected.setMonto(listaTipoCobro.get(0).getValor());
        }
        return listaTipoCobro;
    }

    public void setListaTipoCobro(List<TipoCobro> listaTipoCobro) {
        this.listaTipoCobro = listaTipoCobro;
    }

    
    public void setCuotas(List<CobroCuota> cuotas) {
        this.cuotas = cuotas;
    }
    protected void initializeEmbeddableKey() {
        cuotas=null;
        cuentaController.setSelected(null);
        selected = new RegistroCobro();
        selected.setFechaCreacion(new Date());
        //selected.setMesPrimeraCuota(Integer.parseInt(EnumFormatoFechaHora.formatoMes.format(new Date())));
        selected.setCuotas(1);
    }

    private RegistroCobroFacade getFacade() {
        return ejbFacade;
    }

    public RegistroCobro prepareCreate() { 
        cuotas=null;
        initializeEmbeddableKey();
        return selected;
    }
    public List<Integer> calcularValorCuota(){
        //calcula el valor de la cuota
        int valor;
        int resto;
        total=0;
        valorCuota = new ArrayList<Integer>();
        valor=(selected.getMonto()/selected.getCuotas());
        resto=(selected.getMonto()%selected.getCuotas());
        for(int i=0;i<selected.getCuotas();i++){            
            if(total<resto){                
                valorCuota.add(valor+1);
                total++;
            }else{
                valorCuota.add(valor); 
            }
        }
        
        return valorCuota;
    }
    
 
    public void calcularCuotas(){
        if(selected.getMonto() == 0){
           cuotas = new ArrayList<CobroCuota>();
           return ;
        }
        //calcular cuotas
            BigInteger idRegistroCobro = getFacade().getNextIdRegistroCobro();       
            selected.setIdCuenta(cuentaController.getSelected());
            selected.setIdRegistroCobro(idRegistroCobro.intValue());            
            cuotas = new ArrayList<CobroCuota>();
            valorCuota=calcularValorCuota();
            
            //crear variable tipo calendar
            Calendar fechaCalendar=Calendar.getInstance();
            try{
                //asignar feha tipo date a tipo calendar                
                mes=(fechaCalendar.get(Calendar.MONTH))+2;
                anio=fechaCalendar.get(Calendar.YEAR);
                
                
            }catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error Fecha vencimiento menor a Fecha emisión ","Hola1"));
                System.out.println("");
            }            
           
            for(int i=0;i<selected.getCuotas();i++){
                    CobroCuota cc = new CobroCuota();
                    if(mes==13){
                        mes=1;
                        ++anio;
                    }
                    cc.setAnio(anio);
                    cc.setMes(mes);
                    cc.setPagado(false);
                    cc.setValorCuota(valorCuota.get(i));
                    //Numero de coutas si es individual o total                    
                    CobroCuotaPK ccpk = new CobroCuotaPK();
                    ccpk.setNumeroCuota(i+1);
                    //asignar el valor 
                    ccpk.setIdRegistroCobro(idRegistroCobro.intValue());
                    cc.setCobroCuotaPK(ccpk);
                    cuotas.add(cc);
                    mes++;              
            }
        selected.setMesPrimeraCuota(cuotas.get(0).getMes());
 
    }

    public void create() {
            /*BigInteger idRegistroCobro = getFacade().getNextIdRegistroCobro();
       
            selected.setIdCuenta(cuentaController.getSelected());
            selected.setIdRegistroCobro(idRegistroCobro.intValue());
            
            cuotas = new ArrayList<CobroCuota>();
            CobroCuota cc = new CobroCuota();
            cc.setAnio(2015);
            cc.setMes(12);
            cc.setPagado(false);
            cc.setValorCuota(1500);
            
            
            CobroCuotaPK ccpk = new CobroCuotaPK();
            ccpk.setNumeroCuota(1);
            //asignar el valor 
            ccpk.setIdRegistroCobro(idRegistroCobro.intValue());
            cc.setCobroCuotaPK(ccpk);
           
            cuotas.add(cc);*/
            
            
           // selected.setCobroCuotaList(cuotas);
            //persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RegistroCobroCreated"));
           
            if(getFacade().guardarRegistroCobrosmasCuotas(selected, cuotas)){
                 JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("RegistroCobroCreated"));
            }
            else{
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
            /*
            if (!JsfUtil.isValidationFailed()) {
                items = null;    // Invalidate list of items to trigger re-query.
            }
                    }*/
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RegistroCobroUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RegistroCobroDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RegistroCobro> getItems() {
            //items = getFacade().findAll();  
        return items;
    }

    public void resetDatosNuevaCuentaSeleccionada(){
        cuotas=null;
        selected = new RegistroCobro();
        selected.setFechaCreacion(new Date());
        selected.setCuotas(1);
        calcularCuotas();
    }
    
     public void cambioEnDatos(){
       if(selected.getIdTipoCobro() != null){
           selected.setMonto(selected.getIdTipoCobro().getValor());
           //System.out.println("selected.getNombre():"+selected.getIdTipoCobro().getNombre());
           if(!selected.getIdTipoCobro().getAceptaPagoCuotas()){
               selected.setCuotas(1);
               
           }   
         //comento  
           //System.out.println("selected.getIdTipoCobro():"+selected.getIdTipoCobro().getAceptaPagoCuotas());
             
        }
        calcularCuotas();
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

    public RegistroCobro getRegistroCobro(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<RegistroCobro> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RegistroCobro> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = RegistroCobro.class)
    public static class RegistroCobroControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RegistroCobroController controller = (RegistroCobroController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "registroCobroController");
            return controller.getRegistroCobro(getKey(value));
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
            if (object instanceof RegistroCobro) {
                RegistroCobro o = (RegistroCobro) object;
                return getStringKey(o.getIdRegistroCobro());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RegistroCobro.class.getName()});
                return null;
            }
        }

    }

    
    public boolean deshabilitarCuotas(){
       
        
        if(selected.getIdTipoCobro() != null && selected.getIdTipoCobro().getAceptaPagoCuotas()){
            //System.out.println("deshabilitarCuotas false");
            return false;
        }else{
            if(selected.getIdTipoCobro() != null){
                selected.setCuotas(1);
            }
            return true;
        }
    }
    
    public String mesPago(CobroCuota cc){
        String mes = "";
        Date d = EnumFormatoFechaHora.getDate(1, cc.getMes(), cc.getAnio());
        System.out.println("d.getTime(): "+d.getTime());
        mes = EnumFormatoFechaHora.formatoMesTexto.format(d).toUpperCase();
                
        return mes;
    }
    public String mesPago(RegistroCobro cc){
        String mes = "";
        
        Date d = EnumFormatoFechaHora.getDate(1, cc.getMesPrimeraCuota(), 1);
        System.out.println("d.getTime(): "+d.getTime());
        mes = EnumFormatoFechaHora.formatoMesTexto.format(d).toUpperCase();
                
        return mes;
    }
    public boolean tienePago(){
        
        return true;
    }
    
     public void limpiaFecha(){
        fechaFin=null;
    }
    
      public void buscarPorRangoFechas() {
        items= new ArrayList<>();

        if(fechaInicio!=null&&fechaFin==null){
            items= ejbFacade.findByRange(fechaInicio, fechaInicio); //reporteLibroIngreso(fechaInicio , fechaInicio);
        }
        if(fechaInicio!=null&&fechaFin!=null){
            System.out.println("Fecha: "+fechaInicio);
            items=  ejbFacade.findByRange(fechaInicio, fechaFin); //pagoFacade.reporteLibroIngreso(fechaInicio , fechaFin);
        }         
        
        fechaFin=null;
        fechaInicio=null;
    }
}
