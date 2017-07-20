package com.sycamo.fileUpload;

public class UploadFileBean {
	  
    private Integer id;  
    private String fileName;  
    private String filePath;  
    private String desc;  
  
    public UploadFileBean() {  
  
    }  
  
    public UploadFileBean(Integer id, String fileName, String filePath, String desc) {  
  
        this.id = id;  
        this.fileName = fileName;  
        this.filePath = filePath;  
        this.desc = desc;  
    }  
  
    public UploadFileBean(String fileName, String filePath, String desc) {  
  
        this.fileName = fileName;  
        this.filePath = filePath;  
        this.desc = desc;  
    }  
  
    public Integer getId() {  
  
        return id;  
    }  
  
    public void setId(Integer id) {  
  
        this.id = id;  
    }  
  
    public String getFileName() {  
  
        return fileName;  
    }  
  
    public void setFileName(String fileName) {  
  
        this.fileName = fileName;  
    }  
  
    public String getFilePath() {  
  
        return filePath;  
    }  
  
    public void setFilePath(String filePath) {  
  
        this.filePath = filePath;  
    }  
  
    public String getDesc() {  
  
        return desc;  
    }  
  
    public void setDesc(String desc) {  
  
        this.desc = desc;  
    }  
}
