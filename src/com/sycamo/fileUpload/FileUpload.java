package com.sycamo.fileUpload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;

public class FileUpload extends HttpServlet{
	  
    private static final String FILE_PATH = "C:/Users/Administrator/Documents/files/";  
  
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
  
        ServletFileUpload upload = getServletFileUpload();  
  
        //将request中传经来的表单转化成一个列表  
        List<FileItem> items = null;  
        try {  
            items = upload.parseRequest(new ServletRequestContext(request));  
        } catch (FileUploadException e) {  
            e.printStackTrace();  
        }  
  
        // 把需要上传的FileItem都放入到该Map中  
        //键：文件的待存放的路径 值 对应FileItem 对象  
        Map<String , FileItem> uploadFiles = new HashMap<>();  
  
        //1.构建UploadFileBean的集合,同时填充uploadFiles  
        List<UploadFileBean> beans = buildUploadFileBean(items,uploadFiles);  
  
        //2.进行文件的上传操作  
        upload(uploadFiles);  
        //3.把文件上传的信息保存到数据库中  
        try {  
            SaveUploadFiles.save(beans);  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
        response.sendRedirect(request.getContextPath() + "/success.jsp");  
    }  
  
  
    private void upload(Map<String, FileItem> uploadFiles) throws IOException {  
  
        for(Map.Entry<String , FileItem> uploadFile : uploadFiles.entrySet())  
        {  
            String filePath = uploadFile.getKey();  
            FileItem item = uploadFile.getValue();  
  
            upload(filePath,item.getInputStream());  
            item.delete();  
        }  
  
  
    }  
  
    private void upload(String filePath, InputStream inputStream) throws IOException {  
  
        OutputStream out = new FileOutputStream(filePath);  
  
        byte [] buffer = new byte[1024];  
        int len = 0 ;  
        while((len = inputStream.read(buffer)) != -1)  
        {  
            out.write(buffer,0,len);  
        }  
  
        inputStream.close();  
        out.close();  
    }  
  
    private List<UploadFileBean> buildUploadFileBean(List<FileItem> items, Map<String, FileItem> uploadFiles)  
    {  
        List<UploadFileBean> list = new ArrayList<>();  
  
        //1.遍历FileItem的集合，先得到desc的Map<String，String> 其中键是fieldName  
        //值表单域对应的字段的值  
        //得到文件域的FileItem对象  
        //每个得到一个FileItem对象都创建一个UploadFileBean对象  
        //得到fileName,构建filePath，从1的Map中得到当前的FileItem对应的desc  
        Map<String ,String > descs = new HashMap<>();  
        for(FileItem item :items)  
        {  
            if(item.isFormField())  
            {  
                descs.put(item.getFieldName(),item.getString());  
            }  
        }  
        for(FileItem item :items)  
        {  
            if(!item.isFormField())  
            {  
                String fieldName = item.getFieldName();  
                String index = fieldName.substring( fieldName.length() - 1 );  
  
                String fileName = item.getName();  
                String desc = descs.get("desc" + index);  
  
                String filePath = getFilePath(fileName);  
  
  
                UploadFileBean bean = new UploadFileBean(fileName,filePath,desc);  
                list.add(bean);  
  
                uploadFiles.put(filePath,item);  
            }  
        }  
  
        return list;  
    }  
    //这个方法保证上传的文件的名字不一样  
    private String getFilePath(String fileName) {  
  
        String extName = fileName.substring(fileName.lastIndexOf("."));  
        Random random = new Random();  
        String filePath = FILE_PATH  + random.nextLong() + extName;  
  
        return filePath;  
    }  
    //封装一个方法用于获得upload  
    private ServletFileUpload getServletFileUpload()  
    {  
  
        String exts = FileUploadProperties.getInstance().getProperty("exts");  
        String fileMaxSize = FileUploadProperties.getInstance().getProperty("fileMaxSize");  
        String totalFileMaxSize = FileUploadProperties.getInstance().getProperty("totalFileMaxSize");  
  
        //创建一个DiskFileItemFactory用于获取form对象  
        DiskFileItemFactory factory = new DiskFileItemFactory();  
  
        // 添加限制  
        /** 
         * setSizeThreshold方法用于设置是否将上传文件已临时文件的形式保存在磁盘的临界值 
         *（以字节为单位的int值），如果从没有调用该方法设置此临界值，将会采用系统默认值10KB。 
         * 对应的getSizeThreshold() 方法用来获取此临界值。 
         */  
        factory.setSizeThreshold(1024 * 500);  
  
        File file = new File(this.getServletContext().getRealPath("/WEB-INF/temp"));  
        if(!file.exists() && !file.isDirectory())  
        {  
            file.mkdir();  
        }  
        factory.setRepository(file);//设置临时文件存储的位置  
  
        //创建一个上传的处理器  
        ServletFileUpload upload = new ServletFileUpload(factory);  
  
        //设置编码  
        upload.setHeaderEncoding("UTF-8");  
  
        //设置所有上传文件的大小  
        upload.setSizeMax(Integer.parseInt(totalFileMaxSize));  
  
        //设置所有上传文件的总大小  
        upload.setFileSizeMax(Integer.parseInt(fileMaxSize));  
  
        return upload;  
    }  
}
