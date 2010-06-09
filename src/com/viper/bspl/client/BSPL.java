package com.viper.bspl.client;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.viper.bspl.client.rpc.DataService;
import com.viper.bspl.client.rpc.DataServiceAsync;
import com.viper.bspl.client.vc.BaseView;
import com.viper.bspl.client.vc.DashBoardView;
import com.viper.bspl.client.vc.EditView;
import com.viper.bspl.client.vc.LoginView;
import com.viper.bspl.client.vc.ViewUtil;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class BSPL implements EntryPoint {

	public enum VIEW_TYPE {
		loginView,
		listView,
		editView,
	}
	
	static BaseView currentView = null;
	static PopupPanel waitPanel = ViewUtil.getWaitPanel();
	
	static private LoginInfo loginInfo = null;
	static private DataServiceAsync dataServiceAsync = GWT.create(DataService.class);
	
	@Override
	public void onModuleLoad() {
		
		// check login status
		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.login(GWT.getHostPageBaseURL(), new AsyncCallback<LoginInfo>() {
			@Override
			public void onSuccess(LoginInfo result) {
				loginInfo = result;
				if(loginInfo.isLoggedIn()) {
					Map<String, String> params = new HashMap<String, String>();
					switchView(VIEW_TYPE.listView, params);
				} else {
					Map<String, String> params = new HashMap<String, String>();
					params.put(LoginView.PARAM_LOGINURL, loginInfo.getLoginUrl());
					switchView(VIEW_TYPE.loginView, params);
				}
			}
			@Override
			public void onFailure(Throwable caught) {
			}
		});
		
	}
	
	static public LoginInfo getLoginInfo() {
		return loginInfo;
	}
	
	static public DataServiceAsync getDataService() {
		return dataServiceAsync;
	}

	static public void switchView(VIEW_TYPE type, Map<String, String> parameters) {
		
		// close current view
		if(null != currentView) {
			if(!currentView.closeView()) {
				return;
			}
		}
		
		// clearPage
		RootPanel.get("view").clear();
		
		// create new view
		currentView = createNewView(type, parameters);
		if(null != currentView) {
			
			String id = "";
			if(parameters.containsKey("id")) {
				id = parameters.get("id");
			}
			if(VIEW_TYPE.editView == type && !id.isEmpty()) {
				BSPL.showWaitPanel();
				BSPL.getDataService().getYearReport(id, new AsyncCallback<YearReport>() {
					@Override
					public void onFailure(Throwable caught) {
						BSPL.hideWaitPanel();
					}
					@Override
					public void onSuccess(YearReport result) {
						BSPL.hideWaitPanel();
						((EditView)currentView).setYearReport(result);
						if(result.getSummary().getCreatorEmail().equals(loginInfo.getEmailAddress())) {
							((EditView)currentView).setReadonlyMode(false);
						} else {
							((EditView)currentView).setReadonlyMode(true);
						}
						currentView.initView();
						RootPanel.get("view").add(currentView);
					}
				});
			} else {
				currentView.initView();
				RootPanel.get("view").add(currentView);
			}
			
		}
	}
	
	private static BaseView createNewView(VIEW_TYPE type, Map<String, String> parameters) {
		switch (type) {
		case loginView:
			return new LoginView(parameters);
		case listView:
			return new DashBoardView(parameters);
		case editView:
			return new EditView(parameters);
		default:
			return null;
		}
	}
	
	public static void showWaitPanel() {
		waitPanel.center();
	}
	
	public static void hideWaitPanel() {
		waitPanel.hide();
	}
	
}
