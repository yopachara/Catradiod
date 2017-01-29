package com.yopachara.catradiod.ui.main

import com.yopachara.catradiod.data.remote.model.Cat
import com.yopachara.catradiod.ui.base.AbstractViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(val repo: Cat) : AbstractViewModel() {

//    fun getName() = repo.fullName
//
//    fun getDescription() = repo.description
//
//    fun getStars() = repo.stars.toString()
//
//    fun getForks() = repo.forks.toString()
//
//    fun getAvatarURL() = repo.owner.avatarUrl

}