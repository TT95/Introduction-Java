package hr.fer.zemris.java.hw10.jnotepadpp;

import hr.fer.zemris.java.hw10.jnotepadpp.actions.AscendingAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.CloseTabAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.CopySelectedPartAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.CutSelectedPartAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.DeleteSelectedPartAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.DescendingAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.ExitAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.InvertCaseAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.LowerCaseAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.NewBlankAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.OpenDocumentAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.PasteSelectedPartAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.SaveAsDocumentAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.SaveDocumentAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.SetLanguageAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.StatisticalInfoAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.UniqueAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.UpperCaseAction;
import hr.fer.zemris.java.hw10.jnotepadpp.components.LocalizedMenu;
import hr.fer.zemris.java.hw10.jnotepadpp.localization.FormLocalizationProvider;
import hr.fer.zemris.java.hw10.jnotepadpp.localization.LocalizationProvider;






import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * Text file editor. Implements basic functions for working with text files.
 * Supporting 3 languages: English, German and Croatian. <br>
 * Some of the basic functions: <br>
 * - opening existing document <br>
 * - creating a new blank document <br>
 * - saving document <br>
 * - saving-as document <br>
 * - close document shown it a tab <br>
 * - cut/copy/paste text <br>
 * - statistical info <br>
 * Some of the basic tools: <br>
 * - delete file <br>
 * - copy text <br>
 * - paste text <br>
 * - cut text <br>
 * - change case <br>
 * - sorting lines <br>
 * - deleting simillar lines <br>
 * Also implements clock, and status bar showing current column, line of caret.
 * ToolBar is floatable.Default language is english.
 * 
 * 
 * @author Teo Toplak
 *
 */
public class JNotepadPP extends JFrame {

	private static final long serialVersionUID = 1L;
	/**
	 * tabbed pane containing text files
	 */
	private JTabbedPane pane;
	/**
	 * caret position
	 */
	private JLabel caretPos;

	/**
	 * length of document
	 */
	private JLabel lengthStatusBar;

	/**
	 * current time
	 */
	private JLabel timeStatusBar;
	/**
	 * true if time needs to be stopped
	 * (used before closing program)
	 */
	private boolean killTime;
	/**
	 * icon for unchanged document
	 */
	private ImageIcon iconChanged;
	/**
	 * icon for changed document
	 */
	private ImageIcon iconUnchanged;
	/**
	 * list of current documents
	 */
	private List<TabInfo> tabsList;
	/**
	 * {@link FormLocalizationProvider} for this notepad
	 */
	private FormLocalizationProvider flp;
	/**
	 * action for opening document
	 */
	private OpenDocumentAction openDocumentAction;
	/**
	 * action for closing document
	 */
	private CloseTabAction closeTabAction;
	/**
	 * actiong for opening new document
	 */
	private NewBlankAction newBlankAction;
	/**
	 * action for saving document
	 */
	private SaveDocumentAction saveDocumentAction;
	/**
	 * action for saving document
	 */
	private SaveAsDocumentAction saveAsDocumentAction;
	/**
	 * action for closing program
	 */
	private ExitAction exitAction;
	/**
	 * action for deleting text 
	 */
	private DeleteSelectedPartAction deleteSelectedPartAction;
	/**
	 * action for copying text
	 */
	private CopySelectedPartAction copySelectedPartAction;
	/**
	 * action for cutting text
	 */
	private CutSelectedPartAction  cutSelectedPartAction;
	/**
	 * action for pasing text
	 */
	private PasteSelectedPartAction  pasteSelectedPartAction;
	/**
	 * action for statistical info
	 */
	private StatisticalInfoAction  statisticalInfoAction;
	/**
	 * action for upper case tool
	 */
	private UpperCaseAction upperCaseAction;
	/**
	 * action for lower case tool
	 */
	private LowerCaseAction lowerCaseAction;
	/**
	 * action for invert case tool
	 */
	private InvertCaseAction invertCaseAction;
	/**
	 * action for croatian language
	 */
	private SetLanguageAction setHrAction;
	/**
	 * action for english language
	 */
	private SetLanguageAction setEnAction;
	/**
	 * action for german language
	 */
	private SetLanguageAction setDeAction;
	/**
	 * upper case menu item
	 */
	private JMenuItem UpperCaseItem;
	/**
	 * lower case menu item
	 */
	private JMenuItem LowerCaseItem;
	/**
	 * invert case menu item
	 */
	private JMenuItem InvertCaseItem; 
	/**
	 * action for ascended sort of text
	 */
	private AscendingAction ascendingAction;
	/**
	 * action for descended sort of text
	 */
	private DescendingAction descendingAction;
	/**
	 * action for removing similar text lines
	 */
	private UniqueAction uniqueAction;
	
	/**
	 * Constructor for notepad.Sets only basic stuff.
	 */
	public JNotepadPP() {
		setTitle("JNotepad++");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				tryExiting();
			}
		});
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setSize(550, 400);
		initiGUI();
	}

	/**
	 * Method which initializes GUI creation
	 */
	private void initiGUI() {
		getContentPane().setLayout(new BorderLayout());
		flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
		
		createActions();
		getIcons();
		createTabs();
		createStatusBar();
		createMenus();
		createToolBars();
	}
	
	/**
	 * Creates status bar for notepad
	 */
	private void createStatusBar() {
		JPanel statusBar = new JPanel(new GridLayout(1,3));
		lengthStatusBar = new JLabel("length: ");
		statusBar.add(lengthStatusBar);
		caretPos = new JLabel("Ln:  Col:  Sel:  ");
		statusBar.add(caretPos);
		timeStatusBar = new JLabel("time");
		statusBar.add(timeStatusBar);
		getContentPane().add(statusBar,BorderLayout.PAGE_END);
		startTime();
	}

	/**
	 * Starts clock for notepad
	 */
	private void startTime() {
		killTime = false;
		Thread t = new Thread(()-> {
			while(true) {
				SwingUtilities.invokeLater(()-> {
					timeStatusBar.setText(new Date().toString());
				});				
				try {Thread.sleep(500);} catch (Exception ignorable) {};
				if(killTime) {
					break;
				}
			}
		});
		t.setDaemon(true);
		t.start();
	}

	/**
	 * Method called when user presses exit button on window.
	 * Checks if some of the opened documents were changed but
	 * not saved. Asks user for saving those documents.
	 */
	private void tryExiting() {
		if(tabsList.isEmpty()) {
			dispose();
		}
		int i=0;
		for(TabInfo info : tabsList) {
			int res=0;
			if(info.changed) {
				res = JOptionPane.showConfirmDialog(JNotepadPP.this, 
						"Save "+ pane.getTitleAt(i) +" ?",
						"Save", JOptionPane.YES_NO_CANCEL_OPTION);
				if(res == JOptionPane.YES_OPTION) {
					saveDocumentAction.actionPerformed(new ActionEvent(this, 0, ""));
				} else if(res == JOptionPane.CANCEL_OPTION){
					return;
				}
			}
			i++;
		}
		dispose();
		killTime = true;
	}

	/**
	 * Initializes variables for icons used.
	 */
	private void getIcons() {
		iconChanged= new ImageIcon("images\\untouched.png");
		iconUnchanged= new ImageIcon("images\\touched.png");
	}

	/**
	 * Creates tabbed pane and adds appropriate listeners.
	 */
	private void createTabs() {
		pane = new JTabbedPane();
		tabsList = new ArrayList<>();
		getContentPane().add(pane, BorderLayout.CENTER);
		pane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				Path path = getCurrTabPath();
				//refreshing statusBar when tab changes
				try {
					getCurrTabText().getCaretListeners()[0].caretUpdate(null);
				} catch (IndexOutOfBoundsException ignorable) { } 
				//catch above catches exception when tab is opened
				if(path == null) {
					setTitle("JNotepad++");
				} else {
					setTitle(path.toString());
				}
			}
		});
		
	}

	/**
	 * Initializes actions with proper arguments
	 */
	private void createActions() {
		
		saveDocumentAction = new SaveDocumentAction("Save", flp, this);
		saveAsDocumentAction = new SaveAsDocumentAction("SaveAs", flp, this);
		openDocumentAction = new OpenDocumentAction("Open", flp, this);
		closeTabAction = new CloseTabAction("Close", flp, this,saveDocumentAction);
		newBlankAction = new NewBlankAction("New", flp, this);
		exitAction = new ExitAction("Exit", flp, this);
		deleteSelectedPartAction = new DeleteSelectedPartAction("Delete", flp, this);
		copySelectedPartAction = new CopySelectedPartAction("Copy", flp, this);
		cutSelectedPartAction = new CutSelectedPartAction("Cut", flp, this);
		pasteSelectedPartAction = new PasteSelectedPartAction("Paste", flp, this);
		statisticalInfoAction = new StatisticalInfoAction("StatisticalInfo", flp, this);
		upperCaseAction = new UpperCaseAction("UpperCase", flp, this);
		lowerCaseAction = new LowerCaseAction("LowerCase", flp, this);
		invertCaseAction = new InvertCaseAction("InvertCase", flp, this);
		setHrAction = new SetLanguageAction("Croatian", flp, "hr");
		setEnAction = new SetLanguageAction("English", flp, "en");
		setDeAction = new SetLanguageAction("German", flp,"de");
		ascendingAction = new AscendingAction("Ascending", flp, this);
		descendingAction = new DescendingAction("Descending", flp, this);
		uniqueAction = new UniqueAction("Unique", flp, this);
	}

	/**
	 * Creates menus with all items.
	 */
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu =(JMenu) new LocalizedMenu("File", flp);
		menuBar.add(fileMenu);
		fileMenu.add(new JMenuItem(newBlankAction));
		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(saveAsDocumentAction));
		fileMenu.add(new JMenuItem(closeTabAction));
		fileMenu.add(new JMenuItem(statisticalInfoAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(exitAction));
		JMenu editMenu = new LocalizedMenu("Edit", flp);
		menuBar.add(editMenu);
		editMenu.add(new JMenuItem(deleteSelectedPartAction));
		editMenu.add(new JMenuItem(cutSelectedPartAction));
		editMenu.add(new JMenuItem(copySelectedPartAction));
		editMenu.add(new JMenuItem(pasteSelectedPartAction));
		JMenu toolMenu = new LocalizedMenu("Tools", flp);
		JMenu changeCase = new LocalizedMenu("ChangeCase", flp);
		toolMenu.add(changeCase);
		//making private members of class for actions of
		// disable/enable
		UpperCaseItem = new JMenuItem(upperCaseAction);
		UpperCaseItem.setEnabled(false);
		LowerCaseItem = new JMenuItem(lowerCaseAction);
		LowerCaseItem.setEnabled(false);
		InvertCaseItem = new JMenuItem(invertCaseAction);
		InvertCaseItem.setEnabled(false);
		changeCase.add(UpperCaseItem);
		changeCase.add(LowerCaseItem);
		changeCase.add(InvertCaseItem);
		menuBar.add(toolMenu);
		JMenu sort = new LocalizedMenu("Sort", flp);
		toolMenu.add(sort);
		sort.add(new JMenuItem(ascendingAction));
		sort.add(new JMenuItem(descendingAction));
		sort.add(new JMenuItem(uniqueAction));
		JMenu langMenu = new LocalizedMenu("Languages",flp);
		langMenu.add(new JMenuItem(setHrAction));
		langMenu.add(new JMenuItem(setEnAction));
		langMenu.add(new JMenuItem(setDeAction));
		setJMenuBar(menuBar);
		menuBar.add(langMenu);
	}

	/**
	 * Creates toolbar with appropriate buttons on it.
	 */
	private void createToolBars() {
		JToolBar toolBar = new JToolBar("Tools");
		toolBar.setFloatable(true);
		toolBar.add(new JButton(newBlankAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(openDocumentAction));
		toolBar.add(new JButton(saveDocumentAction));
		toolBar.add(new JButton(saveAsDocumentAction));
		toolBar.add(new JButton(closeTabAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(cutSelectedPartAction));
		toolBar.add(new JButton(copySelectedPartAction));
		toolBar.add(new JButton(pasteSelectedPartAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(statisticalInfoAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(exitAction));
		getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}

	/**
	 * Method called when executing program.
	 * Creates window for notepad and starts initialization.
	 * @param args no command line arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			try {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			} catch (Exception ignorable) { }
			//setting language to english by default
			LocalizationProvider.getInstance().setLanguage("en");
			new JNotepadPP().setVisible(true);
		});
	}
	
	/**
	 * Returns path of current opened document
	 * @return path of current opened document
	 */
	public Path getCurrTabPath() {
		try {
			return tabsList.get(pane.getSelectedIndex()).getPath();
		} catch(Exception e) {
			return null;
		}
	}
	
	/**
	 * Returns true if current tab is changed
	 * @return true if current tab is changed
	 */
	public boolean getCurrTabChange() {
		return tabsList.get(pane.getSelectedIndex()).changed;
	}
	
	/**
	 * Sets current tab path to document
	 * @param path current tab path to document
	 */
	public void setCurrTabPath(Path path) {
		 tabsList.get(pane.getSelectedIndex()).path=path;
	}
	
	/**
	 * Sets document to 'changed' path (or not)
	 * @param changed
	 *            true if yout want for current state of document to be changed
	 */
	public void setCurrTabChange(boolean changed) {
		try {
			tabsList.get(pane.getSelectedIndex()).changed=changed;
		} catch(IndexOutOfBoundsException e) {
			//tab had just been created, no job needed
			return;
		}
		
		if(changed) {
			pane.setIconAt(pane.getSelectedIndex(), iconUnchanged);
		} else {
			pane.setIconAt(pane.getSelectedIndex(), iconChanged);
		}
	}
	
	/**
	 * Returns {@link JTextArea} of current opened document
	 * @return {@link JTextArea} of current opened document
	 */
	public JTextArea getCurrTabText() {
		try {
			JScrollPane scrollPane = (JScrollPane)pane.getComponentAt(pane.getSelectedIndex());
			return (JTextArea) scrollPane.getViewport().getView();
		} catch (NullPointerException ex) {
			return null;
		}
		 
	}
	
	/**
	 * Returns default font
	 * @return defualt font
	 */
	public Font getDefaultFont() {
		return new Font("Arial",Font.PLAIN, 12);
	}
	/**
	 * Returns {@link JTabbedPane} of notepad
	 * @return {@link JTabbedPane} of notepad
	 */
	public JTabbedPane getPane() {
		return pane;
	}

	/**
	 * Returns changed state icon
	 * @return changed state icon
	 */
	public ImageIcon getIconChanged() {
		return iconChanged;
	}

	/**
	 * Returns unchanged state icon
	 * @return unchanged state icon
	 */
	public ImageIcon getIconUnchanged() {
		return iconUnchanged;
	}

	/**
	 * Returns list of {@link TabInfo}
	 * @return list of {@link TabInfo}
	 */
	public List<TabInfo> getTabsList() {
		return tabsList;
	}

	/**
	 * Returns {@link FormLocalizationProvider}
	 * @return {@link FormLocalizationProvider}
	 */
	public FormLocalizationProvider getFlp() {
		return flp;
	}

	/**
	 * Returns upper case action item
	 * @return the upperCaseItem
	 */
	public JMenuItem getUpperCaseItem() {
		return UpperCaseItem;
	}

	/**
	 * Return lower case action item
	 * @return the lowerCaseItem
	 */
	public JMenuItem getLowerCaseItem() {
		return LowerCaseItem;
	}

	/**
	 * Returns inverted case action item
	 * @return the invertCaseItem
	 */
	public JMenuItem getInvertCaseItem() {
		return InvertCaseItem;
	}
	
	/**
	 * returns caret position label
	 * @return the caretPos
	 */
	public JLabel getCaretPos() {
		return caretPos;
	}
	
	/**
	 * Returns length displayed on status bar
	 * @return the lengthStatusBar
	 */
	public JLabel getLengthStatusBar() {
		return lengthStatusBar;
	}
}

