package com.viper.bspl.client;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.viper.bspl.client.vc.BaseView;
import com.viper.bspl.client.vc.EditView;
import com.viper.bspl.client.vc.LoginView;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class BSPL implements EntryPoint {

	public enum VIEW_TYPE {
		loginView,
		listView,
		editView,
	}
	
	BaseView currentView = null;
	
	static private LoginInfo loginInfo = null;
	
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
					switchView(VIEW_TYPE.editView, params);
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

	public void switchView(VIEW_TYPE type, Map<String, String> parameters) {
		
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
			currentView.initView();
			RootPanel.get("view").add(currentView);
		}
	}
	
	private BaseView createNewView(VIEW_TYPE type, Map<String, String> parameters) {
		switch (type) {
		case loginView:
			return new LoginView(parameters);
		case listView:
			return null;
		case editView:
			return new EditView(parameters);
		default:
			return null;
		}
	}
	
}
