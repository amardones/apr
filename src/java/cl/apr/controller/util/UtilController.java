/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.apr.controller.util;

import cl.apr.enums.EnumFormatoFechaHora;
import cl.apr.util.NumeroFormato;
import java.io.Serializable;
import java.util.Date;
import java.util.Locale;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author hmardones
 */
@Named("utilController")
@SessionScoped
public class UtilController implements Serializable{
    
     public boolean filterByText(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim();
        if (filterText == null || filterText.equals("")) {
            return true;
        }

        if (value == null) {
            return false;
        }

        String carName = value.toString().toUpperCase();
        filterText = filterText.toUpperCase();

        if (carName.contains(filterText)) {
            return true;
        } else {
            return false;
        }
    }
    
    public String fechaActual(){
        return EnumFormatoFechaHora.formatoFecha.format(new Date());
    }
    
    static public String formatearNumeroPesos(String pesos){
        return NumeroFormato.formatearNumeroPesos(pesos);
    }
    static public String formatearNumeroPesos(int pesos){
        return NumeroFormato.formatearNumeroPesos(pesos);
    }
}
