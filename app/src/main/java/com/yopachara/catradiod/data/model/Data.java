
package com.yopachara.catradiod.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("MONDAY")
    @Expose
    private List<Monday> monday = null;
    @SerializedName("TUESDAY")
    @Expose
    private List<Tuesday> tuesday = null;
    @SerializedName("WEDNESDAY")
    @Expose
    private List<Wednesday> wednesday = null;
    @SerializedName("THURSDAY")
    @Expose
    private List<Thursday> thursday = null;
    @SerializedName("FRIDAY")
    @Expose
    private List<Friday> friday = null;
    @SerializedName("SATURDAY")
    @Expose
    private List<Saturday> saturday = null;
    @SerializedName("SUNDAY")
    @Expose
    private List<Sunday> sunday = null;

    public List<Monday> getMONDAY() {
        return monday;
    }

    public void setMONDAY(List<Monday> mONDAY) {
        this.monday = mONDAY;
    }

    public List<Tuesday> getTUESDAY() {
        return tuesday;
    }

    public void setTUESDAY(List<Tuesday> tUESDAY) {
        this.tuesday = tUESDAY;
    }

    public List<Wednesday> getWEDNESDAY() {
        return wednesday;
    }

    public void setWEDNESDAY(List<Wednesday> wEDNESDAY) {
        this.wednesday = wEDNESDAY;
    }

    public List<Thursday> getTHURSDAY() {
        return thursday;
    }

    public void setTHURSDAY(List<Thursday> tHURSDAY) {
        this.thursday = tHURSDAY;
    }

    public List<Friday> getFRIDAY() {
        return friday;
    }

    public void setFRIDAY(List<Friday> fRIDAY) {
        this.friday = fRIDAY;
    }

    public List<Saturday> getSATURDAY() {
        return saturday;
    }

    public void setSATURDAY(List<Saturday> sATURDAY) {
        this.saturday = sATURDAY;
    }

    public List<Sunday> getSUNDAY() {
        return sunday;
    }

    public void setSUNDAY(List<Sunday> sUNDAY) {
        this.sunday = sUNDAY;
    }

    @Override
    public String toString() {
        return "Data{" +
                "monday=" + monday +
                ", tuesday=" + tuesday +
                ", wednesday=" + wednesday +
                ", thursday=" + thursday +
                ", friday=" + friday +
                ", saturday=" + saturday +
                ", sunday=" + sunday +
                '}';
    }
}
