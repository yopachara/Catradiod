package com.yopachara.catradiod.injection.component

import com.yopachara.catradiod.injection.component.ActivityComponent
import com.yopachara.catradiod.injection.component.ApplicationComponent
import dagger.Component
import com.yopachara.catradiod.injection.ConfigPersistent
import com.yopachara.catradiod.injection.module.ActivityModule

/**
 * A dagger component that will live during the lifecycle of an Activity but it won't
 * be destroy during configuration changes. Check [BaseActivity] to see how this components
 * survives configuration changes.
 * Use the [ConfigPersistent] scope to annotate dependencies that need to survive
 * configuration changes (for example Presenters).
 */
@ConfigPersistent
@Component(dependencies = arrayOf(ApplicationComponent::class))
interface ConfigPersistentComponent {
    fun activityComponent(activityModule: ActivityModule): ActivityComponent
}
