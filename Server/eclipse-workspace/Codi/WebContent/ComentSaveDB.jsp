<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file = "ConnectDB.jsp"  %>
<%@ page import="java.util.*,java.text.*" %>
<%@ page import="java.sql.*"  %>
<%
request.setCharacterEncoding("UTF-8");

String boardid = request.getParameter("boardid")==null?"":request.getParameter("boardid");
String userid = request.getParameter("userid")==null?"":request.getParameter("userid");
String content = request.getParameter("content")==null?"":request.getParameter("content");
String boardname = request.getParameter("boardname")==null?"":request.getParameter("boardname");



if(boardid.equals("")){
	System.out.println("받는중..");
}else{
	try {
		
		String currenttime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		  int a = Integer.parseInt(boardid);
		  System.out.println(a);
		  System.out.println(userid);
		  System.out.println(content);
		  System.out.println(currenttime);
	      sql = "INSERT INTO COMENT VALUES(?,?,(TO_DATE(?,'YYYY-MM-DD HH24:MI:SS')),?,?)";
	      pstmt = conn.prepareStatement(sql);
	      pstmt.setInt(1,a );
	      pstmt.setString(2, userid);
	      pstmt.setString(3, currenttime);
	      pstmt.setString(4, content);
	      pstmt.setString(5, boardname);
	      pstmt.executeUpdate();
	     
	      System.out.println("boardid = " + boardid+ " userid = " + userid + " content = " + content);
	}catch (Exception e) {
	e.printStackTrace();
	} finally {
		if (pstmt2 != null)try {pstmt2.close();    } catch (SQLException ex) {}
	    if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
	    if (conn != null)try {conn.close();    } catch (SQLException ex) {    }
	}	   


}




%>