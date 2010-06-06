package com.viper.bspl.client.graphic;

import org.vaadin.gwtgraphics.client.DrawingArea;

public abstract class BaseGraphic {
	
	protected DrawingArea canvas = null;
	protected Area area = null;
	protected int marginTop = 10, marginBottom = 10, marginLeft = 10, marginRight = 10;
	
	protected boolean showNumber = false;
	protected boolean showPercent = false;
	
	public boolean isShowNumber() {
		return showNumber;
	}

	public void setShowNumber(boolean showNumber) {
		this.showNumber = showNumber;
	}

	public boolean isShowPercent() {
		return showPercent;
	}

	public void setShowPercent(boolean showPercent) {
		this.showPercent = showPercent;
	}

	public BaseGraphic(DrawingArea canvas) {
		this.canvas = canvas;
		area = new Area(new Point(0,0), new Point(canvas.getWidth(), canvas.getHeight()));
	}
	
	protected Area getDrawingArea() {
		return new Area(
			new Point(area.start.X + marginLeft, area.start.Y + marginTop),
			new Point(area.end.X - marginRight, area.end.Y - marginBottom));
	}
	
	abstract public void reDraw();
}
