package com.viper.bspl.client.vc;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.TextBox;
import com.viper.bspl.client.data.Item;

public class ItemVC {
	
	static public int LEVEL_NORMAL = 100;
	
	static NumberFormat fm = NumberFormat.getFormat("###0.###");
	
	static public void fillToRow(final BlockVC block, Item item, int row, int level, boolean readonlyMode) {
		
		FlexTable table = block.getBlockTable();
		table.addStyleName("blockTable");
		
		// name
		TextBox nameText = new TextBox();
		nameText.setMaxLength(15);
		nameText.setVisibleLength(15);
		nameText.setValue(item.getName());
		if(level < 2 || readonlyMode) {
			nameText.setEnabled(false);
		}
		table.setWidget(row, 0, nameText);
		
		// amount
		TextBox amountText = new TextBox();
		amountText.setMaxLength(10);
		amountText.setVisibleLength(10);
		amountText.setValue(fm.format(item.getAmount()));
		if(item.isAutoCalulate() || readonlyMode) {
			amountText.setEnabled(false);
		} else {
			amountText.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if(event.getCharCode()==KeyCodes.KEY_ENTER) {
						block.reCalculate();
					}
				}
			});
			amountText.addBlurHandler(new BlurHandler() {
				public void onBlur(BlurEvent event) {
					block.reCalculate();
				}
			});
		}
		table.setWidget(row, 1, amountText);
		
		// flag
		if(level < 2) {
			CheckBox cb = new CheckBox("自動計算");
			cb.setValue(item.isAutoCalulate());
			cb.setEnabled(false);
			table.setWidget(row, 2, cb);
		}
		
		// style
		if(level < 2) {
			table.getRowFormatter().addStyleName(row, "bsRowLevel" + level);
			table.getCellFormatter().addStyleName(row, 0, "bsCellLevel" + level);
			table.getCellFormatter().addStyleName(row, 1, "bsCellLevel" + level);
			table.getCellFormatter().addStyleName(row, 1, "cellNumber");
			table.getCellFormatter().addStyleName(row, 2, "bsCellLevel" + level);
		} else {
			table.getRowFormatter().addStyleName(row, "bsRowLevelNormal");
			table.getCellFormatter().addStyleName(row, 0, "bsCellLevelNormal");
			table.getCellFormatter().addStyleName(row, 1, "bsCellLevelNormal");
			table.getCellFormatter().addStyleName(row, 1, "cellNumber");
			table.getCellFormatter().addStyleName(row, 2, "bsCellLevelNormal");
		}
	}
	
	static public String getName(FlexTable table, int row) {
		TextBox nameText = (TextBox)table.getWidget(row, 0);
		return nameText.getText().trim();
	}
	
	static public float getAmount(FlexTable table, int row) {
		TextBox amountText = (TextBox)table.getWidget(row, 1);
		float amount = 0f;
		try {
			amount = Float.parseFloat(amountText.getText().trim());
		} catch (NumberFormatException e) {
		}
		return amount;
	}
	
	static public void setAmount(FlexTable table, int row, float value) {
		TextBox amountText = (TextBox)table.getWidget(row, 1);
		amountText.setValue(fm.format(value));
	}
	
	static public Item getFromRow(FlexTable table, int row) {
		Item item = new Item();
		
		// name
		TextBox nameText = (TextBox) table.getWidget(row, 0);
//		if(nameText.getText().trim().isEmpty()) {
//			return null;
//		}
		item.setName(nameText.getText().trim());
		
		// amount
		TextBox amountText = (TextBox)table.getWidget(row, 1);
//		if(amountText.getText().trim().isEmpty()) {
//			return null;
//		}
		float amount = 0f;
		try {
			amount = Float.parseFloat(amountText.getText().trim());
		} catch (NumberFormatException e) {
		}
		item.setAmount(amount);
		
		// isAutoCalculate
		int level = getRowLevel(table, row);
		if(level < 2) {
			item.setAutoCalulate(true);
		} else {
			item.setAutoCalulate(false);
		}
		
		return item;
	}
	
	static public int getRowLevel(FlexTable table, int row) {
		int level = LEVEL_NORMAL;
		String styleName = table.getRowFormatter().getStyleName(row);
		if(styleName.startsWith("bsRowLevel")) {
			try {
				level = Integer.parseInt(styleName.substring("bsRowLevel".length()));
			} catch (NumberFormatException e) {
			}
		}
		return level;
	}
}
