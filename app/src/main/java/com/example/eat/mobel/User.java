package com.example.eat.mobel;

import java.io.Serializable;

public class User implements Serializable {
    private static User theUser = null;
    public String Username;
    public String userEmail;
    public String userId;
    public String profileImageUrl;
    public String userInfo;


    private User(){
        Username=null;
        userEmail=null;
        userId=null;
        profileImageUrl=null;
        userInfo=null;
    }
    public User(String Name,String email) {
        this.Username = Name;
        this.userEmail=email;

    }
    public static User getInstance()
    {
        if (theUser == null)
            theUser = new User();

        return theUser;
    }

}
