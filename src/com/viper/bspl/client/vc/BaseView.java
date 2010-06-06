package com.viper.bspl.client.vc;

import java.util.Map;

import com.google.gwt.user.client.ui.FlowPanel;

public abstract class BaseView extends FlowPanel {
	
	protected Map<String, String> parameters = null;
	
	public BaseView(Map<String, String> parameters) {
		this.parameters = parameters;
	}
	
	abstract public void initView();
	
	abstract public boolean closeView();
	
}
