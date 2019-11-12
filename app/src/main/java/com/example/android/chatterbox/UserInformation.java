package com.example.android.chatterbox;

import java.io.Serializable;

/**
 * Created by Kunal on 23-Jul-17.
 */

public class UserInformation implements Serializable {

    public String fname;
    public String lname;
    public String reg;
    public String eml;
    public String pass;



    public UserInformation(String fname, String lname, String reg, String eml,String pass) {
        this.eml = eml;
        this.fname = fname;
        this.lname = lname;
        this.reg=reg;
        this.pass=pass;
    }



}
