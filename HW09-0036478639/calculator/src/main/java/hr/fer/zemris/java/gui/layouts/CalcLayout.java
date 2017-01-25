package hr.fer.zemris.java.gui.layouts;

import hr.fer.zemris.java.gui.RCPosition;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.ArrayList;
import java.util.List;

/**
 * Layout used mostly for creation of calculator GUI. Based on grid layout where
 * first element covers first five elements (used to implement calculator
 * screen). User can provide gap between elements added to layout. Elements can
 * be added to layout directly to place user demands (i.e. 4-th column, 3-rd
 * row).
 * 
 * @author Teo Toplak
 *
 */
public class CalcLayout implements LayoutManager2{

	/**
	 * Gap between elements
	 */
	private int gap;
	/**
	 * Matrix of compoments
	 */
	private Component[][] components;
	/**
	 * List of all indexes used.
	 */
	private List<Index> indexes;
	/**
	 * Number of rows
	 */
	public final int ROWS=5;
	/**
	 * Number of columns
	 */
	public final int COLS=7;
	
	/**
	 * Checks if position provided by user is legal.
	 * @param row row position
	 * @param col col position
	 * @throws IllegalArgumentException if position isnt legal.
	 */
	private void checkLegalIndex(int row, int col) throws IllegalArgumentException {
		if(row == 1) {
			if(col==COLS-1 || col==COLS || col==1) {
				return;
			}
		} else {
			if(row > 0 && row <= ROWS) {
				if(col > 0 && col <= COLS) {
					return;
				}
			}
		}
		throw new IllegalArgumentException("Cannot work with given combination of row and column!");
	}
	
	/**
	 * Constructor without gap set.
	 *  Default gap is 0.
	 */
	public CalcLayout() {
		this.gap = 0;
		components = new Component[ROWS][COLS];
		indexes = new ArrayList<CalcLayout.Index>();
	}
	
	/**
	 * Constructor with gap set.
	 * @param gap gap
	 */
	public CalcLayout(int gap) {
		this.gap = gap;
		components = new Component[ROWS][COLS];;
		indexes = new ArrayList<CalcLayout.Index>();
	}

	@Override
	public void addLayoutComponent(String arg0, Component arg1) {};

	
	@Override
	public void layoutContainer(Container parent) {
		 Insets insets = parent.getInsets();
		 int widthPart = (parent.getWidth()-insets.left-insets.right-(COLS-1)*gap)/COLS;
		 int heightPart = (parent.getHeight()-insets.top-insets.bottom-(ROWS-1)*gap)/ROWS;
		 
		 //first component
		 if(components[0][0]!=null) {
			 components[0][0].setBounds(insets.left, insets.top,
					 widthPart*(COLS-2)+(COLS-3)*gap, heightPart);
		 }
		 //other components
		 for(Index i : indexes) {
			 if(i.x!=0 || i.y!=0) {
				 components[i.x][i.y].setBounds(insets.left+i.y*(gap+widthPart),
						 insets.top+i.x*(gap+heightPart), widthPart, heightPart);
			 }
		 }
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return customLayoutSize(parent, "minimum");
	}
	
	@Override
	public Dimension maximumLayoutSize(Container parent) {
		return customLayoutSize(parent, "maximum");
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return customLayoutSize(parent, "preffered");
	}

	/**
	 * Returns size of layout depending of which size is demanded.
	 * String in arguments tells which size is about to be selected:<br>
	 * -"preffered" preffered layout size <br>
	 * -"minimum" minimum layout size <br>
	 * -"maximum" maximum layout size <br>
	 * @param parent parent conatiner
	 * @param s string 
	 * @return dimension of layout
	 */
	private Dimension customLayoutSize(Container parent, String s) {
		//MORAT CES PROVJERIT ZA NULL VRACANJE
		Dimension d = new Dimension();
		int maxHeight = 0;
		int maxWidth = 0;
		for(Index i : indexes) {
			Component c = components[i.x][i.y];
			switch (s) {
			case "preffered":
				maxHeight = Math.max(c.getPreferredSize().height, maxHeight);
				maxWidth = Math.max(c.getPreferredSize().width, maxWidth);
				break;
			case "minimum":	
				maxHeight = Math.max(c.getMinimumSize().height, maxHeight);
				maxWidth = Math.max(c.getMinimumSize().width, maxWidth);
				break;
			case "maximum":
				maxHeight = Math.max(c.getMaximumSize().height, maxHeight);
				maxWidth = Math.max(c.getMaximumSize().width, maxWidth);
				break;
			}
		}
		Insets insets = parent.getInsets();
		d.height=insets.bottom+insets.top+ROWS*maxHeight+(ROWS-1)*gap;
		d.width=insets.left+insets.right+COLS*maxWidth+(COLS-1)*gap;
		return d;
	}
	
	@Override
	public void removeLayoutComponent(Component comp) {
		int remove=-1;
		for(int i=0;i<indexes.size();i++) {
			if(components[indexes.get(i).x][indexes.get(i).y].equals(comp)) {
				remove = i;
				components[indexes.get(i).x][indexes.get(i).y]=null;
				break;
			}
		}
		if(remove == -1) {
			throw new IllegalArgumentException("Component cannot be"
					+ " removed since it doesn't exist!");
		}
		indexes.remove(remove);
	}

	@Override
	public void addLayoutComponent(Component c, Object o) 
			throws IllegalArgumentException {
		if(o instanceof RCPosition) {
			
			int row = ((RCPosition) o).getRow()-1;
			int col = ((RCPosition) o).getCol()-1;
			checkLegalIndex(row+1, col+1);
			components[row][col] = c;
			indexes.add(new Index(row,col));
			
		} else if(o instanceof String) {
			
			((String) o).replaceAll(" ", "");
			String[] arr = ((String) o).split("\\,");
			int row = Integer.parseInt(arr[0])-1;
			int col = Integer.parseInt(arr[1])-1;
			checkLegalIndex(row+1, col+1);
			components[row][col] = c;
			indexes.add(new Index(row,col));
			
		} else {
			throw new IllegalArgumentException("Wrong argument for adding component!");
		}
		
		
	}

	@Override
	public float getLayoutAlignmentX(Container arg0) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container arg0) {
		return 0; 
	}

	@Override
	public void invalidateLayout(Container target) {}; //JEL TREBA OSTAT OVAK?

	private class Index {
		int x;
		int y;
		public Index(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
}
