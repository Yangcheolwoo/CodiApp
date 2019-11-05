package com.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectDB {
    private static ConnectDB instance = new ConnectDB();

    public static ConnectDB getInstance() {
        return instance;
    }
    public ConnectDB() {  }

    // oracle 계정
    String jdbcUrl = "jdbc:oracle:thin:@192.168.0.5:1521:xe";
    String userId = "hunlove789";
    String userPw = "cjfdn1Y";

    Connection conn = null;
    PreparedStatement pstmt = null;
    PreparedStatement pstmt2 = null;
    ResultSet rs = null;
    ResultSet rs2 = null;
    
    String sql = "";
    String sql2 = "";
    String rs3 = "";
    String returns = "a";

    public String connectionjoinDB(String id, String pwd, String name, String nickname) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(jdbcUrl, userId, userPw);

            sql = "SELECT id FROM USERINFO WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                returns = "existence"; //ID가 이미 존재한다.
            } else {
                sql2 = "INSERT INTO USERINFO VALUES(?,?,?,?)";
                pstmt2 = conn.prepareStatement(sql2);
                pstmt2.setString(1, id);
                pstmt2.setString(2, pwd);
                pstmt2.setString(3, name);
                pstmt2.setString(4, nickname);
                pstmt2.executeUpdate();
                returns = "Successjoin"; //회원가입 성공
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt2 != null)try {pstmt2.close();    } catch (SQLException ex) {}
            if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
            if (conn != null)try {conn.close();    } catch (SQLException ex) {    }
        }
        return returns;
    }
    public String connectionloginDB(String id, String pwd) {
    	try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(jdbcUrl, userId, userPw);

            sql = "SELECT pwd FROM USERINFO WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            
            while(rs.next()) {//밑의 해당되는 비밀번호가 있을때 까지 반복 / 찾으면true 못찾으면 false
            	rs3 = rs.getString("pwd");//해당 아이디 비멀번호 찾기
            }
            
            sql2 = "SELECT id FROM USERINFO WHERE id = ?";
            pstmt2 = conn.prepareStatement(sql2);
            pstmt2.setString(1,id);
            rs2 = pstmt.executeQuery();
            if(rs2.next()) {//해당 아이디가 있을경우 조건 만족	
	            if(rs3.equals(pwd)) {//위에서 디비안의 해당 아이디에 대한 비밀번호와 앱에서 보내는 비밀번호 비교
	               	returns ="Successlogin";
	            }
	            else {
	               	returns = "Wrongpw";
	            }
            }
            else {//해당 아이디가 없을경우 조건 만족            
                returns = "NoId"; //ID 없음
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt2 != null)try {pstmt2.close();    } catch (SQLException ex) {}
            if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
            if (conn != null)try {conn.close();    } catch (SQLException ex) {}
        }
        return returns;
    }
    
}
