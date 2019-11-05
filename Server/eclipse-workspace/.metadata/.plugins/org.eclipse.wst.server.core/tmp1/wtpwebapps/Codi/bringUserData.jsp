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
String userid = request.getParameter("userid")==null?"":request.getParameter("userid");

if(userid.equals("") && userid.equals("")){
System.out.println("데이터가져오는중");
out.println("데이터가져오는중");
}
else{
	try{	
		sql = "SELECT E1.*,E2.profileimage FROM ( SELECT * FROM BOARD WHERE userid = ? UNION SELECT * FROM NEEDBOARD WHERE userid = ?)E1 INNER JOIN (SELECT profileimage,id FROM USERINFO WHERE id= ? ) E2 ON E1.userid = E2.id ORDER BY TIME DESC";
		//sql = "SELECT * FROM ( SELECT * FROM BOARD WHERE userid = ? UNION SELECT * FROM NEEDBOARD WHERE userid = ?) ORDER BY TIME DESC";
		//sql = "SELECT BOARD.*,LIKES.flag FROM BOARD,LIKES WHERE boardname = ? and likes.userid = ? and board.id=likes.boardid ";
	    //sql = "SELECT BOARD.*,E2.flag FROM BOARD FULL OUTER JOIN (SELECT * FROM LIKES WHERE USERID = ?) E2 ON board.id = E2.boardid WHERE boardname = ?";
		//sql = "SELECT BOARD.*,DECODE(E2.flag,null,0,1) FLAG  FROM BOARD FULL OUTER JOIN (SELECT * FROM LIKES WHERE USERID = ?) E2 ON board.id = E2.boardid WHERE boardname = ? ORDER BY board.id desc";
	    pstmt = conn.prepareStatement(sql);
	    pstmt.setString(1,userid);
	    pstmt.setString(2,userid);
	    pstmt.setString(3,userid);
		
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
			jsonObj.put("profileimage",rs.getString("profileimage"));
			jsonObj.put("likecount",rs.getString("likecount"));
			//jsonObj.put("flag",rs.getString("flag"));
			jArray.add(jsonObj);    	
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		//String jsonst = jArray.toJSONString();
		jsonMain.put("boardData",jArray);
		out.println(jsonMain);
		System.out.println(jsonMain);
		
		if (pstmt2 != null)try {pstmt2.close();    } catch (SQLException ex) {}
		if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
		if (conn != null)try {conn.close();    } catch (SQLException ex) {    }
	}
}


	
%>