package com.viper.bspl.client.data;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

public class BSPLPairContainer implements XMLSerializable {
	
	Statement bsState = null;
	Statement plState = null;
	
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
	public void parseFromXML(Element elem) {
		bsState = new BSStatement();
		plState = new PLStatement();
		if(elem.getNodeName() == "BSPLPair") {
			NodeList children = elem.getElementsByTagName("Statement");
			for(int i = 0; i < children.getLength(); i++) {
				Element child = (Element) children.item(i);
				String type = child.getAttribute("type");
				if(type == "BS") {
					bsState.parseFromXML(child);
				} else if(type == "PL") {
					plState.parseFromXML(child);
				}
			}
		}
	}
	
	@Override
	public Element serializeToXML(Document document) {
		Element retElem = document.createElement("BSPLPair");
		if(null != bsState) {
			retElem.appendChild(bsState.serializeToXML(document));
		}
		if(null != plState) {
			retElem.appendChild(plState.serializeToXML(document));
		}
		return retElem;
	}
	
	public String serializeToXMLString() {
		Document document = XMLParser.createDocument();
		document.appendChild(serializeToXML(document));
		return document.toString();
	}
	
}
