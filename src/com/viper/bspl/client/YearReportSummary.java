package com.viper.bspl.client;

import java.io.Serializable;
import java.util.Date;

public class YearReportSummary implements Serializable {
	
	private long id = 0;
	private String companyName;
	private String year;
	private String creatorNickname;
	private String creatorEmail;
	private Date createDate;
	private Date lastUpdate;
	
	
	@Override
	public boolean equals(Object obj) {
		if(obj != null && obj instanceof YearReportSummary) {
			if(this.id != ((YearReportSummary)obj).getId()) {
				return false;
			}
			if(!this.companyName.equals(((YearReportSummary)obj).getCompanyName())) {
				return false;
			}
			if(!this.year.equals(((YearReportSummary)obj).getYear())) {
				return false;
			}
			if(!this.creatorNickname.equals(((YearReportSummary)obj).getCreatorNickname())) {
				return false;
			}
			if(!this.creatorEmail.equals(((YearReportSummary)obj).getCreatorEmail())) {
				return false;
			}
//			if(!this.createDate.equals(((YearReportSummary)obj).getCreateDate())) {
//				return false;
//			}
//			if(!this.lastUpdate.equals(((YearReportSummary)obj).getLastUpdate())) {
//				return false;
//			}
			return true;
		} else {
			return false;
		}
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getCreatorNickname() {
		return creatorNickname;
	}
	public void setCreatorNickname(String creatorNickname) {
		this.creatorNickname = creatorNickname;
	}
	public String getCreatorEmail() {
		return creatorEmail;
	}
	public void setCreatorEmail(String creatorEmail) {
		this.creatorEmail = creatorEmail;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public YearReportSummary deepClone() {
		YearReportSummary cloneObj = new YearReportSummary();
		cloneObj.companyName = this.companyName;
		cloneObj.id = this.id;
		cloneObj.creatorEmail = this.creatorEmail;
		cloneObj.creatorNickname = this.creatorNickname;
		cloneObj.createDate = this.createDate;
		cloneObj.lastUpdate = this.lastUpdate;
		cloneObj.year = this.year;
		return cloneObj;
	}
	
}
