package com.viper.bspl.client.vc;

import com.google.gwt.user.client.ui.FlexTable;
import com.viper.bspl.client.data.Item;

public class BlockVC {
	
	private FlexTable blockTable = new FlexTable();
	private Item item = null;
	private boolean readonlyMode = false;
	
	public FlexTable getBlockTable() {
		return blockTable;
	}
	
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public BlockVC(Item item, boolean readonlyMode) {
		this.item = item;
		this.readonlyMode = readonlyMode;
		init();
	}

	private void init() {
		if(null == item) {
			return;
		}
		
		fillToTable(item, 0, 0);
	}
	
	public int fillToTable(Item item, int row, int level) {
		ItemVC.fillToRow(this, item, row, level, readonlyMode);
		int rowCount = 1;
		for(Item child : item.getChildren()) {
			rowCount += fillToTable(child, row+rowCount, level+1);
		}
		return rowCount;
	}
	
	public void collectDataFromInput() {
		int level = ItemVC.getRowLevel(blockTable, 0);
		item = ItemVC.getFromRow(blockTable, 0);
		if(null != item) {
			collectDataFromInput222(item, level, 0);
			item.getAmount();
		}
	}
	
	private int collectDataFromInput222(Item parentItem, int parentLevel, int parentRow) {
		int row = parentRow + 1;
		while(row < blockTable.getRowCount()) {
			int level = ItemVC.getRowLevel(blockTable, row);
			Item next = ItemVC.getFromRow(blockTable, row);
			if(level > parentLevel) {
				parentItem.getChildren().add(next);
				row += collectDataFromInput222(next, level, row);
				row++;
			} else {
				row--;
				break;
			}
		}
		return row - parentRow;
	}
	
	public void reCalculate() {
		reCalculate(this.item, 0);
	}
	
	public int reCalculate(Item item, int row) {
		int count = 0;
		if(item.isAutoCalulate()) {
			count++;
			for(Item child : item.getChildren()) {
				count += reCalculate(child, row + count);
			}
			ItemVC.setAmount(blockTable, row, item.getAmount());
		} else {
			item.setName(ItemVC.getName(blockTable, row));
			item.setAmount(ItemVC.getAmount(blockTable, row));
			count++;
		}
		return count;
	}

}
