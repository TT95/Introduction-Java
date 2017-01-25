package hr.fer.zemris.java.gui.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.List;
import javax.swing.JComponent;

/**
 * Class representing component for creation of graphs showing some kind of data.
 * Takes {@link BarChart} as source of data for creation of graph.
 * Graph is made of x and y axis and rectangles showing values of data provided.
 * For more info about data storage read {@link BarChart} javadoc.
 * 
 * @author Teo Toplak
 *
 */
public class BarChartComponent extends JComponent{


	private static final long serialVersionUID = 1L;
	/**
	 * {@link BarChart} which provides data for graph
	 */
	private BarChart chart;
	/**
	 * offset from window rim
	 */
	private int OFFSET=20;
	/**
	 * width of biggest number shown on y axis
	 */
	private int YNUM;
	/**
	 * space made for description of x axis 
	 */
	private int XNUM=OFFSET;
	/**
	 * used for creating little dashes on axes for easier indication of values
	 */
	private int LINE_OFFSET=10;
	/**
	 * offset of numbers on y axis used to avoid overlaping with dashes
	 */
	private int NUM_OFFSET=3;

	/**
	 * Constructor taking {@link BarChart} as only argument for creation of this
	 * component.
	 * 
	 * @param chart {@link BarChart} chart
	 */
	public BarChartComponent(BarChart chart) {
		super();
		this.chart = chart;

	}

	@Override
	protected void paintComponent(Graphics g1) {
		Insets ins = getInsets();
		Graphics2D g = (Graphics2D) g1;
		FontMetrics metrics = g.getFontMetrics();
		YNUM = metrics.stringWidth(String.valueOf(chart.getYmax()));
		
		Dimension size = getSize();
		Rectangle rect = new Rectangle(ins.left, ins.top, size.width
				- ins.right - ins.left, size.height - ins.top - ins.bottom);


		int xzero = rect.x+2*OFFSET+YNUM;
		int yzero = rect.y+rect.height-(2*OFFSET+XNUM);
		
		int xaxisLen = (rect.x+rect.width-2*OFFSET) - (xzero);
		int yaxisLen = yzero - (rect.y+3*OFFSET);
		
		g.setStroke(new BasicStroke(2));
		g.setFont(new Font("default", Font.BOLD, 11));
		
		//grid
		int ymax = getYMax(chart.getYmax(), chart.getYmin(), chart.getScale());
		int numOfSeg = (ymax-chart.getYmin())/chart.getScale();
		int yseg = yaxisLen / numOfSeg;
		//y grid
		int count=0;
		for(int i=chart.getYmin();i<=ymax;i+=chart.getScale()) {
			g.drawLine(xzero-LINE_OFFSET,(yzero)-count*yseg,
					rect.x+rect.width-2*OFFSET,(yzero)-count*yseg);
			g.drawString(String.valueOf(i),xzero-metrics.stringWidth(String.valueOf(i)),
					(yzero)-count*yseg-NUM_OFFSET);
			count++;
		}
		//xgrid
		List<XYValue> list = chart.getList();
		int xseg = xaxisLen / list.size();

		for(int i=0;i<list.size();i++) {
			g.setColor(Color.black);
			g.drawLine(xzero+(i+1)*xseg, yzero+LINE_OFFSET,
					xzero+(i+1)*xseg, rect.y+3*OFFSET);
			XYValue value = list.get(i);
			g.drawString(String.valueOf(value.getX()), xzero+(xseg/2)+(i*xseg), yzero+YNUM);
			g.setColor(Color.red);
			g.fill3DRect(xzero+i*xseg,
					yzero- ((value.getY()-chart.getYmin())/chart.getScale()*yseg),
					xseg, ((value.getY()-chart.getYmin())/chart.getScale()*yseg), false);
		}
		
		g.setColor(Color.black);
		//y axis
		g.drawLine(xzero, yzero+LINE_OFFSET,xzero,rect.y+3*OFFSET-LINE_OFFSET );
		//x axis
		g.drawLine(xzero+LINE_OFFSET,yzero, 
				rect.x+rect.width-2*OFFSET+LINE_OFFSET, yzero );
		//arrow head on y axis
		createArrowHead(xzero, yzero-yaxisLen-LINE_OFFSET, false, g);
		//arrow head on x axis
		createArrowHead(xzero+xaxisLen+LINE_OFFSET, yzero, true, g);
		

		//path
		g.setFont(new Font("default", Font.ITALIC, 13));
		g.drawString(chart.getPath(),
				rect.x+(rect.width/2)-metrics.stringWidth(String.valueOf(chart.getPath()))/2,
				rect.y+OFFSET);

		g.setFont(new Font("default", Font.ROMAN_BASELINE, 14));
		//x desc
		g.drawString(chart.getxDesc(),
				rect.x+(rect.width/2)-metrics.stringWidth(String.valueOf(chart.getxDesc()))/2,
				rect.y+rect.height-OFFSET);
		//y desc
		AffineTransform at2 = AffineTransform.getQuadrantRotateInstance(3);
		g.setTransform(at2);
		g.drawString(chart.getyDesc(),
				-(rect.y+(rect.height*2/3)-metrics.stringWidth(String.valueOf(chart.getyDesc()))/2),
				OFFSET);
	
	}
	
	/**
	 * Used for creation of little arrowheads for axes. Can only be used for
	 * creation of verical arrowhead and arrowhead rotated to right.
	 * 
	 * @param x x position of center for arrowhead
	 * @param y y position of center for arrowhead
	 * @param RightRotate if you want to create arrow rotated to right
	 * @param g graphics for creation of arrowhead
	 */
	private void createArrowHead(int x, int y, boolean RightRotate, Graphics2D g) {
		Polygon arrowHead = new Polygon();  
		if(RightRotate) {
			arrowHead.addPoint( x,y-5);
			arrowHead.addPoint( x,y+5);
			arrowHead.addPoint( x+10,y);
		} else {
			arrowHead.addPoint( x-5,y);
			arrowHead.addPoint( x+5,y);
			arrowHead.addPoint( x,y-10);			
		}
		g.fill(arrowHead);
		
	}

	/**
	 * Takes max, min values of y axis along with scaling of axis, returning
	 * value for y axis divideable with ymax-ymin.
	 * 
	 * @param ymax max y value 
	 * @param ymin min y value
	 * @param scale scaling on y axis
	 * @return
	 */
	private int getYMax(int ymax, int ymin, int scale) {
		int i=ymin-scale;
		do {
			i+=scale;
		} while(i<ymax);
		return i;
	}
	
}
