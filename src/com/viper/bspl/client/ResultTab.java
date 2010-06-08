package com.viper.bspl.client;

import org.vaadin.gwtgraphics.client.DrawingArea;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.viper.bspl.client.data.Statement;
import com.viper.bspl.client.graphic.BSPLGraph;
import com.viper.bspl.client.vc.EditView;

public class ResultTab extends BaseTab {

	private String companyName = "";
	private String year = "";
	private Statement bsState = null;
	private Statement plState = null;
	
	DrawingArea canvas = null;
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String s) {
		this.year = s;
	}

	public Statement getBsState() {
		return bsState;
	}

	public void setBsState(Statement bsState) {
		this.bsState = bsState;
	}

	public Statement getPlState() {
		return plState;
	}

	public void setPlState(Statement plState) {
		this.plState = plState;
	}

	@Override
	public void init() {
		// redraw button
		Button prevButton = new Button("再描画");
//		prevButton.addStyleName("btnPreview");
		prevButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				companyName = EditView.getCompanyName();
				year = EditView.getYear();
				reDraw();
			}
		});
		fp.add(prevButton);

		int width = (int) (Window.getClientWidth() * 0.9);
		int height = (int) (Window.getClientHeight() * 0.9);
		canvas = new DrawingArea(width, height);
		fp.add(canvas);
	}
	
	public void reDraw() {
		
		canvas.clear();
		
		BSPLGraph graph = new BSPLGraph(canvas);
		graph.setCompanyName(companyName);
		graph.setYear(year);
		graph.setBsState(bsState);
		graph.setPlState(plState);
		graph.reDraw();
		
	}

}
