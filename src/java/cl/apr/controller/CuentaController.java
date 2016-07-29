package cl.apr.controller;


import cl.apr.entity.Cuenta;
import org.primefaces.model.map.Marker;
import cl.apr.controller.util.JsfUtil;
import cl.apr.controller.util.JsfUtil.PersistAction;
import cl.apr.entity.CuentaSubsidio;
import cl.apr.entity.Medidor;
import cl.apr.entity.Subsidio;
import cl.apr.facade.CuentaFacade;
import com.sun.javafx.scene.control.skin.VirtualFlow;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;


@Named("cuentaController")
@SessionScoped
public class CuentaController implements Serializable {

    @EJB
    private cl.apr.facade.CuentaFacade ejbFacade;
    @Inject
    private SocioController socioController;
    
    @Inject
    private MedidorController medidorController;
    @EJB
    private cl.apr.facade.MedidorFacade ejbMedidorFacade;
    @EJB
    private cl.apr.facade.ValoresParametricosFacade ejbParametricos;
    @EJB
    private cl.apr.facade.CuentaSubsidioFacade ejbCuentaSubsidio;
    private List<Cuenta> items = null;
    private LatLng itemGmap=null;
    private Cuenta selected;
    
    //google maps
    private MapModel emptyModel;
    private Marker marker;
    private String title;
    private double lat=0;      
    private double lng=0;
   
    //private Subsidio subsidio;

//    public Subsidio getSubsidio() {
//        return subsidio;
//    }
//
//    public void setSubsidio(Subsidio subsidio) {
//        this.subsidio = subsidio;
//    }    

    public CuentaController() {
    }
    @PostConstruct
    public void init() {
        emptyModel = new DefaultMapModel();
    }
    public MapModel getEmptyModel() {
        emptyModel = new DefaultMapModel();
        getItems();
            for(Cuenta item : items) {
                    
                    if(item.getGpsLatitud()!=null&&item.getGpsLongitud()!=null){
                    itemGmap= new LatLng(Double.parseDouble(item.getGpsLatitud()),Double.parseDouble(item.getGpsLongitud()));                
                    emptyModel.addOverlay(new Marker(itemGmap,item.getIdCuenta().toString()+" "+item.getRut().getNombre()+" "+item.getRut().getApellido()));
                    }
            }
        
        return emptyModel;
    }
   

    public void setEmptyModel(MapModel emptyModel) {
        this.emptyModel = emptyModel;
    }
    public void addMarker() {
        marker = new Marker(new LatLng(lat, lng), title);
        emptyModel.addOverlay(marker);       
        selected.setGpsLatitud(String.valueOf(lat));
        selected.setGpsLongitud(String.valueOf(lng));
        update();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Marca agregada","Cuenta :"+title+ ", Lat:" + lat + ", Lng:" + lng));
    }
    

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
   public void onMarkerSelect(OverlaySelectEvent event) {       
        marker = (Marker) event.getOverlay();
        
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Marca Seleccionada", marker.getTitle()));
    }
      
    public Marker getMarker() {
        return marker;
    }
    
    
   public void tituloCuenta(){
       setTitle(selected.getIdCuenta().toString()+" "+selected.getRut().getNombre()+" "+selected.getRut().getApellido());
   }
    public Cuenta getSelected() {
        return selected;
    }

    public void setSelected(Cuenta selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private CuentaFacade getFacade() {
        return ejbFacade;
    }

    public Cuenta prepareCreate() {
        selected = new Cuenta();
        selected.setFechaCreacion(new Date());
        selected.setCuentaSubsidio(new CuentaSubsidio());
        initializeEmbeddableKey();
        return selected;
    }
    public Cuenta prepareUpdate() {
        if(selected.getCuentaSubsidio() == null)
            selected.setCuentaSubsidio(new CuentaSubsidio());
        
        return selected;
    }
   /* public ActionListener createActionListener() {
        return new ActionListener() {
            @Override
            public void processAction(ActionEvent event) throws AbortProcessingException {
                   //cuentaController.prepareCreate();
                   //medidorController.prepareCreate();
                   socioController.prepareCreate();
            }
            
        };
    }*/
    public void create() {
        boolean nCuentaEnBD=false;        
        for (Cuenta item : items) {
            if(item.getIdCuenta().equals(selected.getIdCuenta())){
            nCuentaEnBD=true;
            }                
        }
        if(!nCuentaEnBD){
            selected.setFechaCreacion(new Date());
            persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("CuentaCreated"));
            if (!JsfUtil.isValidationFailed()) {
                 System.out.println("limpiando cuenta create");
                items = null;    // Invalidate list of items to trigger re-query.
                selected = null;
            }
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: N° de Cuenta ya exixte",null));
        }
    }

    public void update() {
        //subsidio=selected.getCuentaSubsidio().getIdSubsidio();
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("CuentaUpdated"));
         if (!JsfUtil.isValidationFailed()) {
              System.out.println("limpiando cuenta update");
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("CuentaDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Cuenta> getItems() {        
        if(items == null){
         items = getFacade().findAllCuentas();
         System.out.println("cuentas.size(): "+items.size());
        }
         return items;
    }
    
    public int getMontoSubsidio(){
        int monto=0;
        int porcentaje=0;
        int m3Fijo=0;
        int valorM3=0;
        
        porcentaje=(selected.getCuentaSubsidio().getIdSubsidio().getPorcentaje().intValue());
        valorM3=ejbParametricos.getLastValoresParametricos().getValorM3();
        m3Fijo=ejbParametricos.getLastValoresParametricos().getM3Fijos();
        return monto=m3Fijo*valorM3*porcentaje;
    }
    
   
    
    public List<Medidor> getMedidoresDisponiblesEditar() {
        if(selected != null && selected.getNumeroMedidor() != null){
            return ejbMedidorFacade.getMedidoresDisponiblesEditar(selected.getNumeroMedidor().getNumeroMedidor());
        }else{
            return new ArrayList<Medidor>();
        }
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            
            try {
                if (persistAction != PersistAction.DELETE) {
                    if(selected.getCuentaSubsidio() != null && selected.getCuentaSubsidio().getIdSubsidio() != null){                        
                        selected.getCuentaSubsidio().setIdCuenta(selected.getIdCuenta());
                        //System.out.println("agrega subsidio");
                        getFacade().edit(selected);                       
                    }else{
                        //System.out.println("sin subsidio");                       
                        selected.setCuentaSubsidio(null);
                        getFacade().edit(selected);
                    }
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

    public Cuenta getCuenta(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Cuenta> getItemsAvailableSelectMany() {
        return getFacade().findAllCuentas();
    }

    public List<Cuenta> getItemsAvailableSelectOne() {
        return getFacade().findAllCuentas();
    }

    @FacesConverter(forClass = Cuenta.class)
    //@FacesConverter("cuentaControllerConverter")
    public static class CuentaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CuentaController controller = (CuentaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "cuentaController");
            return controller.getCuenta(getKey(value));
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
            if (object instanceof Cuenta) {
                Cuenta o = (Cuenta) object;
                return getStringKey(o.getIdCuenta());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Cuenta.class.getName()});
                return null;
            }
        }

    }
    
     public List<Cuenta> completeCuenta(String query) {
        List<Cuenta> filteredThemes = new ArrayList<Cuenta>();
        try{
             List<Cuenta> allThemes = getItems();

            Cuenta c = null;
            String s = "";        
            for (int i = 0; i < allThemes.size(); i++) {
                 c = allThemes.get(i);
                 if(c != null){
                    s = c.getIdCuenta() + c.getRut().getRut() + c.getRut().getNombre() + c.getRut().getApellido();
                    if(s.toLowerCase().contains(query.toLowerCase())) {
                        filteredThemes.add(c);
                    }
                 }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return filteredThemes;
    }
     
     public void quitarSubsidio(AjaxBehaviorEvent event){
         if(selected != null){
             selected.setCuentaSubsidio(new CuentaSubsidio());
             System.out.println("cuenta subsidio reset");
         }
     }
     
    

}
