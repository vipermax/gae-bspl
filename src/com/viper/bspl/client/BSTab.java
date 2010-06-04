package com.viper.bspl.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.widgetideas.graphics.client.Color;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;

public class BSTab extends BaseTab {

	private VerticalPanel mainPanel = new VerticalPanel();
	
	@Override
	void init() {

		// canvas widget
		GWTCanvas canvas = new GWTCanvas(400, 400);
		canvas.setLineWidth(1);
		canvas.setStrokeStyle(Color.GREEN);

		canvas.beginPath();
		canvas.moveTo(1, 1);
		canvas.lineTo(1, 50);
		canvas.lineTo(50, 50);
		canvas.lineTo(50, 1);
		canvas.closePath();
		canvas.stroke();
	    
		// data area
		FlexTable dataTable = new FlexTable();
		FlowPanel leftPanel = new FlowPanel();
		FlowPanel rightPanel = new FlowPanel();
		dataTable.setWidget(0, 0, leftPanel);
		dataTable.setWidget(0, 1, rightPanel);
		
		// make tables
		Block b1= new Block(new Item("流動資産", 0, true));
		Block b2= new Block(new Item("固定資産", 0, true));
		leftPanel.add(b1.getBlockTable());
		leftPanel.add(b2.getBlockTable());
		
		Block b3= new Block(new Item("流動負債", 0, true));
		Block b4= new Block(new Item("固定負債", 0, true));
		Block b5= new Block(new Item("資本金", 0, true));
		rightPanel.add(b3.getBlockTable());
		rightPanel.add(b4.getBlockTable());
		rightPanel.add(b5.getBlockTable());
		
		fp.add(dataTable);
		fp.add(canvas);
	}
	
}
