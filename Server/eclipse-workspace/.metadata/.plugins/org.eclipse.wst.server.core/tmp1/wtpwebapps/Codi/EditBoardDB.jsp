<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file = "ConnectDB.jsp"  %>
<%@ page import="java.util.*,java.text.*" %>
<%@ page import="java.sql.*"  %>
<%@page import="java.io.*" %>
<%
String folderTypePath = "C:/Users/ZestPC/eclipse-workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp1/wtpwebapps/Codi/image/";

String boardname = request.getParameter("boardname")==null?"":request.getParameter("boardname");
String userid = request.getParameter("userid")==null?"":request.getParameter("userid");
String imgpath = request.getParameter("imgpath")==null?"":request.getParameter("imgpath");
String boardid = request.getParameter("boardid")==null?"":request.getParameter("boardid");
if(boardname.equals("") && userid.equals("")){
	
}else{
	try{	
		if(boardname.equals("need")){
			sql = "DELETE FROM NEEDBOARD WHERE userid= ? AND imagepath= ?";
		}else{
			sql = "DELETE FROM BOARD WHERE userid= ? AND imagepath= ?";
		}
	    pstmt = conn.prepareStatement(sql);
	    pstmt.setString(1, userid);
	    pstmt.setString(2, imgpath);
	    rs = pstmt.executeQuery();
	    
	    if(rs.next()){
	    	returns = "게시글 삭제완료";
	    }
	    sql2 = "SELECT boardid FROM COMENT WHERE boardid = ?";
	    pstmt2 = conn.prepareStatement(sql2);
	    pstmt2.setString(1,boardid);
	    rs2 = pstmt2.executeQuery();
	    
	    if(rs2.next()){
		    sql3 = "DELETE FROM COMENT WHERE boardid = ?";
		    pstmt3 = conn.prepareStatement(sql3);
		    pstmt3.setString(1,boardid);
		    rs3 = pstmt3.executeQuery();
		    
		    if(rs3.next()){
		    	returns = "댓글 삭제완료";
		    }
	    }
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		File del_File = new File(folderTypePath+boardname+"/"+imgpath);
		if(del_File.exists()){
			del_File.delete();
		}else{
			System.out.println("이미지가 존재하지않습니다.");
		}
		System.out.println("id : " + userid + "imgpath : " + imgpath+" Delete");
		if (pstmt2 != null)try {pstmt2.close();    } catch (SQLException ex) {}
		if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
		if (conn != null)try {conn.close();    } catch (SQLException ex) {    }
	}	   
}
System.out.println(returns);

%>