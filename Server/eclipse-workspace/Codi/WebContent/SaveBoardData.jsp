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
//이미지를 저장할 경로 입력.
//AtomicInteger seq = new AtomicInteger();
//int up = seq.incrementAndGet();
//String up = "up.NEXTVAL";
String folderTypePath = "C:/Users/ZestPC/eclipse-workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp1/wtpwebapps/Codi/image/";
String name = new String();
String fileName = new String();
String realFileName = new String();
String array[] = new String[4];
String arraylist[] = new String[4];
int n =1 ;
int sizeLimit = 10 * 1024 * 1024; // 5메가까지 제한 넘어서면 예외발생
try {
	
    MultipartRequest multi = new MultipartRequest(request, folderTypePath, sizeLimit,"UTF-8",new DefaultFileRenamePolicy());
     
    Enumeration params = multi.getParameterNames();
    
	if(params.hasMoreElements()){
		for(int i=0; i<arraylist.length; i++){
			array[i] = (String) params.nextElement();
		
			if(array[i].equals("content") || array[i].equals("title")){
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
	int i;
	i=fileName.lastIndexOf(".");
	realFileName = "Codi_" +arraylist[3]+"_"+ now + fileName.substring(fileName.lastIndexOf("."),fileName.length());
	//System.out.println(realFileName);
	
	File oldFile = new File(folderTypePath + fileName);
	File newFile = new File(folderTypePath + arraylist[2]+"/" +realFileName); //
	if(oldFile.renameTo(newFile)){
		System.out.println("Sucess");
		oldFile.delete();
	}else{
		System.out.println("Error");
	}		
	try {
		String currenttime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		sql = "SELECT id from BOARD where rownum <= 1 order by id desc";
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		int lastid = 1;
		if(rs.next()){
			lastid = rs.getInt("id")+1;
		}
	    sql2 = "INSERT INTO BOARD VALUES(?,?,?,?,(TO_DATE(?,'YYYY-MM-DD HH24:MI:SS')),?,?,?)";
	    pstmt2 = conn.prepareStatement(sql2); 
	  	pstmt2.setInt(1, lastid); //게시판 고유번호 PK
	    pstmt2.setString(2, arraylist[3]); //사용자 이름
	    pstmt2.setString(3, arraylist[1]); //제목
	    pstmt2.setString(4, arraylist[0]); //내용
	    pstmt2.setString(5, currenttime); //시간
	    pstmt2.setString(6, realFileName); //저장된 이미지이름
	    pstmt2.setString(7, arraylist[2]);//db이름
	    pstmt2.setInt(8,0);//초기 좋아요 카운트
	    pstmt2.executeUpdate();
	    
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
