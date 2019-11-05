<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding = "UTF-8"%>
<%@ include file = "ConnectDB.jsp"  %>

<%
String boardid = request.getParameter("boardid")==null?"":request.getParameter("boardid");
String userid = request.getParameter("userid")==null?"":request.getParameter("userid");
String boardname = request.getParameter("boardname")==null?"":request.getParameter("boardname");
int count = 0;
if(boardid.equals("") || userid.equals("")){
	returns = "좋아요 판별중";
}
else {
	try{	
		String Liketable;
		String boardtablename;
		//System.out.println(boardname);
		if(boardname.equals("need")){
			Liketable = "NEEDLIKES";
			boardtablename = "NEEDBOARD";
		}else{
			Liketable = "LIKES";
			boardtablename = "BOARD";
		}
		sql = "SELECT flag FROM "+Liketable+" WHERE boardid = ? AND userid = ? ";
	    pstmt = conn.prepareStatement(sql);
	    pstmt.setString(1, boardid);
	    pstmt.setString(2, userid);
	    rs = pstmt.executeQuery();
	    
	    while(rs.next()){
	    	ry = rs.getString("flag");
	    }
	    
	    if(ry.equals("1")) {   	
	    	sql2 = "UPDATE "+Liketable+" SET FLAG = 0 WHERE BOARDID = ? AND USERID = ?";
	    	pstmt2 = conn.prepareStatement(sql2);
	    	//pstmt2.setInt(1, 0);
	    	pstmt2.setString(1,boardid);
	    	pstmt2.setString(2,userid);
	    	rs2 = pstmt2.executeQuery();	
	    	//returns = "좋아요 취소";
	    	//System.out.println("종아요 취소");
	    	
	    }else if(ry.equals("0")){
	    	sql2 = "UPDATE "+Liketable+" SET FLAG = 1 WHERE BOARDID = ? AND USERID = ?";
	    	pstmt2 = conn.prepareStatement(sql2);
	    	//pstmt2.setInt(1, 1);
	    	pstmt2.setString(1,boardid);
	    	pstmt2.setString(2,userid);
	    	rs2 = pstmt2.executeQuery();	
	    	//System.out.println("다시 종아요 ");
	    	//returns = "다시 좋아요";
	    }else {
	    	sql2 = "INSERT INTO "+Liketable+" VALUES(?,?,?)";
	    	pstmt2 = conn.prepareStatement(sql2);
	    	pstmt2.setString(1, boardid);
	    	pstmt2.setString(2, userid);
	    	pstmt2.setInt(3, 1);
	    	rs2 = pstmt2.executeQuery();
	    	//System.out.println("새로 좋아요");
	    	//returns = "새로운 좋아요";
	    }
	    sql3 = "SELECT COUNT(*) AS boardid FROM "+Liketable+" WHERE boardid = ? AND flag = ?";
		pstmt3 = conn.prepareStatement(sql3);
		pstmt3.setString(1,boardid);
		pstmt3.setInt(2,1);
		rs3 = pstmt3.executeQuery();
		while(rs3.next()){
			count = rs3.getInt(1);
		}
		sql4 = "UPDATE "+boardtablename+" SET likecount = ? WHERE id = ?";
		pstmt4 = conn.prepareStatement(sql4);
		pstmt4.setInt(1,count);
		pstmt4.setString(2,boardid);
		rs4 = pstmt4.executeQuery();
		//System.out.println("좋아요수"+count);
	    out.println(count);
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		if (pstmt4 != null)try {pstmt4.close();    } catch (SQLException ex) {}
		if (pstmt3 != null)try {pstmt3.close();    } catch (SQLException ex) {}
		if (pstmt2 != null)try {pstmt2.close();    } catch (SQLException ex) {}
		if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
		if (conn != null)try {conn.close();    } catch (SQLException ex) {    }
	}	   
	//System.out.println("좋아요 수정");
}

%>