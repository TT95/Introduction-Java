package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * Class used for storing some kind of data. Contains list of numbered values,
 * maximum, minimum value, scaling of values, path to file and descriptions
 * (usually descritpions od x and y axis),
 * 
 * @author Teo Toplak
 *
 */
public class BarChart {

	/**
	 * list of numbered values
	 */
	private List<XYValue> list;
	/**
	 * description of x axis
	 */
	private String xDesc;
	/**
	 * description of y axis
	 */
	private String yDesc;
	/**
	 * minimum value
	 */
	private int ymin;
	/**
	 * maximum value
	 */
	private int ymax;
	/**
	 * value scailing
	 */
	private int scale;
	/**
	 * path to text file
	 */
	private String path;
	
	/**
	 * Construcor which takes all data for storing.
	 * @param list list of numbered values
	 * @param xDesc description of x axis
	 * @param yDesc description of y axis
	 * @param ymin minimum value
	 * @param ymax maximum value
	 * @param scale value scailing
	 * @param path path to text file
	 */
	public BarChart(List<XYValue> list, String xDesc, String yDesc, int ymin,
			int ymax, int scale, String path) {
		this.list = list;
		this.xDesc = xDesc;
		this.yDesc = yDesc;
		this.ymin = ymin;
		this.ymax = ymax;
		this.scale = scale;
		this.path = path;
	}

	/**
	 * List of numbered values getter
	 * @return List of numbered values
	 */
	public List<XYValue> getList() {
		return list;
	}

	/**
	 * Description of x axis getter
	 * @return Description of x axis
	 */
	public String getxDesc() {
		return xDesc;
	}

	/**
	 * Description of y axis getter
	 * @return Description of y axis
	 */
	public String getyDesc() {
		return yDesc;
	}

	/**
	 * Minimum value getter
	 * @return Minimum value
	 */
	public int getYmin() {
		return ymin;
	}

	/**
	 * Maximum value getter
	 * @return Maximum value 
	 */
	public int getYmax() {
		return ymax;
	}

	/**
	 * Value scailing getter
	 * @return Value scailing
	 */
	public int getScale() {
		return scale;
	}

	/**
	 * Path to text file getter
	 * @return Path to text file
	 */
	public String getPath() {
		return path;
	}


	
	
	
	
	
}
