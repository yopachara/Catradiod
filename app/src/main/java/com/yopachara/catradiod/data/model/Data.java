
package com.yopachara.catradiod.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("Monday")
    @Expose
    private List<Monday> mONDAY = null;
    @SerializedName("Tuesday")
    @Expose
    private List<Tuesday> tUESDAY = null;
    @SerializedName("Wednesday")
    @Expose
    private List<Wednesday> wEDNESDAY = null;
    @SerializedName("Thursday")
    @Expose
    private List<Thursday> tHURSDAY = null;
    @SerializedName("Friday")
    @Expose
    private List<Friday> fRIDAY = null;
    @SerializedName("Saturday")
    @Expose
    private List<Saturday> sATURDAY = null;
    @SerializedName("Sunday")
    @Expose
    private List<Sunday> sUNDAY = null;

    public List<Monday> getMONDAY() {
        return mONDAY;
    }

    public void setMONDAY(List<Monday> mONDAY) {
        this.mONDAY = mONDAY;
    }

    public List<Tuesday> getTUESDAY() {
        return tUESDAY;
    }

    public void setTUESDAY(List<Tuesday> tUESDAY) {
        this.tUESDAY = tUESDAY;
    }

    public List<Wednesday> getWEDNESDAY() {
        return wEDNESDAY;
    }

    public void setWEDNESDAY(List<Wednesday> wEDNESDAY) {
        this.wEDNESDAY = wEDNESDAY;
    }

    public List<Thursday> getTHURSDAY() {
        return tHURSDAY;
    }

    public void setTHURSDAY(List<Thursday> tHURSDAY) {
        this.tHURSDAY = tHURSDAY;
    }

    public List<Friday> getFRIDAY() {
        return fRIDAY;
    }

    public void setFRIDAY(List<Friday> fRIDAY) {
        this.fRIDAY = fRIDAY;
    }

    public List<Saturday> getSATURDAY() {
        return sATURDAY;
    }

    public void setSATURDAY(List<Saturday> sATURDAY) {
        this.sATURDAY = sATURDAY;
    }

    public List<Sunday> getSUNDAY() {
        return sUNDAY;
    }

    public void setSUNDAY(List<Sunday> sUNDAY) {
        this.sUNDAY = sUNDAY;
    }

}
