package com.viper.bspl.client.vc;

import java.util.Date;
import java.util.Map;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.viper.bspl.client.BSPL;
import com.viper.bspl.client.BSTab;
import com.viper.bspl.client.PLTab;
import com.viper.bspl.client.ProductInfo;
import com.viper.bspl.client.ResultTab;

public class EditView extends BaseView {

	public static TextBox companyNameText = new TextBox();;
	public static ListBox yearList = new ListBox();;
	
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
		infoArea.add(companyNameText);
		infoArea.add(new InlineLabel("年度:"));
		yearList = new ListBox();
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
		
		// bs tab
		final BSTab bsTab = new BSTab();
		bsTab.init();
		
		mainTab.add(bsTab.getContent(), "BS");
		
		// pl tab
		final PLTab plTab = new PLTab();
		plTab.init();

		mainTab.add(plTab.getContent(), "PL");
		
		// result tab
		final ResultTab resultTab = new ResultTab();
		resultTab.init();
		
		mainTab.add(resultTab.getContent(), "比例図（結果）");
		
//		mainTab.add(new HTML("<h2>この機能は開発中です。</h2>"), "比例図（結果）");

		mainTab.setWidth("100%");
		mainTab.selectTab(0);
		
		// tab click event
		mainTab.addSelectionHandler(new SelectionHandler<Integer>() {
			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				if((int)(event.getSelectedItem()) == 2) {
					resultTab.setCompanyName(companyNameText.getText());
					int selectedIndex = yearList.getSelectedIndex();
					if(selectedIndex > 0) {
						resultTab.setYear(yearList.getValue(selectedIndex));
					} else {
						resultTab.setYear("");
					}
					resultTab.setBsState(bsTab.getStatementData());
					resultTab.setPlState(plTab.getStatementData());
					resultTab.reDraw();
				}
			}
		});
		
		mainArea.add(mainTab);
		this.add(mainArea);
	}

}
