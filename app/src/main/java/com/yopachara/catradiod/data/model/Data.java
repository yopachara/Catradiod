
package com.yopachara.catradiod.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("MONDAY")
    @Expose
    private List<Program> monday = null;
    @SerializedName("TUESDAY")
    @Expose
    private List<Program> tuesday = null;
    @SerializedName("WEDNESDAY")
    @Expose
    private List<Program> wednesday = null;
    @SerializedName("THURSDAY")
    @Expose
    private List<Program> thursday = null;
    @SerializedName("FRIDAY")
    @Expose
    private List<Program> friday = null;
    @SerializedName("SATURDAY")
    @Expose
    private List<Program> saturday = null;
    @SerializedName("SUNDAY")
    @Expose
    private List<Program> sunday = null;

    public List<Program> getMonday() {
        return monday;
    }

    public void setMonday(List<Program> monday) {
        this.monday = monday;
    }

    public List<Program> getTuesday() {
        return tuesday;
    }

    public void setTuesday(List<Program> tuesday) {
        this.tuesday = tuesday;
    }

    public List<Program> getWednesday() {
        return wednesday;
    }

    public void setWednesday(List<Program> wednesday) {
        this.wednesday = wednesday;
    }

    public List<Program> getThursday() {
        return thursday;
    }

    public void setThursday(List<Program> thursday) {
        this.thursday = thursday;
    }

    public List<Program> getFriday() {
        return friday;
    }

    public void setFriday(List<Program> friday) {
        this.friday = friday;
    }

    public List<Program> getSaturday() {
        return saturday;
    }

    public void setSaturday(List<Program> saturday) {
        this.saturday = saturday;
    }

    public List<Program> getSunday() {
        return sunday;
    }

    public void setSunday(List<Program> sunday) {
        this.sunday = sunday;
    }

    public void setSUNDAY(List<Program> sUNDAY) {
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
                ", program=" + sunday +
                '}';
    }
}
