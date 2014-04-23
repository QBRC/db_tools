import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SimpleSQLParser implements SQLAble{
	private HashMap<String,ForeignKey> foreignKeys;
	
	public SimpleSQLParser(){
		foreignKeys = new HashMap<String,ForeignKey>();
	}
	
	public SimpleSQLParser(HashMap<String, ForeignKey> foreignKeys) {
		super();
		this.foreignKeys = foreignKeys;
	}

	public void addForeignKey(String column, ForeignKey key){
		foreignKeys.put(column, key);
	}
	
	@Override
	public String toSql(String tblName,List<String> headers,List<String> columns) {
		StringBuilder builder = new StringBuilder();
		builder.append("INSERT INTO ");
		builder.append(tblName);
		builder.append("(");
		for(int i = 0 ; i < headers.size() ; i++){
			builder.append(headers.get(i));
			if(i < headers.size()-1){
				builder.append(",");
			}
		}
		builder.append(") VALUES(");
		for(int i = 0 ; i < headers.size() ; i++){
			ForeignKey key = foreignKeys.get(headers.get(i));
			if(key == null){
				builder.append("'");
				builder.append(columns.get(i));
				builder.append("'");
			}else{
				builder.append(key.toSQL(columns.get(i)));
			}
			if(i < headers.size()-1){
				builder.append(",");
			}
		}
		builder.append(");");
		return builder.toString();
	}

	@Override
	public List<String> cleanSql(List<String> sql) {
		List<String> ret = new ArrayList<String>();
		for(String str : sql){
			str = str.replace("''", "null");
			str = str.replace("'FALSE'", "0");
			str = str.replace("'TRUE'", "1");
			str = str.replace("'true'", "0");
			str = str.replace("'false'", "1");
			str = str.replace("'N'", "0");
			str = str.replace("'Y'", "1");
			str = str.replace("'NA'", "null");
			ret.add(str);
			
		}
		return ret;
	}


}
