package indigoSrc;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;

import parser.CommandKey;


public class TabbedPaneDisplay extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTabbedPane tabbedPaneDisplay;
	private JTextPane taskDisplayPane; 
	//private JTable inboxTable;
	public LogicFacade id = new LogicFacade();
	public static ArrayList<JTextPane> PaneArray = new ArrayList<JTextPane>();
	
	public TabbedPaneDisplay(){
		super(new GridLayout(1,1));

		tabbedPaneDisplay = new JTabbedPane();

		JComponent allPanel = makeTextPanel(taskDisplayPane, new LogicFacade("view").display);
		tabbedPaneDisplay.addTab("   Inbox   ", null, allPanel, "Displays all tasks.");
		tabbedPaneDisplay.setMnemonicAt(0, KeyEvent.VK_1);
		
		JComponent dailyPanel = makeTextPanel(taskDisplayPane, new LogicFacade("view -t").display);
		tabbedPaneDisplay.addTab("   Today   ", null, dailyPanel, "Displays daily tasks.");
		tabbedPaneDisplay.setMnemonicAt(1, KeyEvent.VK_2);
		
		JComponent weeklyPanel = makeTextPanel(taskDisplayPane, new LogicFacade("view -w").display);
		tabbedPaneDisplay.addTab("    This Week    ", null, weeklyPanel, "Displays weekly tasks.");
		tabbedPaneDisplay.setMnemonicAt(2, KeyEvent.VK_3);
		
		JComponent monthlyPanel = makeTextPanel(taskDisplayPane, new LogicFacade("view -m").display);
		tabbedPaneDisplay.addTab("    This Month    ", null, monthlyPanel, "Displays monthly tasks.");
		tabbedPaneDisplay.setMnemonicAt(3, KeyEvent.VK_4);
		
		JComponent floatingPanel = makeTextPanel(taskDisplayPane, new LogicFacade("view -f").display);
		tabbedPaneDisplay.addTab("    Floating    ", null, floatingPanel, "Displays floating tasks.");
		tabbedPaneDisplay.setMnemonicAt(4, KeyEvent.VK_5);

		add(tabbedPaneDisplay);
		
		tabbedPaneDisplay.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	}

	private JComponent makeTextPanel(JTextPane textPaneTemp, String text) {
		
		JPanel tabbedPanel = new JPanel();
		
		textPaneTemp = new JTextPane();
		PaneArray.add(textPaneTemp);

		JScrollPane scroll = new JScrollPane(textPaneTemp);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		textPaneTemp.setText(text);
		textPaneTemp.setEditable(false);
		textPaneTemp.setCaretPosition(0);
		tabbedPanel.setLayout(new GridLayout(1,1));
		
		tabbedPanel.add(scroll);
		return tabbedPanel;
		
		
	} 
	

	public void update(String text){
		//TODO

		Parser parse = new Parser(text + "");
		if(parse.getKeyCommand().equals(CommandKey.READ)){
			LogicFacade lf = new LogicFacade(text);
			setTab(lf.setTab);
			PaneArray.get(0).setText(lf.display);
		} else if(parse.getKeyCommand().equals(CommandKey.SEARCH)){
			setTab(0);
			PaneArray.get(0).setText(new LogicFacade(text).display);
		} else {
			setTab(0);
			PaneArray.get(0).setText(new LogicFacade("-v").display);
		}
		PaneArray.get(1).setText(new LogicFacade("view -t").display);
		PaneArray.get(2).setText(new LogicFacade("view -w").display);
		PaneArray.get(3).setText(new LogicFacade("view -m").display);
		PaneArray.get(4).setText(new LogicFacade("view -f").display);

	}
	

	public void setTab(int index){
		
		if (index == 1){
			tabbedPaneDisplay.setSelectedIndex(1);

		} else if (index == 2){
			tabbedPaneDisplay.setSelectedIndex(2);
			
		} else if (index == 3){
			tabbedPaneDisplay.setSelectedIndex(3);
			
		} else if (index == 4){
			tabbedPaneDisplay.setSelectedIndex(4);
			
		} else {
			tabbedPaneDisplay.setSelectedIndex(0);
			
		}
	}
}
