

public class ForeignKey{
	private String table;
	private String column;

	private String retCol;
	
	public ForeignKey(String retCol,String table, String columm) {
		super();
		this.retCol = retCol;
		this.table = table;
		this.column = columm;

	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String columm) {
		this.column = columm;
	}
	
	public String toSQL(String value){
		return "(SELECT " + retCol + " FROM " + table + " WHERE " + column + "='"+value+"' LIMIT 1)";
	}

}
