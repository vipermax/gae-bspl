package com.viper.bspl.client.data;

import java.util.ArrayList;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;

public class Item implements XMLSerializable {
	
	public enum MODE {
		BLOCK_HEADER,
		BLOCK_ITEM,
	}
	
	private String name = "";
	private float amount = 0.0f;
	private boolean isAutoCalulate = false;
	private ArrayList<Item> children = new ArrayList<Item>();
	
	public Item() {
	}
	
	public Item(String name, float amount, boolean isAutoCalulate) {
		this.name = name;
		this.amount = amount;
		this.isAutoCalulate = isAutoCalulate;
	}
	
	public Item(String name, float amount, boolean isAutoCalulate, int blankChildCount) {
		this.name = name;
		this.amount = amount;
		this.isAutoCalulate = isAutoCalulate;
		addBlankChildren(blankChildCount);
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getAmount() {
		if(isAutoCalulate && children.size() > 0) {
			float sum = 0.0f;
			for(Item child : children) {
				sum += child.getAmount();
			}
			amount = sum;
			return amount;
		} else {
			return amount;
		}
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public boolean isAutoCalulate() {
		return isAutoCalulate;
	}
	public void setAutoCalulate(boolean isAutoCalulate) {
		this.isAutoCalulate = isAutoCalulate;
	}
	public ArrayList<Item> getChildren() {
		return children;
	}
	public void setChildren(ArrayList<Item> children) {
		this.children = children;
	}
	
	public void addBlankChildren(int count) {
		for(int i = 0; i < count; i++) {
			children.add(new Item("", 0f, false));
		}
	}

	@Override
	public String toString() {
		String ret = "[" + name + "," + amount + "," + isAutoCalulate + "]¥n";
		for(Item child : children) {
			ret = ret + child.toString();
		}
		return ret;
	}

	@Override
	public void parseFromXML(Element elem) {
		name = elem.getAttribute("name");
		amount = Float.parseFloat(elem.getAttribute("amount"));
		isAutoCalulate = Boolean.parseBoolean(elem.getAttribute("isAutoCalulate"));
		
		children = new ArrayList<Item>();
		NodeList leftChildren = elem.getElementsByTagName("Item");
		for(int i = 0; i < leftChildren.getLength(); i++) {
			Element childElem = (Element) leftChildren.item(i);
			Item item = new Item();
			item.parseFromXML(childElem);
			children.add(item);
		}

	}

	@Override
	public Element serializeToXML(Document document) {
		Element retElem = document.createElement("Item");
		retElem.setAttribute("name", name);
		retElem.setAttribute("amount", Float.toString(amount));
		retElem.setAttribute("isAutoCalulate", Boolean.toString(isAutoCalulate));
		for(Item child: children) {
			retElem.appendChild(child.serializeToXML(document));
		}
		return retElem;
	}
	
}
