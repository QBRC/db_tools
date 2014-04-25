package Drivers;

import java.io.IOException;
import java.util.List;

public class CLI {
	
	
	/**
	 * ENTRY POINT FOR APPLICATION
	 * 
	 * Uses the ToolFacade to get functionality for the application
	 * The input file is passed in by cmd arg-0
	 * Use the XML Parser to read bits you need from the input file
	 * 
	 * Declare a model to hold foreign key/mapping/table definitions
	 * Some default XML files are loaded into the mappings/matrix_definitions/model_Definition 
	 * folder. To see how these are defined, check those out.
	 * 
	 * Use the appropriate parser in the ToolFacade functions.
	 * 	-SimpleSQLParser
	 * 	-MatrixSQLParser
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException{
		DriverTools intf = new DriverTools();
		
		List<String> sql = null;
		String action = args[0];
		String input_file = args[1];
		String model_file = args[2];
		String definition_file = null;
		String delimeter = null;
		try{
			definition_file = args[3];
			delimeter = args[4];
		}catch(ArrayIndexOutOfBoundsException e){
			
		}
		
		if(action.compareTo("simple") == 0){ //simple
			sql = intf.convertCSVtoSQL(input_file, 
					model_file,
					definition_file,
					delimeter);
			
		}else if(action.compareTo("matrix") == 0){ //matrix
			sql = intf.convertMatrixtoSql(input_file, 
					model_file,
					definition_file,
					delimeter);
		}else if(action.compareTo("diff") == 0){
			delimeter = args[2];
			sql = intf.findDifferences(input_file, delimeter);
		}else{
			System.err.println("Unknown action.");	
		}
		
		if(sql != null){
			for(String str : sql){
				System.out.println(str);
			}
		}
				
	}
}
