package com.viper.bspl.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.viper.bspl.client.YearReport;
import com.viper.bspl.client.YearReportSummary;
import com.viper.bspl.client.rpc.DataService;
import com.viper.bspl.client.rpc.NotLoggedInException;
import com.viper.bspl.client.rpc.ServiceResponse;

public class DataServiceImpl extends RemoteServiceServlet implements
		DataService {

	private static final Logger LOG = Logger.getLogger(DataServiceImpl.class.getName());
	private static final PersistenceManagerFactory PMF = JDOHelper.getPersistenceManagerFactory("transactions-optional");

	@Override
	public String addOrUpdateYearReport(YearReport yearReport)
			throws NotLoggedInException {
		checkLoggedIn();
		PersistenceManager pm = getPersistenceManager();
		String id = Long.toString(yearReport.getSummary().getId());
		try {
			if(0 == yearReport.getSummary().getId()) {
				// insert
				DBInnerYearReport dbReport = convert(yearReport);
				Date now = new Date();
				dbReport.setCreateDate(now);
				dbReport.setLastUpdate(now);
				pm.makePersistent(dbReport);
				id = Long.toString(dbReport.getId());
			} else {
				// update
				Query query = pm.newQuery(DBInnerYearReport.class, "id==" + yearReport.getSummary().getId());
				List<DBInnerYearReport> queryResult = (List<DBInnerYearReport>) query.execute();
				if(queryResult.size() > 0) {
					DBInnerYearReport targetReport = queryResult.get(0);
					targetReport.setCompanyName(yearReport.getSummary().getCompanyName());
					targetReport.setYear(yearReport.getSummary().getYear());
					targetReport.setXmlData(yearReport.getXmlData());
					Date now = new Date();
					targetReport.setLastUpdate(now);
					pm.makePersistent(targetReport);
				}
			}
		} finally {
			pm.close();
		}
		return id;
	}

	@Override
	public YearReport getYearReport(String id) throws NotLoggedInException {
		checkLoggedIn();
		PersistenceManager pm = getPersistenceManager();
		try {
			Query query = pm.newQuery(DBInnerYearReport.class, "id==" + id);
			List<DBInnerYearReport> queryResult = (List<DBInnerYearReport>) query.execute();
			if(0 == queryResult.size()) {
				return null;
			} else {
				DBInnerYearReport targetReport = queryResult.get(0);
				return convert(targetReport);
			}
		} finally {
			pm.close();
		}
	}
	
	@Override
	public YearReportSummary[] getReportList(String creatorEmail) throws NotLoggedInException {
		checkLoggedIn();
		PersistenceManager pm = getPersistenceManager();
		try {
			Query query = pm.newQuery(DBInnerYearReport.class);
			ArrayList parameters = new ArrayList();
			if(creatorEmail.trim().length() > 0) {
				query.setFilter("creatorEmail==email");
				query.declareParameters("java.lang.String email");
				parameters.add(creatorEmail);
			}
			query.setOrdering("lastUpdate desc");
			List<DBInnerYearReport> queryResult = (List<DBInnerYearReport>) query.executeWithArray(parameters.toArray());
			ArrayList<YearReportSummary> summaryList = new ArrayList<YearReportSummary>();
			for(DBInnerYearReport dbReport : queryResult) {
				YearReport yearReport = convert(dbReport);
				summaryList.add(yearReport.getSummary());
			}
			return summaryList.toArray(new YearReportSummary[0]);
		} finally {
			pm.close();
		}
	}
	
	private DBInnerYearReport convert(YearReport yearReport) {
		DBInnerYearReport dbReport = new DBInnerYearReport();
		dbReport.setCompanyName(yearReport.getSummary().getCompanyName());
		dbReport.setYear(yearReport.getSummary().getYear());
		dbReport.setCreatorEmail(yearReport.getSummary().getCreatorEmail());
		dbReport.setCreatorNickname(yearReport.getSummary().getCreatorNickname());
		dbReport.setCreateDate(yearReport.getSummary().getCreateDate());
		dbReport.setLastUpdate(yearReport.getSummary().getLastUpdate());
		dbReport.setXmlData(yearReport.getXmlData());
		dbReport.setDeleteFlag(false);
		return dbReport;
	}
	
	private YearReport convert(DBInnerYearReport dbReport) {
		YearReport yearReport = new YearReport();
		yearReport.setSummary(new YearReportSummary());
		yearReport.getSummary().setId(dbReport.getId());
		yearReport.getSummary().setYear(dbReport.getYear());
		yearReport.getSummary().setCompanyName(dbReport.getCompanyName());
		yearReport.getSummary().setCreatorEmail(dbReport.getCreatorEmail());
		yearReport.getSummary().setCreatorNickname(dbReport.getCreatorNickname());
		yearReport.getSummary().setCreateDate(dbReport.getCreateDate());
		yearReport.getSummary().setLastUpdate(dbReport.getLastUpdate());
		yearReport.setXmlData(dbReport.getXmlData());
		return yearReport;
	}

	private void checkLoggedIn() throws NotLoggedInException {
		if (getUser() == null) {
			throw new NotLoggedInException("Not logged in.");
		}
	}

	private User getUser() {
		UserService userService = UserServiceFactory.getUserService();
		return userService.getCurrentUser();
	}

	private PersistenceManager getPersistenceManager() {
		return PMF.getPersistenceManager();
	}
}
