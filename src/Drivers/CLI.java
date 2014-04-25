package Drivers;

import interfaces.SQLAble;

import java.io.File;
import java.io.IOException;
import java.util.List;

import DB.MatrixSQLParser;
import DB.SimpleSQLParser;
import Facades.ToolFacade;
import Models.Model;

public class CLI {
	
	public CLI(){
		
	}
	
	public List<String> convertMatrixtoSql(String inputPath,String modelDefintion, String matrixDefinition,String delimeter) throws IOException{
		File input_file = new File(inputPath);
		File model_file = new File(modelDefintion);
		File matrix_file = new File(matrixDefinition);
		SQLAble strategy = new MatrixSQLParser(matrix_file);
		
		Model model = new Model(model_file,null);
		
		return ToolFacade.MatrixToSQL(input_file, delimeter, strategy, model);
	}
	
	public List<String> convertCSVtoSQL(String inputPath,String modelDefinition, String mappingDefinition,String delimeter) throws IOException{
		File input_file = new File(inputPath);
		File model_file = new File(modelDefinition);
		File mapping_file = new File(mappingDefinition);
		SQLAble strategy = new SimpleSQLParser();
		Model model = new Model(model_file,mapping_file);
		
		return ToolFacade.CSVtoSQL(input_file, delimeter, strategy, model);	
	}
	/**
	 * ENTRY POINT FOR APPLICATION
	 * 
	 * Uses the ToolFacade to get functionality for the application
	 * The input file is passed in by cmd arg-0
	 * Use the XML Parser to read bits you need from the input file
	 * 
	 * Declare a model to hold foreign key/mapping/table definitions
	 * Some default xml files are loaded into the mappings/matrix_definitions/model_Definition 
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
		CLI intf = new CLI();
		List<String> sql = null;
		String action = args[0];
		String input_file = args[1];
		String model_file = args[2];
		String definition_file = args[3];
		String delimeter = args[4];
		
		
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
