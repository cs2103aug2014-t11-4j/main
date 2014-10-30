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
	public IndigoLogic id = new IndigoLogic();
	public static ArrayList<JTextPane> PaneArray = new ArrayList<JTextPane>();
	public TabbedPaneDisplay(){
		super(new GridLayout(1,1));
		
		
		
		tabbedPaneDisplay = new JTabbedPane();
		JComponent allPanel = makeTextPanel(taskDisplayPane, id.display);
		tabbedPaneDisplay.addTab("Inbox", null, allPanel, "Displays all tasks.");
		tabbedPaneDisplay.setMnemonicAt(0, KeyEvent.VK_1);
		
		JComponent dailyPanel = makeTextPanel(taskDisplayPane, new IndigoLogic("view today").display);
		tabbedPaneDisplay.addTab("Today", null, dailyPanel, "Displays daily tasks.");
		tabbedPaneDisplay.setMnemonicAt(1, KeyEvent.VK_2);
		
		JComponent weeklyPanel = makeTextPanel(taskDisplayPane, new IndigoLogic("view this week").display);
		tabbedPaneDisplay.addTab("This Week", null, weeklyPanel, "Displays weekly tasks.");
		tabbedPaneDisplay.setMnemonicAt(2, KeyEvent.VK_3);
		
		JComponent monthlyPanel = makeTextPanel(taskDisplayPane, new IndigoLogic("view this month").display);
		tabbedPaneDisplay.addTab("This Month", null, monthlyPanel, "Displays monthly tasks.");
		tabbedPaneDisplay.setMnemonicAt(3, KeyEvent.VK_4);
		
		JComponent floatingPanel = makeTextPanel(taskDisplayPane, new IndigoLogic("view -f").display);
		tabbedPaneDisplay.addTab("Floating", null, floatingPanel, "Displays no-deadline tasks.");
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
		tabbedPanel.setLayout(new GridLayout(1,1));
		tabbedPanel.add(scroll);
		
		return tabbedPanel;
		
		
	} 
	
	public void update(String text){
		//TODO
		if(text.contains("view")){
			setTab(text);
		}
		else{
			PaneArray.get(0).setText(new IndigoLogic("view").display);
		}
		PaneArray.get(0).setText(new IndigoLogic("view").display);
		PaneArray.get(1).setText(new IndigoLogic("view -t").display);
		PaneArray.get(2).setText(new IndigoLogic("view -w").display);
		PaneArray.get(3).setText(new IndigoLogic("view -m").display);
		PaneArray.get(4).setText(new IndigoLogic("view -f").display);
	}
	
	private void setTab(String index){
		if (index.contains("today")){
			tabbedPaneDisplay.setSelectedIndex(1);
		}
		else if (index.contains("this week")){
			tabbedPaneDisplay.setSelectedIndex(2);
		}
		else if (index.contains("this month")){
			tabbedPaneDisplay.setSelectedIndex(2);
		}
		else if (index.contains("all tasks")){
			tabbedPaneDisplay.setSelectedIndex(4);
		}
		else {
			tabbedPaneDisplay.setSelectedIndex(0);
		}
	}
}
