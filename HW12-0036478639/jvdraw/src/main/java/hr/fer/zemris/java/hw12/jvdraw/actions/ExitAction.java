package hr.fer.zemris.java.hw12.jvdraw.actions;

import hr.fer.zemris.java.hw12.jvdraw.JVDraw;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 * Class representing ExitAction.
 * @author Teo Toplak
 *
 */
public class ExitAction extends AbstractAction{

	/**
	 * what is happening 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * {@link JVDraw} instance using this action
	 */
	private JVDraw jVDraw;	
	
	/**
	 * Constructor taking only reference to {@link JVDraw}
	 * using this actiokn
	 * @param jVDraw {@link JVDraw} instance
	 */
	public ExitAction(JVDraw jVDraw) {
		this.jVDraw=jVDraw;
		putValue(NAME, "Exit");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		jVDraw.tryExiting();
	}
}

