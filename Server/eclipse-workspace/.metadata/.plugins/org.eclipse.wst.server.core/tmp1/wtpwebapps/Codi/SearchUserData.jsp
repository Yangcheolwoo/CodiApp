<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file = "ConnectDB.jsp"  %>
<%@ page import="java.util.*,java.text.*" %>
<%@ page import="java.sql.*"  %>
<%@ page import="org.json.simple.JSONArray"%>
<%@ page import="org.json.*" %>
<%

JSONArray jArray = new JSONArray();
JSONObject jsonMain = new JSONObject();

String search = request.getParameter("search")==null?"":request.getParameter("search");

if(search.equals("")){
	//System.out.println(검색중);
}else{
	try{	
		sql = "SELECT id,profileimage FROM USERINFO";
	    pstmt = conn.prepareStatement(sql);
	    rs = pstmt.executeQuery();
	    
	    while(rs.next()) {//밑의 해당되는 비밀번호가 있을때 까지 반복 / 찾으면true 못찾으면 false
	    	JSONObject jsonObj = new JSONObject();
			jsonObj.put("id",rs.getString("id"));
			jsonObj.put("profileimage",rs.getString("profileimage"));			
			jArray.add(jsonObj);
	    }
	    	      
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		jsonMain.put("SearchUser",jArray);
		out.println(jsonMain);
		System.out.println(jsonMain);
		
		if (pstmt2 != null)try {pstmt2.close();    } catch (SQLException ex) {}
		if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
		if (conn != null)try {conn.close();    } catch (SQLException ex) {    }
	}	   
}
%>