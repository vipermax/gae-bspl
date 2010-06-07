package com.viper.bspl.client.data;


public class PLStatement extends Statement {

	public PLStatement() {
		super(Type.PL);
	}
	
	static public PLStatement getBlankStatement() {
		PLStatement state = new PLStatement();
		
		Item expense = new Item("費用と利益", 0, true);
		expense.getChildren().add(new Item("費用", 0, true, 10));
		expense.getChildren().add(new Item("利益", 0, true, 3));
		state.leftPart.add(expense);
		
		Item revenue = new Item("収入", 0, true);
		revenue.getChildren().add(new Item("収入", 0, true, 5));
		state.rightPart.add(revenue);
		
		return state;
	}

}
