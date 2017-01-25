package hr.fer.zemris.java.hw13.chart;

import java.awt.Dimension;
import java.util.Map;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

/**
 * Class representing pie chart diagram.
 * Shows pie with radnom different colors, 
 * named parts and legend. Doesnt show
 * quantity of elements, just ratio 
 * between them. When created you can select
 * name of diagram, apllication title, and 
 * must provide map containing pairs
 * values - name.
 * 
 * @author Teo Toplak
 *
 */
public class PieChart extends JFrame {

	/**
	 * what is happening
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * panel used
	 */
	private ChartPanel panel;
	/**
	 * values of chart
	 */
	private Map<String,Integer> values;

	/**
	 * Constructor taking application title, chart
	 * title and map with pari name-value.
	 * @param applicationTitle application title
	 * @param chartTitle chart title
	 * @param values values
	 */
	public PieChart(String applicationTitle, String chartTitle,
			Map<String,Integer> values) {
		super(applicationTitle);

		this.values = values;
		// This will create the dataset 
		PieDataset dataset = createDataset();
		// based on the dataset we create the chart
		JFreeChart chart = createChart(dataset, chartTitle);
		// we put the chart into a panel
		ChartPanel chartPanel = new ChartPanel(chart);

		// default size
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		// add it to our application
		chartPanel.setSize(500, 270);
		setContentPane(chartPanel);
		panel=chartPanel;
	}


	/**
	 * Creates a sample dataset 
	 */
	private  PieDataset createDataset() {
		DefaultPieDataset result = new DefaultPieDataset();

		values.forEach((k,v)-> {
			result.setValue(k, v);
		});
		return result;
	}


	/**
	 * Panel getter
	 * @return the panel
	 */
	public ChartPanel getPanel() {
		return panel;
	}

	
	/**
	 * chart size getter
	 * @return dimension of chart
	 */
	public Dimension getChartSize() {
		return new Dimension(500, 270);
	}
	/**
	 * Creates a chart
	 */
	private JFreeChart createChart(PieDataset dataset, String title) {

		JFreeChart chart = ChartFactory.createPieChart3D(title,          // chart title
				dataset,                // data
				true,                   // include legend
				true,
				false);

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		return chart;

	}
} 
