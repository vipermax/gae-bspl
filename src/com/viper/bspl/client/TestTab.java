package com.viper.bspl.client;

import org.vaadin.gwtgraphics.client.DrawingArea;
import org.vaadin.gwtgraphics.client.shape.Circle;
import org.vaadin.gwtgraphics.client.shape.Text;

public class TestTab extends BaseTab {

	DrawingArea canvas = new DrawingArea(400, 400);
	
	@Override
	void init() {
		
		Circle circle = new Circle(100, 100, 50);
		circle.setFillColor("red");
		canvas.add(circle);
		
		Text text = new Text(200, 200, "日本語");
		canvas.add(text);
		
		fp.add(canvas);
	}

}
