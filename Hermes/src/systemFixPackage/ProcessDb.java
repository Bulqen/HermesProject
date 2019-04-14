package systemFixPackage;
import java.sql.*;
import java.util.ArrayList;

public class ProcessDb {

	ProcessDb () {
		
	}
	
	public static String [] getColumnNames(ResultSet rS) {

		ResultSetMetaData meta;
		int nColumns;
		String [] columnNames ;
		try {
			meta = rS.getMetaData();
			nColumns = meta.getColumnCount();
			columnNames  = new String [nColumns];
			for (int i = 1; i <= nColumns; i++ ) {
				columnNames[i-1] = meta.getColumnName(i);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		return columnNames;

	}
	
	public static ArrayList <String []>  getAllAsList(ResultSet rS) {
		ArrayList <String []> resultList = new ArrayList<String []>();
		String [] ColumnNames = getColumnNames(rS);
		
		
		
		try {
			while(rS.next()) {
				String [] row = new String [ColumnNames.length]; 
 				for(int i = 0; i < ColumnNames.length; i++) {
					row[i] = rS.getString(ColumnNames[i]);
					if(i != ColumnNames.length) {

					}
					
				}
 				resultList.add(row);
				
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		



		return resultList;

	}
	
	
}

