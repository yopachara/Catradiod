package com.yopachara.catradiod.ui.main

import com.yopachara.catradiod.ui.base.BaseMvpPresenter
import com.yopachara.catradiod.ui.base.MvpView

object MainContract {

    interface View: MvpView {
//        fun showRibots(ribots: List<Ribot>)
        fun showRibotsEmpty()
        fun showError()
    }

    abstract class Presenter: BaseMvpPresenter<View>() {
        abstract fun loadRibots()
    }
}
