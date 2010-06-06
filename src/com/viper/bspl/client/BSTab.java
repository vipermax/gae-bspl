package com.viper.bspl.client;

import org.vaadin.gwtgraphics.client.DrawingArea;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.viper.bspl.client.data.BSStatement;
import com.viper.bspl.client.data.Statement;
import com.viper.bspl.client.graphic.BSArea;
import com.viper.bspl.client.vc.StatementVC;

public class BSTab extends BaseTab {

	Statement state = BSStatement.getBlankStatement();
	StatementVC stateVC = new StatementVC(state);
	
	@Override
	void init() {

		// preview button
		Button prevButton = new Button("プレビュー");
		prevButton.addStyleName("btnPreview");
		prevButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				preview();
			}
		});
		
		fp.add(prevButton);
		fp.add(stateVC.getDataTable());
	}
	
	private void preview() {
		stateVC.collectDataFromInput();
		
		// popup window
		int width = (int) (Window.getClientWidth() * 0.8);
		int height = (int) (Window.getClientHeight() * 0.8);
		DrawingArea popCanvas = new DrawingArea(width, height);
		BSArea bsGraphic = new BSArea(popCanvas, state);
		bsGraphic.reDraw();
		
		final PopupPanel popup = new PopupPanel(false, true);
		popup.addStyleName("popup");
		VerticalPanel popupContent = new VerticalPanel();
		popupContent.add(popCanvas);
		Button closeBtn = new Button("閉じる");
		closeBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				popup.hide();
			}
		});
		popupContent.add(closeBtn);
		popup.add(popupContent);
		popup.center();
	}
	
	public Statement getStatementData() {
		stateVC.collectDataFromInput();
		return state;
	}
}
