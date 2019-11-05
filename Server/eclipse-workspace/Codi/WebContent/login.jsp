<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file = "ConnectDB.jsp"  %>
<%@ page import="java.util.*,java.text.*" %>
<%@ page import="java.sql.*"  %>
<%@ page import="org.json.simple.JSONArray"%>
<%@ page import="org.json.*" %>
<%

JSONArray jArray = new JSONArray();
JSONObject jsonObj = new JSONObject();
String id = request.getParameter("id")==null?"":request.getParameter("id");
String pw = request.getParameter("pw")==null?"":request.getParameter("pw");


if(id.equals("") && pw.equals("")){
	returns = "로그인시작";
	System.out.println(returns);
}else{
	try{	
		sql = "SELECT pwd,id,profileimage,intro FROM USERINFO WHERE id = ?";
	    pstmt = conn.prepareStatement(sql);
	    pstmt.setString(1, id);
	    rs = pstmt.executeQuery();
	    
	    while(rs.next()) {//밑의 해당되는 비밀번호가 있을때 까지 반복 / 찾으면true 못찾으면 false
	    	jsonObj = new JSONObject();
			jsonObj.put("id",rs.getString("id"));
			jsonObj.put("profileimage",rs.getString("profileimage"));
			jsonObj.put("intro",rs.getString("intro"));
			System.out.println("저장 완료");
	    	ry = rs.getString("pwd");//해당 아이디 비멀번호 찾기
	    }
	    
	    sql2 = "SELECT id FROM USERINFO WHERE id = ?";
	    pstmt2 = conn.prepareStatement(sql2);
	    pstmt2.setString(1,id);
	    rs2 = pstmt.executeQuery();
	    
	    if(rs2.next()) {//해당 아이디가 있을경우 조건 만족		
	        if(ry.equals(pw)) {//위에서 디비안의 해당 아이디에 대한 비밀번호와 앱에서 보내는 비밀번호 비교
	    		out.println(jsonObj);
	        	System.out.println(jsonObj);
	        }
	        else {
	           	out.println("Wrongpw");
	        }
	    }
	    else {//해당 아이디가 없을경우 조건 만족            
	        out.println("NoId"); //ID 없음
	    }
	
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		if (pstmt2 != null)try {pstmt2.close();    } catch (SQLException ex) {}
		if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
		if (conn != null)try {conn.close();    } catch (SQLException ex) {    }
	}	   
}
%>