package DB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import interfaces.SQLAble;
/**
 * 
 * Used for parsing out data matrix files. CNVData, Expression Data, etcs.
 * 
 * For excel files that the 1st row is a set of keys(horizontal-keys)
 * And the 1st column is a nother set of keys (vertical-keys)
 * Their intersection is the associated data (data-key)
 * 
 * A table would exist with a definition of (verticalKeyName,horizontalKeyName,dataKeyName)
 * e.g
 * Sample,   GeneID    Value
 * (SAM4342, ENS12312, .003)
 * 
 * 
 * @author Eric
 *
 */
public class MatrixSQLParser implements SQLAble{
	private String verticalKeyName;
	private String horizontalKeyName;
	private String dataKeyName;
	
	
	public MatrixSQLParser(String horizontalKeyName, String verticalKeyName,String dataKeyName) {
		super();
		this.verticalKeyName = verticalKeyName;
		this.horizontalKeyName = horizontalKeyName;
		this.dataKeyName = dataKeyName;
	}

	@Override
	public String toSql(String tblName,List<String> headers,List<String> columns,
			HashMap<String,ForeignKey> foreignKeys) {
		StringBuilder builder = new StringBuilder();
		builder.append("INSERT INTO ");
		builder.append(tblName);
		builder.append("(");
		builder.append(horizontalKeyName);
		builder.append(",");
		builder.append(verticalKeyName);
		builder.append(",");
		builder.append(dataKeyName);
		builder.append(") VALUES ");
		String verticalKey = columns.get(0);
		for(int i = 1 ; i < headers.size() ; i++){
			String header = headers.get(i);
			String data = columns.get(i);
			
			builder.append("(");
			//header insert, check if we need to do a nested select or direct value
			ForeignKey key_header = foreignKeys.get(this.horizontalKeyName);
			ForeignKey key_vertical = foreignKeys.get(this.verticalKeyName);
			ForeignKey key_data = foreignKeys.get(this.dataKeyName);
			
			if(key_header == null){
				builder.append("'");
				builder.append(header);
				builder.append("'");
			}else{
				builder.append(key_header.toSQL(header));
			}
			builder.append(",");
			if(key_vertical == null){
				builder.append("'");
				builder.append(verticalKey);
				builder.append("'");
			}else{
				builder.append(key_vertical.toSQL(header));
			}
			builder.append(",");
			if(key_data == null){
				builder.append("'");
				builder.append(data);
				builder.append("'");
			}else{
				builder.append(key_data.toSQL(data));
			}
			
			builder.append(")");
			if(i < headers.size()-1){
				builder.append(",");
			}
		}
		builder.append(";");
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
