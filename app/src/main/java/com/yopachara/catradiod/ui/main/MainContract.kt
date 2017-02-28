package com.yopachara.catradiod.ui.main

import com.yopachara.catradiod.data.remote.model.Cat
import com.yopachara.catradiod.ui.base.BaseMvpPresenter
import com.yopachara.catradiod.ui.base.MvpView

object MainContract {

    interface View: MvpView {
//        fun showRibots(ribots: List<Ribot>)
        fun showRibotsEmpty()
        fun showSong(cat : Cat)
    }

    abstract class Presenter: BaseMvpPresenter<View>() {
        abstract fun loadRibots()
    }
}
