package com.viper.bspl.client.data;

import java.util.ArrayList;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;


public class Statement implements XMLSerializable {
	
	public enum Type {
		BS,
		PL,
	}
	
	protected Type type;
	protected ArrayList<Item> leftPart = new ArrayList<Item>();
	protected ArrayList<Item> rightPart = new ArrayList<Item>();
	
	public Statement(Type type) {
		this.type = type;
	}
	
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public ArrayList<Item> getLeftPart() {
		return leftPart;
	}
	public void setLeftPart(ArrayList<Item> leftPart) {
		this.leftPart = leftPart;
	}
	public ArrayList<Item> getRightPart() {
		return rightPart;
	}
	public void setRightPart(ArrayList<Item> rightPart) {
		this.rightPart = rightPart;
	}
	
	public float getLeftTotal() {
		float sum = 0f;
		for(Item item : leftPart) {
			sum += item.getAmount();
		}
		return sum;
	}
	
	public float getRightTotal() {
		float sum = 0f;
		for(Item item : rightPart) {
			sum += item.getAmount();
		}
		return sum;
	}
	
	public float getTotal() {
		return Math.max(getLeftTotal(), getRightTotal());
	}

	@Override
	public void parseFromXML(Element elem) {
		String type = elem.getAttribute("type");
		if(type.equals("BS")) {
			this.type = Type.BS;
		} else if (type.equals("PL")) {
			this.type = Type.PL;
		}
		leftPart = new ArrayList<Item>();
		rightPart = new ArrayList<Item>();
		
		Element leftListElem = (Element) elem.getElementsByTagName("LeftList").item(0);
		NodeList leftChildren = leftListElem.getChildNodes();
		for(int i = 0; i < leftChildren.getLength(); i++) {
			Element childElem = (Element) leftChildren.item(i);
				if(childElem.getNodeName().equals("Item")) {
				Item item = new Item();
				item.parseFromXML(childElem);
				leftPart.add(item);
			}
		}

		Element rightListElem = (Element) elem.getElementsByTagName("RightList").item(0);
		NodeList rightChildren = rightListElem.getChildNodes();
		for(int i = 0; i < rightChildren.getLength(); i++) {
			Element childElem = (Element) rightChildren.item(i);
				if(childElem.getNodeName().equals("Item")) {
				Item item = new Item();
				item.parseFromXML(childElem);
				rightPart.add(item);
			}
		}
	}

	@Override
	public Element serializeToXML(Document document) {
		Element retElem = document.createElement("Statement");
		if(type == Type.BS) {
			retElem.setAttribute("type", "BS");
		} else if(type == Type.PL) {
			retElem.setAttribute("type", "PL");
		}
		Element leftListElem = document.createElement("LeftList");
		Element rightListElem = document.createElement("RightList");
		retElem.appendChild(leftListElem);
		retElem.appendChild(rightListElem);
		
		for(Item item : leftPart) {
			leftListElem.appendChild(item.serializeToXML(document));
		}
		
		for(Item item : rightPart) {
			rightListElem.appendChild(item.serializeToXML(document));
		}

		return retElem;
	}
}
