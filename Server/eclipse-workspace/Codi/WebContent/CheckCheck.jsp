<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file = "ConnectDB.jsp"  %>
<%@ page import="java.util.*,java.text.*" %>
<%@ page import="java.sql.*"  %>
<%
request.setCharacterEncoding("UTF-8");

String key = new String();
String receiveid = request.getParameter("receiveid")==null?"":request.getParameter("receiveid");

if(receiveid.equals("")){
	System.out.println("받는중..");
}else{
	try {
		
	      sql = "SELECT KEYCHAT FROM CHATKEY WHERE receiverid = ? ";
	      pstmt = conn.prepareStatement(sql);
	      pstmt.setString(1, receiveid);
	      rs = pstmt.executeQuery();
	     
	      while(rs.next())
	      {
	    	  key = rs.getString("KEYCHAT");
	      }
	      
	      System.out.println("KEY = " + key);

	      out.println(key);
	}catch (Exception e) {
	e.printStackTrace();
	} finally {
		if (pstmt2 != null)try {pstmt2.close();    } catch (SQLException ex) {}
	    if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
	    if (conn != null)try {conn.close();    } catch (SQLException ex) {    }
	}	   


}




%>