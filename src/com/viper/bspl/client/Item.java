package com.viper.bspl.client;

import java.util.ArrayList;

public class Item {
	
	private String name = "";
	private float amount = 0.0f;
	private boolean isAutoCalulate = false;
	private ArrayList<Item> children = new ArrayList<Item>();
	
	public Item(String name, float amount, boolean isAutoCalulate) {
		this.name = name;
		this.amount = amount;
		this.isAutoCalulate = isAutoCalulate;
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
			return sum;
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
	
}
