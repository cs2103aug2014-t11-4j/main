package indigoSrc;
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
	public TabbedPaneDisplay(){
		super(new GridLayout(3,3));
		
		tabbedPaneDisplay = new JTabbedPane();
		
		JComponent dailyPanel = makeTextPanel(textPaneToday, "Tasks for today displayed here.");
		tabbedPaneDisplay.addTab("Today", null, dailyPanel, "Displays daily tasks.");
		tabbedPaneDisplay.setMnemonicAt(0, KeyEvent.VK_1);
		
		JComponent weeklyPanel = makeTextPanel("Tasks for the week displayed here.");
		tabbedPaneDisplay.addTab("This Week", null, weeklyPanel, "Displays weekly tasks.");
		tabbedPaneDisplay.setMnemonicAt(1, KeyEvent.VK_2);
		
		JComponent monthlyPanel = makeTextPanel("Tasks for the month displayed here.");
		tabbedPaneDisplay.addTab("This Month", null, monthlyPanel, "Displays monthly tasks.");
		tabbedPaneDisplay.setMnemonicAt(2, KeyEvent.VK_3);
		
		JComponent floatingPanel = makeTextPanel("Tasks without deadlines displayed here.");
		tabbedPaneDisplay.addTab("Floating", null, floatingPanel, "Displays no-deadline tasks.");
		tabbedPaneDisplay.setMnemonicAt(3, KeyEvent.VK_4);
		
		add(tabbedPaneDisplay);
		
		tabbedPaneDisplay.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	}
	private JComponent makeTextPanel(String text) {
		JPanel panel = new JPanel(false);
		JTextPane textPane = new JTextPane();
		
		textPane.setText(text);
		//filler.setHorizontalAlignment(JLabel.CENTER);
		panel.setLayout(new GridLayout(3,3));
		panel.add(textPane);
		
		
		return panel;
		
	}
	private JComponent makeTextPanel(JTextPane textPane, String text) {
		//TODO
		JPanel panel = new JPanel(false);
		textPaneToday = new JTextPane();
		
		textPaneToday.setText(text);
		//filler.setHorizontalAlignment(JLabel.CENTER);
		panel.setLayout(new GridLayout(3,3));
		panel.add(textPaneToday);
		
		
		return panel;
		
	} 
	
	public void update(){
		//TODO
		IndigoLogic lc = new IndigoLogic();
		textPaneToday.setText("updated!");
	}
	
	/*	private void setTabbedTextPane(){
		
		for (int i=0; i < inputs.size(); i++){
					 textPane.setText(inputs.get(i) + '\n');
		}
	}*/
}
