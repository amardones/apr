/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.apr.pdf.chart;

import java.awt.Color;
import java.awt.Paint;
import java.awt.image.BufferedImage;
import java.io.*;
import org.jfree.data.category.DefaultCategoryDataset; /* We will use this dataset class to populate data for our bar chart */
import org.jfree.chart.ChartFactory; /* used to create a chart object */
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.JFreeChart; 
import org.jfree.chart.ChartUtilities; /* We will use this class to convert the chart to a PNG image file */

/**
 *
 * @author hmardones
 */
public class BarChartAviso {
    
     static private Paint[] colors = new Paint[]{Color.red, Color.blue, Color.green, 
      Color.yellow, Color.orange, Color.cyan, 
      Color.magenta, Color.blue}; 
     
   public static BufferedImage crearBarchart(){
       BufferedImage bi  = null;
         try {
                
                /* Step - 1: Define the data for the bar chart  */
                DefaultCategoryDataset my_bar_chart_dataset = new DefaultCategoryDataset();
                my_bar_chart_dataset.addValue(34, "mes", "Ene");
                my_bar_chart_dataset.addValue(45, "mes", "Feb");
                my_bar_chart_dataset.addValue(22, "mes", "Mar");
                my_bar_chart_dataset.addValue(12, "mes", "Abr");
                my_bar_chart_dataset.addValue(56, "mes", "May");
                my_bar_chart_dataset.addValue(60, "mes", "jun");
                my_bar_chart_dataset.addValue(2, "mes", "Jul");
                my_bar_chart_dataset.addValue(15, "mes", "Ago");
                
                
                
                /* Step -2:Define the JFreeChart object to create bar chart */
                JFreeChart chart=ChartFactory.createBarChart("","Mes","MT3",my_bar_chart_dataset,PlotOrientation.VERTICAL,true,true,false);         
                chart.setBackgroundPaint(Color.WHITE);
                          
                 /* Step -3: Write the output as PNG file with bar chart information */                
                 int width=800; /* Width of the image */
                 int height=480; /* Height of the image */    
                 bi = chart.createBufferedImage(width, height);
                 /*
                 
                 File BarChart=new File("output_chart.png");              
                 ChartUtilities.saveChartAsPNG(BarChart,BarChartObject,width,height); 
                         */
         }
         catch (Exception i)
         {
             System.out.println(i);
         }
         
         return bi;
    }
}
