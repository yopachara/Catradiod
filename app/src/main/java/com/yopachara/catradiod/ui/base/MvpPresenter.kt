package com.yopachara.catradiod.ui.base

import com.yopachara.catradiod.ui.base.MvpView

/**
 * Every presenter in the app must either implement this interface or extend BasePresenter
 * indicating the MvpView type that wants to be attached with.
 */
interface MvpPresenter<in V : MvpView> {
    fun attachView(view: V)
    fun detachView()
}
