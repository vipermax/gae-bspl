package com.viper.bspl.client.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.viper.bspl.client.YearReport;
import com.viper.bspl.client.YearReportSummary;

@RemoteServiceRelativePath("data")
public interface DataService extends RemoteService {
	public String addOrUpdateYearReport(YearReport yearReport) throws NotLoggedInException;
	public YearReport getYearReport(String id) throws NotLoggedInException;
	public YearReportSummary[] getReportList() throws NotLoggedInException;
}
