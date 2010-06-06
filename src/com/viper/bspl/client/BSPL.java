package com.viper.bspl.client;

import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class BSPL implements EntryPoint {

	@Override
	public void onModuleLoad() {
		
		loadHeader();
		
		loadInfoArea();
		
		loadMainArea();
		
		loadFooter();
	}
	
	private void loadHeader() {
		
	}
	
	private void loadFooter() {
		// footer
		FlowPanel footer = new FlowPanel();
		footer.add(new InlineLabel("Version: " + ProductInfo.version));
		RootPanel.get("footer").add(footer);
	}
	
	private void loadInfoArea() {
		// infomation area
		FlowPanel infoArea = new FlowPanel();
		infoArea.add(new InlineLabel("会社名:"));
		infoArea.add(new TextBox());
		infoArea.add(new InlineLabel("年度:"));
		ListBox yearList = new ListBox();
		yearList.addItem("", "-1");
		int currentYear = Integer.parseInt(DateTimeFormat.getFormat("yyyy").format(new Date()));
		for(int i = 1970; i < currentYear + 5; i++) {
			yearList.addItem(Integer.toString(i), Integer.toString(i));
		}
		infoArea.add(yearList);
		
		RootPanel.get("infoArea").add(infoArea);
	}
	
	private void loadMainArea() {
		// main area
		DecoratedTabPanel mainTab = new DecoratedTabPanel();
		
		BSTab bsTab = new BSTab();
		bsTab.init();
		
		mainTab.add(bsTab.getContent(), "[BS]");
		mainTab.add(new HTML("<h2>Hello tab 2</h2>"), "[PL]");
		mainTab.add(new HTML("BS/PL"), "[BS/PL]");

		TestTab testTab = new TestTab();
		testTab.init();
		mainTab.add(testTab.getContent(), "[TEST]");

		mainTab.setWidth("100%");
		mainTab.selectTab(0);
		
		RootPanel.get("mainArea").add(mainTab);
	}
}
