<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file = "ConnectDB.jsp"  %>
<%@ page import="java.util.*,java.text.*" %>
<%@ page import="java.sql.*"  %>

<%
	String id = request.getParameter("id");
	String pw = request.getParameter("pw");
	String name = request.getParameter("name");
	String nickname = request.getParameter("nickname");	
	   
try {
			   
        sql = "SELECT id FROM USERINFO WHERE id = ?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, id);
        rs = pstmt.executeQuery();
        
        if (rs.next()) {
            returns = "existence"; //ID가 이미 존재한다.
        } else {
            sql2 = "INSERT INTO USERINFO VALUES(?,?,?,?,?)";
            pstmt2 = conn.prepareStatement(sql2);
            pstmt2.setString(1, id);
            pstmt2.setString(2, pw);
            pstmt2.setString(3, name);
            pstmt2.setString(4, nickname);
            pstmt2.setString(5, "0");
            pstmt2.executeUpdate();
            returns = "Successjoin"; //회원가입 성공
        } 
}catch (Exception e) {
	e.printStackTrace();
	} finally {
		if (pstmt2 != null)try {pstmt2.close();    } catch (SQLException ex) {}
        if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
        if (conn != null)try {conn.close();    } catch (SQLException ex) {    }
    }	   
System.out.println(returns);
out.println(returns);  
%>