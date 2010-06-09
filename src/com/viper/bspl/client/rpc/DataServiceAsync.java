package com.viper.bspl.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.viper.bspl.client.YearReport;
import com.viper.bspl.client.YearReportSummary;

public interface DataServiceAsync {
	
	public void addOrUpdateYearReport(YearReport yearReport, AsyncCallback<String> async);
	public void getYearReport(String id, AsyncCallback<YearReport> async);
	public void getReportList(String creatorEmail, AsyncCallback<YearReportSummary[]> async);
	
}
