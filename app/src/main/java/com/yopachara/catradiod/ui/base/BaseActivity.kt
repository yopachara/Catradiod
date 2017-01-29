package com.yopachara.catradiod.ui.base

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v7.app.AppCompatActivity
import com.yopachara.catradiod.ApplicationComponent
import com.yopachara.catradiod.CatradiodAPP

abstract class BaseActivity: AppCompatActivity() {

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        injectDependencies(CatradiodAPP.graph)
    }

    abstract fun injectDependencies(graph: ApplicationComponent)
}