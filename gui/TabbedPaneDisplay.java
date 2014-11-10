package gui;

import indigoSrc.LogicFacade;

import java.awt.Font;
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
import parser.Parser;
//@author A0115530W
/**This is the class deals with the JTabbedPane component of the GUI. It updates the tabs 
 * according to the users commands.
 */
public class TabbedPaneDisplay extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private static final int INBOX_TAB_INDEX = 0;
	private static final int TODAY_TAB_INDEX = 1;
	private static final int THIS_WEEK_TAB_INDEX = 2;
	private static final int THIS_MONTH_TAB_INDEX = 3;
	private static final int FLOATING_TAB_INDEX = 4;
	private static final int PAGE_TOP = 0;
	private static final int FONT_SIZE = 14;

	private JTabbedPane tabbedPaneDisplay;
	private JTextPane taskDisplayPane; 
	public LogicFacade id = new LogicFacade();
	public static ArrayList<JTextPane> PaneArray = new ArrayList<JTextPane>();
	
	public TabbedPaneDisplay(){
		super(new GridLayout(1,1));

		tabbedPaneDisplay = new JTabbedPane();

		JComponent allPanel = makeTextPanel(taskDisplayPane, new LogicFacade("view").display);
		tabbedPaneDisplay.addTab("    Inbox    ", null, allPanel, "Displays all tasks.");
		tabbedPaneDisplay.setMnemonicAt(INBOX_TAB_INDEX, KeyEvent.VK_1);
		
		JComponent dailyPanel = makeTextPanel(taskDisplayPane, new LogicFacade("view -t").display);
		tabbedPaneDisplay.addTab("    Today    ", null, dailyPanel, "Displays daily tasks.");
		tabbedPaneDisplay.setMnemonicAt(TODAY_TAB_INDEX, KeyEvent.VK_2);
		
		JComponent weeklyPanel = makeTextPanel(taskDisplayPane, new LogicFacade("view -w").display);
		tabbedPaneDisplay.addTab("    This Week    ", null, weeklyPanel, "Displays weekly tasks.");
		tabbedPaneDisplay.setMnemonicAt(THIS_WEEK_TAB_INDEX, KeyEvent.VK_3);
		
		JComponent monthlyPanel = makeTextPanel(taskDisplayPane, new LogicFacade("view -m").display);
		tabbedPaneDisplay.addTab("    This Month    ", null, monthlyPanel, "Displays monthly tasks.");
		tabbedPaneDisplay.setMnemonicAt(THIS_MONTH_TAB_INDEX , KeyEvent.VK_4);
		
		JComponent floatingPanel = makeTextPanel(taskDisplayPane, new LogicFacade("view -f").display);
		tabbedPaneDisplay.addTab("   Floating   ", null, floatingPanel, "Displays floating tasks.");
		tabbedPaneDisplay.setMnemonicAt(FLOATING_TAB_INDEX, KeyEvent.VK_5);
		
		tabbedPaneDisplay.setSelectedIndex(TODAY_TAB_INDEX);
		add(tabbedPaneDisplay);
		
		tabbedPaneDisplay.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	}

	private JComponent makeTextPanel(JTextPane textPaneTemp, String text) {
		
		JPanel tabbedPanel = new JPanel();
		
		textPaneTemp = new JTextPane();
		Font font = new Font("Monospaces", Font.PLAIN, FONT_SIZE);
		PaneArray.add(textPaneTemp);

		JScrollPane scroll = new JScrollPane(textPaneTemp);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		textPaneTemp.setFont(font);
		textPaneTemp.setText(text);
		textPaneTemp.setEditable(false);
		textPaneTemp.setCaretPosition(PAGE_TOP);
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
			PaneArray.get(INBOX_TAB_INDEX).setText(lf.display);
		} else if(parse.getKeyCommand().equals(CommandKey.SEARCH)){
			setTab(INBOX_TAB_INDEX);
			PaneArray.get(INBOX_TAB_INDEX).setText(new LogicFacade(text).display);
		} else {
			setTab(INBOX_TAB_INDEX);
			PaneArray.get(INBOX_TAB_INDEX).setText(new LogicFacade("-v").display);
		}
		PaneArray.get(INBOX_TAB_INDEX).setCaretPosition(PAGE_TOP);
		
		PaneArray.get(TODAY_TAB_INDEX).setText(new LogicFacade("view -t").display);
		PaneArray.get(TODAY_TAB_INDEX).setCaretPosition(PAGE_TOP);
		
		PaneArray.get(THIS_WEEK_TAB_INDEX).setText(new LogicFacade("view -w").display);
		PaneArray.get(THIS_WEEK_TAB_INDEX).setCaretPosition(PAGE_TOP);
		
		PaneArray.get(THIS_MONTH_TAB_INDEX).setText(new LogicFacade("view -m").display);
		PaneArray.get(THIS_MONTH_TAB_INDEX).setCaretPosition(PAGE_TOP);
		
		PaneArray.get(FLOATING_TAB_INDEX).setText(new LogicFacade("view -f").display);
		PaneArray.get(FLOATING_TAB_INDEX).setCaretPosition(PAGE_TOP);

	}
	
	public void setTab(int index){	
		if (index == TODAY_TAB_INDEX){
			tabbedPaneDisplay.setSelectedIndex(TODAY_TAB_INDEX);
		} else if (index == THIS_WEEK_TAB_INDEX){
			tabbedPaneDisplay.setSelectedIndex(THIS_WEEK_TAB_INDEX);
		} else if (index == THIS_MONTH_TAB_INDEX){
			tabbedPaneDisplay.setSelectedIndex(THIS_MONTH_TAB_INDEX);
		} else if (index == FLOATING_TAB_INDEX){
			tabbedPaneDisplay.setSelectedIndex(FLOATING_TAB_INDEX);
		} else {
			tabbedPaneDisplay.setSelectedIndex(INBOX_TAB_INDEX);
		}
	}
}
