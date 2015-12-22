package br.com.ac.graph;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import br.com.ac.surpresa.Surpresa;

public class FrequenciaMaiorOcorrenciaGraph extends ApplicationFrame
{
   public FrequenciaMaiorOcorrenciaGraph( String applicationTitle , String chartTitle, int numConcursoInicio, boolean maiorEmedioMaior)
   {
      super(applicationTitle);
      CategoryDataset dataset = createDataset(numConcursoInicio, maiorEmedioMaior);
      JFreeChart lineChart = ChartFactory.createLineChart(
         chartTitle,
         "Concurso","Ocorrencia",
         dataset,
         PlotOrientation.VERTICAL,
         true,true,false);
      
      final CategoryPlot plot = (CategoryPlot) lineChart.getPlot();
      plot.getRenderer().setSeriesStroke(0, new BasicStroke(3f));
      plot.getRenderer().setSeriesStroke(1, new BasicStroke(3f));
      
      //settings
      final CategoryAxis categoryAxis = (CategoryAxis) plot.getDomainAxis();
      categoryAxis.setAxisLineVisible(true);
      categoryAxis.setTickMarksVisible(true);
      //categoryAxis.setVisible(false);

      final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
      //rangeAxis.setAxisLineVisible(false);
      rangeAxis.setVisible(true);
      rangeAxis.setLabelPaint(Color.BLUE);
      rangeAxis.setRange(0, 6);
      rangeAxis.setTickUnit(new NumberTickUnit(1));
      
      
      
      ChartPanel chartPanel = new ChartPanel( lineChart );
      chartPanel.setPreferredSize( new java.awt.Dimension( 2500 , 700 ) );
      setContentPane( chartPanel );
   }

   private DefaultCategoryDataset createDataset(int numConcInicio, boolean maiorEmedioMaior)
   {
      DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
      //XYSeriesCollection dataset = new XYSeriesCollection();
      //dataset.setAutoWidth(false);
      //dataset.setIntervalWidth(2000);
      
      Surpresa surpresa = new Surpresa();
      List<List<Integer>> tuto = new ArrayList<List<Integer>>();
      tuto = surpresa.geraFromResource();

      List<int[]> ocorrencias = new ArrayList<int[]>();
      int apartircc = numConcInicio;
      ocorrencias = surpresa.getHistoricoOcorrencias(tuto, false);
      ocorrencias = ocorrencias.subList(apartircc - 1, ocorrencias.size());
      
      int i = 1;
      for (int[] ocr : ocorrencias) {
		 if(maiorEmedioMaior){
			 dataset.addValue( ocr[0] , "Ocorrencia Maior" , String.valueOf(i+apartircc) );
			 dataset.addValue( ocr[1] , "Ocorrencia Media Maior" , String.valueOf(i+apartircc) );
		 }else{
			 dataset.addValue( ocr[2] , "Ocorrencia Media Menor" , String.valueOf(i+apartircc) );
			 dataset.addValue( ocr[3] , "Ocorrencia Menor" , String.valueOf(i+apartircc) );
		 }
    	 i++;
      }
      //dataset.addValue( 15 , "schools" , "1970" );
      
      return dataset;
   }
   
   
   public static void main( String[ ] args ) 
   {
	   
      FrequenciaMaiorOcorrenciaGraph chart = new FrequenciaMaiorOcorrenciaGraph(
      "Ocorrencias" ,
      "Ocorrência",
      1500,
      true);

      chart.pack( );
      RefineryUtilities.centerFrameOnScreen( chart );
      chart.setVisible( true );
   }
}