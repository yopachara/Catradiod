package com.yopachara.catradiod.ui.about

import android.app.DialogFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.yopachara.catradiod.R

class MyDialogFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle): View? {
        val v = inflater.inflate(R.layout.hello_world, container, false)
        val tv = v.findViewById(R.id.text)
        (tv as TextView).text = "This is an instance of MyDialogFragment"
        return v
    }

    companion object {
        internal fun newInstance(): MyDialogFragment {
            return MyDialogFragment()
        }
    }
}