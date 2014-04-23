package Models;
import java.util.HashMap;

import DB.ForeignKey;


public class Model {
	protected HashMap<String,ForeignKey> foreignKeys;
	protected HashMap<String,String> translationTable;
	protected String tableName;

	public Model(HashMap<String, ForeignKey> foreignKeys,
			HashMap<String, String> translationTable, String tableName) {
		super();
		this.foreignKeys = foreignKeys;
		this.translationTable = translationTable;
		this.tableName = tableName;
	}



	public HashMap<String, ForeignKey> getForeignKeys() {
		return foreignKeys;
	}



	public void setForeignKeys(HashMap<String, ForeignKey> foreignKeys) {
		this.foreignKeys = foreignKeys;
	}



	public HashMap<String, String> getTranslationTable() {
		return translationTable;
	}



	public void setTranslationTable(HashMap<String, String> translationTable) {
		this.translationTable = translationTable;
	}



	public String getTableName() {
		return tableName;
	}



	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	

}
