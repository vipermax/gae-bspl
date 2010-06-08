package com.viper.bspl.client;

import java.io.Serializable;

public class YearReport implements Serializable {
	
	private YearReportSummary summary = new YearReportSummary();;
	private String xmlData = "";
	
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
	
	@Override
	public boolean equals(Object obj) {
		if(obj != null && obj instanceof YearReport) {
			if(!this.summary.equals(((YearReport)obj).getSummary())) {
				return false;
			}
			if(!this.xmlData.equals(((YearReport)obj).getXmlData())) {
				return false;
			}
			return true;
		} else {
			return false;
		}
	}
	
	public YearReport deepClone() {
		YearReport cloneObj = new YearReport();
		cloneObj.setSummary(summary.deepClone());
		cloneObj.setXmlData(xmlData);
		return cloneObj;
	}
	
}
