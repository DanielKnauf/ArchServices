package knaufdan.android.arch.dagger

import dagger.Module
import dagger.Provides
import knaufdan.android.arch.base.component.common.divider.IHorizontalDividerComponentFactory
import knaufdan.android.arch.base.component.common.divider.implementation.HorizontalDividerComponentFactory
import knaufdan.android.arch.base.component.common.text.ITextComponentFactory
import knaufdan.android.arch.base.component.common.text.implementation.TextComponentFactory
import knaufdan.android.core.resources.IResourceProvider
import javax.inject.Singleton

@Module
class ComponentFactoryModule {
    @Provides
    @Singleton
    fun provideTextComponentFactory(
        resourceProvider: IResourceProvider
    ): ITextComponentFactory = TextComponentFactory(
        resourceProvider = resourceProvider
    )

    @Provides
    @Singleton
    fun provideHorizontalDividerComponentFactory(
        resourceProvider: IResourceProvider
    ): IHorizontalDividerComponentFactory =
        HorizontalDividerComponentFactory(
            resourceProvider = resourceProvider
        )
}
