package com.sycamo.fileUpload;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SaveUploadFiles {
	  
    private static Connection connection = null;  
    private static Statement statement = null;  
  
    //保存一个list  
    public static void save(List<UploadFileBean> list) throws SQLException {  
  
        try {  
            connection = Database.getConn();  
            statement = connection.createStatement();  
        } catch (ClassNotFoundException e) {  
            e.printStackTrace();  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
  
        for(UploadFileBean bean : list)  
        {  
            String fileName = bean.getFileName();  
            String filePath = bean.getFilePath();  
            String desc = bean.getDesc();  
  
            String sql = "INSERT INTO file(file_name, file_path, file_desc) " +  
                    "VALUES ('" + fileName + "','" + filePath + "','" + desc + "')";  
            statement.execute(sql);  
        }  
    }  
    //得到一个list  
    public static List<UploadFileBean> getList() throws SQLException, ClassNotFoundException {  
        connection = Database.getConn();  
        statement = connection.createStatement();  
        List<UploadFileBean> list = new ArrayList<>();  
        String sql = "SELECT * FROM file";  
  
        ResultSet rs = statement.executeQuery(sql);  
  
        while(rs.next())  
        {  
            String fileName = rs.getString("file_name");  
            String filePath = rs.getString("file_path");  
            String desc = rs.getString("file_desc");  
            UploadFileBean bean = new UploadFileBean(fileName,filePath,desc);  
            list.add(bean);  
        }  
        return list;  
    }  
  
}
