package Models;
import java.util.HashMap;

import DB.ForeignKey;

/**
 * 
 * Used as a representation of a database table
 * 
 * @author Eric
 *
 */
public class Model {
	/**
	 * Defines a mapping of foreign keys in the table
	 * The key of the hash represents the name of the column 
	 * in the model in question that maps to a ForeignKey object
	 * 
	 * eg. 
	 * Pateints table.
	 * hash Pat_Histology to a Foreign Key in the Histology table
	 * 
	 */
	protected HashMap<String,ForeignKey> foreignKeys;
	
	/**
	 * Defines the translation between the csv files
	 * and the database table
	 * e.g
	 * CSV files has column OASS which is mapped to Pat_Overal_Survival_Status
	 */
	protected HashMap<String,String> translationTable;
	
	/**
	 * The name of the table this model represents
	 */
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
