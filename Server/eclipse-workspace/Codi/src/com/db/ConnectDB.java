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

    // oracle ����
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
                returns = "existence"; //ID�� �̹� �����Ѵ�.
            } else {
                sql2 = "INSERT INTO USERINFO VALUES(?,?,?,?)";
                pstmt2 = conn.prepareStatement(sql2);
                pstmt2.setString(1, id);
                pstmt2.setString(2, pwd);
                pstmt2.setString(3, name);
                pstmt2.setString(4, nickname);
                pstmt2.executeUpdate();
                returns = "Successjoin"; //ȸ������ ����
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
            
            while(rs.next()) {//���� �ش�Ǵ� ��й�ȣ�� ������ ���� �ݺ� / ã����true ��ã���� false
            	rs3 = rs.getString("pwd");//�ش� ���̵� ��ֹ�ȣ ã��
            }
            
            sql2 = "SELECT id FROM USERINFO WHERE id = ?";
            pstmt2 = conn.prepareStatement(sql2);
            pstmt2.setString(1,id);
            rs2 = pstmt.executeQuery();
            if(rs2.next()) {//�ش� ���̵� ������� ���� ����	
	            if(rs3.equals(pwd)) {//������ ������ �ش� ���̵� ���� ��й�ȣ�� �ۿ��� ������ ��й�ȣ ��
	               	returns ="Successlogin";
	            }
	            else {
	               	returns = "Wrongpw";
	            }
            }
            else {//�ش� ���̵� ������� ���� ����            
                returns = "NoId"; //ID ����
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
