package com.example.d8.mapassign3;

import android.net.Uri;

public class SportTeam {
    private int id;
    private String name;
    private String sport;
    private String city;
    private String mvp;
    private Uri logoUri;

    public SportTeam(){
        this.id         = 0;
        this.name       = "";
        this.sport      = "";
        this.city       = "";
        this.mvp        = "";
        this.logoUri    = Uri.parse("drawable/image_not_found");
    };

    public SportTeam(int id, String name, String sport, String city, String mvp, Uri logo){
        this.id         = id;
        this.name       = name;
        this.sport      = sport;
        this.city       = city;
        this.mvp        = mvp;
        this.logoUri    = logo;
    }

    public int getId() { return id; }

    public String getName(){ return name; }

    public String getSport() {return sport; }

    public String getCity(){
        return city;
    }

    public String getMVP()  { return mvp; }

    public Uri getLogoUri() {return logoUri; }

    public String toString() {return "" + id + ", " + name + ", " + sport  + ", " + city + ", " + mvp + ", " + logoUri.toString(); }
}
