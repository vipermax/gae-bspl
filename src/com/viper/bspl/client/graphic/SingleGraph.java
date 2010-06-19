package com.viper.bspl.client.graphic;

import org.vaadin.gwtgraphics.client.DrawingArea;
import org.vaadin.gwtgraphics.client.shape.Path;
import org.vaadin.gwtgraphics.client.shape.Rectangle;
import org.vaadin.gwtgraphics.client.shape.Text;

import com.google.gwt.i18n.client.NumberFormat;
import com.viper.bspl.client.data.Item;
import com.viper.bspl.client.data.Statement;
import com.viper.bspl.client.vc.DrawUtil;
import com.viper.bspl.client.vc.DrawUtil.ALIGN;

public class SingleGraph extends BaseGraph {

	int FONT_SIZE_TITLE = 16;
	int FONT_SIZE_BLOCK_NAME = 16;
	int FONT_SIZE_NAME = 12;
	int FONT_SIZE_NUMBER = 8;
	private int headerHeight = 30;
	
	public Statement state = null;
	
	public SingleGraph(DrawingArea canvas, Statement state) {
		super(canvas);
		this.state = state;
	}
	
	Area drawArea = null;
	double hwRate = 1.5f;
	double heightScale = 1f;
	int wide = 100;
	float graphTotal = 0f;
	float selfTotal;
	boolean drawCompanyName = false;
	private String companyName = "";
	private String year = "";
	
	public boolean isDrawCompanyName() {
		return drawCompanyName;
	}

	public void setDrawCompanyName(boolean drawCompanyName) {
		this.drawCompanyName = drawCompanyName;
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

	public void setYear(String year) {
		this.year = year;
	}

	public float getGraphTotal() {
		return graphTotal;
	}

	public void setGraphTotal(float graphTotal) {
		this.graphTotal = graphTotal;
	}
	
	@Override
	public void reDraw() {
		
		// drawing area
		drawArea = getDrawingArea();
		
		if(drawCompanyName) {
			drawArea.start.Y += headerHeight;
		}
		
		selfTotal = state.getTotal();
		if(0 == graphTotal) {
			graphTotal = Math.max(state.getLeftTotal(), state.getRightTotal());
		}
		heightScale = (double)drawArea.getHeight() / graphTotal;
		wide = (int) (graphTotal * heightScale * 0.3);
		
		float fontRate = drawArea.getHeight() / 400;
		FONT_SIZE_BLOCK_NAME = (int) (FONT_SIZE_BLOCK_NAME * fontRate);
		FONT_SIZE_NAME = (int) (FONT_SIZE_NAME * fontRate);
		FONT_SIZE_NUMBER = (int) (FONT_SIZE_NUMBER * fontRate);
		
		// drawCompanyName
		if(drawCompanyName) {
			drawHeader();
		}
		
		// drawTitle
		drawTitle();
		
		// drawStatement
		drawStatement();
	}
	
	private void drawTitle() {
		// title
		int titleTextY = drawArea.start.Y - FONT_SIZE_TITLE;
		int centerX = (drawArea.start.X + drawArea.end.X) / 2;
		Text titleText = DrawUtil.generateText(centerX, titleTextY, title, FONT_SIZE_TITLE, "black", ALIGN.center);
		canvas.add(titleText);
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

	private void drawStatement() {
		Point start = new Point(0, 0);
		// left
		start.X = (drawArea.start.X + drawArea.end.X) / 2 - wide;
		start.Y = drawArea.start.Y;
		for(Item item : state.getLeftPart()) {
			start = drawTopLevel(item, start, false);
		}
		// right
		start.X = (drawArea.start.X + drawArea.end.X) / 2;
		start.Y = drawArea.start.Y;
		for(Item item : state.getRightPart()) {
			start = drawTopLevel(item, start, true);
		}
	}
	
	private Point drawTopLevel(Item item, Point start, boolean rightSide) {
		
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
			childStart = drawBlock(child, childStart, rightSide);
		}
		
		return new Point(start.X, (int) (start.Y + item.getAmount() * heightScale));
	}
	
	private Point drawBlock(Item item, Point start, boolean rightSide) {

		if(0f == item.getAmount()) {
			return start;
		}
		
		// rect
		Rectangle rect = new Rectangle(start.X, start.Y, wide, (int) (item.getAmount() * heightScale));
		rect.setStrokeColor("black");
		rect.setStrokeWidth(1);
		rect.setFillColor("white");
		canvas.add(rect);
		
		// name
		int nameTextY = start.Y + (int) (item.getAmount() * heightScale) / 2 - 1;
		if(showNumber || showPercent) {
			nameTextY -= FONT_SIZE_NAME / 2;
		}
		int centerX;
		ALIGN align = ALIGN.right;
		if(rightSide) {
			align = ALIGN.left;
			centerX = start.X + wide + 20;
		} else {
			centerX = start.X - 20;
		}
		Text nameText = DrawUtil.generateText(centerX, nameTextY, item.getName(), FONT_SIZE_BLOCK_NAME, "black", align);
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
				numberString += "(" + fmPercent.format(item.getAmount() / selfTotal * 100) + "%)";
			}
			
			int numberTextY = start.Y + (int) (item.getAmount() * heightScale) / 2 + FONT_SIZE_NUMBER / 2 + 1;
			Text numberText = DrawUtil.generateText(centerX, numberTextY, numberString, FONT_SIZE_NUMBER + 2, "black", align);
			canvas.add(numberText);
		}
		
		// bracket
		Point brackStart = new Point(0, 0);
		Point brackEnd = new Point(0, 0);
		int brackMargin = 5;
		if(rightSide) {
			brackStart = new Point(start.X + wide + brackMargin, start.Y);
			brackEnd = new Point(start.X + wide + 20 - brackMargin, (int) (start.Y + item.getAmount() * heightScale));
		} else {
			brackStart = new Point(start.X - brackMargin, start.Y);
			brackEnd = new Point(start.X - 20 + brackMargin, (int) (start.Y + item.getAmount() * heightScale));
		}
		Path bracketPath = DrawUtil.generateBracket(brackStart, brackEnd);
		canvas.add(bracketPath);
		
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
		rect.setStrokeWidth(1);
		rect.setFillColor("white");
		canvas.add(rect);

		// name text
		if(item.getAmount() * heightScale > FONT_SIZE_NAME / 2) {
			int nameTextY = start.Y + (int) (item.getAmount() * heightScale) / 2;
			if(showNumber || showPercent) {
				nameTextY -= FONT_SIZE_NAME / 2;
			}
			int centerX = start.X + wide / 2;
			Text nameText = DrawUtil.generateText(centerX, nameTextY, item.getName(), FONT_SIZE_NAME, "black", ALIGN.center);
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
					numberString += "(" + fmPercent.format(item.getAmount() / selfTotal * 100) + "%)";
				}
				
				int numberTextY = start.Y + (int) (item.getAmount() * heightScale) / 2 + FONT_SIZE_NUMBER / 2;
				Text numberText = DrawUtil.generateText(centerX, numberTextY, numberString, FONT_SIZE_NUMBER, "black", ALIGN.center);
				canvas.add(numberText);
			}
		}
		
		return new Point(start.X, (int) (start.Y + item.getAmount() * heightScale));
	}

}
