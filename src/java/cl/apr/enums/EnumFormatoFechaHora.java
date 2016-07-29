/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.apr.enums;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author hmardones
 */
public class EnumFormatoFechaHora {
    
        static public SimpleDateFormat formatoTimestamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        static public SimpleDateFormat formatoTimestampInverso = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	static public SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
	static public SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");
	static public SimpleDateFormat formatoMes= new SimpleDateFormat("MM");
        static public SimpleDateFormat formatoAnio= new SimpleDateFormat("yyyy");
	static public SimpleDateFormat formatoDiaMesTextoCortoAnio= new SimpleDateFormat("dd MMM yyyy");
	static public SimpleDateFormat formatoDiaMesTexto= new SimpleDateFormat("dd MMMM");
        static public SimpleDateFormat formatoMesTextoAnio= new SimpleDateFormat("MMMM yyyy");
        static public SimpleDateFormat formatoMesTexto= new SimpleDateFormat("MMMM");
	static public SimpleDateFormat formatoAnoMesDia= new SimpleDateFormat("yyyy/MM/dd");
        static public SimpleDateFormat formatoAnoMesDiaSinSeparador= new SimpleDateFormat("yyyyMMdd");
        
        
        static public  Date getDateBy(int day){
            Date d = new Date();           
            int month = Integer.parseInt(formatoMes.format(d))-1; //12
            int year = Integer.parseInt(formatoAnio.format(d)); // 1988

            System.out.println(year);

            Calendar c = Calendar.getInstance();
            c.set(year, month, day, 0, 0);  
            
            return c.getTime();
        }
         static public  Date getLastDateMonth(){
            Date d = new Date();           
            int month = Integer.parseInt(formatoMes.format(d))-1; //12
            int year = Integer.parseInt(formatoAnio.format(d)); // 1988

            System.out.println(year);

            Calendar c = Calendar.getInstance();
                    
            c.set(year, month,  Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH), 0, 0);  
            
            return c.getTime();
        }
         
          static public  Date getDate(int dia, int mes, int anio){
                   
            int day = dia; //12
            int month = mes -1; //12
            int year = anio; // 1988

            System.out.println(year);

            Calendar c = Calendar.getInstance();
                    
            c.set(year, month,  day);  
            
            return c.getTime();
        }
        
        
        
}
