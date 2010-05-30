package com.viper.bspl.client;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.VerticalPanel;

public class BSTab extends BaseTab {

	private FlexTable stocksFlexTable = new FlexTable();
	
	private VerticalPanel mainPanel = new VerticalPanel();
	
	@Override
	void init() {
		// Create table for stock data.
		stocksFlexTable.setText(0, 0, "Symbol");
		stocksFlexTable.setText(0, 1, "Price");
		stocksFlexTable.setText(0, 2, "Change");
		stocksFlexTable.setText(0, 3, "Remove");

		mainPanel.add(stocksFlexTable);
		
		fp.add(mainPanel);
	}
	
	
	
}
