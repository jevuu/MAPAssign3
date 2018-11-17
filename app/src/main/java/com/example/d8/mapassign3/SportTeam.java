package com.example.d8.mapassign3;

public class SportTeam {
    private int id;
    private String name;
    private String sport;
    private String city;
    private String stadium;
    private String mvp;

    public SportTeam(){
        this.id         = 0;
        this.name       = "";
        this.sport      = "";
        this.city       = "";
        this.stadium    = "";
        this.mvp        = "";
    };

    public SportTeam(int id, String name, String sport, String city, String stadium, String mvp){
        this.id         = id;
        this.name       = name;
        this.sport      = sport;
        this.city       = city;
        this.stadium    = stadium;
        this.mvp        = mvp;
    }

    public int getId() { return id; }

    public String getName(){ return name; }

    public String getSport() {return sport; }

    public String getCity(){
        return city;
    }

    public String getStadium() { return stadium; }

    public String getMVP()  { return mvp; }

    public String toString() {return "" + id + ", " + name + ", " + sport  + ", " + city + ", " + stadium + ", " + mvp; }
}
