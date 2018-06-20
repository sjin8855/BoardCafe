package com.boardcafe.boardcafe;

public class Reservation_information {
    String user_email;
    String time;
    long cost;
    String table_id;
    String number;
    String reservation_id;
    public Reservation_information(){
        user_email = null;
        time = null;
        cost = 0;
        table_id = null;
        number = null;
        reservation_id = null;
    }

    public Reservation_information(String user_email,String time, long cost, String table_id,String number,String reservation_id){
        this.user_email = user_email;
        this.time = time;
        this.cost = cost;
        this.table_id = table_id;
        this.number = number;
        this.reservation_id = reservation_id;
    }
    public String getUID(){return this.user_email;}
    public String getReservation_time(){return this.time;}
    public long getReservation_cost(){return this.cost;}
    public String getTable_name(){return this.table_id;}
    public String get_Number(){return this.number;}
    public String getReservation_id(){return this.reservation_id;}
    public void setUID(String UID){this.user_email = user_email;}
    public void setReservation_time(String time){this.time = time;}
    public void setReservation_cost(long cost){this.cost = cost;}
    public void setTable_name(String table_id){this.table_id = table_id;}
    public void setNumber(String number){this.number = number;}
    public void setReservation_id(String reservation_id){this.reservation_id = reservation_id;}
}
