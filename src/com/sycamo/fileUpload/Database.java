package com.sycamo.fileUpload;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Connection connection = null;  
    
    
    public Database() {}  
  
    public static Connection getConn() throws ClassNotFoundException, SQLException {  
        Class.forName("com.mysql.jdbc.Driver");  
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","wang");  
        return connection;  
    }  
}
