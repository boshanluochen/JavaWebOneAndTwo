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
        //�������ͣ��������������ļ�Ҫ����  
        resp.setContentType("application/x-msdownload");  
        //������Ӧͷ�����û�����ѡ�񣬵��������chromeû���ã������Զ�����  
        resp.setHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(fileName,"UTF-8"));  
          
        //��д�ļ�  
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
