import java.util.List;

/**
 * Used to convert CSV files to sql
 * @author sroos
 *
 */
public interface SQLAble {
	public String toSql(String tblName,List<String> headers,List<String> columns);
	public List<String> cleanSql(List<String> sql);
}
