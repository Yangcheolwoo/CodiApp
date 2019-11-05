<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding = "UTF-8"%>
<%@ include file = "ConnectDB.jsp"  %>
<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@page import="java.util.*" %>
<%@page import="java.io.*" %>
<%@page import="java.text.*" %>
<%@page import="java.net.URLDecoder" %>
<%@page import= "java.util.concurrent.atomic.AtomicInteger"%>


<%
String folderTypePath = "C:/Users/ZestPC/eclipse-workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp1/wtpwebapps/Codi/image/";
String name = new String();
String fileName = new String();
String realFileName = new String();
String array[] = new String[2];
String arraylist[] = new String[2];
int n =1 ;
int sizeLimit = 10 * 1024 * 1024; // 5메가까지 제한 넘어서면 예외발생
try {
	System.out.println("이미지다운시작");
    MultipartRequest multi = new MultipartRequest(request, folderTypePath, sizeLimit,"UTF-8",new DefaultFileRenamePolicy());
     
    
    Enumeration params = multi.getParameterNames();
    
	if(params.hasMoreElements()){
		for(int i=0; i<arraylist.length; i++){
			array[i] = (String) params.nextElement();
		
			if(array[i].equals("intro")){
				arraylist[i] = ((multi.getParameter(array[i]))).replace(System.getProperty("line.separator"),"").substring(2);//들어오는문자열 뒤에 \n이 포함되서 들어오므로 이걸 제거해줌 //ne
			}
		
		//String item = (multi.getParameter(array[i])).replace(System.getProperty("line.separator"),"");//들어오는문자열 뒤에 \n이 포함되서 들어오므로 이걸 제거해줌
		//URLDecoder.decode((URLDecoder.decode(item,"8859_1")),"UTF-8");
			else{
				arraylist[i] = (multi.getParameter(array[i])).replace(System.getProperty("line.separator"),"");//들어오는문자열 뒤에 \n이 포함되서 들어오므로 이걸 제거해줌 //new String(item.getBytes("8859_1"),"UTF-8");
			}
		}
	}
    
	 Enumeration files = multi.getFileNames();
	    //파일 정보가 있다면      
	    
    if (files.hasMoreElements()) {
    	name = (String) files.nextElement();
    	fileName = multi.getFilesystemName(name);
    }
	
	for(int i=0; i<arraylist.length; i++){
    	System.out.println(array[i]);
    	System.out.println(arraylist[i]);
    }
	
} catch (IOException e) {
    out.println("안드로이드 부터 이미지를 받아옵니다.");
}finally{
	
	
	String now = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
	if(fileName != null){
		int i;
		i=fileName.lastIndexOf(".");
		realFileName = "Codi_ProfileImage_"+ now + fileName.substring(fileName.lastIndexOf("."),fileName.length());
		System.out.println(realFileName);
	}
	File oldFile = new File(folderTypePath + fileName);
	File newFile = new File(folderTypePath+"ProFIleImage/" + realFileName); //
	if(oldFile.renameTo(newFile)){
		System.out.println("Sucess");
		oldFile.delete();
	}else{
		System.out.println("Error");
	}	
	
	try {
		if(fileName != null && array[0] != null){
			sql = "UPDATE USERINFO SET PROFILEIMAGE = ? , INTRO = ? WHERE id = ?";
			
		}
	    pstmt = conn.prepareStatement(sql); 
	  	pstmt.setString(1, realFileName); //이미지 이름
	    pstmt.setString(2, arraylist[1]); //intro
	    pstmt.setString(3, arraylist[0]); //사용자 이름
	    pstmt.executeUpdate();
	    
	}catch (Exception e) {
		e.printStackTrace();
	} finally {
		System.out.println("sucess");
		out.println("sucess");
		if (pstmt2 != null)try {pstmt2.close();    } catch (SQLException ex) {}
	   if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
	   if (conn != null)try {conn.close();    } catch (SQLException ex) {    }
	}	   

}
%>