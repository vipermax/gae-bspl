package com.viper.bspl.client;

import com.google.gwt.user.client.ui.FlexTable;

public class Block {
	
	private FlexTable blockTable = new FlexTable();
	private Item item = null;
	
	public FlexTable getBlockTable() {
		return blockTable;
	}
	
	public Block(Item item) {
		this.item = item;
		init();
	}

	private void init() {
		if(null == item) {
			return;
		}
		blockTable.setText(0, 0, item.getName());
		blockTable.setText(0, 1, Float.toString(item.getAmount()));
		blockTable.setText(0, 2, Boolean.toString(item.isAutoCalulate()));
	}
	
}
