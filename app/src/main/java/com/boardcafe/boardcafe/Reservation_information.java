package com.boardcafe.boardcafe;

public class Reservation_information {
    String user_email;
    String time;
    String cost;
    String table_id;
    public Reservation_information(){
        user_email = null;
        time = null;
        cost = null;
        table_id = null;
    }

    public Reservation_information(String user_email,String time, String cost, String table_id){
        this.user_email = user_email;
        this.time = time;
        this.cost = cost;
        this.table_id = table_id;
    }
    public String getUID(){return this.user_email;}
    public String getReservation_time(){return this.time;}
    public String getReservation_cost(){return this.cost;}
    public String getTable_name(){return this.table_id;}
    public void setUID(String UID){this.user_email = user_email;}
    public void setReservation_time(String time){this.time = time;}
    public void setReservation_cost(String cost){this.cost = cost;}
    public void setTable_name(String table_id){this.table_id = table_id;}
}
