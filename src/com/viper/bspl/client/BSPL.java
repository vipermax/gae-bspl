package com.viper.bspl.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class BSPL implements EntryPoint {

	@Override
	public void onModuleLoad() {
		
		DecoratedTabPanel mainTab = new DecoratedTabPanel();
		
		BSTab bsTab = new BSTab();
		bsTab.init();
		
		mainTab.add(bsTab.getContent(), "[BS]");
		mainTab.add(new HTML("<h2>Hello tab 2</h2>"), "[PL]");
		mainTab.add(new HTML("BS/PL"), "[BS/PL]");
		
		mainTab.setWidth("100%");
		mainTab.selectTab(0);
		
		RootPanel.get("mainArea").add(mainTab);
		
	}
	
}
