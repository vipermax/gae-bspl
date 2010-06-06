package com.viper.bspl.client.graphic;

import org.vaadin.gwtgraphics.client.DrawingArea;
import org.vaadin.gwtgraphics.client.Line;
import org.vaadin.gwtgraphics.client.shape.Rectangle;
import org.vaadin.gwtgraphics.client.shape.Text;

import com.google.gwt.i18n.client.NumberFormat;
import com.viper.bspl.client.data.Item;
import com.viper.bspl.client.data.Statement;
import com.viper.bspl.client.vc.TextUtil;

public class BSArea extends BaseGraphic {

	int FONT_SIZE_NAME = 12;
	int FONT_SIZE_NUMBER = 8;
	
	public Statement state = null;
	
	public BSArea(DrawingArea canvas, Statement state) {
		super(canvas);
		this.state = state;
	}
	
	Area drawArea = null;
	double hwRate = 1.5f;
	double heightScale = 1f;
	int wide = 100;
	float total;
	
	@Override
	public void reDraw() {
		
		// drawing area
		drawArea = getDrawingArea();
		
		total = Math.max(state.getLeftTotal(), state.getRightTotal());
		heightScale = (double)drawArea.getHeight() / total;
		wide = (int) (total * heightScale * 0.3);
		
		// drawStatement
		drawStatement();
	}
	
	private void drawStatement() {
		Point start = new Point(0, 0);
		// left
		start.X = (drawArea.start.X + drawArea.end.X) / 2 - wide;
		start.Y = drawArea.start.Y;
		for(Item item : state.getLeftPart()) {
			start = drawTopLevel(item, start);
		}
		// right
		start.X = (drawArea.start.X + drawArea.end.X) / 2;
		start.Y = drawArea.start.Y;
		for(Item item : state.getRightPart()) {
			start = drawTopLevel(item, start);
		}
	}
	
	private Point drawTopLevel(Item item, Point start) {
		
		if(0f == item.getAmount()) {
			return start;
		}
		
		Rectangle rect = new Rectangle(start.X, start.Y, wide, (int) (item.getAmount() * heightScale));
		rect.setStrokeColor("black");
		rect.setStrokeWidth(2);
		rect.setFillColor("white");
		canvas.add(rect);
		
		Point childStart = start;
		for(Item child : item.getChildren()) {
			childStart = drawBlock(child, childStart);
		}
		
		return new Point(start.X, (int) (start.Y + item.getAmount() * heightScale));
	}
	
	private Point drawBlock(Item item, Point start) {

		if(0f == item.getAmount()) {
			return start;
		}
		
		// rect
		Rectangle rect = new Rectangle(start.X, start.Y, wide, (int) (item.getAmount() * heightScale));
		rect.setStrokeColor("black");
		rect.setStrokeWidth(2);
		rect.setFillColor("white");
		canvas.add(rect);
		
		// name
		int nameTextY = start.Y + (int) (item.getAmount() * heightScale) / 2;
		if(showNumber || showPercent) {
			nameTextY -= FONT_SIZE_NAME / 2;
		}
		int centerX = start.X - 20;
		Text nameText = TextUtil.generateText(centerX, nameTextY, item.getName(), FONT_SIZE_NAME, "black");
		nameText.setRotation(90);
		canvas.add(nameText);
		
		Point childStart = start;
		for(Item child : item.getChildren()) {
			childStart = drawItem(child, childStart);
		}
		
		return new Point(start.X, (int) (start.Y + item.getAmount() * heightScale));
	}
	
	static NumberFormat fmAmount = NumberFormat.getFormat("###0.###");
	static NumberFormat fmPercent = NumberFormat.getFormat("#0.#");
	
	private Point drawItem(Item item, Point start) {
		
		if(0f == item.getAmount()) {
			return start;
		}
		
		Rectangle rect = new Rectangle(start.X, start.Y, wide, (int) (item.getAmount() * heightScale));
		rect.setStrokeColor("black");
		rect.setStrokeWidth(2);
		rect.setFillColor("white");
		canvas.add(rect);

		// name text
		int nameTextY = start.Y + (int) (item.getAmount() * heightScale) / 2;
		if(showNumber || showPercent) {
			nameTextY -= FONT_SIZE_NAME / 2;
		}
		int centerX = start.X + wide / 2;
		Text nameText = TextUtil.generateText(centerX, nameTextY, item.getName(), FONT_SIZE_NAME, "black");
		canvas.add(nameText);
		
		// number text
		if(showNumber || showPercent) {
			String numberString = "";
			if(showNumber) {
				numberString += fmAmount.format(item.getAmount());
			}
			if(showNumber && showPercent) {
				numberString += " ";
			}
			if(showPercent) {
				numberString += "(" + fmPercent.format(item.getAmount() / total * 100) + "%)";
			}
			
			int numberTextY = start.Y + (int) (item.getAmount() * heightScale) / 2 + FONT_SIZE_NUMBER / 2;
			Text numberText = TextUtil.generateText(centerX, numberTextY, numberString, FONT_SIZE_NUMBER, "black");
			canvas.add(numberText);
		}
		
		return new Point(start.X, (int) (start.Y + item.getAmount() * heightScale));
	}

}
