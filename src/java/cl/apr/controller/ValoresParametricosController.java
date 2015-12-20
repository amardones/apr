package cl.apr.controller;

import cl.apr.entity.ValoresParametricos;
import cl.apr.controller.util.JsfUtil;
import cl.apr.controller.util.JsfUtil.PersistAction;
import cl.apr.facade.ValoresParametricosFacade;

import java.io.Serializable;
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
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("valoresParametricosController")
@SessionScoped
public class ValoresParametricosController implements Serializable {

    @EJB
    private cl.apr.facade.ValoresParametricosFacade ejbFacade;
    private List<ValoresParametricos> items = null;
    private ValoresParametricos selected;
    private ValoresParametricos ultimoValoresParametricos;

    public ValoresParametricos getUltimoValoresParametricos() {
        ultimoValoresParametricos = ejbFacade.getLastValoresParametricos();
        
        return ultimoValoresParametricos;
    }

    public void setUltimoValoresParametricos(ValoresParametricos ultimoValoresParametricos) {
        this.ultimoValoresParametricos = ultimoValoresParametricos;
    }
    
    public ValoresParametricosController() {
    }

    public ValoresParametricos getSelected() {
        return selected;
    }

    public void setSelected(ValoresParametricos selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
         selected.setFechaActualizacion(new Date());
    }

    private ValoresParametricosFacade getFacade() {
        return ejbFacade;
    }

    public ValoresParametricos prepareCreate() {
        ultimoValoresParametricos = ejbFacade.getLastValoresParametricos();
        selected = new ValoresParametricos();
        if(ultimoValoresParametricos != null){
            selected = ultimoValoresParametricos; 
            selected.setIdValoresParametricos(null);           
        }
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ValoresParametricosCreated"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; 
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ValoresParametricosUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ValoresParametricosDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<ValoresParametricos> getItems() {
        if (items == null) {
            items = getFacade().getValoresParametricos();
        }
        return items;
    }
    
    public boolean permiteEliminar() {
        if (selected != null && selected.getIdValoresParametricos() != null) {
           //return v.getPeriodoList().size();
            return getFacade().getCantidadAvisosCobro(selected.getIdValoresParametricos()) == 0;
        }
        return false;
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

    public ValoresParametricos getValoresParametricos(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<ValoresParametricos> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<ValoresParametricos> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = ValoresParametricos.class)
    public static class ValoresParametricosControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ValoresParametricosController controller = (ValoresParametricosController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "valoresParametricosController");
            return controller.getValoresParametricos(getKey(value));
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
            if (object instanceof ValoresParametricos) {
                ValoresParametricos o = (ValoresParametricos) object;
                return getStringKey(o.getIdValoresParametricos());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ValoresParametricos.class.getName()});
                return null;
            }
        }

    }

}
