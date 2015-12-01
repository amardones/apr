/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.apr.enums;

import java.text.SimpleDateFormat;

/**
 *
 * @author hmardones
 */
public class EnumFormatoFechaHora {
    
        static public SimpleDateFormat formatoTimestamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        static public SimpleDateFormat formatoTimestampInverso = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	static public SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
	static public SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");
	static public SimpleDateFormat formatoAnio= new SimpleDateFormat("yyyy");
	static public SimpleDateFormat formatoDiaMesTextoCortoAnio= new SimpleDateFormat("dd MMM yyyy");
	static public SimpleDateFormat formatoDiaMesTexto= new SimpleDateFormat("dd MMMM");
        static public SimpleDateFormat formatoMesTextoAnio= new SimpleDateFormat("MMMM yyyy");
	static public SimpleDateFormat formatoAnoMesDia= new SimpleDateFormat("yyyy/MM/dd");
}
