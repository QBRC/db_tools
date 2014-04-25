package Facades;
import interfaces.SQLAble;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import DB.ForeignKey;
import IO.CSVReader;
import Models.Model;

public class ToolFacade {
	
	private ToolFacade(){
		
	}
	/**
	 * 
	 * Used to get the difference between columns in a CSV file
	 * in order to see which keys are missing from files that we are
	 * using to insert data. Use the first column as the baseline for
	 * required elements. Example, patient ids, and other columns
	 * as patient ids from other tables, like samples, or expression data.
	 * 
	 *  e.g
	 *  ColA	ColB	ColC
	 *  a		a		b
	 *  b		d		e
	 *  c		b		f
	 *
	 *	Would return d,e,f
	 *
	 *
	 * @param f
	 * @param delimeter
	 * @return
	 * @throws IOException
	 */
	public static List<String> GetMissingDependencies(File f, String delimeter) throws IOException{
		HashMap<Integer,Set<String>> columnData = new HashMap<Integer,Set<String>>();
		
		List<String> missingValues = new ArrayList<String>();
		FileReader file_reader = new FileReader(f);
		BufferedReader reader = new BufferedReader(file_reader);
		CSVReader csv_reader = new CSVReader(delimeter);
		
		List<String> columns = null;
		//read csv line by line and convert to sql, and add to a list
		while((columns = csv_reader.getcolumns(reader)) != null){
			for(int i = 0 ; i < columns.size() ;i++){
				if(columnData.get(i) == null){
					columnData.put(i, new TreeSet<String>());
				}
				columnData.get(i).add(columns.get(i));
			}
		}	
		Set<Integer> keys = columnData.keySet();
		for(int i = 1 ; i < keys.size();i++){
			Set<String> data = columnData.get(i);
			for(String str : data){
				if(!columnData.get(0).contains(str)){
					missingValues.add(str);
				}
			}
		}
		return missingValues;
	}
	
	public static List<String> MatrixToSQL(File f, String delimeter, SQLAble strategy,
			Model model) throws IOException{
		HashMap<String,ForeignKey> foreignKeys = model.getForeignKeys();
		String tblName = model.getTableName();
		
		FileReader file_reader = new FileReader(f);
		BufferedReader reader = new BufferedReader(file_reader);
		CSVReader csv_reader = new CSVReader(delimeter);
		List<String> headers = csv_reader.getHeaders(reader);
		List<String> columns = null;
		List<String> sqlLines = new ArrayList<String>();
		
		
		//read csv line by line and convert to sql, and add to a list
		while((columns = csv_reader.getcolumns(reader)) != null){
			String ret = strategy.toSql(tblName,headers,columns,foreignKeys);
			sqlLines.add(ret);
		}
		
		//clean readers up
		reader.close();
		file_reader.close();
		
		//return the cleaned sql
		return strategy.cleanSql(sqlLines);		

	}
	public static List<String> CSVtoSQL(File f,String delimeter, SQLAble strategy,Model model) throws IOException{
		//var definition
		HashMap<String,String> translationTable = model.getTranslationTable();
		String tblName = model.getTableName();
		FileReader file_reader = new FileReader(f);
		BufferedReader reader = new BufferedReader(file_reader);
		CSVReader csv_reader = new CSVReader(delimeter);
		List<String> headers = csv_reader.getHeaders(reader);
		List<String> columns = null;
		List<String> sqlLines = new ArrayList<String>();
		
		//convert the headers from the csv to the definition of the db schema
		if(translationTable != null){
			headers = csv_reader.convertHeaders(headers,translationTable);
		}
		
		//read csv line by line and convert to sql, and add to a list
		while((columns = csv_reader.getcolumns(reader)) != null){
			String ret = strategy.toSql(tblName,headers,columns,model.getForeignKeys());
			sqlLines.add(ret);
		}
		
		//clean readers up
		reader.close();
		file_reader.close();
		
		//return the cleaned sql
		return strategy.cleanSql(sqlLines);		
	}
}
