package com.example.myapplication.Fragment.Menu4;



public class ItemData{
    String id;
    String boardid;
    String boardname;
    String title;
    String time;
    String imgpath;
    String content;
    String likecount;
    String userprofileimage;
    String toggle;
    //String likeBoolean;

    public ItemData(){

    }
    public String getId(){return id; }
    public void setId(String id){this.id = id; }

    public String getBoardId(){return boardid; }
    public void setBoardId(String boardid){this.boardid = boardid; }

    public String getBoardDB(){return boardname; }
    public void setBoardDB(String boardname){this.boardname = boardname; }

    public String getTitle(){ return title; }
    public void setTitle(String title){ this.title = title; }

    public String getContent(){ return content; }
    public void setContent(String content){ this.content = content; }

    public String getImgPath(){ return imgpath; }
    public void setImgPath(String imgpath){ this.imgpath = imgpath; }

    public String getTime(){ return time; }
    public void setTime(String time){ this.time = time; }

    public String getLikeCount(){ return likecount; }
    public void setLikeCount(String likecount){this.likecount = likecount; }

    public String getUserProFileImage(){ return userprofileimage; }
    public void setUserProFileImage(String userprofileimage){ this.userprofileimage = userprofileimage; }

    public String getToggle(){ return toggle; }
    public void setToggle(String toggle){ this.toggle = toggle; }


}
