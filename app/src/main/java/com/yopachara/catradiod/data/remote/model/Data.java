
package com.yopachara.catradiod.data.remote.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("MONDAY")
    @Expose
    private List<MONDAY> mONDAY = null;
    @SerializedName("TUESDAY")
    @Expose
    private List<TUESDAY> tUESDAY = null;
    @SerializedName("WEDNESDAY")
    @Expose
    private List<WEDNESDAY> wEDNESDAY = null;
    @SerializedName("THURSDAY")
    @Expose
    private List<THURSDAY> tHURSDAY = null;
    @SerializedName("FRIDAY")
    @Expose
    private List<FRIDAY> fRIDAY = null;
    @SerializedName("SATURDAY")
    @Expose
    private List<SATURDAY> sATURDAY = null;
    @SerializedName("SUNDAY")
    @Expose
    private List<SUNDAY> sUNDAY = null;

    public List<MONDAY> getMONDAY() {
        return mONDAY;
    }

    public void setMONDAY(List<MONDAY> mONDAY) {
        this.mONDAY = mONDAY;
    }

    public List<TUESDAY> getTUESDAY() {
        return tUESDAY;
    }

    public void setTUESDAY(List<TUESDAY> tUESDAY) {
        this.tUESDAY = tUESDAY;
    }

    public List<WEDNESDAY> getWEDNESDAY() {
        return wEDNESDAY;
    }

    public void setWEDNESDAY(List<WEDNESDAY> wEDNESDAY) {
        this.wEDNESDAY = wEDNESDAY;
    }

    public List<THURSDAY> getTHURSDAY() {
        return tHURSDAY;
    }

    public void setTHURSDAY(List<THURSDAY> tHURSDAY) {
        this.tHURSDAY = tHURSDAY;
    }

    public List<FRIDAY> getFRIDAY() {
        return fRIDAY;
    }

    public void setFRIDAY(List<FRIDAY> fRIDAY) {
        this.fRIDAY = fRIDAY;
    }

    public List<SATURDAY> getSATURDAY() {
        return sATURDAY;
    }

    public void setSATURDAY(List<SATURDAY> sATURDAY) {
        this.sATURDAY = sATURDAY;
    }

    public List<SUNDAY> getSUNDAY() {
        return sUNDAY;
    }

    public void setSUNDAY(List<SUNDAY> sUNDAY) {
        this.sUNDAY = sUNDAY;
    }

}
