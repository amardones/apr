package cl.apr.util;

import java.text.NumberFormat;
import java.util.Locale;

public class NumeroFormato {
	
	static public String formatearNumeroPesos(String pesos){
		String result="";
		int num=0;
		try{
			num=Integer.parseInt(pesos);
			result = NumberFormat.getInstance(Locale.GERMANY).format(num);
		}catch(Exception e){
			
		}
		return result;
	}
        static public String formatearNumeroPesos(int pesos){
		String result="";
		int num=0;
		try{
			num=pesos;
			result = NumberFormat.getInstance(Locale.GERMANY).format(num);
		}catch(Exception e){
			
		}
		return result;
	}

}
