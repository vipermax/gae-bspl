package com.viper.bspl.client.data;

import java.util.ArrayList;


public class Statement {
	
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
}
