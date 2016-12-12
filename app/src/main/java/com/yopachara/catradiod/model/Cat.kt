package com.yopachara.catradiod.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Cat {


    @SerializedName("now")
    @Expose
    var now: Now? = null

    @SerializedName("next")
    @Expose
    var next: Next? = null

}
