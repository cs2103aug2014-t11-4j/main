package indigoSrc;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;


public class TabbedPaneDisplay extends JPanel {
	
	private JTabbedPane tabbedPaneDisplay;
	private JTextPane textPaneToday; 
	public IndigoLogic id = new IndigoLogic();
	public TabbedPaneDisplay(){
		super(new GridLayout(1,1));
		
		tabbedPaneDisplay = new JTabbedPane();
	//	tabbedPaneDisplay.setMinimumSize(new Dimension(600,200)));
		
		JComponent allPanel = makeTextPanel(textPaneToday, id.display);
		tabbedPaneDisplay.addTab("Inbox", null, allPanel, "Displays all tasks.");
		tabbedPaneDisplay.setMnemonicAt(0, KeyEvent.VK_1);
		
		JComponent dailyPanel = makeTextPanel(textPaneToday, new IndigoLogic("view today").display);
		tabbedPaneDisplay.addTab("Today", null, dailyPanel, "Displays daily tasks.");
		tabbedPaneDisplay.setMnemonicAt(0, KeyEvent.VK_1);
		
		JComponent weeklyPanel = makeTextPanel(textPaneToday, new IndigoLogic("view this week").display);
		tabbedPaneDisplay.addTab("This Week", null, weeklyPanel, "Displays weekly tasks.");
		tabbedPaneDisplay.setMnemonicAt(1, KeyEvent.VK_2);
		
		JComponent monthlyPanel = makeTextPanel(textPaneToday, new IndigoLogic("view this month").display);
		tabbedPaneDisplay.addTab("This Month", null, monthlyPanel, "Displays monthly tasks.");
		tabbedPaneDisplay.setMnemonicAt(2, KeyEvent.VK_3);
		
		JComponent floatingPanel = makeTextPanel(textPaneToday, new IndigoLogic("view -f").display);
		tabbedPaneDisplay.addTab("Floating", null, floatingPanel, "Displays no-deadline tasks.");
		tabbedPaneDisplay.setMnemonicAt(3, KeyEvent.VK_4);
		
		add(tabbedPaneDisplay);
		
		tabbedPaneDisplay.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	}

	private JComponent makeTextPanel(JTextPane textPane, String text) {
		//TODO
		JPanel panel = new JPanel(false);
		textPaneToday = new JTextPane();
		
		textPaneToday.setText(text);
		//filler.setHorizontalAlignment(JLabel.CENTER);
		panel.setLayout(new GridLayout(1,1));
		panel.add(textPaneToday);
		
		
		return panel;
		
	} 
	
	public void update(){
		//TODO
		IndigoLogic lc = new IndigoLogic("view -today");
		textPaneToday.setText(lc.display);
	}
	
	/*	private void setTabbedTextPane(){
		
		for (int i=0; i < inputs.size(); i++){
					 textPane.setText(inputs.get(i) + '\n');
		}
	}*/
}
