package com.viper.bspl.client.data;

public class BSStatement extends Statement {
	
	public BSStatement() {
		super(Type.BS);
	}
	
	static public BSStatement getBlankStatement() {
		BSStatement state = new BSStatement();
		
		Item asset = new Item("資産の部", 0, true);
		asset.getChildren().add(new Item("流動資産", 0, true, 10));
		asset.getChildren().add(new Item("固定資産", 0, true, 10));
		state.leftPart.add(asset);
		
		Item liability = new Item("負債の部", 0, true);
		liability.getChildren().add(new Item("流動負債", 0, true, 10));
		liability.getChildren().add(new Item("固定負債", 0, true, 5));
		state.rightPart.add(liability);
		
		Item equity = new Item("資本の部", 0, true);
		equity.getChildren().add(new Item("資本", 0, true, 5));
		state.rightPart.add(equity);
		
		return state;
	}
	
}
