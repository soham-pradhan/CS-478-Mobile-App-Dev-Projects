package com.example.project2;

public class Model {
    private String header;
    public String desc;
    private int imgname;

    //Defining all the objects get and set methods, data filled in main activiy

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getImgname() {
        return imgname;
    }

    public void setImgname(int imgname) {
        this.imgname = imgname;
    }

}
