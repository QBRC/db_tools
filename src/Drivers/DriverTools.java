package Drivers;

import interfaces.SQLAble;

import java.io.File;
import java.io.IOException;
import java.util.List;

import DB.MatrixSQLParser;
import DB.SimpleSQLParser;
import Facades.ToolFacade;
import Models.Model;

public class DriverTools {
	
	public DriverTools(){
		
	}
	
	/**
	 * Used to determine a difference in columns in a CSV File
	 * Use the first column as a basis for comparison 
	 * 
	 * e.g
	 * 
	 * a	| a		| a
	 * b	| c		| b
	 * d	| d		| e
	 * f	| b		|
	 * 
	 * Output would be
	 * 
	 * (COL2 UNION COL3) - COL1
	 * = ((a,c,d,b) UNION (a,b,e)) - (a,b,d,f)
	 * = (a,b,c,d,e) - (a,b,d,f)
	 * = (c,e)
	 * 
	 * 
	 * Can be used as a verification before inserting into the database
	 * to ensure that we 
	 * 
	 * @param inputPath path to the CSV file for determining differences
	 * @param delimeter - the delimiter in the file, usually tab or comma
	 * @return (UNION of Columns 2..N) - COL1, see example above
	 * 
	 * @throws IOException if we can't read the file
	 */
	public List<String> findDifferences(String inputPath,String delimeter) throws IOException{
		File input_file = new File(inputPath);
		return ToolFacade.GetMissingDependencies(input_file, delimeter);	
	}
	
	/**
	 * 
	 * Used to convert Matrix CSV to SQL
	 * 
	 * GID	| SAM0001	| SAM0002	| SAM0003
	 * ----------------------------------------
	 * G1	| .009		| .023		| .122
	 * G2	| .13		| .009		| .23
	 * G3	| .1114		| .343		| .990
	 * 
	 * Would create 3 insert statements, but 9 records to be inserted
	 * with value tuples:
	 * 
	 * INSERT INTO *..... * VALUES (SAM001,G1,.009),(SAM002,G1,.023),(SAM003,G1,.112)
	 * INSERT INTO *..... * VALUES (SAM001,G2,.13),(SAM002,G2,.009),(SAM003,G2,.23)
	 * INSERT INTO *..... * VALUES (SAM001,G3,.1114),(SAM002,G3,.343),(SAM003,G3,.990)
	 * 
	 * 
	 * @param inputPath - the CSV file we are converting
	 * @param modelDefintion - the XML file that defines the
	 *  	table we are inserting into
	 * @param matrixDefinition - the XML file that defines 
	 * 		the names of the vertical-keys (row0) and the horizontal-keys(column 0)
	 *  	and the name of the data-keys
	 * @param delimeter - delimiter the delimiter of the CSV file, will typically 
	 * 		be a comma or tab
	 * @return a list of SQL statements that represent the entire CSV file on the inputPath
	 * @throws IOException IOException if there is ANY configuration problem. Invalid files. Incorrectly formated File. 
	 * 		Output will be verbose enough to determine the error
	 */
	public List<String> convertMatrixtoSql(String inputPath,String modelDefintion, String matrixDefinition,String delimeter) throws IOException{
		File input_file = new File(inputPath);
		File model_file = new File(modelDefintion);
		File matrix_file = new File(matrixDefinition);
		SQLAble strategy = new MatrixSQLParser(matrix_file);
		Model model = new Model(model_file,null);
		return ToolFacade.MatrixToSQL(input_file, delimeter, strategy, model);
	}
	/**
	 * Used to convert a CSV into SQL with basic setup
	 * 
	 * Header1	| Headder2	| Header3
	 * a		| b			| c
	 * d		| e			| f
	 * 
	 * Would create 2 insert statements for columns Header1,Header2,and Header 3 with 
	 * value tuples:
	 * (a,b,c)
	 * (d,e,f)
	 * 
	 * @param inputPath - the CSV file we are converting
	 * @param modelDefinition - the XML file that defines the
	 *  	table we are inserting into
	 * @param mappingDefinition - the XML file that defines
	 *  	the mapping between CSV columns and DB columns
	 * @param delimeter the delimiter of the CSV file, will typically 
	 * 		be a comma or tab
	 * @return a list of SQL statements that represent the entire CSV file on the inputPath
	 * @throws IOException if there is ANY configuration problem. Invalid files. Incorrectly formated File. 
	 * 		Output will be verbose enough to determine the error
	 */
	public List<String> convertCSVtoSQL(String inputPath,String modelDefinition, String mappingDefinition,String delimeter) throws IOException{
		File input_file = new File(inputPath);
		File model_file = new File(modelDefinition);
		File mapping_file = new File(mappingDefinition);
		SQLAble strategy = new SimpleSQLParser();
		Model model = new Model(model_file,mapping_file);
		
		return ToolFacade.CSVtoSQL(input_file, delimeter, strategy, model);	
	}
}
