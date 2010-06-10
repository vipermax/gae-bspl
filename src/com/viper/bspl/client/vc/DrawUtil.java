package com.viper.bspl.client.vc;

import org.vaadin.gwtgraphics.client.shape.Path;
import org.vaadin.gwtgraphics.client.shape.Text;

import com.viper.bspl.client.graphic.Point;

public class DrawUtil {
	
	public enum ALIGN {
		left,
		center,
		right,
	}
	
	static public Path generateBracket(Point start, Point end) {
		
		if(end.Y - start.Y > Math.abs(end.X - start.X) * 3) {
			int wide = (end.X - start.X) / 2;
			
			Path path = new Path(start.X, start.Y);
			path.lineTo(start.X + wide, start.Y + Math.abs(wide));
			path.lineTo(start.X + wide, (start.Y + end.Y) / 2 - Math.abs(wide));
			path.lineTo(end.X, (start.Y + end.Y) / 2);
			path.lineTo(start.X + wide, (start.Y + end.Y) / 2 + Math.abs(wide));
			path.lineTo(start.X + wide, end.Y - Math.abs(wide));
			path.lineTo(start.X, end.Y);
			
			path.setStrokeColor("black");
			path.setStrokeWidth(1);
			path.setFillOpacity(1.0f);
			
			return path;
		} else {
			int direction = (end.X - start.X) / Math.abs((end.X - start.X));
			Path path = new Path(end.X, (start.Y + end.Y) / 2);
			path.lineTo(start.X, (start.Y + end.Y) / 2);
			path.lineTo(start.X + direction*2, (start.Y + end.Y) / 2 - direction*2);
			path.moveTo(start.X, (start.Y + end.Y) / 2);
			path.lineTo(start.X + direction*2, (start.Y + end.Y) / 2 + direction*2);
			return path;
		}
	}
	
	static public Text generateText(int baseX, int baseY, String s, int fontSize, String color, ALIGN align) {
		
		int X, Y;
		X = baseX;
		if(align == ALIGN.center) {
			X -= (int)(stringWidth(s) * fontSize / 4);
		} else if (align == ALIGN.right) {
			X -= (int)(stringWidth(s) * fontSize / 2);
		}
		
		Y = baseY + fontSize / 4;
		
		Text text = new Text(X, Y, s);
		text.setStrokeWidth(0);
		text.setFillColor(color);
		text.setFontSize(fontSize);
		
		return text;
	}
	
	static private int stringWidth(String text) {
		int ret = 0;
		for(int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if( c <= '\u007e' ||
				c == '\u00a5' ||
				c == '\u203e' ||
				( c >= '\uff61' && c <= '\uff9f' )
			  ) {
				ret += 1;
			} else {
				ret += 2;
			}
		}
		return ret;
	}
	
}
