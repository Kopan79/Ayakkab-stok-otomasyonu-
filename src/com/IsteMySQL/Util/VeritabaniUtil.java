package com.IsteMySQL.Util;
import java.sql.*;



public class VeritabaniUtil {
  static Connection conn=null;
  public static Connection Connect() {
      try {

          conn=DriverManager.getConnection("jdbc:mysql://localhost/ayakkabidb" , "root","");
          return conn;

    } catch (Exception e) {
        // TODO: handle exception
        System.out.println(e.getMessage().toString());
        return null;
    }
  }
public static ResultSet getData(String string) {
	// TODO Auto-generated method stub
	return null;
}
}