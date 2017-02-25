package com.yopachara.catradiod.data.remote.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Cat {


    @SerializedName("now")
    @Expose
    var now: Now? = null

    @SerializedName("next")
    @Expose
    var next: Next? = null

    override fun toString(): String {
        return now.toString() +"\n"+next.toString()
    }


}
