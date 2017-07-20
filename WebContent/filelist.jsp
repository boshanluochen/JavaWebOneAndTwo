<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>  
<head>  
    <title>Title</title>  
</head>  
    <table border="1">  
        <tr>  
            <td>文件名</td>  
            <td>文件描述</td>  
            <td>操作</td>  
        </tr>  
        <c:forEach var="file" items="${requestScope.fileList}">  
            <tr>  
                <td>${file.fileName}</td>  
                <td>${file.desc}</td>  
                <td><a href="DownloadServlet?fileName=${file.fileName}&filePath=${file.filePath}">下载</a></td>  
            </tr>  
        </c:forEach>  
    </table>  
    <br><br>  
    <a href="fileupload.jsp">上传文件</a>  
</body>  
</html>  
