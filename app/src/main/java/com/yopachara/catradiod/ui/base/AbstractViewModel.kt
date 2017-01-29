package com.yopachara.catradiod.ui.base

import android.databinding.BaseObservable
import com.yopachara.catradiod.ui.base.ViewModel

abstract class AbstractViewModel : BaseObservable(), ViewModel {

    override fun bind() {
    }

    override fun unbind() {
    }

    override fun onDestroy() {
        // Hook for subclasses to clean up used resources
    }
}