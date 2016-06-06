package cl.apr.controller;

import cl.apr.beans.RegistroEstadoReporte;
import cl.apr.entity.RegistroEstado;
import cl.apr.controller.util.JsfUtil;
import cl.apr.controller.util.JsfUtil.PersistAction;
import cl.apr.entity.Periodo;
import cl.apr.facade.RegistroEstadoFacade;
import java.io.IOException;

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
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Named("registroEstadoController")
@SessionScoped
public class RegistroEstadoController implements Serializable {

    @EJB
    private cl.apr.facade.RegistroEstadoFacade ejbFacade;
    @EJB
    private cl.apr.facade.CuentaFacade ejbCuentaFacade;
    @EJB
    private cl.apr.facade.AvisoCobroFacade ejbAvisoCobroFacade;

    
    
    private List<RegistroEstado> items = null;
    private RegistroEstado selected;
    private double metrosCalculados;
    private Integer periodo;
   
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
             if (!JsfUtil.isValidationFailed()) {
                items = null;    // Invalidate list of items to trigger re-query.
             }
        }else{
             JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("RegistroEstadosEditarMetrosCubicosError"));
              items = null; 
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
        if (items == null) {
            if(periodoController.getSelected() != null){
                    if(periodoController.getSelected().getIdPeriodo()==null){            
                    items = getFacade().getRegistroEstadoPorPeriodo(periodoController.getUltimoPeriodo().getIdPeriodo());
                    periodoController.setSelected(periodoController.getUltimoPeriodo());
                    }
                    if(periodoController.getSelected().getIdPeriodo()!=null)
                    items = getFacade().getRegistroEstadoPorPeriodo(periodoController.getSelected().getIdPeriodo()); 
                    System.out.println("Registros de estado: "+items.size());
            }
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
    public boolean permiteExportarPDF(){        
        if( items!= null &&  items.size()>0){
            System.out.println("si");
            return true;
        }else{
            return false;
        }
        
    }
    
     
    public void verReporteRegistro() {
        try {
            
                FacesContext ctx = FacesContext.getCurrentInstance();
                ExternalContext ectx = ctx.getExternalContext();
                HttpServletRequest request = (HttpServletRequest) ectx.getRequest();
                HttpServletResponse response = (HttpServletResponse) ectx.getResponse();
                RequestDispatcher dispatcher = request.getRequestDispatcher("/verReporteRegistro");
                dispatcher.forward(request, response);
                ctx.responseComplete();
                System.out.println("call servlet");
            
        } catch (ServletException ex) {
            Logger.getLogger(RegistroCobroController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RegistroCobroController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                    ejbAvisoCobroFacade.crearAvisosDeCobro(selected.getRegistroEstadoPK().getIdPeriodo(), selected.getRegistroEstadoPK().getIdCuenta());
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

    public Integer getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Integer periodo) {
        this.periodo = periodo;
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
     public boolean permiteRecalcular(RegistroEstado item) {
         if(item != null){
            if(periodoController.getSelected() != null && periodoController.ultimoPeriodo(periodoController.getSelected())){
                boolean r   = ejbCuentaFacade.permiteRecalcular(item.getPeriodo().getIdPeriodo(),item.getCuenta().getIdCuenta());
                //System.out.println("item.getCuenta().getIdCuenta():"+item.getCuenta().getIdCuenta());
                //System.out.println("item.getPeriodo().getIdPeriodo():"+item.getPeriodo().getIdPeriodo());
                //System.out.println("permiteRecalcular:"+r);
                return r;
            }
        }
        return false;
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
