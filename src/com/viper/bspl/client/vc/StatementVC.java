package com.viper.bspl.client.vc;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.viper.bspl.client.data.Item;
import com.viper.bspl.client.data.Statement;

public class StatementVC {
	
	FlexTable dataTable = new FlexTable();
	FlowPanel leftPanel = new FlowPanel();
	FlowPanel rightPanel = new FlowPanel();
	
	ArrayList<BlockVC> leftBlocks = new ArrayList<BlockVC>();
	ArrayList<BlockVC> rightBlocks = new ArrayList<BlockVC>();
	
	private Statement state = null;
	private boolean readonlyMode = false;

	public StatementVC(Statement state, boolean readonlyMode) {
		this.state = state;
		this.readonlyMode = readonlyMode;
		init();
	}
	
	public FlexTable getDataTable() {
		return dataTable;
	}
	
	public Statement getState() {
		return state;
	}

	public void setState(Statement state) {
		this.state = state;
	}

	private void init() {
		
		dataTable.addStyleName("mainTable");
		
		dataTable.setWidget(0, 0, leftPanel);
		dataTable.setWidget(0, 1, rightPanel);
		
		if(null == state) { 
			return;
		}
		
		for(Item item : state.getLeftPart()) {
			BlockVC block = new BlockVC(item, readonlyMode);
			leftBlocks.add(block);
			leftPanel.add(block.getBlockTable());
		}

		for(Item item : state.getRightPart()) {
			BlockVC block = new BlockVC(item, readonlyMode);
			rightBlocks.add(block);
			rightPanel.add(block.getBlockTable());
		}
	}
	
	public void collectDataFromInput() {
		// left
		state.getLeftPart().clear();
		for(BlockVC block : leftBlocks) {
			block.collectDataFromInput();
			state.getLeftPart().add(block.getItem());
		}
		// right
		state.getRightPart().clear();
		for(BlockVC block : rightBlocks) {
			block.collectDataFromInput();
			state.getRightPart().add(block.getItem());
		}
	}
	
	public void reCalculate() {
		for(BlockVC block : leftBlocks) {
			block.reCalculate();
		}
		for(BlockVC block : rightBlocks) {
			block.reCalculate();
		}
	}
	
}
