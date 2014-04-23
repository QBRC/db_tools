package DB;


public class ForeignKey{
	private String table;
	private String matchColumn;
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
