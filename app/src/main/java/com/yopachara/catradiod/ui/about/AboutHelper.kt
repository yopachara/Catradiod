package com.yopachara.catradiod.ui.about

import android.content.Intent
import com.vansuita.materialabout.builder.AboutBuilder
import android.widget.FrameLayout
import android.app.Activity
import android.view.View
import com.yopachara.catradiod.R


/**
 * Created by yopachara on 3/4/2017 AD.
 */

class AboutHelper private constructor(private val activity: Activity)  {

    fun init(): AboutHelper {
        activity.setTheme(theme)


        return this
    }

    fun loadAbout() {
        val flHolder = activity.findViewById(R.id.about) as FrameLayout

        flHolder.addView(
                AboutBuilder.with(activity)
                        .setAppIcon(R.drawable.ic_launcher_app)
                        .setAppName(R.string.app_name)
                        .setPhoto(R.drawable.img_profile)
                        .setCover(R.drawable.img_artboard)
                        .setLinksAnimated(true)
                        .setDividerDashGap(13)
                        .setName("Pachara Sukcharoen")
                        .setSubTitle("Android Developer")
                        .setLinksColumnsCount(4)
                        .setBrief("I'm just a past time magicians")
//                        .addGooglePlayStoreLink("13589059248328602485")
                        .addGitHubLink("yopachara")
                        .addFacebookLink("100001421231203")
                        .addTwitterLink("2863936368")
                        .addInstagramLink("yopachara")
                        .addLinkedInLink("pachara-sukcharoen-74400011b")
                        .addEmailLink("yopachara@gmail.com")
                        .addGoogleLink("yopachara")
                        .addFiveStarsAction()
                        .addMoreFromMeAction("Pachara Sukcharoen")
                        .setVersionNameAsAppSubTitle()
                        .addShareAction(R.string.app_name)
                        .addUpdateAction()
                        .setActionsColumnsCount(2)
                        .addFeedbackAction("yopachara@gmail.com")
//                        .addIntroduceAction(null as Intent?)
//                        .addHelpAction(null as Intent?)
//                        .addChangeLogAction(null as Intent?)
//                        .addRemoveAdsAction(null as Intent?)
//                        .addDonateAction(null as Intent?)
                        .setWrapScrollView(true)
                        .build())
    }



    companion object {
        private var theme = R.style.AppThemeLight

        fun with(activity: Activity): AboutHelper {
            return AboutHelper(activity)
        }
    }
}