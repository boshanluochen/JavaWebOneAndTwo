<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<title>File Upload</title>
	<script src="js/jquery-3.1.1.js"></script>
	<script>  
	    $(function () {  
	        var i = 2;  
	        //添加文件的点击事件  
	        $("#addFile").click(function () {  
	            $("#option").before("<tr class='file'><td>File"+ i +":</td><td><input type='file' name='file"+ i +"'/></td></tr>" +  
	                    "<tr class='desc'><td>Desc"+ i +":</td><td><input type='text' name='desc"+ i +"'/><button id='delete"+ i +"'>删除</button></td></tr>");  
	            i++;  
	            //delete按钮的点击事件  
	            $("#delete" + (i - 1)).click(function () {  
	                i--;  
	                //删除当前节点和该节点的前一个节点  
	                var $tr = $(this).parent().parent();  
	                $tr.prev("tr").remove();  
	                $tr.remove();  
	
	                //保证文件与描述的序号一致  
	                $(".file").each(function (number) {  
	                    var n = number + 1;  
	                    $(this).find("td:first").text("File"+n+":");  
	                    $(this).find("td:last input").attr("name","file" + n);  
	                });  
	
	                $(".desc").each(function (number) {  
	                    var n = number + 1;  
	                    $(this).find("td:first").text("Desc" + n + ":");  
	                    $(this).find("td:last input").attr("name","desc" + n);  
	                });  
	            });  
	            return false;  
	        });  
	    });  
	</script>  
	</head>
	<body>  
	    <form action="FileUpload" method="post" enctype="multipart/form-data">  
	        <table border="1px">  
	            <tr class="file">  
	                <td>File1:</td>  
	                <td><input type="file" name="file1"/></td>  
	            </tr>  
	            <tr class="desc">  
	                <td>Desc1:</td>  
	                <td><input type="text" name="desc1"/></td>  
	            </tr>  
	            <tr id="option">  
	                <td><input type="submit" value="提交!" /></td>  
	                <td><button id="addFile">添加文件</button></td>  
	            </tr>  
	        </table>  
	    </form>  
	    <a href="FileListlerServlet">查看现有文件</a>  
	</body>  
</html>
