package IO;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;


public interface CSVReadable {
	List<String> getHeaders(BufferedReader reader) throws IOException;
	List<String> convertHeaders(List<String> headers,HashMap<String,String> translationTable);
	List<String> getcolumns(BufferedReader reader) throws IOException;
}
