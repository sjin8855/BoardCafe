package com.boardcafe.boardcafe;

public class User {
    private String userEmail;
    private int userAccount;

    public User(String userEmail, int userAccount) {

        this.userEmail = userEmail;
        this.userAccount = userAccount;
    }

    public User() {

    }


    public  String getUserEmail() {
        return userEmail;
    }
    public void setuserEmail(String userEmail)
    {
        this.userEmail = userEmail;
    }
    public void setuserAccount(int userAccount)
    {
        this.userAccount = userAccount;
    }
    public  int getuserAccount() {
        return userAccount;
    }

}
