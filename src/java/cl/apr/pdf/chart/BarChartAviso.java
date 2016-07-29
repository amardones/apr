/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.apr.pdf.chart;

import cl.apr.beans.BarChartItem;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Paint;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import org.jfree.data.category.DefaultCategoryDataset; /* We will use this dataset class to populate data for our bar chart */
import org.jfree.chart.ChartFactory; /* used to create a chart object */
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.JFreeChart; 
import org.jfree.chart.ChartUtilities; /* We will use this class to convert the chart to a PNG image file */
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;

/**
 *
 * @author hmardones
 */
public class BarChartAviso {
    
     static private Paint[] colors = new Paint[]{Color.red, Color.blue, Color.green, 
      Color.yellow, Color.orange, Color.cyan, 
      Color.magenta, Color.blue}; 
     
   public static BufferedImage crearBarchart(List<BarChartItem> barChartItems){
       BufferedImage bi  = null;
         try {
                
             ChartFactory.setChartTheme(StandardChartTheme.createJFreeTheme());
             //ChartFactory.setChartTheme(StandardChartTheme.createDarknessTheme());

                /* Step - 1: Define the data for the bar chart  */
                DefaultCategoryDataset my_bar_chart_dataset = new DefaultCategoryDataset();
               int i=0;
                for (BarChartItem barChartItem : barChartItems) {
                    if(barChartItem.getNombre().equals("-")){
                        my_bar_chart_dataset.addValue(barChartItem.getValor(), "serie", (++i)+"");
                    }else
                        my_bar_chart_dataset.addValue(barChartItem.getValor(), "serie", barChartItem.getNombre());
                }
//                my_bar_chart_dataset.addValue(34, "mes", "Ene");
//                my_bar_chart_dataset.addValue(25, "mes", "Feb");
//                my_bar_chart_dataset.addValue(22, "mes", "Mar");
//                my_bar_chart_dataset.addValue(12, "mes", "Abr");
//                my_bar_chart_dataset.addValue(40, "mes", "May");
//                my_bar_chart_dataset.addValue(30, "mes", "jun");
//                my_bar_chart_dataset.addValue(2, "mes", "Jul");
//                my_bar_chart_dataset.addValue(15, "mes", "Ago");
                
                
                
                /* Step -2:Define the JFreeChart object to create bar chart */
                //JFreeChart chart=ChartFactory.createBarChart("Detalle de sus consumos","","MT3",my_bar_chart_dataset,PlotOrientation.VERTICAL,true,true,false);    
                JFreeChart chart=ChartFactory.createBarChart("","","MT3",my_bar_chart_dataset,PlotOrientation.VERTICAL,true,true,false);     
               
                
                
                //chart.setBackgroundPaint(Color.lightGray);
                // get a reference to the plot for further customisation... 
                final CategoryPlot plot = chart.getCategoryPlot(); 
                CategoryItemRenderer renderer = new CustomRenderer(); 
        
                renderer.setSeriesPaint(0, Color.DARK_GRAY );
                    
                plot.setRenderer(renderer);
                plot.setBackgroundPaint(Color.white);
               
                chart.setBorderVisible(false);
                chart.setBackgroundPaint(Color.white);
                chart.setBorderStroke(null);
                chart.getLegend().visible = false;
                 /* Step -3: Write the output as PNG file with bar chart information */                
                 int width=480; /* Width of the image */
                 int height=250; /* Height of the image */    
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

    class CustomRenderer extends BarRenderer 
    { 
     private Paint[] colors;
     public CustomRenderer() 
     { 
        this.colors = new Paint[] {Color.red, Color.blue, Color.green, 
          Color.yellow, Color.orange, Color.cyan, 
          Color.magenta, Color.blue}; 
     }
     /*
     public Paint getItemPaint(final int row, final int column) 
     { 
        // returns color for each column 
        return Color.gray;
        //return (this.colors[column % this.colors.length]); 
     } */
    }
