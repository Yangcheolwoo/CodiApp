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
//String folderTypePath = "C:/Users/ZestPC/eclipse-workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp1/wtpwebapps/Codi/image/";
//String db = request.getParameter("db")==null?"":request.getParameter("db");
String userid = request.getParameter("userid")==null?"":request.getParameter("userid");

if(userid.equals("")){
System.out.println("데이터가져오는중");
out.println("데이터가져오는중");
}
else{
	try{	
		//sql = "SELECT * FROM BOARD WHERE BOARDNAME = ? ORDER BY ID DESC";
		//sql = "SELECT BOARD.*,LIKES.flag FROM BOARD,LIKES WHERE boardname = ? and likes.userid = ? and board.id=likes.boardid ";
	    //sql = "SELECT BOARD.*,E2.flag FROM BOARD FULL OUTER JOIN (SELECT * FROM LIKES WHERE USERID = ?) E2 ON board.id = E2.boardid WHERE boardname = ?";
		//sql = "SELECT NEEDBOARD.*,DECODE(E2.flag,null,0,1,1,0,0) FLAG FROM NEEDBOARD FULL OUTER JOIN (SELECT * FROM NEEDLIKES WHERE USERID = ?) E2 ON needboard.id = E2.boardid ORDER BY needboard.id desc";
	    sql = "SELECT E1.*,DECODE(E2.flag,null,0,1,1,0,0) FLAG FROM (SELECT NEEDBOARD.*,USERINFO.profileimage FROM NEEDBOARD INNER JOIN USERINFO ON needboard.userid = userinfo.id) E1 FULL OUTER JOIN (SELECT * FROM NEEDLIKES WHERE USERID = ?) E2 ON E1.id = E2.boardid WHERE boardname = 'need' ORDER BY E1.id desc";
		pstmt = conn.prepareStatement(sql);
	    pstmt.setString(1,userid);
		
	    rs = pstmt.executeQuery();
		while(rs.next()){
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("id",rs.getString("id"));
			jsonObj.put("userid",rs.getString("userid"));
			jsonObj.put("title",rs.getString("title"));
			jsonObj.put("content",rs.getString("content"));
			jsonObj.put("time",rs.getString("time"));
			jsonObj.put("imagepath",rs.getString("imagepath"));
			jsonObj.put("boardname",rs.getString("boardname"));
			jsonObj.put("likecount",rs.getString("likecount"));
			jsonObj.put("profileimage",rs.getString("profileimage"));
			jsonObj.put("flag",rs.getString("flag"));
			jArray.add(jsonObj);    	
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		//String jsonst = jArray.toJSONString();
		jsonMain.put("NeedData",jArray);
		out.println(jsonMain);
		System.out.println(jsonMain);
		
		if (pstmt2 != null)try {pstmt2.close();    } catch (SQLException ex) {}
		if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
		if (conn != null)try {conn.close();    } catch (SQLException ex) {    }
	}
}


	
%>
