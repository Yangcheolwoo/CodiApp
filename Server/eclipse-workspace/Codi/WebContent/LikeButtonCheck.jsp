<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding = "UTF-8"%>
<%@ include file = "ConnectDB.jsp"  %>
<%

String boardid = request.getParameter("boardid")==null?"":request.getParameter("boardid");
String userid = request.getParameter("userid")==null?"":request.getParameter("userid");
String likeFlag="";
int num=0;
if(boardid.equals("") || userid.equals("")){	
}
else{
	try{
		sql = "SELECT flag FROM LIKES WHERE boardid = ? AND userid = ?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1,boardid);
		pstmt.setString(2,userid);
		rs = pstmt.executeQuery();
		
		while(rs.next()){
			num = rs.getInt("flag");
		}
		//System.out.println(num);
		String flag = Integer.toString(num);
		
		if(flag.equals("0") || flag.equals("")){
			likeFlag = "false";
		}else{
			likeFlag = "true";
		}

		//System.out.println(returns);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
			if (conn != null)try {conn.close();} catch (SQLException ex) {}
		}	   
	//System.out.println(likeFlag);
	out.println(likeFlag);
}



%>