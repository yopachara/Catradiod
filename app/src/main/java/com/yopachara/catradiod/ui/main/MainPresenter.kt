package com.yopachara.catradiod.ui.main

import com.yopachara.catradiod.data.DataManager
import com.yopachara.catradiod.data.model.DJ
import com.yopachara.catradiod.data.model.DjSchedule
import com.yopachara.catradiod.data.model.Program
import com.yopachara.catradiod.data.remote.model.Cat
import com.yopachara.catradiod.injection.ConfigPersistent
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import javax.inject.Inject

import rx.lang.kotlin.FunctionSubscriber
import rx.lang.kotlin.addTo
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

@ConfigPersistent
class MainPresenter
@Inject
constructor(private val dataManager: DataManager) : MainContract.Presenter() {

    private val compositeSubscription = CompositeSubscription()
    final val DEFAULT_PATTERN = "HH:mm:ss"

    override fun detachView() {
        super.detachView()
        compositeSubscription.clear()
    }

    fun millis2String(millis: Long): String {
        return SimpleDateFormat(DEFAULT_PATTERN, Locale.getDefault()).format(Date(millis))
    }

    var dj = dataManager.getPreferenceHelper().getDjSchedule()

    fun getSchedule(dj: DjSchedule) {
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_WEEK)
        when (day) {
            Calendar.SUNDAY -> {
                view.showDj(getProgram(dj.data.sunday))
            }
            Calendar.MONDAY -> {
                view.showDj(getProgram(dj.data.monday))
            }
            Calendar.TUESDAY -> {
                view.showDj(getProgram(dj.data.tuesday))
            }
            Calendar.WEDNESDAY -> {
                view.showDj(getProgram(dj.data.wednesday))
            }
            Calendar.THURSDAY -> {
                view.showDj(getProgram(dj.data.thursday))
            }
            Calendar.FRIDAY -> {
                view.showDj(getProgram(dj.data.friday))
            }
            Calendar.SATURDAY -> {
                view.showDj(getProgram(dj.data.saturday))
            }
        }
    }

    fun getProgram(programs: List<Program>): Program {
        val calendar = Calendar.getInstance()
        val time = millis2String(calendar.timeInMillis).split(":")
        for (program in programs) {
            var shiftStartHr = program.shiftStart.split(":")[0]
            val shiftEndHr = program.shiftEnd.split(":")[0]
            var shiftStartMin = program.shiftStart.split(":")[1]
            val shiftEndMin = program.shiftEnd.split(":")[1]

            if (Integer.parseInt(shiftStartHr) == 24) {
                shiftStartHr = "0"
            }
            if (time[0] in shiftStartHr..shiftEndHr && shiftEndHr != time[0]) {
                Timber.d(program.toString())
                return program
            } else if (time[0] in shiftStartHr..shiftEndHr && shiftEndHr == time[0] && time[1] <= "00") {
                return program
            }
        }
        return programs[programs.lastIndex]

    }

    override fun syncDj() {
        if (dj.data != null) {
            getSchedule(dj)
        } else {
            dataManager.getSchedule()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        dj ->
                        run {
                            getSchedule(dj)
//                            Timber.d(dj.toString())
//                            view.showDj(dj)
                        }
                    }, {
                        e ->
                        Timber.e(e)
                    })
        }
    }

    override fun loadRibots() {
        dataManager.getSong()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .repeatWhen { completed -> completed.delay(1, TimeUnit.MINUTES) }
                .subscribe({
                    cat ->
                    Timber.d(cat.toString())
                    view.showSong(cat)
                    //                            if (it.isEmpty()) view.showRibotsEmpty() else view.showRibots(it)
                }, {
                    Timber.e(it, "There was an error loading the ribots.")
                }
                ).addTo(compositeSubscription)
    }

}
