<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file = "ConnectDB.jsp"  %>
<%@ page import="java.util.*,java.text.*" %>
<%@ page import="java.sql.*"  %>
<%@ page import="org.json.simple.JSONArray"%>
<%@ page import="org.json.*" %>


<%
JSONObject jsonObj = new JSONObject();
String id = request.getParameter("id")==null?"":request.getParameter("id");
if(id.equals("")){
	
}else{
	//SELECT COUNT(*) AS bookmarkctn FROM BOOKMARK WHERE BOOKMARK= 'jong'
	//sql = "SELECT E1.*,E2.bookmarkctn FROM (SELECT id,profileimage,intro FROM USERINFO WHERE id = ?) E1 FULL OUTER JOIN (SELECT BOOKMARK,COUNT(*) AS bookmarkctn FROM BOOKMARK GROUP BY BOOKMARK HAVING BOOKMARK=?) E2 ON E1.id = E2.bookmark";
	sql = "SELECT * FROM (SELECT id,profileimage,intro FROM USERINFO WHERE id = ?)";
	pstmt = conn.prepareStatement(sql);
	pstmt.setString(1, id);
	rs = pstmt.executeQuery();
	
	while(rs.next()) {
		//jsonObj = new JSONObject();
		jsonObj.put("id",rs.getString("id"));
		jsonObj.put("profileimage",rs.getString("profileimage"));
		jsonObj.put("intro",rs.getString("intro"));
		
	}

	sql2 = "SELECT SUM(CTN) AS boardctn FROM ((SELECT COUNT(*) AS CTN FROM BOARD WHERE userid = ?) UNION ALL (SELECT COUNT(*) AS CTN2 FROM NEEDBOARD WHERE userid = ?))";
	pstmt2 = conn.prepareStatement(sql2);
	pstmt2.setString(1,id);
	pstmt2.setString(2,id);
	rs2 = pstmt2.executeQuery();
	
	while(rs2.next()){
		jsonObj.put("boardctn",rs2.getString("boardctn"));
	}
	
	sql3 = "SELECT COUNT(*) AS bookmarkctn FROM BOOKMARK WHERE BOOKMARK= ?";
	pstmt3 = conn.prepareStatement(sql3);
	pstmt3.setString(1,id);
	rs3 = pstmt3.executeQuery();
	
	while(rs3.next()){
		jsonObj.put("bookmarkctn",rs3.getString("bookmarkctn"));
	}
	out.println(jsonObj);
}

%>