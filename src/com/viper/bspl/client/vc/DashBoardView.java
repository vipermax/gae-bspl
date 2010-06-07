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
		mainPanel.add(new InlineHTML("<h2>自分作成した財務諸表</h2>"));
		
		FlowPanel listPanel = new FlowPanel();
		listPanel.addStyleName("listPanel");
		
		// create buttom
		Button createBtn = new Button("新規作成");
		createBtn.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				Map<String, String> params = new HashMap<String, String>();
				BSPL.switchView(VIEW_TYPE.editView, parameters);
			}
		});
		listPanel.add(createBtn);

		final FlexTable table = new FlexTable();
		// table header
		table.setText(0, 0, "会社名");
		table.setText(0, 1, "年度");
		table.setText(0, 2, "作成日");
		table.setText(0, 3, "最終更新日");
		table.setText(0, 4, "");
		table.getRowFormatter().addStyleName(0, "listTableHeadRow");
		table.getCellFormatter().addStyleName(0, 0, "listTableHeadCell");
		table.getCellFormatter().addStyleName(0, 1, "listTableHeadCell");
		table.getCellFormatter().addStyleName(0, 2, "listTableHeadCell");
		table.getCellFormatter().addStyleName(0, 3, "listTableHeadCell");
		
		BSPL.getDataService().getReportList(new AsyncCallback<YearReportSummary[]>() {
			@Override
			public void onFailure(Throwable caught) {
			}
			@Override
			public void onSuccess(YearReportSummary[] result) {
				int row = 1;
				for(final YearReportSummary summary : result) {
					table.setText(row, 0, summary.getCompanyName());
					table.setText(row, 1, summary.getYear() + "年");
					table.setText(row, 2, DateTimeFormat.getFormat("yyyy/MM/dd HH:mm:ss").format(summary.getCreateDate()));
					table.setText(row, 3, DateTimeFormat.getFormat("yyyy/MM/dd HH:mm:ss").format(summary.getLastUpdate()));
					Button editBtn = new Button("編集");
					table.setWidget(row, 4, editBtn);
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
						table.getRowFormatter().addStyleName(row, "listTableDataRowOdd");
					} else {
						table.getRowFormatter().addStyleName(row, "listTableDataRowEven");
					}
					table.getCellFormatter().addStyleName(row, 0, "listTableDataCell");
					table.getCellFormatter().addStyleName(row, 1, "listTableDataCell");
					table.getCellFormatter().addStyleName(row, 2, "listTableDataCell");
					table.getCellFormatter().addStyleName(row, 3, "listTableDataCell");
					row++;
				}
			}
		});
		
		listPanel.add(table);

		listPanel.add(new InlineHTML("<div class=\"caution\">" +
				"<p>注意：このサイトは皆さんが作成した比例縮尺図を共有するためのサイトであります。<br/>" +
				"基本的、個人作ったデータは、後ほど全ユーザーに公開することを予定しています。<br/>" +
				"御了承ください。</p></div>"));
		
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
		this.add(footer);
	}

	@Override
	public boolean closeView() {
		return true;
	}


}
