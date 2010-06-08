package com.viper.bspl.client.vc;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;

public class ViewUtil {
	
	static public PopupPanel getWaitPanel() {

		FlowPanel messagePanel = new FlowPanel();
		messagePanel.add(new Label("ただ今処理中です。しばらくお待ちください。。。"));
		
		PopupPanel popup = new PopupPanel(false);
		popup.addStyleName("loginPopup");
		popup.add(messagePanel);
		popup.setGlassEnabled(true);
		
		return popup;
	}
	
}
