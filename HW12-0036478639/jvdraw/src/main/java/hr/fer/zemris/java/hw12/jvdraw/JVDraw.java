package hr.fer.zemris.java.hw12.jvdraw;

import hr.fer.zemris.java.hw12.jvdraw.JDrawingCanvas.geomObject;
import hr.fer.zemris.java.hw12.jvdraw.actions.ExitAction;
import hr.fer.zemris.java.hw12.jvdraw.actions.ExportDocumentAction;
import hr.fer.zemris.java.hw12.jvdraw.actions.OpenDocumentAction;
import hr.fer.zemris.java.hw12.jvdraw.actions.SaveAsDocumentAction;
import hr.fer.zemris.java.hw12.jvdraw.objects.GeometricalObject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

/**
 * Class representing simple implementation of drawing program.
 * Offers user to add objects by drawing it on canvas and importing them
 * from outside document. User can save document, export it as image in different
 * formats, change existing objects with list added on right side of frame.<br>
 * Objects currently supported for drawng: <br>
 * - line <br>
 * - filled circle <br>
 * - circle <br>
 * <p>
 * User can change foreground and background color of drawing canvas.
 * Foreground color defines all rims of objects; in terms of lines this is
 * line color; in terms of circles this is rim of circle. Background color 
 * represent filling color of filled circle. Program notifies user if current work
 * was not saved when exiting program. Important: program does not implement removing
 * objects.
 * 
 * @author Teo Toplak
 *
 */
public class JVDraw extends JFrame implements IDrawingModelListener{
	
	/**
	 * what is happening
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * true if current work is saved
	 */
	private boolean saved=true;
	/**
	 * default background color
	 */
	private final Color defaultBackground = Color.blue;
	/**
	 * default foreground color
	 */
	private final Color defaultForeground = Color.green;
	/**
	 * core model for this program; stores all objects into list
	 */
	private JDrawingModel model = new JDrawingModel(defaultForeground, defaultBackground);
	
	/**
	 * Simple constructor for initialization.
	 * Takes no arguments.
	 */
	public JVDraw() {
		setTitle("JVDraw");
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				tryExiting();
			}
		});
		setSize(700, 400);
		initiGUI();
	}

	/**
	 * Initializes GUI for this program.
	 * Also takes care of listeners linking.
	 */
	private void initiGUI() {
		getContentPane().setLayout(new BorderLayout());
		
		// creating statusbar
		StatusBar statusBar = new StatusBar(defaultForeground, defaultBackground);
		getContentPane().add(statusBar, BorderLayout.PAGE_END);
		
		//creating menubar
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		fileMenu.add(new JMenuItem(new OpenDocumentAction(this)));
		fileMenu.add(new JMenuItem(new SaveAsDocumentAction(this)));
		fileMenu.add(new JMenuItem(new ExportDocumentAction(this)));
		fileMenu.add(new JMenuItem(new ExitAction(this)));
		setJMenuBar(menuBar);
		
		//creating canvas
		JDrawingCanvas canvas = new JDrawingCanvas(model);
		getContentPane().add(canvas,BorderLayout.CENTER);
		model.addDrawingModelListener(canvas);
		model.addDrawingModelListener(this);
		//creating list
		DrawingObjectsListModel listModel = new DrawingObjectsListModel(model);
		model.addDrawingModelListener(listModel);
		JList<GeometricalObject> objectList = new JList<GeometricalObject>(listModel);
		objectList.addMouseListener(new CustomListListener(this, objectList));
		JScrollPane pane = new JScrollPane(objectList);
		pane.setPreferredSize(new Dimension(140, 0));
		getContentPane().add(pane, BorderLayout.LINE_END);
		
		//creating toolbar
		JToolBar toolBar = new JToolBar("Tools");
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));

		List<IColorChangeListener> list = new ArrayList<IColorChangeListener>();
		list.add(statusBar);
		list.add(model);
		toolBar.add(new JColorArea(defaultForeground, list, true));
		toolBar.add(new JColorArea(defaultBackground, list, false));
		
		toolBar.addSeparator();
		
		ButtonGroup group = new ButtonGroup();
		JToggleButton lineToggle = new JToggleButton("Line");
		lineToggle.addActionListener((e) -> {
			canvas.newDrawType(geomObject.LINE);
		});
		group.add(lineToggle);
		toolBar.add(lineToggle);
		JToggleButton circleToggle = new JToggleButton("Circle");
		circleToggle.addActionListener((e) -> {
			canvas.newDrawType(geomObject.CIRCLE);
		});
		group.add(circleToggle);
		toolBar.add(circleToggle);
		JToggleButton fCircleToggle = new JToggleButton("Filled Circle");
		fCircleToggle.addActionListener((e) -> {
			canvas.newDrawType(geomObject.FCIRCLE);
		});
		group.add(fCircleToggle);
		toolBar.add(fCircleToggle);
		//activating line as default
		lineToggle.doClick();
		
		getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}
	
	/**
	 * Method called when user presses exit button on window.
	 * Checks if work was not saved. Asks user for saving document.
	 */
	public void tryExiting() {
		if(saved) {
			dispose();
		}
			int res=0;
				res = JOptionPane.showConfirmDialog(JVDraw.this, 
						"Save image?",
						"Save", JOptionPane.YES_NO_OPTION);
				if(res == JOptionPane.YES_OPTION) {
					Action save = new SaveAsDocumentAction(this);
					save.actionPerformed(new ActionEvent(this, 0, ""));
				} 
		dispose();
	}
	
	

	/**
	 * Returns true if work is saved
	 * @return the saved
	 */
	public boolean isSaved() {
		return saved;
	}

	/**
	 * Sets saved status
	 * @param saved the saved to set
	 */
	public void setSaved(boolean saved) {
		this.saved = saved;
	}

	/**
	 * Model getter
	 * @return the model
	 */
	public JDrawingModel getModel() {
		return model;
	}

	

	@Override
	public void objectsAdded(IDrawingModel source, int index0, int index1) {
		saved = false;
	}

	@Override
	public void objectsChanged(IDrawingModel source, int index0, int index1) {
		saved = false;
	}

	@Override
	public void objectsRemoved(IDrawingModel source, int index0, int index1) {
		saved = false;
	}

	
	/**
	 * Method called when executing program.
	 * Accepts no command line arguments.
	 * @param args no command line arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			try {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			} catch (Exception ignorable) { }
			//setting language to english by default
			new JVDraw().setVisible(true);
		});
	}
}
