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
import com.viper.bspl.client.graphic.SingleGraph;
import com.viper.bspl.client.vc.StatementVC;

public class BSTab extends BaseTab {

	Statement state = BSStatement.getBlankStatement();
	StatementVC stateVC = new StatementVC(state);
	
	@Override
	public void init() {

		// preview button
		Button prevButton = new Button("BSプレビュー");
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
		SingleGraph bsGraphic = new SingleGraph(popCanvas, state);
		bsGraphic.setTitle("貸借対照表");
		bsGraphic.reDraw();
		
		final PopupPanel popup = new PopupPanel();
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
		
		popup.setAutoHideEnabled(true);
		popup.setGlassEnabled(true);
		popup.setModal(true);
		popup.center();
	}
	
	public Statement getStatement() {
		stateVC.collectDataFromInput();
		return state;
	}
}
