package org.mahjong.matoso.bean;

import java.util.Set;

public class Round {
	private int number;
	private Set<Table> tables;

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Set<Table> getTables() {
		return tables;
	}

	public void setTables(Set<Table> listTables) {
		this.tables = listTables;
	}
}
