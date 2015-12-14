package br.com.ac.graph;

import java.awt.BasicStroke;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import br.com.ac.surpresa.Surpresa;

public class MediaDevpGraph extends ApplicationFrame
{
   public MediaDevpGraph( String applicationTitle , String chartTitle, int numInicioConcurso )
   {
      super(applicationTitle);
      JFreeChart lineChart = ChartFactory.createLineChart(
         chartTitle,
         "Concurso","Media",
         createDataset(numInicioConcurso),
         PlotOrientation.VERTICAL,
         true,true,false);
      
      final CategoryPlot plot = (CategoryPlot) lineChart.getPlot();
      plot.getRenderer().setSeriesStroke(0, new BasicStroke(3f));
      plot.getRenderer().setSeriesStroke(1, new BasicStroke(3f));
      
      ChartPanel chartPanel = new ChartPanel( lineChart );
      chartPanel.setPreferredSize( new java.awt.Dimension( 5000 , 800 ) );
      setContentPane( chartPanel );
   }

   private DefaultCategoryDataset createDataset(int numInicioConcurso )
   {
      DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
      
      Surpresa surpresa = new Surpresa();
      List<List<Integer>> tuto = new ArrayList<List<Integer>>();
      tuto = surpresa.geraFromResource();

      List<List<Double>> mediaDesv = new ArrayList<List<Double>>();
      mediaDesv = surpresa.mapaMediaDesvPadrao(tuto);
      
      int i = 0;
      for (List<Double> list : mediaDesv) {
    	 if(i > numInicioConcurso){
    		 dataset.addValue( list.get(0) , "Media" , i+"" );
    		 dataset.addValue( list.get(1) , "Devp" , i+"" );
    	 }
    	 i++;
      }
      //dataset.addValue( 15 , "schools" , "1970" );
      
      return dataset;
   }
   public static void main( String[ ] args ) 
   {
      MediaDevpGraph chart = new MediaDevpGraph(
      "Media" ,
      "Numer of Schools vs years",
      1500);

      chart.pack( );
      RefineryUtilities.centerFrameOnScreen( chart );
      chart.setVisible( true );
   }
}