package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Demonstrates {@link BarChartComponent} class. Uses {@link JFrame} with
 * {@link BarChartComponent} as only component on it. Takes command line
 * argument as path to text file with data needed for creation of
 * {@link BarChartComponent}. <br>
 * Text file should be created with following rules: <br>
 * line 1: x axis description <br>
 * line 2: y axis description <br>
 * line 3: values separated with spaces, and value components with commas <br>
 * line 4: minimum value of y axis to be shown <br>
 * line 5: maximum value of y axis to be shown <br>
 * line 6: scaling of y axis <br>
 * 
 * @author Teo Toplak
 *
 */
public class BarChartDemo extends JFrame{

	private static final long serialVersionUID = 1L;
	/**
	 * Path to text file
	 */
	private String path;
	
	/**
	 * Constructor which takes path to text file as argument.
	 * @param path path to text file
	 */
	public BarChartDemo(String path) {
		this.path=path;
		setLocation(20,50);
		setSize(1000, 600);
		setTitle("BarChartDemo");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		initGUI();
	}
	
	/**
	 * Creates {@link BarChart} instance with path to text file for parsing.
	 * @param path path to text file
	 * @return {@link BarChart} chart
	 */
	private BarChart createBarChart(String path) {

		List<String> input= new ArrayList<>();
		try { 
			input = Files.readAllLines(Paths.get(path),
					StandardCharsets.UTF_8);
			} catch (Exception e){
				System.err.println("Failed reading document!");
				System.exit(-1);
			}
		String xDesc = input.get(0);
		String yDesc = input.get(1);
		String[] arr = input.get(2).split("\\s+");
		int ymin = 0;
		int ymax = 0;
		int scale = 0;
		List<XYValue> values = new ArrayList<>();
		try {
			for(String str : arr) {
				String[] value = str.split("\\,");
				if(value.length!=2) {
					System.err.println("Failed parsing document!");
					System.exit(-1);
				}
				values.add(new XYValue(Integer.parseInt(value[0]),Integer.parseInt(value[1])));
			}
			ymin = Integer.parseInt(input.get(3));
			ymax = Integer.parseInt(input.get(4));
			scale = Integer.parseInt(input.get(5));
		} catch(Exception e) {
			System.err.println("Failed parsing document!");
			System.exit(-1);
		}
		return new BarChart(values, xDesc, yDesc, ymin, ymax, scale, path);
	}
	
	/**
	 * Initaites GUI of this demonstration.
	 * {@link BarChartComponent} is created and added here.
	 */
	private void initGUI() {

		BarChart model = createBarChart(path);
		BarChartComponent comp = new BarChartComponent(model);

		getContentPane().setLayout( new BorderLayout());
		getContentPane().add(comp, BorderLayout.CENTER);
	}
	
	/**
	 * Method called when executing programm.
	 * Takes command line input: read class javadoc.
	 * @param args command line input
	 */
	public static void main(String[] args) {
		if(args.length==0) {
			System.err.println("Set path to document in command line!");
			System.exit(1);
		}
		SwingUtilities.invokeLater(()-> {
			JFrame frame = new BarChartDemo(args[0]);
			frame.setVisible(true);
		});
	}
	
}
