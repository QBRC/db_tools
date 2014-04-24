package DB;

/**
 * 
 * Used to store information about foreign keys for a model
 * 
 * @author Eric Roos
 *
 */
public class ForeignKey{
	/**
	 * 
	 * The table the foreign key references
	 * SQL: ....* REFERENCES(#{table})
	 */
	private String table;
	
	/**
	 * The column in there WHERE clause
	 * SQL: ...* WHERE #{matchColumn} = #{value}
	 * 
	 */
	private String matchColumn;
	
	/**
	 * 
	 * The column in the select clause
	 * SQL: SELECT #{retCol} ....*
	 */
	private String retCol;
	
	public ForeignKey(String retCol,String table, String columm) {
		super();
		this.retCol = retCol;
		this.table = table;
		this.matchColumn = columm;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getMatchColumn() {
		return matchColumn;
	}

	public void setMatchColumn(String columm) {
		this.matchColumn = columm;
	}
	
	public String toSQL(String value){
		return "(SELECT " + retCol + " FROM " + table + " WHERE " + matchColumn + "='"+value+"' LIMIT 1)";
	}

}
