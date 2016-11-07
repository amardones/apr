/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.apr.pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.html.WebColors;

/**
 *
 * @author hmardones
 */
public class UtilPDF {
    
    static public float MARGIN_LEFT = 20;
    static public float MARGIN_RIGHT = 20;
    static public float MARGIN_TOP = 15;
    static public float MARGIN_BOTTOM = 15;
    
    private static BaseColor colorRelleno = WebColors.getRGBColor("#eeeeee");
    public static BaseColor colorBlueLigth = WebColors.getRGBColor("#009edd"); 
    public static BaseColor colorBlue = WebColors.getRGBColor("#00008B"); 
    public static BaseColor borderColor = BaseColor.LIGHT_GRAY;

    public static Font fTitulo1 = new Font(Font.getFamily("ARIAL"),20,Font.BOLD);
    public static Font fTitulo2 = new Font(Font.getFamily("ARIAL"),13,Font.BOLD);
    public static Font fSeparador = new Font(Font.getFamily("ARIAL"),4,Font.NORMAL);
    //private static Font fTitulo = new Font(Font.getFamily("ARIAL"),14,Font.BOLD);
    //private static Font fTituloTabla = new Font(Font.getFamily("ARIAL"),11,Font.BOLD);
    //private static Font fSubTituloTabla = new Font(Font.getFamily("ARIAL"),8,Font.BOLD);//10
    public static Font fCuerpoCabeceraTabla= new Font(Font.getFamily("ARIAL"),10f,Font.BOLD);
    public static Font fCuerpoCabeceraTablaTotal= new Font(Font.getFamily("ARIAL"),13f,Font.BOLD);
    //private static Font fCuerpoCabeceraTablaWhite= new Font(Font.getFamily("ARIAL"),7f,Font.BOLD, BaseColor.WHITE);
    public static Font fCuerpoTabla = new Font(Font.getFamily("ARIAL"),9f,Font.BOLD, BaseColor.DARK_GRAY);
    public static Font fCuerpoTablaBold = new Font(Font.getFamily("ARIAL"),9f,Font.BOLD, BaseColor.DARK_GRAY);
    //private static BaseColor colorRellenoLightGray = BaseColor.LIGHT_GRAY;	
}
