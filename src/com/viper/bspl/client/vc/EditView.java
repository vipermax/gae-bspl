package com.viper.bspl.client.vc;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.XMLParser;
import com.viper.bspl.client.BSPL;
import com.viper.bspl.client.BSTab;
import com.viper.bspl.client.LoginInfo;
import com.viper.bspl.client.PLTab;
import com.viper.bspl.client.ProductInfo;
import com.viper.bspl.client.ResultTab;
import com.viper.bspl.client.YearReport;
import com.viper.bspl.client.YearReportSummary;
import com.viper.bspl.client.BSPL.VIEW_TYPE;
import com.viper.bspl.client.data.BSPLPairContainer;
import com.viper.bspl.client.rpc.ServiceResponse;

public class EditView extends BaseView {

	YearReport yearReport = new YearReport();
	
	public static TextBox companyNameText = new TextBox();
	public static ListBox yearList = new ListBox();;
	
	FlowPanel mainArea = new FlowPanel();
	DecoratedTabPanel mainTab = new DecoratedTabPanel();
	final BSTab bsTab = new BSTab();
	final PLTab plTab = new PLTab();
	final ResultTab resultTab = new ResultTab();
	
	public EditView(Map<String, String> parameters) {
		super(parameters);
		companyNameText.setText("");
		yearList.setSelectedIndex(0);
	}

	public void setYearReport(YearReport yearReport) {
		this.yearReport = yearReport;
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

		infoArea.add(new InlineHTML("<h1>BS、PL比例縮尺図（単年度）</h1>"));

		// returnto list button
		Button returnBtn = new Button("<<一覧へ戻る");
		returnBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Map<String, String> params = new HashMap<String, String>();
				BSPL.switchView(VIEW_TYPE.listView, params);
			}
		});
		infoArea.add(returnBtn);
		
		infoArea.add(new InlineHTML("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"));
		
		// save button
		Button saveBtn = new Button("保存");
		saveBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				
				YearReportSummary summary = yearReport.getSummary();
				summary.setCompanyName(companyNameText.getText().trim());
				int selectedIndex = yearList.getSelectedIndex();
				if(selectedIndex > 0) {
					summary.setYear(yearList.getValue(selectedIndex));
				} else {
					summary.setYear("");
				}
				
				if(summary.getCompanyName().isEmpty() || summary.getYear().isEmpty()) {
					Window.alert("[会社名]と[年度]を入力してください。");
					return;
				}
				
				Date nowDate = new Date();
				summary.setCreateDate(nowDate);
				summary.setLastUpdate(nowDate);
				LoginInfo loginInfo = BSPL.getLoginInfo();
				summary.setCreatorNickname(loginInfo.getNickname());
				summary.setCreatorEmail(loginInfo.getEmailAddress());

				yearReport.setSummary(summary);
				
				// generate xml
				BSPLPairContainer container = new BSPLPairContainer();
				container.setBsState(bsTab.getStatement());
				container.setPlState(plTab.getStatement());

				yearReport.setXmlData(container.serializeToXMLString());
				
				BSPL.getDataService().addOrUpdateYearReport(yearReport, new AsyncCallback<String>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("保存失敗しました。");
					}
					@Override
					public void onSuccess(String result) {
						yearReport.getSummary().setId(Long.parseLong(result));
						Window.alert("データをサーバーに保存しました。");
					}
				});
			}
		});

		infoArea.add(saveBtn);
		
		infoArea.add(new InlineHTML("<br/>"));
		
		// info
		infoArea.add(new InlineLabel("会社名:"));
		if(yearReport.getSummary().getId() > 0) {
			companyNameText.setText(yearReport.getSummary().getCompanyName());
		}
		infoArea.add(companyNameText);
		
		infoArea.add(new InlineLabel("年度:"));
		yearList = new ListBox();
		yearList.addItem("", "-1");
		int currentYear = Integer.parseInt(DateTimeFormat.getFormat("yyyy").format(new Date()));
		for(int i = 1970; i < currentYear + 5; i++) {
			yearList.addItem(Integer.toString(i), Integer.toString(i));
		}
		if(yearReport.getSummary().getId() > 0) {
			for(int i = 0; i < yearList.getItemCount(); i++) {
				if(yearList.getItemText(i).trim().equals(yearReport.getSummary().getYear().trim())) {
					yearList.setSelectedIndex(i);
					break;
				}
			}
		}
		infoArea.add(yearList);
		
		this.add(infoArea);
	}
	
	private void loadMainArea() {
		// main area
		mainArea.addStyleName("mainArea");
		
		// parse statement data
		if(yearReport.getSummary().getId() > 0) {
			BSPLPairContainer container = new BSPLPairContainer();
			Document document = XMLParser.parse(yearReport.getXmlData());
			container.parseFromXML(document.getDocumentElement());
			bsTab.setState(container.getBsState());
			plTab.setState(container.getPlState());
		}
		
		// bs tab
		bsTab.init();
		
		mainTab.add(bsTab.getContent(), "BS");
		
		// pl tab
		plTab.init();

		mainTab.add(plTab.getContent(), "PL");
		
		// result tab
		resultTab.init();
		
		mainTab.add(resultTab.getContent(), "比例縮尺図（結果）");
		
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
					resultTab.setBsState(bsTab.getStatement());
					resultTab.setPlState(plTab.getStatement());
					resultTab.reDraw();
				}
			}
		});
		
		mainArea.add(mainTab);
		this.add(mainArea);
	}

}
