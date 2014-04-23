import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;


public class ToolFacade {
	
	public ToolFacade(){
		
	}
	
	public List<String> CSVtoSQL(File f,String delimeter, HashMap<String, String> translationTable,SQLAble strategy,String tblName) throws IOException{
		FileReader file_reader = new FileReader(f);
		BufferedReader reader = new BufferedReader(file_reader);
		CSVReader csv_reader = new CSVReader(delimeter);
		List<String> headers = csv_reader.getHeaders(reader);
		if(translationTable != null){
			headers = csv_reader.convertHeaders(headers,translationTable);
		}
		List<String> columns = null;
		List<String> sqlLines = new ArrayList<String>();
		while((columns = csv_reader.getcolumns(reader)) != null){
			String ret = strategy.toSql(tblName,headers,columns);
			sqlLines.add(ret);
		}
		return strategy.cleanSql(sqlLines);		
	}
	
	public static void main(String[] args) throws IOException{
		ToolFacade facade = new ToolFacade();
		File file = new File("/Users/sroos/Documents/samples.txt");
		
		//DEFINE OUR MAPPING FROM CSV TO SQL TABLES
		HashMap<String,String> translationTable = new HashMap<String,String>();
		/*
		translationTable.put("Histology_NCode", "Pat_Histology");
		translationTable.put("OASS_Code","Pat_Overall_Survival_Status");
		translationTable.put("OAST","Pat_Overall_Survival_time");
		translationTable.put("Gender","Pat_Gender");
		translationTable.put("age", "Pat_Age");
		translationTable.put("smoking_code","Pat_smoking_code");
		translationTable.put("Packyears", "Pat_packs_per_year");
		translationTable.put("race_code","Pat_race");
		translationTable.put("PatientID_Unique", "Pat_ID");
		*/
		translationTable.put("PatientID_Unique", "Pat_ID");
		translationTable.put("SampleID","Sample_ID");
		translationTable.put("Tissue_code","Sam_Tissue");
		translationTable.put("NormID", "Sam_Normalization");
		
		//DEFINE ANY FOREIGN KEY LOOKUPS
		HashMap<String,ForeignKey> foreignKeys = new HashMap<String,ForeignKey>();
		foreignKeys.put("Sam_Normalization", new ForeignKey("Norm_ID","Normalization","Norm_Title"));
		foreignKeys.put("Sam_Tissue", new ForeignKey("Tis_ID","Tissue","Tis_Title"));
		foreignKeys.put("Sam_Patient", new ForeignKey("Pat_ID","Patient","Pat_ID"));
		List<String> sql = facade.CSVtoSQL(file, "\t",translationTable, new SimpleSQLParser(foreignKeys), "Samples");
		
		for(String str : sql){
			System.out.println(str);
		}
		
	}
}
