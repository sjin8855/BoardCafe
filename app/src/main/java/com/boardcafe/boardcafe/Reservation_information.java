package com.boardcafe.boardcafe;

public class Reservation_information {
    String user_email;
    String time;
    long cost;
    String table_id;
    String number;
    public Reservation_information(){
        user_email = null;
        time = null;
        cost = 0;
        table_id = null;
        number = null;
    }

    public Reservation_information(String user_email,String time, long cost, String table_id,String number){
        this.user_email = user_email;
        this.time = time;
        this.cost = cost;
        this.table_id = table_id;
        this.number = number;
    }
    public String getUser_email(){return this.user_email;}
    public String getTime(){return this.time;}
    public long getCost(){return this.cost;}
    public String getTable_id(){return this.table_id;}
    public String getNumber(){return this.number;}
    public void setUser_email(String user_email){this.user_email = user_email;}
    public void setTime(String time){this.time = time;}
    public void setCost(long cost){this.cost = cost;}
    public void setTable_id(String table_id){this.table_id = table_id;}
    public void setNumber(String number){this.number = number;}
}
