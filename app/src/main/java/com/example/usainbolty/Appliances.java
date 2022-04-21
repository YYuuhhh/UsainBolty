package com.example.usainbolty;

public class Appliances {
    private String text;
    private String subtext;
    private int multiplier;
    private int image;
    public Appliances (String text, String subtext, int multiplier, int image){
        this.text = text;
        this.subtext = subtext;
        this.multiplier = multiplier;
        this.image = image;
    }
    public String gettext (){
        return text;
    }
    public String getsubtext (){
        return subtext;
    }
    public int getmultiplier (){
        return multiplier;
    }
    public int getimage (){
        return image;
    }
}
