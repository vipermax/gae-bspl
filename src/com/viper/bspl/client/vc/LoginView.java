package com.viper.bspl.client.vc;

import java.util.Map;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.viper.bspl.client.ProductInfo;

public class LoginView extends BaseView {

	static public final String PARAM_LOGINURL = "PARAM_LOGINURL";
	
	public LoginView(Map<String, String> parameters) {
		super(parameters);
	}

	@Override
	public void initView() {
		FlowPanel loginPanel = new FlowPanel();
		Anchor signInLink = new Anchor("ログインページへ");
		signInLink.setHref((String) parameters.get(PARAM_LOGINURL));
		Label titleLabel = new Label("BS、PL比例図 (" + ProductInfo.version + ")");
		titleLabel.addStyleName("loginTitle");
		loginPanel.add(titleLabel);
		loginPanel.add(new HTML("<br/>"));
		loginPanel.add(new Label("Googleアカウントでログインしてください。"));
		loginPanel.add(signInLink);
		
		PopupPanel popup = new PopupPanel(false);
		popup.addStyleName("loginPopup");
		this.add(popup);

		popup.add(loginPanel);
		popup.setGlassEnabled(true);
		popup.center();
		
	}

	@Override
	public boolean closeView() {
		return true;
	}

}
