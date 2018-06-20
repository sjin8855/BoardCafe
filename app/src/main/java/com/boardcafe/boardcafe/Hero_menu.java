package com.boardcafe.boardcafe;

public class Hero_menu {
    String category;
    long cost;
    String name;

    public Hero_menu(){
        category = null;
        cost = 0;
        name = null;
    }

    public Hero_menu(String category,long cost,String name){
        this.category = category;
        this.cost = cost;
        this.name = name;

    }


    public String getCategory(){return this.category;}
    public long getcost(){return this.cost;}
    public String getname(){return this.name;}
    public void setCategory(String category){this.category = category;}
    public void setcost(long cost){this.cost = cost;}
    public void setname(String name){this.name = name;}


}
