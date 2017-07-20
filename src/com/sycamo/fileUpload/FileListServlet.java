package com.sycamo.fileUpload;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileListServlet extends HttpServlet{
    @Override  
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {  
        List<UploadFileBean> beans = null;  
        try {  
            beans = SaveUploadFiles.getList();  
        } catch (SQLException e) {  
            e.printStackTrace();  
        } catch (ClassNotFoundException e) {  
            e.printStackTrace();  
        }  
        req.setAttribute("fileList",beans);  
        req.getRequestDispatcher("/filelist.jsp").forward(req,resp);  
    }  
}
