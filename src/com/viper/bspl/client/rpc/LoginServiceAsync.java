package com.viper.bspl.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.viper.bspl.client.LoginInfo;

public interface LoginServiceAsync {
	public void login(String requestUri, AsyncCallback<LoginInfo> async);
}
