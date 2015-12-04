package cl.apr.controller;

import cl.apr.entity.Periodo;
import cl.apr.controller.util.JsfUtil;
import cl.apr.controller.util.JsfUtil.PersistAction;
import cl.apr.entity.ValoresParametricos;
import cl.apr.enums.EnumFormatoFechaHora;
import cl.apr.facade.PeriodoFacade;

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
import javax.inject.Inject;

@Named("periodoController")
@SessionScoped
public class PeriodoController implements Serializable {

    
    @EJB
    private cl.apr.facade.PeriodoFacade ejbFacade;
    
    @Inject
    private ValoresParametricosController valoresParametricosController;
    
    private List<Periodo> items = null;
    private Periodo selected;
    private Periodo ultimoPeriodo;
    
    public List<Periodo> getPeriodos() {
        List<Periodo> periodos = ejbFacade.getPeriodos();
        ultimoPeriodo = ejbFacade.getLastPeriodo();
        System.out.println("ultimoPeriodo: "+ultimoPeriodo.getNombre());
        if(selected == null && periodos != null && periodos.size() > 0){
            selected = periodos.get(0);
        }
        return  periodos;
    }
    
    public boolean ultimoPeriodo(){
        try{
            return ultimoPeriodo.getIdPeriodo() == selected.getIdPeriodo();
        }catch(Exception e){}
        
        return false;
    }
    
    public PeriodoController() {
    }

    public Periodo getSelected() {
        return selected;
    }

    public void setSelected(Periodo selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
        
        selected.setNombre(EnumFormatoFechaHora.formatoMesTextoAnio.format(new Date()));
        selected.setFechaEmision(new Date());
        selected.setFechaFin(new Date());
        selected.setFechaInicio(new Date());
        selected.setFechaTomaLectura(new Date());
        selected.setFechaVencimiento(new Date());
    }

    private PeriodoFacade getFacade() {
        return ejbFacade;
    }

    public Periodo prepareCreate() {
        ValoresParametricos v =  valoresParametricosController.getUltimoValoresParametricos();
        valoresParametricosController.setSelected(v);
        if(v != null){
            System.out.println("v.getFechaActualizacion().toString(): "+v.getFechaActualizacion().toString());
            selected = new Periodo();
            selected.setIdValoresParametricos(v);
            initializeEmbeddableKey();
            return selected;
        }else{
             System.out.println("v.getFechaActualizacion().toString(): null ");
        }
       return null;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PeriodoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PeriodoUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("PeriodoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Periodo> getItems() {
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

    public Periodo getPeriodo(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Periodo> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Periodo> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Periodo.class)
    public static class PeriodoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PeriodoController controller = (PeriodoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "periodoController");
            return controller.getPeriodo(getKey(value));
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
            if (object instanceof Periodo) {
                Periodo o = (Periodo) object;
                return getStringKey(o.getIdPeriodo());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Periodo.class.getName()});
                return null;
            }
        }

    }

}
