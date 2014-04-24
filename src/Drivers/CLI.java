package Drivers;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import DB.ForeignKey;
import DB.MatrixSQLParser;
import DB.SimpleSQLParser;
import Facades.ToolFacade;
import IO.MappingConstants;
import IO.XMLParser;
import Models.Model;
import Models.ModelType;

public class CLI {
	
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
		ToolFacade facade = new ToolFacade();
		File file = new File(args[0]);
		XMLParser parser = new XMLParser();
		File cnvDecl = new File("model_definitions/CNVData.xml");
		Model CNVData = new Model(parser.getForeignKeys(cnvDecl),null,parser.getTableName(cnvDecl));
		File matrix_file = new File("matrix_definitions/cnv_matrix.xml");
		
		String dataKeyName = parser.getField(matrix_file, "data-key");
		String verticalKeyName = parser.getField(matrix_file, "vertical-key");
		String horizontalKeyName = parser.getField(matrix_file, "horizontal-key");
		
		MatrixSQLParser sqlParser = new MatrixSQLParser(horizontalKeyName,verticalKeyName, dataKeyName);
		List<String> sql = facade.MatrixToSQL(file, ",", sqlParser, CNVData);
		
		/*List<String> sql = facade.CSVtoSQL(file, ",",new SimpleSQLParser(), new Model(
					foreignKeys,
					mappings, 
					tableName
				));
		*/
		for(String str : sql){
			System.out.println(str);
		}
		
	}
}
