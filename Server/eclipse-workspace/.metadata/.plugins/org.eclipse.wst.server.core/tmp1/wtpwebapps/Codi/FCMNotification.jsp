<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.google.android.gcm.server.*" %>
 

<%
/*
    ArrayList<String> token = new ArrayList<String>();    //token값을 ArrayList에 저장
    String MESSAGE_ID = String.valueOf(Math.random() % 100 + 1);    //메시지 고유 ID
    boolean SHOW_ON_IDLE = false;    //옙 활성화 상태일때 보여줄것인지
    int LIVE_TIME = 1;    //옙 비활성화 상태일때 FCM가 메시지를 유효화하는 시간입니다.
    int RETRY = 2;    //메시지 전송에 실패할 시 재시도 횟수입니다.
 
    
    String simpleApiKey = "AAAADifFQ8c:APA91bFl4P2H2X6K0YMaZWPHvgE2UM718HklCUrI6E8RhM89Y5_szHnItTB-h4BRhJ6WDbWoxFZZMaj8lcbM58xN_AuWpun16urYyFJIxAJjAIQaNtwwS_SV2UjP4fxoMgiYAz9LID8Z";
    String gcmURL = "https://android.googleapis.com/fcm/send";    
    //Connection conn = null; 
    //PreparedStatement stmt = null; 
    //ResultSet rs = null;

    try {

    	//String jdbcUrl = "jdbc:mysql://localhost:3306/"'database이름'"; // MySQL 계정
    	//String dbId = "userId"; // MySQL 계정
    	//String dbPw = "userPwd"; // 비밀번호        
        //String sql = "sql문"; // 등록된 token을 찾아오도록 하는 sql문
	
		//Class.forName("com.mysql.jdbc.Driver");
		//conn = DriverManager.getConnection(jdbcUrl, dbId, dbPw);
        //stmt = conn.prepareStatement(sql);    
        //rs = stmt.executeQuery();
        
        //모든 등록ID를 리스트로 묶음
        //while(rs.next()){
       token.add("eYxAWyFaAHg:APA91bGWMYcXsBeLbltj87Ot_YCXQVETd0Hk3eWzOZrtorF16osSai3V4YCaDvQvC11tv49KwyHtJHEYxtSG81eklBaqSX0KvIQp1gZ9x0H5fzc3IJ1823mZ4ZTsVjOYPLnT-NZKUwbL"); //저장된 토큰을 가져와 ArrayList에 저장합니다.
        //}
        //conn.close();
        request.setCharacterEncoding("utf-8");
    	String title = new String("Udoby".getBytes("UTF-8"),"UTF-8");    
        String msg = new String("mungchungE".getBytes("UTF-8"), "UTF-8");   //메시지 한글깨짐 처리  // msg.jsp 에서 전달받은 메시지

        out.print(msg);
        Sender sender = new Sender(simpleApiKey);
        Message message = new Message.Builder()
        .collapseKey(MESSAGE_ID)
        .delayWhileIdle(SHOW_ON_IDLE)
        .timeToLive(LIVE_TIME)
        .addData("body",msg)
        .addData("title",title)
        .build();
//위의 addData의 키 값인 "message" 와 "title" 안드로이드의 FirebaseMessagingService 에서
//받은 message , title과 일치해야 합니다.

     MulticastResult result1 = sender.send(message,token,RETRY);
   	    if (result1 != null) {
           List<Result> resultList = result1.getResults();
           for (Result result : resultList) {
               System.out.println(result.getErrorCodeName()); 
           }
       }
    }catch (Exception e) {
        e.printStackTrace();
    }
    
    System.out.println("핸드폰 메세지 전송중..");
    */
%>





