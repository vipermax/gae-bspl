package com.viper.bspl.client.graphic;

import org.vaadin.gwtgraphics.client.DrawingArea;
import org.vaadin.gwtgraphics.client.shape.Text;

import com.viper.bspl.client.data.Statement;
import com.viper.bspl.client.vc.DrawUtil;
import com.viper.bspl.client.vc.DrawUtil.ALIGN;

public class BSPLGraph extends BaseGraph {

	private String companyName = "";
	private String year = "";
	private Statement bsState = null;
	private Statement plState = null;
	
	static final int FONT_SIZE_TITLE = 20;
	private int headerHeight = 30;
	
	public BSPLGraph(DrawingArea canvas) {
		super(canvas);
	}

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
	public void reDraw() {
		
		// draw header
		drawHeader();
		
		// draw BS
		SingleGraph bsGraphic = new SingleGraph(canvas, bsState);
		bsGraphic.setArea(new Area(new Point(0, headerHeight), new Point(canvas.getWidth()/2, canvas.getHeight())));
		bsGraphic.setGraphTotal(Math.max(bsState.getTotal(), plState.getTotal()));
		bsGraphic.setTitle("貸借対照表");
		bsGraphic.reDraw();
		
		// draw PL
		SingleGraph plGraphic = new SingleGraph(canvas, plState);
		plGraphic.setArea(new Area(new Point(canvas.getWidth()/2, headerHeight), new Point(canvas.getWidth(), canvas.getHeight())));
		plGraphic.setGraphTotal(Math.max(bsState.getTotal(), plState.getTotal()));
		plGraphic.setTitle("損益計算書");
		plGraphic.reDraw();
		
	}
	
	private void drawHeader() {
		
		String text = "";
		if(companyName.trim().isEmpty()) {
			text += "<会社名>";
		} else {
			text += companyName.trim();
		}
		
		text += "　　　";
		
		if(year.trim().isEmpty()) {
			text += "(XXXX年)";
		} else {
			text += "(" + year.trim() + "年)";
		}
		
		int headerTextY = FONT_SIZE_TITLE;
		int centerX = canvas.getWidth() / 2;
		Text headerText = DrawUtil.generateText(centerX, headerTextY, text, FONT_SIZE_TITLE, "black", ALIGN.center);
		canvas.add(headerText);
	}

}
