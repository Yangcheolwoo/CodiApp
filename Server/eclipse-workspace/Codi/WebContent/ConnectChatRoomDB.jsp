<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file = "ConnectDB.jsp"  %>
<%@ page import="java.util.*,java.text.*" %>
<%@ page import="java.sql.*"  %>
<%

String senderid = request.getParameter("senderid")==null?"":request.getParameter("senderid");
String receiverid = request.getParameter("receiverid")==null?"":request.getParameter("receiverid");
String chat_key = request.getParameter("chat_key")==null?"":request.getParameter("chat_key");



if(senderid.equals("") && receiverid.equals("")){
	System.out.println("받는중..");
}else{
	try {
		
		  System.out.println("정보 받는중");
		//String currenttime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());

		  System.out.println(senderid);
		  System.out.println(receiverid);
		  System.out.println(chat_key);
		  
	      sql = "INSERT INTO CHATKEY VALUES(?,?,?)";
	      pstmt = conn.prepareStatement(sql);
	      pstmt.setString(1, senderid);
	      pstmt.setString(2, receiverid);
	      pstmt.setString(3, chat_key);
	      pstmt.executeUpdate();
	     
	      System.out.println("senderid = " + senderid + " receiverid = " + receiverid + " chat_key = " + chat_key);
	}catch (Exception e) {
	e.printStackTrace();
	} finally {
		if (pstmt2 != null)try {pstmt2.close();    } catch (SQLException ex) {}
	    if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
	    if (conn != null)try {conn.close();    } catch (SQLException ex) {    }
	}	   


}
%>