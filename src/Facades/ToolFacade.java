package Facades;
import interfaces.SQLAble;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import DB.ForeignKey;
import IO.CSVReader;
import Models.Model;

public class ToolFacade {
	
	public ToolFacade(){
		
	}
	public List<String> MatrixToSQL(File f, String delimeter, SQLAble strategy,
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
	public List<String> CSVtoSQL(File f,String delimeter, SQLAble strategy,Model model) throws IOException{
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
