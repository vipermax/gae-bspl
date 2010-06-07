package com.viper.bspl.client.vc;

import java.util.Map;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.viper.bspl.client.BSPL;
import com.viper.bspl.client.ProductInfo;
import com.viper.bspl.client.YearReportSummary;

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
		mainPanel.add(new Label("自分作成した財務諸表一覧"));
		
		final FlexTable table = new FlexTable();
		// table header
		table.setText(0, 0, "会社名");
		table.setText(0, 1, "年度");
		table.setText(0, 2, "作成日");
		table.setText(0, 3, "最終更新日");
		
		BSPL.getDataService().getReportList(new AsyncCallback<YearReportSummary[]>() {
			@Override
			public void onFailure(Throwable caught) {
			}
			@Override
			public void onSuccess(YearReportSummary[] result) {
				int row = 1;
				for(YearReportSummary summary : result) {
					table.setText(row, 0, summary.getCompanyName());
					table.setText(row, 1, summary.getYear() + "年");
					table.setText(row, 2, DateTimeFormat.getLongDateFormat().format(summary.getCreateDate()));
					table.setText(row, 3, DateTimeFormat.getLongDateFormat().format(summary.getLastUpdate()));
					row++;
				}
			}
		});
		
		mainPanel.add(table);
		
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
