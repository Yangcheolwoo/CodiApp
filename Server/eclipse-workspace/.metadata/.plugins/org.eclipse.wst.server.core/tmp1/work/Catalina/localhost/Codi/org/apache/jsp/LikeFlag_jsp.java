/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.5.41
 * Generated at: 2019-10-24 15:39:08 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.sql.*;

public final class LikeFlag_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(1);
    _jspx_dependants.put("/ConnectDB.jsp", Long.valueOf(1571928113282L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("java.sql");
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = null;
  }

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException {

    final java.lang.String _jspx_method = request.getMethod();
    if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method) && !javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET POST or HEAD");
      return;
    }

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write('\r');
      out.write('\n');
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");

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


      out.write("\r\n");
      out.write("\r\n");

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


    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
