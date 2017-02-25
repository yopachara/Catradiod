package com.yopachara.catradiod.ui.base

import com.yopachara.catradiod.ui.base.MvpPresenter
import com.yopachara.catradiod.ui.base.MvpView

/**
 * Base class that implements the Presenter interface and provides a base implementation for
 * attachView() and detachView(). It also handles keeping a reference to the [_view] that
 * can be accessed from the children classes by calling [view].
 */
open class BaseMvpPresenter<T : MvpView> : MvpPresenter<T> {

    private var _view: T? = null
    val view: T
        get() { return _view ?: throw MvpViewNotAttachedException() }

    override fun attachView(view: T) {
        _view = view
    }

    override fun detachView() {
        _view = null
    }

    class MvpViewNotAttachedException : RuntimeException(
            "Please call Presenter.attachView(MvpView) before requesting data to the Presenter")
}
