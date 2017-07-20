package com.sycamo.fileUpload;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownloadServlet extends HttpServlet{
    @Override  
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {  
        String filePath = req.getParameter("filePath");  
        String fileName = req.getParameter("fileName");  
        //设置类型，告诉浏览器这个文件要下载  
        resp.setContentType("application/x-msdownload");  
        //设置响应头，让用户自行选择，但是这个对chrome没有用，它会自动下载  
        resp.setHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(fileName,"UTF-8"));  
          
        //读写文件  
        OutputStream out = resp.getOutputStream();  
        InputStream in = new FileInputStream(filePath);  
  
        byte [] buffer = new byte[1024];  
        int len = 0;  
        while ((len = in.read(buffer)) != -1)  
        {  
            out.write(buffer,0,len);  
        }  
        in.close();  
    }  
}
