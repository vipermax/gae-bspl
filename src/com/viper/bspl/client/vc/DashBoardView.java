package com.viper.bspl.client.vc;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.viper.bspl.client.BSPL;
import com.viper.bspl.client.ProductInfo;
import com.viper.bspl.client.YearReportSummary;
import com.viper.bspl.client.BSPL.VIEW_TYPE;

public class DashBoardView extends BaseView {

	public DashBoardView(Map<String, String> parameters) {
		super(parameters);
	}

	@Override
	public void initView() {
		loadHeader();
		loadMainArea();
		loadFooter();
	}
	
	private void loadMainArea() {
		FlowPanel mainPanel = new FlowPanel();
		
		mainPanel.add(new InlineHTML("<h1>財務諸表一覧</h1>"));
		
		FlowPanel listPanel = new FlowPanel();
		listPanel.addStyleName("listPanel");
		
		listPanel.add(new InlineHTML("<br/>"));
		listPanel.add(new InlineHTML("<h2>自分作成した財務諸表</h2>"));

		// create buttom
		Button createBtn = new Button("新規作成");
		createBtn.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				Map<String, String> params = new HashMap<String, String>();
				BSPL.switchView(VIEW_TYPE.editView, params);
			}
		});
		listPanel.add(createBtn);

		// self report list
		final FlexTable selftReportTable = new FlexTable();
		selftReportTable.setText(0, 0, "会社名");
		selftReportTable.setText(0, 1, "年度");
		selftReportTable.setText(0, 2, "作成日");
		selftReportTable.setText(0, 3, "最終更新日");
		selftReportTable.setText(0, 4, "");
		selftReportTable.getRowFormatter().addStyleName(0, "listTableHeadRow");
		selftReportTable.getCellFormatter().addStyleName(0, 0, "listTableHeadCell");
		selftReportTable.getCellFormatter().addStyleName(0, 1, "listTableHeadCell");
		selftReportTable.getCellFormatter().addStyleName(0, 2, "listTableHeadCell");
		selftReportTable.getCellFormatter().addStyleName(0, 3, "listTableHeadCell");
		
		BSPL.showWaitPanel();
		BSPL.getDataService().getReportList(BSPL.getLoginInfo().getEmailAddress(), new AsyncCallback<YearReportSummary[]>() {
			@Override
			public void onFailure(Throwable caught) {
				BSPL.hideWaitPanel();
			}
			@Override
			public void onSuccess(YearReportSummary[] result) {
				int row = 1;
				for(final YearReportSummary summary : result) {
					selftReportTable.setText(row, 0, summary.getCompanyName());
					if(summary.getYear().length() > 0) {
						selftReportTable.setText(row, 1, summary.getYear() + "年");
					} else {
						selftReportTable.setText(row, 1, "");
					}
					selftReportTable.setText(row, 2, DateTimeFormat.getFormat("yyyy/MM/dd HH:mm:ss").format(summary.getCreateDate()));
					selftReportTable.setText(row, 3, DateTimeFormat.getFormat("yyyy/MM/dd HH:mm:ss").format(summary.getLastUpdate()));
					Button editBtn = new Button("編集");
					selftReportTable.setWidget(row, 4, editBtn);
					editBtn.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							Map<String, String> params = new HashMap<String, String>();
							params.put("id", Long.toString(summary.getId()));
							BSPL.switchView(VIEW_TYPE.editView, params);
						}
					});
					// style
					if(row % 2 == 1) {
						selftReportTable.getRowFormatter().addStyleName(row, "listTableDataRowOdd");
					} else {
						selftReportTable.getRowFormatter().addStyleName(row, "listTableDataRowEven");
					}
					selftReportTable.getCellFormatter().addStyleName(row, 0, "listTableDataCell");
					selftReportTable.getCellFormatter().addStyleName(row, 1, "listTableDataCell");
					selftReportTable.getCellFormatter().addStyleName(row, 2, "listTableDataCell");
					selftReportTable.getCellFormatter().addStyleName(row, 3, "listTableDataCell");
					row++;
				}
				BSPL.hideWaitPanel();
			}
		});
		
		listPanel.add(selftReportTable);

//		listPanel.add(new InlineHTML("<div class=\"caution\">" +
//				"<p>注意：このサイトは皆さんが作成した比例縮尺図を共有するためのサイトであります。<br/>" +
//				"基本的、個人作ったデータは、後ほど全ユーザーに公開することを予定しています。<br/>" +
//				"御了承ください。</p></div>"));
		
		listPanel.add(new InlineHTML("<br/>"));
		
		listPanel.add(new InlineHTML("<h2>ほかのユーザーが作成した財務諸表</h2>"));

		// self report list
		final FlexTable allReportTable = new FlexTable();
		allReportTable.setText(0, 0, "会社名");
		allReportTable.setText(0, 1, "年度");
		allReportTable.setText(0, 2, "作成日");
		allReportTable.setText(0, 3, "最終更新日");
		allReportTable.setText(0, 4, "作成者");
		allReportTable.setText(0, 5, "");
		allReportTable.getRowFormatter().addStyleName(0, "listTableHeadRow");
		allReportTable.getCellFormatter().addStyleName(0, 0, "listTableHeadCell");
		allReportTable.getCellFormatter().addStyleName(0, 1, "listTableHeadCell");
		allReportTable.getCellFormatter().addStyleName(0, 2, "listTableHeadCell");
		allReportTable.getCellFormatter().addStyleName(0, 3, "listTableHeadCell");
		allReportTable.getCellFormatter().addStyleName(0, 4, "listTableHeadCell");
		
		BSPL.showWaitPanel();
		BSPL.getDataService().getReportList("", new AsyncCallback<YearReportSummary[]>() {
			@Override
			public void onFailure(Throwable caught) {
				BSPL.hideWaitPanel();
			}
			@Override
			public void onSuccess(YearReportSummary[] result) {
				int row = 1;
				for(final YearReportSummary summary : result) {
					
					if(summary.getCreatorEmail().equals(BSPL.getLoginInfo().getEmailAddress())) {
						continue;
					}
					
					allReportTable.setText(row, 0, summary.getCompanyName());
					if(summary.getYear().length() > 0) {
						allReportTable.setText(row, 1, summary.getYear() + "年");
					} else {
						allReportTable.setText(row, 1, "");
					}
					allReportTable.setText(row, 2, DateTimeFormat.getFormat("yyyy/MM/dd HH:mm:ss").format(summary.getCreateDate()));
					allReportTable.setText(row, 3, DateTimeFormat.getFormat("yyyy/MM/dd HH:mm:ss").format(summary.getLastUpdate()));
//					allReportTable.setText(row, 4, summary.getCreatorNickname() + "(" + summary.getCreatorEmail() + ")");
					allReportTable.setText(row, 4, summary.getCreatorNickname());
					Button editBtn = new Button("閲覧");
					allReportTable.setWidget(row, 5, editBtn);
					editBtn.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							Map<String, String> params = new HashMap<String, String>();
							params.put("id", Long.toString(summary.getId()));
							BSPL.switchView(VIEW_TYPE.editView, params);
						}
					});
					// style
					if(row % 2 == 1) {
						allReportTable.getRowFormatter().addStyleName(row, "listTableDataRowOdd");
					} else {
						allReportTable.getRowFormatter().addStyleName(row, "listTableDataRowEven");
					}
					allReportTable.getCellFormatter().addStyleName(row, 0, "listTableDataCell");
					allReportTable.getCellFormatter().addStyleName(row, 1, "listTableDataCell");
					allReportTable.getCellFormatter().addStyleName(row, 2, "listTableDataCell");
					allReportTable.getCellFormatter().addStyleName(row, 3, "listTableDataCell");
					allReportTable.getCellFormatter().addStyleName(row, 4, "listTableDataCell");
					row++;
				}
				BSPL.hideWaitPanel();
			}
		});
		
		listPanel.add(allReportTable);
		
		mainPanel.add(listPanel);
		this.add(mainPanel);
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
		
		footer.add(new InlineHTML("&nbsp;&nbsp;&nbsp;&nbsp;"));
		
		// report bug link
		Anchor bugReportLink = new Anchor("不具合を報告する");
		bugReportLink.setHref("http://spreadsheets.google.com/viewform?formkey=dEdBR0Q4bVdiZmNhV3JtRFpLcnNuUWc6MQ");
		bugReportLink.setTarget("_blank");
		footer.add(bugReportLink);
		
		this.add(footer);
	}

	@Override
	public boolean closeView() {
		return true;
	}


}
