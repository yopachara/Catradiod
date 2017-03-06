package com.yopachara.catradiod.ui.main

import com.yopachara.catradiod.data.model.DJ
import com.yopachara.catradiod.data.model.DjSchedule
import com.yopachara.catradiod.data.model.Program
import com.yopachara.catradiod.data.remote.model.Cat
import com.yopachara.catradiod.ui.base.BaseMvpPresenter
import com.yopachara.catradiod.ui.base.MvpView

object MainContract {

    interface View: MvpView {
//        fun showRibots(ribots: List<Ribot>)
        fun showRibotsEmpty()
        fun showSong(cat : Cat)
        fun showDj(dj: Program)
//        fun showFilterDialog()
//        fun reloadSearchFilter()
    }

    abstract class Presenter: BaseMvpPresenter<View>() {
        abstract fun loadRibots()
        abstract fun syncDj()
    }
}
