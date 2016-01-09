package cl.apr.controller;

import cl.apr.entity.Socio;
import cl.apr.controller.util.JsfUtil;
import cl.apr.controller.util.JsfUtil.PersistAction;
import cl.apr.facade.SocioFacade;

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

@Named("socioController")
@SessionScoped
public class SocioController implements Serializable {

    @EJB
    private cl.apr.facade.SocioFacade ejbFacade;
    private List<Socio> items = null;
    private Socio selected;
    private String rut;
    private String validacionRut;

    public SocioController() {
    }

    public Socio getSelected() {
        return selected;
    }

    public void setSelected(Socio selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private SocioFacade getFacade() {
        return ejbFacade;
    }

    public Socio prepareCreate() {
        selected = new Socio();
        initializeEmbeddableKey();
        System.out.println("Prepare Socio");
        return selected;
    }

    public void create() {
        boolean rutEnBD=false;        
        for (Socio socio : this.getItems()) {
            if(socio.getRut().equals(selected.getRut())){
               rutEnBD=true;
            }
        }
        if(!rutEnBD){
                persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("SocioCreated"));
                if (!JsfUtil.isValidationFailed()) {
                    items = null;    // Invalidate list of items to trigger re-query.
                } 
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error Rut ya existe",null));
     
        }
            
        
    }
   

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("SocioUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("SocioDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Socio> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }
public List<Socio> completeSocio(String query) {
        System.out.println("Entro complete");
        List<Socio> filteredThemes = new ArrayList<Socio>();
        try{
            List<Socio> allThemes = getFacade().findAll();

            Socio c = null;
            String s = "";        
            for (int i = 0; i < allThemes.size(); i++) {
                 c = allThemes.get(i);
                 if(c != null){
                    s = c.getRut()+ c.getNombre() + c.getApellido() + c.getCelular();
                    if(s.toLowerCase().contains(query)) {
                        filteredThemes.add(c);
                    }
                 }
            }
        }catch(Exception e){
            System.out.println("EXCEPTION");
            e.printStackTrace();
        }
        System.out.println("Sale complete");
        return filteredThemes;
    }
    public boolean validarRut() {
        rut=null;
        boolean validacion = false;
        try {
        rut =  selected.getRut().toUpperCase();
        rut = rut.replace(".", "");
        rut = rut.replace("-", "");
        int rutAux = Integer.parseInt(rut.substring(0, rut.length() - 1));

        char dv = rut.charAt(rut.length() - 1);

        int m = 0, s = 1;
        for (; rutAux != 0; rutAux /= 10) {
        s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
        }
        if (dv == (char) (s != 0 ? s + 47 : 75)) {
        validacion = true;
        }

        } catch (java.lang.NumberFormatException e) {
        } catch (Exception e) {
        }
        return validacion;
        }
    
    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {  
                    if(validarRut()){
                        getFacade().edit(selected);
                    }else{
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error Rut inválido",null));
                        return;
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

    public Socio getSocio(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<Socio> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Socio> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Socio.class)
    public static class SocioControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SocioController controller = (SocioController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "socioController");
            return controller.getSocio(getKey(value));
        }

        java.lang.String getKey(String value) {
            java.lang.String key;
            key = value;
            return key;
        }

        String getStringKey(java.lang.String value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Socio) {
                Socio o = (Socio) object;
                return getStringKey(o.getRut());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Socio.class.getName()});
                return null;
            }
        }

    }

}
