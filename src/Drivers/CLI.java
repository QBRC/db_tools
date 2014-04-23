package Drivers;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import DB.ForeignKey;
import DB.SimpleSQLParser;
import Facades.ToolFacade;
import IO.MappingConstants;
import IO.XMLParser;
import Models.Model;
import Models.ModelType;

public class CLI {
	public static void main(String[] args) throws IOException{
		ToolFacade facade = new ToolFacade();
		File file = new File("C:/Users/eric/Documents/merged_for_database_cleaned.csv");
		XMLParser parser = new XMLParser();
		File model_file = new File(ModelType.PATIENT);
		File mapping_file = new File(MappingConstants.PATIENT_TCGA);
		
		HashMap<String, ForeignKey> foreignKeys = parser.getForeignKeys(model_file);
		HashMap<String,String> mappings = parser.getMappings(mapping_file);
		String tableName = parser.getTableName(model_file);
		
		List<String> sql = facade.CSVtoSQL(file, ",",new SimpleSQLParser(), new Model(
					foreignKeys,
					mappings, 
					tableName
				));
		
		for(String str : sql){
			System.out.println(str);
		}
		
	}
}
