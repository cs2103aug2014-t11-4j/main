package indigoSrc;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;


public class TabbedPaneDisplay extends JPanel {
	
	private JTabbedPane tabbedPaneDisplay;
	private JTextPane taskDisplayPane; 
	public LogicFacade id = new LogicFacade();
	public static ArrayList<JTextPane> PaneArray = new ArrayList<JTextPane>();
	public TabbedPaneDisplay(){
		super(new GridLayout(1,1));
		
		
		
		tabbedPaneDisplay = new JTabbedPane();
		JComponent allPanel = makeTextPanel(taskDisplayPane, new LogicFacade("view all").display);
		tabbedPaneDisplay.addTab("Inbox", null, allPanel, "Displays all tasks.");
		tabbedPaneDisplay.setMnemonicAt(0, KeyEvent.VK_1);
		
		JComponent dailyPanel = makeTextPanel(taskDisplayPane, new LogicFacade("view -t").display);
		tabbedPaneDisplay.addTab("Today", null, dailyPanel, "Displays daily tasks.");
		tabbedPaneDisplay.setMnemonicAt(1, KeyEvent.VK_2);
		
		JComponent weeklyPanel = makeTextPanel(taskDisplayPane, new LogicFacade("view -w").display);
		tabbedPaneDisplay.addTab("This Week", null, weeklyPanel, "Displays weekly tasks.");
		tabbedPaneDisplay.setMnemonicAt(2, KeyEvent.VK_3);
		
		JComponent monthlyPanel = makeTextPanel(taskDisplayPane, new LogicFacade("view -m").display);
		tabbedPaneDisplay.addTab("This Month", null, monthlyPanel, "Displays monthly tasks.");
		tabbedPaneDisplay.setMnemonicAt(3, KeyEvent.VK_4);

		add(tabbedPaneDisplay);
		
		tabbedPaneDisplay.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	}

	private JComponent makeTextPanel(JTextPane textPaneTemp, String text) {
		
		JPanel tabbedPanel = new JPanel();
		textPaneTemp = new JTextPane();
		PaneArray.add(textPaneTemp);

		JScrollPane scroll = new JScrollPane(textPaneTemp);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		textPaneTemp.setText(text);
		textPaneTemp.setEditable(false);
		textPaneTemp.setCaretPosition(0);
		tabbedPanel.setLayout(new GridLayout(1,1));
		tabbedPanel.add(scroll);
		
		return tabbedPanel;
		
		
	} 
	
	public String update(String text){
		//TODO
		LogicFacade control = new LogicFacade(text);
		setTab(control.setTab);
		PaneArray.get(0).setText(control.display);
		PaneArray.get(1).setText(new LogicFacade("view -t").display);
		PaneArray.get(2).setText(new LogicFacade("view -w").display);
		PaneArray.get(3).setText(new LogicFacade("view -m").display);
		
		return control.feedback;
	}
	
	private void setTab(int index){
		
		if (index == 1){
			tabbedPaneDisplay.setSelectedIndex(1);

		} else if (index == 2){
			tabbedPaneDisplay.setSelectedIndex(2);
			
		} else if (index == 3){
			tabbedPaneDisplay.setSelectedIndex(3);
			
		} else {
			tabbedPaneDisplay.setSelectedIndex(0);
			
		}
	}
}
