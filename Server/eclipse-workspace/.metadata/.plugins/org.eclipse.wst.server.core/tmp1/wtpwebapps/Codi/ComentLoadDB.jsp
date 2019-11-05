<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file = "ConnectDB.jsp"  %>
<%@ page import="java.util.*,java.text.*" %>
<%@ page import="java.sql.*"  %>
<%@ page import="java.io.*" %>
<%@ page import="org.json.simple.JSONArray"%>
<%@ page import="org.json.*" %>

<%
JSONArray jArray = new JSONArray();
JSONObject jsonMain = new JSONObject();

String boardid = request.getParameter("boardid")==null?"":request.getParameter("boardid");
String boardname = request.getParameter("boardname")==null?"":request.getParameter("boardname");
if(boardid.equals("")){
System.out.println("데이터가져오는중");
out.println("데이터가져오는중");
}
else{
	try{	
		
		//sql = "SELECT userid,time,content FROM COMENT WHERE boardid = ? AND boardname = ?";
	    sql = "SELECT E1.userid,E1.time,E1.content,E1.profileimage FROM (SELECT COMENT.*, USERINFO.profileimage FROM COMENT INNER JOIN USERINFO ON coment.userid = userinfo.id) E1 WHERE E1.boardid = ? AND E1.boardname = ?";
		pstmt = conn.prepareStatement(sql);
	    pstmt.setString(1,boardid);		
	    pstmt.setString(2,boardname);
	    rs = pstmt.executeQuery();
		while(rs.next()){
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("userid",rs.getString("userid"));
			jsonObj.put("time",rs.getString("time"));
			jsonObj.put("content",rs.getString("content"));
			jsonObj.put("profileimage",rs.getString("profileimage"));
			jArray.add(jsonObj);    	
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		//String jsonst = jArray.toJSONString();
		jsonMain.put("ComentData",jArray);
		out.println(jsonMain);
		System.out.println(jsonMain);
		
		if (pstmt2 != null)try {pstmt2.close();    } catch (SQLException ex) {}
		if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
		if (conn != null)try {conn.close();    } catch (SQLException ex) {    }
	}
}


%>