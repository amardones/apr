package cl.apr.controller;

import cl.apr.entity.ValorTramoM3;
import cl.apr.controller.util.JsfUtil;
import cl.apr.controller.util.JsfUtil.PersistAction;
import cl.apr.entity.ValoresParametricos;
import cl.apr.facade.ValorTramoM3Facade;

import java.io.Serializable;
import java.math.BigInteger;
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

@Named("valorTramoM3Controller")
@SessionScoped
public class ValorTramoM3Controller implements Serializable {

    @EJB
    private cl.apr.facade.ValorTramoM3Facade ejbFacade;
    private List<ValorTramoM3> items = null;
    private ValorTramoM3 selected;

     @Inject
    private ValoresParametricosController valoresParametricosController;
     
    public ValorTramoM3Controller() {
    }

    public ValorTramoM3 getSelected() {
        return selected;
    }

    public void setSelected(ValorTramoM3 selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ValorTramoM3Facade getFacade() {
        return ejbFacade;
    }

    public ValorTramoM3 prepareCreate() {
        selected = new ValorTramoM3();
        selected.setNombreTramo("Tramo "+(items.size()+1));
        ValoresParametricos valParaUltimo = valoresParametricosController.getUltimoValoresParametricos();
        if(items == null  || items.size() == 0){            
            if(valParaUltimo != null){
                  selected.setM3Inicio(valParaUltimo.getM3Fijos());
                  selected.setM3Final(valParaUltimo.getM3Fijos() + valParaUltimo.getM3Fijos() );
                  selected.setPorcentajeRecargo(new BigInteger("0"));
            }          
        }else{
            ValorTramoM3 ultimoTramo = getFacade().getLastValorTramoM3();
            if(ultimoTramo != null){                
                selected.setM3Inicio(ultimoTramo.getM3Final());
                selected.setM3Final(ultimoTramo.getM3Final());
                 if(valParaUltimo != null){
                      selected.setM3Final(ultimoTramo.getM3Final() + valParaUltimo.getM3Fijos());
                 }
            }
        }
        
        initializeEmbeddableKey();
        return selected;
    }
    
    public boolean permiteEliminar(){
         ValorTramoM3 ultimoTramo = getFacade().getLastValorTramoM3();
         if(ultimoTramo != null && selected != null){
             return ultimoTramo.getIdValorTramo() == selected.getIdValorTramo();
         }
         return selected != null;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ValorTramoM3Created"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ValorTramoM3Updated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ValorTramoM3Deleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<ValorTramoM3> getItems() {
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

    public ValorTramoM3 getValorTramoM3(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<ValorTramoM3> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<ValorTramoM3> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = ValorTramoM3.class)
    public static class ValorTramoM3ControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ValorTramoM3Controller controller = (ValorTramoM3Controller) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "valorTramoM3Controller");
            return controller.getValorTramoM3(getKey(value));
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
            if (object instanceof ValorTramoM3) {
                ValorTramoM3 o = (ValorTramoM3) object;
                return getStringKey(o.getIdValorTramo());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ValorTramoM3.class.getName()});
                return null;
            }
        }

    }

}
