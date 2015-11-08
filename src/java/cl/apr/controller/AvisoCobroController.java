package cl.apr.controller;

import cl.apr.entity.AvisoCobro;
import cl.apr.controller.util.JsfUtil;
import cl.apr.controller.util.JsfUtil.PersistAction;
import cl.apr.facade.AvisoCobroFacade;

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

@Named("avisoCobroController")
@SessionScoped
public class AvisoCobroController implements Serializable {

    @EJB
    private cl.apr.facade.AvisoCobroFacade ejbFacade;
    private List<AvisoCobro> items = null;
    private AvisoCobro selected;

    public AvisoCobroController() {
    }

    public AvisoCobro getSelected() {
        return selected;
    }

    public void setSelected(AvisoCobro selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.getAvisoCobroPK().setIdCuenta(selected.getCuenta().getIdCuenta());
        selected.getAvisoCobroPK().setIdPeriodo(selected.getPeriodo().getIdPeriodo());
    }

    protected void initializeEmbeddableKey() {
        selected.setAvisoCobroPK(new cl.apr.entity.AvisoCobroPK());
    }

    private AvisoCobroFacade getFacade() {
        return ejbFacade;
    }

    public AvisoCobro prepareCreate() {
        selected = new AvisoCobro();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("AvisoCobroCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("AvisoCobroUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("AvisoCobroDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<AvisoCobro> getItems() {
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

    public AvisoCobro getAvisoCobro(cl.apr.entity.AvisoCobroPK id) {
        return getFacade().find(id);
    }

    public List<AvisoCobro> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<AvisoCobro> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = AvisoCobro.class)
    public static class AvisoCobroControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AvisoCobroController controller = (AvisoCobroController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "avisoCobroController");
            return controller.getAvisoCobro(getKey(value));
        }

        cl.apr.entity.AvisoCobroPK getKey(String value) {
            cl.apr.entity.AvisoCobroPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new cl.apr.entity.AvisoCobroPK();
            key.setIdPeriodo(Integer.parseInt(values[0]));
            key.setIdCuenta(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(cl.apr.entity.AvisoCobroPK value) {
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
            if (object instanceof AvisoCobro) {
                AvisoCobro o = (AvisoCobro) object;
                return getStringKey(o.getAvisoCobroPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), AvisoCobro.class.getName()});
                return null;
            }
        }

    }

}
