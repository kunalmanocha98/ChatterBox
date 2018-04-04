package com.example.android.chatterbox;

/**
 * Created by Kunal on 23-Jul-17.
 */

public class UserInformation {

    private String fname;
    private String lname;
    private String reg;
    private String eml;
    private String pass;



    public UserInformation(String fname, String lname, String reg, String eml,String pass) {
        this.eml = eml;
        this.fname = fname;
        this.lname = lname;
        this.reg=reg;
        this.pass=pass;
    }



}
