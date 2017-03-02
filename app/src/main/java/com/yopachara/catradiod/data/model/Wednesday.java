
package com.yopachara.catradiod.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Wednesday {

    @SerializedName("shift_start")
    @Expose
    private String shiftStart;
    @SerializedName("shift_end")
    @Expose
    private String shiftEnd;
    @SerializedName("shift_thumb")
    @Expose
    private String shiftThumb;
    @SerializedName("short_desc")
    @Expose
    private String shortDesc;
    @SerializedName("shift_title")
    @Expose
    private String shiftTitle;
    @SerializedName("shift_desc")
    @Expose
    private String shiftDesc;
    @SerializedName("DJ")
    @Expose
    private List<DJ> dJ = null;

    public String getShiftStart() {
        return shiftStart;
    }

    public void setShiftStart(String shiftStart) {
        this.shiftStart = shiftStart;
    }

    public String getShiftEnd() {
        return shiftEnd;
    }

    public void setShiftEnd(String shiftEnd) {
        this.shiftEnd = shiftEnd;
    }

    public String getShiftThumb() {
        return shiftThumb;
    }

    public void setShiftThumb(String shiftThumb) {
        this.shiftThumb = shiftThumb;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getShiftTitle() {
        return shiftTitle;
    }

    public void setShiftTitle(String shiftTitle) {
        this.shiftTitle = shiftTitle;
    }

    public String getShiftDesc() {
        return shiftDesc;
    }

    public void setShiftDesc(String shiftDesc) {
        this.shiftDesc = shiftDesc;
    }

    public List<DJ> getDJ() {
        return dJ;
    }

    public void setDJ(List<DJ> dJ) {
        this.dJ = dJ;
    }

    @Override
    public String toString() {
        return "Wednesday{" +
                "shiftStart='" + shiftStart + '\'' +
                ", shiftEnd='" + shiftEnd + '\'' +
                ", shiftThumb='" + shiftThumb + '\'' +
                ", shortDesc='" + shortDesc + '\'' +
                ", shiftTitle='" + shiftTitle + '\'' +
                ", shiftDesc='" + shiftDesc + '\'' +
                ", dJ=" + dJ +
                '}';
    }
}
