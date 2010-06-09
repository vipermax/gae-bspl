package com.viper.bspl.client;

import com.google.gwt.user.client.ui.FlowPanel;

public abstract class BaseTab {
	
	protected FlowPanel fp = new FlowPanel();
	
	public FlowPanel getContent() {
		return fp;
	}
	
	abstract void init(boolean readonlyMode);
	
}
