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
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;


import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;

@Named("avisoCobroController")
@SessionScoped
public class AvisoCobroController implements Serializable {

    //@ManagedProperty(value="#{periodoController}")
    @Inject
    private PeriodoController periodoController;
    
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
    
          
    public void generarTodosLosAvisoCobro() {
      if(periodoController.getSelected() != null && getFacade().crearAvisosDeCobro(periodoController.getSelected().getIdPeriodo(), -1)){
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AvisoCobroCreated"));
      }else{
          JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("AvisoDeCobroCrearError"));      
      }
    }
    /*
     public void generarAvisoCobro() {
         if(this.selected != null){
            if(periodo != null && getFacade().crearAvisosDeCobro(this.selected.getAvisoCobroPK().getIdPeriodo(), this.selected.getAvisoCobroPK().getIdCuenta())){
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CuentaCreated"));
            }
        }
    }
     */
     public void generarAvisoCobro(AvisoCobro aviso) {
         if(aviso != null){
            if(periodoController.getSelected() != null && getFacade().crearAvisosDeCobro(aviso.getAvisoCobroPK().getIdPeriodo(), aviso.getAvisoCobroPK().getIdCuenta())){
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AvisoCobroCreated"));
            }else{
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("AvisoDeCobroCrearError"));      
            }
        }
    }

    protected void setEmbeddableKeys() {
        selected.getAvisoCobroPK().setIdCuenta(selected.getRegistroEstado().getCuenta().getIdCuenta());
        selected.getAvisoCobroPK().setIdPeriodo(selected.getRegistroEstado().getPeriodo().getIdPeriodo());
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

//    public List<Periodo> getPeriodos() {
//        List<Periodo> periodos = ejbPeriodoFacade.getPeriodos();
//        if(periodo == null && periodos != null && periodos.size() > 0){
//            periodo = periodos.get(0);
//        }
//        return  periodos;
//    }
//    
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
        items = new ArrayList<>();
        try{  
            if(periodoController.getSelected() != null){
             //return periodo.getAvisoCobroList();
                  items = getFacade().getAvisosPorPeriodo(periodoController.getSelected().getIdPeriodo());
                 return  items;
            }
         }catch(Exception e){
              items = new ArrayList<>();
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
    
    public List<AvisoCobro> getAvisodeCobroDisponibles() {
        return getFacade().avisodeCobroDisponibles();
    }
    public List<AvisoCobro> getMedidoresDisponiblesEditar() {
        
        return ejbFacade.getavisoDeCobroDisponiblesEditar(selected.getRegistroEstado().getCuenta().getIdCuenta(),selected.getRegistroEstado().getPeriodo().getIdPeriodo());
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
    
    

     public void verAvisos() {
        try {
            if(periodoController.getSelected() != null){
                FacesContext ctx = FacesContext.getCurrentInstance();
                ExternalContext ectx = ctx.getExternalContext();
                HttpServletRequest request = (HttpServletRequest) ectx.getRequest();
                HttpServletResponse response = (HttpServletResponse) ectx.getResponse();
                RequestDispatcher dispatcher = request.getRequestDispatcher("/verAviso");
                request.setAttribute("idPeriodo", periodoController.getSelected().getIdPeriodo());
                dispatcher.forward(request, response);
                ctx.responseComplete();
                System.out.println("call servlet");
            }
        } catch (ServletException ex) {
            Logger.getLogger(AvisoCobroController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AvisoCobroController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     public void verAviso(AvisoCobro aviso) {
        try {
            
            if(aviso != null){
                FacesContext ctx = FacesContext.getCurrentInstance();
                ExternalContext ectx = ctx.getExternalContext();
                
                HttpServletRequest request = (HttpServletRequest) ectx.getRequest();
                HttpServletResponse response = (HttpServletResponse) ectx.getResponse();
                RequestDispatcher dispatcher = request.getRequestDispatcher("/verAviso");
                request.setAttribute("idCuenta", aviso.getAvisoCobroPK().getIdCuenta());
                request.setAttribute("idPeriodo", aviso.getAvisoCobroPK().getIdPeriodo());
                dispatcher.forward(request, response);
                ctx.responseComplete();
                System.out.println("call servlet is cuenta: " + aviso.getRegistroEstado().getCuenta().getIdCuenta());
            }
        } catch (ServletException ex) {
            Logger.getLogger(AvisoCobroController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AvisoCobroController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
     /*
     public boolean showRecalcular() {
         try{  
                return periodoController.ultimoPeriodo();
            
         }catch(Exception e){
                e.printStackTrace();
         }
        
         return false;
     }
*/
}
