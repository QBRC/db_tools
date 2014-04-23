package interfaces;
import java.util.HashMap;
import java.util.List;

import DB.ForeignKey;

/**
 * Used to convert CSV files to sql
 * @author sroos
 *
 */
public interface SQLAble {
	public String toSql(String tblName,List<String> headers,List<String> columns,HashMap<String,ForeignKey> foreignKeys);
	public List<String> cleanSql(List<String> sql);
}
