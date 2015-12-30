package cl.apr.controller;

import cl.apr.entity.DetalleAvisoCobro;
import cl.apr.controller.util.JsfUtil;
import cl.apr.controller.util.JsfUtil.PersistAction;
import cl.apr.facade.DetalleAvisoCobroFacade;
import cl.apr.beans.ItemReporte;

import java.io.Serializable;
import java.util.ArrayList;
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

@Named("reportesController")
@SessionScoped
public class ReportesController implements Serializable {

    @EJB
    private cl.apr.facade.DetalleAvisoCobroFacade ejbFacade;
    @EJB
    private cl.apr.facade.PagoFacade pagoFacade;
    private List<DetalleAvisoCobro> items = null;
    private ItemReporte selected;
    private List<ItemReporte> itemReporte;
    
    

    public ReportesController() {
    }

    public ItemReporte getSelected() {
        return selected;
    }

    public void setSelected(ItemReporte selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }
    public List<ItemReporte> items(){
        return pagoFacade.reporteLibroIngreso(new Date() , new Date());
    }
    
//    private DetalleAvisoCobroFacade getFacade() {
//        return ejbFacade;
//    }
//  

//    public List<itemReporte> getItems() {
//        if (items == null) {
//            items = getFacade().findAll();
//        }
//        return items;
//    }

//    
//    public DetalleAvisoCobro getDetalleAvisoCobro(java.lang.Integer id) {
//        return getFacade().find(id);
//    }
//
//    public List<DetalleAvisoCobro> getItemsAvailableSelectMany() {
//        return getFacade().findAll();
//    }

//    public List<itemReporte> getItemsAvailableSelectOne() {
//        return getFacade().findAll();
//    }
   

  /* @FacesConverter(forClass = DetalleAvisoCobro.class)
    public static class DetalleAvisoCobroControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ReportesController controller = (ReportesController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "detalleAvisoCobroController");
            return controller.getDetalleAvisoCobro(getKey(value));
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
            if (object instanceof DetalleAvisoCobro) {
                DetalleAvisoCobro o = (DetalleAvisoCobro) object;
                return getStringKey(o.getIdDetalleAvisoCobro());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), DetalleAvisoCobro.class.getName()});
                return null;
            }
        }

    }*/

}
