package cl.apr.controller;

import cl.apr.entity.Cuenta;
import cl.apr.controller.util.JsfUtil;
import cl.apr.controller.util.JsfUtil.PersistAction;
import cl.apr.entity.CuentaSubsidio;
import cl.apr.entity.Medidor;
import cl.apr.entity.Subsidio;
import cl.apr.facade.CuentaFacade;

import java.io.Serializable;
import java.util.ArrayList;
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

@Named("cuentaController")
@SessionScoped
public class CuentaController implements Serializable {

    @EJB
    private cl.apr.facade.CuentaFacade ejbFacade;
    @EJB
    private cl.apr.facade.MedidorFacade ejbMedidorFacade;
    @EJB
    private cl.apr.facade.CuentaSubsidioFacade ejbCuentaSubsidio;
    private List<Cuenta> items = null;
    private Cuenta selected;
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
        selected.setCuentaSubsidio(new CuentaSubsidio());
        initializeEmbeddableKey();
        return selected;
    }
    public Cuenta prepareUpdate() {
        if(selected.getCuentaSubsidio() == null)
            selected.setCuentaSubsidio(new CuentaSubsidio());
        
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("CuentaCreated"));
        if (!JsfUtil.isValidationFailed()) {
             System.out.println("limpiando cuenta create");
            items = null;    // Invalidate list of items to trigger re-query.
            selected = null;
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
        
         items = getFacade().findAll();
         return items;
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
                        System.out.println("agrega subsidio");
                        getFacade().edit(selected);                       
                    }else{
                        System.out.println("sin subsidio");
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
        return getFacade().findAll();
    }

    public List<Cuenta> getItemsAvailableSelectOne() {
        return getFacade().findAll();
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
            List<Cuenta> allThemes = getFacade().findAll();

            Cuenta c = null;
            String s = "";        
            for (int i = 0; i < allThemes.size(); i++) {
                 c = allThemes.get(i);
                 if(c != null){
                    s = c.getIdCuenta() + c.getRut().getRut() + c.getRut().getNombre() + c.getRut().getApellido();
                    if(s.toLowerCase().contains(query)) {
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
//      public void limpiarDetalles(AjaxBehaviorEvent event){
//        itemsDetalleAvisos = null;
//        detalleAvisoSelected = null;
//    }

}
