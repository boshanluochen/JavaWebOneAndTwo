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
  
        //��request�д������ı�ת����һ���б�  
        List<FileItem> items = null;  
        try {  
            items = upload.parseRequest(new ServletRequestContext(request));  
        } catch (FileUploadException e) {  
            e.printStackTrace();  
        }  
  
        // ����Ҫ�ϴ���FileItem�����뵽��Map��  
        //�����ļ��Ĵ���ŵ�·�� ֵ ��ӦFileItem ����  
        Map<String , FileItem> uploadFiles = new HashMap<>();  
  
        //1.����UploadFileBean�ļ���,ͬʱ���uploadFiles  
        List<UploadFileBean> beans = buildUploadFileBean(items,uploadFiles);  
  
        //2.�����ļ����ϴ�����  
        upload(uploadFiles);  
        //3.���ļ��ϴ�����Ϣ���浽���ݿ���  
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
  
        //1.����FileItem�ļ��ϣ��ȵõ�desc��Map<String��String> ���м���fieldName  
        //ֵ�����Ӧ���ֶε�ֵ  
        //�õ��ļ����FileItem����  
        //ÿ���õ�һ��FileItem���󶼴���һ��UploadFileBean����  
        //�õ�fileName,����filePath����1��Map�еõ���ǰ��FileItem��Ӧ��desc  
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
    //���������֤�ϴ����ļ������ֲ�һ��  
    private String getFilePath(String fileName) {  
  
        String extName = fileName.substring(fileName.lastIndexOf("."));  
        Random random = new Random();  
        String filePath = FILE_PATH  + random.nextLong() + extName;  
  
        return filePath;  
    }  
    //��װһ���������ڻ��upload  
    private ServletFileUpload getServletFileUpload()  
    {  
  
        String exts = FileUploadProperties.getInstance().getProperty("exts");  
        String fileMaxSize = FileUploadProperties.getInstance().getProperty("fileMaxSize");  
        String totalFileMaxSize = FileUploadProperties.getInstance().getProperty("totalFileMaxSize");  
  
        //����һ��DiskFileItemFactory���ڻ�ȡform����  
        DiskFileItemFactory factory = new DiskFileItemFactory();  
  
        // �������  
        /** 
         * setSizeThreshold�������������Ƿ��ϴ��ļ�����ʱ�ļ�����ʽ�����ڴ��̵��ٽ�ֵ 
         *�����ֽ�Ϊ��λ��intֵ���������û�е��ø÷������ô��ٽ�ֵ���������ϵͳĬ��ֵ10KB�� 
         * ��Ӧ��getSizeThreshold() ����������ȡ���ٽ�ֵ�� 
         */  
        factory.setSizeThreshold(1024 * 500);  
  
        File file = new File(this.getServletContext().getRealPath("/WEB-INF/temp"));  
        if(!file.exists() && !file.isDirectory())  
        {  
            file.mkdir();  
        }  
        factory.setRepository(file);//������ʱ�ļ��洢��λ��  
  
        //����һ���ϴ��Ĵ�����  
        ServletFileUpload upload = new ServletFileUpload(factory);  
  
        //���ñ���  
        upload.setHeaderEncoding("UTF-8");  
  
        //���������ϴ��ļ��Ĵ�С  
        upload.setSizeMax(Integer.parseInt(totalFileMaxSize));  
  
        //���������ϴ��ļ����ܴ�С  
        upload.setFileSizeMax(Integer.parseInt(fileMaxSize));  
  
        return upload;  
    }  
}
