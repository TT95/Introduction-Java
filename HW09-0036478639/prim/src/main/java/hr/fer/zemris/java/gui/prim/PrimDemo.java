package hr.fer.zemris.java.gui.prim;


import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

/**
 * Demonstrates {@link PrimListModel} class.
 * Uses two lists and a button for demonstration.
 * Accepts no command line arguments.
 * By clicking button "next" program generates prime numbers.
 * 
 * @author Teo Toplak
 *
 */
public class PrimDemo extends JFrame{

	/**
	 * Constructor which takes no arguments and creates frame for demonstration.
	 */
	public PrimDemo() {
		setSize(200,300);
		setLocation(300, 300);
		setTitle("PrimDemo");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initGUI();
		
	}
	
	/**
	 * Initiates GUI of this demonstration frame. Creates 2 lists and button
	 * which invokes generation of prime numbers on click.
	 */
	private void initGUI() {
		getContentPane().setLayout(new GridLayout(2,1));
		PrimListModel model = new PrimListModel();
		JList<Integer> list1 = new JList<>(model);
		JScrollPane pane1 = new JScrollPane(list1);
		JList<Integer> list2 = new JList<>(model);
		JScrollPane pane2 = new JScrollPane(list2);
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,pane1,pane2);
		splitPane.setResizeWeight(.5d);
		getContentPane().add(splitPane);
		JButton button = new JButton("next");
		button.addActionListener(e -> {
			model.next();
		});
		getContentPane().add(button);
		
	}

	private static final long serialVersionUID = 1L;

	/**
	 * Method called when executing program.
	 * Takes no command line arguments.
	 * @param args accepts no command line arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new PrimDemo();
			frame.setVisible(true);
		});
	}

	
}
