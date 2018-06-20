package com.boardcafe.boardcafe;

public class Table_information {
    String table_id;
    String reservation;

    public Table_information(){
        table_id = null;
        reservation = "false";
    }
    public Table_information(String table_id){
        this.table_id = table_id;
        this.reservation = "true";
    }
    public String getTable_id(){return this.table_id;}
    public String getReservation(){return this.reservation;}
    public void setTable_id(String table_name){this.table_id = table_name;}
    public void setReservation(String re_check){this.reservation = re_check;}

}
