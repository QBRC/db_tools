import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CSVReader implements CSVReadable{
	private String delimeter;
	
	public CSVReader(String delimeter){
		super();
		this.delimeter = delimeter;

	}

	@Override
	public List<String> getHeaders(BufferedReader reader) throws IOException {
		String header_line = reader.readLine();
		String[] headers = header_line.split(delimeter);
		List<String> headers_list = new ArrayList<String>();
		for(String s: headers){
			headers_list.add(s);
		}
		return headers_list;
	}
	@Override
	public List<String> convertHeaders(List<String> headers,HashMap<String,String> translationTable) {
		List<String> converted = new ArrayList<String>();
		for(String s : headers){
			String translated = translationTable.get(s);
			if(translated != null){
				converted.add(translated);
			}else{
				throw new NoSuchFieldError("Cannot find a translation for: " + s);
			}
		}
		return converted;
	}
	@Override
	public List<String> getcolumns(BufferedReader reader) throws IOException {
		List<String> columns = null;
		String line = reader.readLine();
		if(line != null){
			columns = new ArrayList<String>();
			String[] str_cols = line.split(delimeter);
			for(String str : str_cols){
				columns.add(str);
			}
		}
		return columns;
	}
}
