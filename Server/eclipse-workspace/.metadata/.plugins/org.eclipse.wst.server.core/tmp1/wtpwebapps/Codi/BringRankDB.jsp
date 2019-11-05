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
String type = request.getParameter("type")==null?"":request.getParameter("type");
String userid = request.getParameter("userid")==null?"":request.getParameter("userid");
if(type.equals("")){
System.out.println("데이터가져오는중");
out.println("데이터가져오는중");
}
else{
	try{	
		//System.out.println("type:" + type);
		//System.out.println("userid:" + userid);
		if(type.equals("month")){ //한달 top5
			//sql= "SELECT * FROM ( SELECT * FROM (SELECT * FROM BOARD WHERE time >= TRUNC(sysdate,'MM') AND time <= TRUNC(ADD_MONTHS(sysdate,+1),'MM')-1/(24*60*60) ORDER BY likecount DESC) ORDER BY time ASC) WHERE ROWNUM <= 5";
			//sql= "SELECT * FROM ( SELECT * FROM BOARD WHERE time >= TRUNC(sysdate,'MM') AND time <= TRUNC(ADD_MONTHS(sysdate,+1),'MM')-1/(24*60*60) ORDER BY likecount DESC,time ASC) WHERE ROWNUM <= 5";
			sql = "SELECT * FROM ( SELECT BOARD.*,DECODE(E1.flag,null,0,1,1,0,0) FLAG FROM BOARD FULL OUTER JOIN (SELECT * FROM LIKES WHERE userid = ?) E1 ON BOARD.id = E1.boardid WHERE time >= TRUNC(sysdate,'MM') AND time <= TRUNC(ADD_MONTHS(sysdate,+1),'MM')-1/(24*60*60)  ORDER BY likecount DESC,time ASC) WHERE ROWNUM <= 5";
		}else if(type.equals("week")){ //주간 top5
			//sql= "SELECT * FROM ( SELECT * FROM (SELECT * FROM BOARD WHERE time >= TRUNC(SYSDATE,'IW') AND time <= TRUNC(SYSDATE+7,'IW')-1/(24*60*60) ORDER BY likecount DESC) ORDER BY time ASC) WHERE ROWNUM <= 5";
			sql = "SELECT * FROM ( SELECT BOARD.*,DECODE(E1.flag,null,0,1,1,0,0) FLAG FROM BOARD FULL OUTER JOIN (SELECT * FROM LIKES WHERE userid = ?) E1 ON BOARD.id = E1.boardid WHERE time >= TRUNC(SYSDATE,'IW') AND time <= TRUNC(SYSDATE+7,'IW')-1/(24*60*60) ORDER BY likecount DESC,time ASC) WHERE ROWNUM <= 5";
		}else{ //일일 top5
			sql = "SELECT * FROM ( SELECT BOARD.*,DECODE(E1.flag,null,0,1,1,0,0) FLAG FROM BOARD FULL OUTER JOIN (SELECT * FROM LIKES WHERE userid = ?) E1 ON BOARD.id = E1.boardid WHERE time >= TRUNC(SYSDATE,'DD') AND time <= TRUNC(SYSDATE+1,'DD')-1/(24*60*60) ORDER BY likecount DESC,time ASC) WHERE ROWNUM <= 5";
			//sql="SELECT * FROM ( SELECT * FROM BOARD WHERE time >= TRUNC(SYSDATE,'DD') AND time <= TRUNC(SYSDATE+1,'DD')-1/(24*60*60) ORDER BY likecount DESC,time ASC) WHERE ROWNUM <= 5";
			//sql="SELECT * FROM ( SELECT * FROM (SELECT * FROM BOARD WHERE time >= TRUNC(SYSDATE,'DD') AND time <= TRUNC(SYSDATE+1,'DD')-1/(24*60*60) ORDER BY likecount DESC) ORDER BY time ASC) WHERE ROWNUM <= 5";
		}	
		//sql = "SELECT * FROM ( SELECT * FROM BOARD ORDER BY likecount DESC) WHERE ROWNUM <= 5";
		
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
			jsonObj.put("flag",rs.getString("flag"));
			jArray.add(jsonObj);    	
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		String jsonst = jArray.toJSONString();
		
		jsonMain.put("rankData",jArray);
		out.println(jsonMain);
		System.out.println(jsonMain);
		
		if (pstmt2 != null)try {pstmt2.close();    } catch (SQLException ex) {}
		if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
		if (conn != null)try {conn.close();    } catch (SQLException ex) {    }
	}
}


%>