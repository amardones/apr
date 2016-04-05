package cl.apr.controller;

import cl.apr.entity.RegistroEstado;
import cl.apr.controller.util.JsfUtil;
import cl.apr.controller.util.JsfUtil.PersistAction;
import cl.apr.entity.Periodo;
import cl.apr.facade.RegistroEstadoFacade;

import java.io.Serializable;
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
import javax.inject.Inject;

@Named("registroEstadoController")
@SessionScoped
public class RegistroEstadoController implements Serializable {

    @EJB
    private cl.apr.facade.RegistroEstadoFacade ejbFacade;
    private List<RegistroEstado> items = null;
    private RegistroEstado selected;
    private double metrosCalculados;
   
    @Inject
    private PeriodoController periodoController;

    public RegistroEstadoController() {
    }

    public RegistroEstado getSelected() {
        return selected;
    }

    public void setSelected(RegistroEstado selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.getRegistroEstadoPK().setIdPeriodo(selected.getPeriodo().getIdPeriodo());
        selected.getRegistroEstadoPK().setIdCuenta(selected.getCuenta().getIdCuenta());
    }

    protected void initializeEmbeddableKey() {
        selected.setRegistroEstadoPK(new cl.apr.entity.RegistroEstadoPK());
    }

    private RegistroEstadoFacade getFacade() {
        return ejbFacade;
    }
    

    public RegistroEstado prepareCreate() {
        selected = new RegistroEstado();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RegistroEstadoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        if(selected.getMetrosCubicos() >= 0){
             persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RegistroEstadoUpdated"));
        }else{
             JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("RegistroEstadosEditarMetrosCubicosError"));
        }
       
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RegistroEstadoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RegistroEstado> getItems() {
        /*if (items == null) {
            items = getFacade().findAll();
        }
        return items;*/
        items = null;
        if(periodoController.getSelected() != null){
                if(periodoController.getSelected().getIdPeriodo()==null){            
                items = getFacade().getRegistroEstadoPorPeriodo(periodoController.getUltimoPeriodo().getIdPeriodo());
                periodoController.setSelected(periodoController.getUltimoPeriodo());
                }
                if(periodoController.getSelected().getIdPeriodo()!=null)
                items = getFacade().getRegistroEstadoPorPeriodo(periodoController.getSelected().getIdPeriodo()); 
        }
        return items;
    }
   
    public double getMetros() {
        return  metrosCalculados;
    }
    public void calculaM3(){
        metrosCalculados=(selected.getEstadoActual()-selected.getEstadoAnterior());
        selected.setMetrosCubicos(metrosCalculados);
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

    public RegistroEstado getRegistroEstado(cl.apr.entity.RegistroEstadoPK id) {
        return getFacade().find(id);
    }

    public List<RegistroEstado> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<RegistroEstado> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }
    
     public void estadoActual(RegistroEstado item) {
         if(item != null){
            if(periodoController.getSelected() != null ){
                this.selected = item;
            }
        }
    }

    @FacesConverter(forClass = RegistroEstado.class)
    public static class RegistroEstadoControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RegistroEstadoController controller = (RegistroEstadoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "registroEstadoController");
            return controller.getRegistroEstado(getKey(value));
        }

        cl.apr.entity.RegistroEstadoPK getKey(String value) {
            cl.apr.entity.RegistroEstadoPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new cl.apr.entity.RegistroEstadoPK();
            key.setIdPeriodo(Integer.parseInt(values[0]));
            key.setIdCuenta(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(cl.apr.entity.RegistroEstadoPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getIdPeriodo());
            sb.append(SEPARATOR);
            sb.append(value.getIdCuenta());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof RegistroEstado) {
                RegistroEstado o = (RegistroEstado) object;
                return getStringKey(o.getRegistroEstadoPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RegistroEstado.class.getName()});
                return null;
            }
        }

    }

}
