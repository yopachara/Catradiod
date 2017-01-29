package com.yopachara.catradiod.data.remote.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Now {

    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("song")
    @Expose
    var song: String? = null

    @SerializedName("slug")
    @Expose
    var slug: String? = null
    /**

     * @return
     * *     The album
     */
    /**

     * @param album
     * *     The album
     */
    @SerializedName("album")
    @Expose
    var album: String? = null
    /**

     * @return
     * *     The qikplayUrl
     */
    /**

     * @param qikplayUrl
     * *     The qikplay_url
     */
    @SerializedName("qikplay_url")
    @Expose
    var qikplayUrl: String? = null
    /**

     * @return
     * *     The uid
     */
    /**

     * @param uid
     * *     The uid
     */
    @SerializedName("uid")
    @Expose
    var uid: String? = null
    /**

     * @return
     * *     The name
     */
    /**

     * @param name
     * *     The name
     */
    @SerializedName("name")
    @Expose
    var name: String? = null
    /**

     * @return
     * *     The avatar
     */
    /**

     * @param avatar
     * *     The avatar
     */
    @SerializedName("avatar")
    @Expose
    var avatar: String? = null



}
