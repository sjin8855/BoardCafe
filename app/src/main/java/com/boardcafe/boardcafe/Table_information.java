package com.boardcafe.boardcafe;

public class Table_information {
    String table_id;
    String reservation;
    String number;

    public Table_information(){
        table_id = null;
        reservation = "false";
        number = null;
    }
    public Table_information(String table_id,String number){
        this.table_id = table_id;
        this.reservation = "true";
        this.number = number;
    }
    public String getTable_id(){return this.table_id;}
    public String getReservation(){return this.reservation;}
    public String getNumber(){return this.number;}
    public void setTable_id(String table_name){this.table_id = table_name;}
    public void setReservation(String re_check){this.reservation = re_check;}
    public void setNumber(String number){this.number = number;}
}
