package gui;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class DefiningConstraints {
	
	private static final int BOTTOM_PANEL_INDEX = 1;
	private static final int TABBED_PANE_INDEX = 2;
	private static final int TOP_PANEL_INDEX = 3;
	private static final int INPUT_FIELD_INDEX = 4;
	private static final int USER_FEEDBACK_INDEX = 5;

	public GridBagConstraints setConstraints(int componentIndex) {
		GridBagConstraints constraints;
		Insets topPanel = new Insets(20,10,5,10);
		
		
		Insets insetsOfReadInput = new Insets(0,20,0,20);
		Insets insetsOfUserFeedback = new Insets(0, 20,0,20);		
		Insets bottomPanel = new Insets(5,10,50,10);
		Insets insetsOfTabbedPane = new Insets(10,20,5,10);
		//Insets insetsOfFloatingPanel = new Insets(10,10,5,20);
		
		if(componentIndex ==  TOP_PANEL_INDEX){
			constraints = new GridBagConstraints(0,0,3,3,0.1,0.1,GridBagConstraints.CENTER,GridBagConstraints.BOTH,topPanel,0,0);

			return constraints;
		}
		
		else if(componentIndex == INPUT_FIELD_INDEX){
			constraints = new GridBagConstraints(0,1,3,1,0.1,0.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,insetsOfReadInput,0,0);

			return constraints;
		}
		else if(componentIndex == USER_FEEDBACK_INDEX){
			constraints = new GridBagConstraints(0,2,1,1,0.1,0.1,GridBagConstraints.CENTER,GridBagConstraints.BOTH,insetsOfUserFeedback,0,0);
			return constraints;
		}
		
		else if(componentIndex ==BOTTOM_PANEL_INDEX){

			constraints = new GridBagConstraints(0,3,3,9,0.0,0.3,GridBagConstraints.CENTER,GridBagConstraints.BOTH,bottomPanel,0,0);
			return constraints;
		}
		else if(componentIndex == TABBED_PANE_INDEX){
			constraints = new GridBagConstraints(0,1,2,8,0.1,0.1,GridBagConstraints.CENTER,GridBagConstraints.BOTH,insetsOfTabbedPane,0,0);

			return constraints;
		}

		return null;
	}
}
