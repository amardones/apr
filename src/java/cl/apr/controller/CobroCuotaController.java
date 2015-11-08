package cl.apr.controller;

import cl.apr.entity.CobroCuota;
import cl.apr.controller.util.JsfUtil;
import cl.apr.controller.util.JsfUtil.PersistAction;
import cl.apr.facade.CobroCuotaFacade;

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

@Named("cobroCuotaController")
@SessionScoped
public class CobroCuotaController implements Serializable {

    @EJB
    private cl.apr.facade.CobroCuotaFacade ejbFacade;
    private List<CobroCuota> items = null;
    private CobroCuota selected;

    public CobroCuotaController() {
    }

    public CobroCuota getSelected() {
        return selected;
    }

    public void setSelected(CobroCuota selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.getCobroCuotaPK().setIdRegistroCobro(selected.getRegistroCobro().getIdRegistroCobro());
    }

    protected void initializeEmbeddableKey() {
        selected.setCobroCuotaPK(new cl.apr.entity.CobroCuotaPK());
    }

    private CobroCuotaFacade getFacade() {
        return ejbFacade;
    }

    public CobroCuota prepareCreate() {
        selected = new CobroCuota();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("CobroCuotaCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("CobroCuotaUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("CobroCuotaDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<CobroCuota> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
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

    public CobroCuota getCobroCuota(cl.apr.entity.CobroCuotaPK id) {
        return getFacade().find(id);
    }

    public List<CobroCuota> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<CobroCuota> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = CobroCuota.class)
    public static class CobroCuotaControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CobroCuotaController controller = (CobroCuotaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "cobroCuotaController");
            return controller.getCobroCuota(getKey(value));
        }

        cl.apr.entity.CobroCuotaPK getKey(String value) {
            cl.apr.entity.CobroCuotaPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new cl.apr.entity.CobroCuotaPK();
            key.setIdRegistroCobro(Integer.parseInt(values[0]));
            key.setNumeroCuota(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(cl.apr.entity.CobroCuotaPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getIdRegistroCobro());
            sb.append(SEPARATOR);
            sb.append(value.getNumeroCuota());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof CobroCuota) {
                CobroCuota o = (CobroCuota) object;
                return getStringKey(o.getCobroCuotaPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), CobroCuota.class.getName()});
                return null;
            }
        }

    }

}
