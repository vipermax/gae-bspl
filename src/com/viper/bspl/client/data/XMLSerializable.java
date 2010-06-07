package com.viper.bspl.client.data;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;

public interface XMLSerializable {
	
	public Element serializeToXML(Document document);
	public void parseFromXML(Element elem);
	
}
