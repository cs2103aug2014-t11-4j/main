package indigoSrc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Listener implements ActionListener, KeyListener {
	
	private FillUpMainWindow object = new FillUpMainWindow();
	private TabbedPaneDisplay taskDisplay = new TabbedPaneDisplay();
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String text = object.getReadInputText();
		object.selectAllReadInput();
		LogicFacade controller = new LogicFacade(text);
		object.setUserFeedbackText(controller.feedback);
		taskDisplay.update(text);
		object.readInputFocus();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		 displayInfo(e, object.getReadInputText());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		displayInfo(e, object.getReadInputText());
	}

	@Override
	public void keyTyped(KeyEvent e) {
		displayInfo(e, object.getReadInputText());
	}


	private void displayInfo(KeyEvent e, String command) {
	    int id = e.getID();
	    if (id == KeyEvent.KEY_TYPED) {	
	    } else if(id == KeyEvent.KEY_PRESSED) {	
	    	if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Z) {
	    		LogicFacade lc = new LogicFacade("undo");
	    		taskDisplay.update(lc.display);
	    	} else if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Y){
	    		LogicFacade lc = new LogicFacade("redo");
	    		taskDisplay.update(lc.display);
	    	} else if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_D){
	    		object.setReadInputText("delete ");
	    	} else if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_N){
	    		object.setReadInputText("add ");
	    	} else if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_E){
	    		object.setReadInputText("edit ");
	    	} else if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_F){
	    		object.setReadInputText("search ");
	    	} else if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_C){
	    		
	    	}
	    } 
	    
	    else if(id == KeyEvent.KEY_RELEASED) {
	    	if (command.equals("a") || command.equals("ad") || command.equals("add")) 
	    		object.setUserFeedbackText( "add <some task>");
	    	else if (command.equals("d") ||command.equals("de") || command.equals("del") || command.equals("dele")|| command.equals("delet")|| command.equals("delete"))
	    		object.setUserFeedbackText( "delete <index>");
	    	else if (command.equals("v") || command.equals("vi") || command.equals("vie"))
	    		object.setUserFeedbackText( "view <-t/-w/-m/done/undone>");
	    	else if (command.equals("e") || command.equals("ed") || command.equals("edi") || command.equals("edit"))
	    		object.setUserFeedbackText( "edit <index> <some task>");
	    	else if (command.equals("c")||command.equals("co")||command.equals("com")||command.equals("comp")||command.equals("compl")
	    			||command.equals("comple")||command.equals("complet")||command.equals("complete")){
	    		object.setUserFeedbackText("complete <index>");
	    	}
	    }
	}
}