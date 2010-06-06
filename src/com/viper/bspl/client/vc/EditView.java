package com.viper.bspl.client.vc;

import java.util.Date;
import java.util.Map;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.viper.bspl.client.BSPL;
import com.viper.bspl.client.BSTab;
import com.viper.bspl.client.ProductInfo;

public class EditView extends BaseView {

	public EditView(Map<String, String> parameters) {
		super(parameters);
	}

	@Override
	public void initView() {
		loadHeader();
		loadInfoArea();
		loadMainArea();
		loadFooter();
	}

	@Override
	public boolean closeView() {
		return true;
	}

	private void loadHeader() {
		// header
		FlowPanel header = new FlowPanel();
		FlowPanel headerLine = new FlowPanel();
		headerLine.addStyleName("headerLine");
		headerLine.add(new InlineLabel(BSPL.getLoginInfo().getNickname() + " (" + BSPL.getLoginInfo().getEmailAddress() + ")"));
		headerLine.add(new InlineLabel(" | "));
		Anchor signOutLink = new Anchor("ログアウト");
		signOutLink.setHref(BSPL.getLoginInfo().getLogoutUrl());
		headerLine.add(signOutLink);
		
		header.add(headerLine);
		header.addStyleName("headerArea");
		header.add(new InlineHTML("<h1>BS、PL比例図（単年度）</h1>"));
		this.add(header);
	}
	
	private void loadFooter() {
		// footer
		FlowPanel footer = new FlowPanel();
		footer.addStyleName("footerArea");
		footer.add(new InlineLabel("Version: " + ProductInfo.version));
		this.add(footer);
	}
	
	private void loadInfoArea() {
		// infomation area
		FlowPanel infoArea = new FlowPanel();
		infoArea.addStyleName("infoArea");
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
		
		this.add(infoArea);
	}
	
	private void loadMainArea() {
		// main area
		FlowPanel mainArea = new FlowPanel();
		mainArea.addStyleName("mainArea");
		
		DecoratedTabPanel mainTab = new DecoratedTabPanel();
		
		BSTab bsTab = new BSTab();
		bsTab.init();
		
		mainTab.add(bsTab.getContent(), "BS");
		mainTab.add(new HTML("<h2>このページは開発中です。</h2>"), "PL");
		mainTab.add(new HTML("<h2>このページは開発中です。</h2>"), "比例図（結果）");

		mainTab.setWidth("100%");
		mainTab.selectTab(0);
		
		mainArea.add(mainTab);
		this.add(mainArea);
	}

}
