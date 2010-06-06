package com.viper.bspl.client.graphic;

public class Area {
	
	public Point start, end;

	public Area(Point start, Point end) {
		super();
		this.start = start;
		this.end = end;
	}

	public Point getStart() {
		return start;
	}

	public void setStart(Point start) {
		this.start = start;
	}

	public Point getEnd() {
		return end;
	}

	public void setEnd(Point end) {
		this.end = end;
	}
	
	public int getWidth() {
		return end.X - start.X;
	}
	
	public int getHeight() {
		return end.Y - start.Y;
	}
}
