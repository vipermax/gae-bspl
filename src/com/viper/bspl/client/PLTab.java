package com.viper.bspl.client;

import org.vaadin.gwtgraphics.client.DrawingArea;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.viper.bspl.client.data.PLStatement;
import com.viper.bspl.client.data.Statement;
import com.viper.bspl.client.graphic.SingleGraph;
import com.viper.bspl.client.vc.EditView;
import com.viper.bspl.client.vc.StatementVC;

public class PLTab extends BaseTab {

	Statement state = PLStatement.getBlankStatement();
	StatementVC stateVC = null;
	
	public void setState(Statement state) {
		this.state = state;
	}

	@Override
	public void init(boolean readonlyMode) {

		stateVC = new StatementVC(state, readonlyMode);

		// preview button
		Button prevButton = new Button("PLプレビュー");
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
		SingleGraph plGraphic = new SingleGraph(popCanvas, state);
		plGraphic.setCompanyName(EditView.getCompanyName());
		plGraphic.setYear(EditView.getYear());
		plGraphic.setDrawCompanyName(true);
		plGraphic.setTitle("損益計算書");
		plGraphic.setShowNumber(true);
		plGraphic.setShowPercent(true);
		plGraphic.reDraw();
		
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
