package com.sycamo.fileUpload;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class FileUploadListener implements ServletContextListener{
    // Public constructor is required by servlet spec  
    public FileUploadListener() {  
  
    }  
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
        //获取properties文件  
        InputStream in = getClass().getClassLoader().getResourceAsStream("/upload.properties");  
  
        Properties properties = new Properties();  
        try {  
            properties.load(in);  
            //添加到FileUploadProperties类中  
            for(Map.Entry<Object,Object > prop: properties.entrySet())  
            {  
                String propertyName = (String) prop.getKey();  
                String propertyValue = (String) prop.getValue();  
                FileUploadProperties.getInstance().addProperty(propertyName,propertyValue);  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
		
	}
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}


}
