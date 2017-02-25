
package com.yopachara.catradiod.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DJ {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("short_name")
    @Expose
    private String shortName;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("DJimg")
    @Expose
    private String djImg;
    @SerializedName("status")
    @Expose
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDjImg() {
        return djImg;
    }

    public void setDjImg(String djImg) {
        this.djImg = djImg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
