package IO;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Interface used for different readers
 * May be excessive to be split into an interface at this
 * point but, may in the future have a use.
 * 
 * @author Eric Roos
 *
 */
public interface CSVReadable {
	List<String> getHeaders(BufferedReader reader) throws IOException;
	List<String> convertHeaders(List<String> headers,HashMap<String,String> translationTable);
	List<String> getcolumns(BufferedReader reader) throws IOException;
}
