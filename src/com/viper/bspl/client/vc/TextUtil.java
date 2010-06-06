package com.viper.bspl.client.vc;

import org.vaadin.gwtgraphics.client.shape.Text;

public class TextUtil {
	
	static public Text generateText(int centerX, int centerY, String s, int fontSize, String color) {
		
		int X, Y;
		X = (int) (centerX - stringWidth(s) * fontSize / 4);
		Y = centerY + fontSize / 4;
		
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
