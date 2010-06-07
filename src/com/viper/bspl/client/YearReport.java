package com.viper.bspl.client;

import java.io.Serializable;

public class YearReport implements Serializable {
	
	private YearReportSummary summary;
	private String xmlData;
	
	public YearReportSummary getSummary() {
		return summary;
	}
	public void setSummary(YearReportSummary summary) {
		this.summary = summary;
	}
	public String getXmlData() {
		return xmlData;
	}
	public void setXmlData(String xmlData) {
		this.xmlData = xmlData;
	}
	
}
