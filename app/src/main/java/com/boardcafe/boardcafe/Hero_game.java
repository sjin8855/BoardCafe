package com.boardcafe.boardcafe;

public class Hero_game {
    String name;
    String time;
    String intro;
    String person_number;

    public Hero_game(){
        name = null;
        time = null;
        intro = null;
        person_number = null;
    }

    public Hero_game(String intro,String person_number,String time){
        this.intro = intro;
        this.person_number = person_number;
        this.time = time;

    }

    public String getname(){return this.name;}
    public String gettime(){return this.time;}
    public String getintro(){return this.intro;}
    public String getperson_number(){return this.person_number;}
    public void setName(String name){this.name = name;}
    public void setTime(String time){this.time = time;}
    public void setIntro(String intro){this.intro = intro;}
    public void setPerson_number(String person_number){this.person_number = person_number;}
}
