<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import ="java.sql.*"%>

<%
//oracle 계정
String jdbcUrl = "jdbc:oracle:thin:@220.68.233.34:1521:xe";
String userId = "hunlove789";
String userPw = "cjfdn1Y";

Connection conn = null;
PreparedStatement pstmt = null;
PreparedStatement pstmt2 = null;
PreparedStatement pstmt3 = null;
PreparedStatement pstmt4 = null;

ResultSet rs = null;
ResultSet rs2 = null;
ResultSet rs3 = null;
ResultSet rs4 = null;

String sql = "";
String sql2 = "";
String sql3 = "";
String sql4 = "";

String ry = "";

String returns = "";


try
{
 Class.forName("oracle.jdbc.driver.OracleDriver");
 conn = DriverManager.getConnection(jdbcUrl, userId, userPw);    
}
catch(SQLException e){
 out.println(e);
}

%>