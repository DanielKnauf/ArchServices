package knaufdan.android.arch.dagger

import dagger.Module
import dagger.Provides
import knaufdan.android.arch.base.component.base.button.IButtonComponentFactory
import knaufdan.android.arch.base.component.base.button.implementation.ButtonComponentFactory
import knaufdan.android.arch.base.component.base.divider.IHorizontalDividerComponentFactory
import knaufdan.android.arch.base.component.base.divider.implementation.HorizontalDividerComponentFactory
import knaufdan.android.arch.base.component.base.info.IInfoComponentFactory
import knaufdan.android.arch.base.component.base.info.implementation.InfoComponentFactory
import knaufdan.android.arch.base.component.base.text.ITextComponentFactory
import knaufdan.android.arch.base.component.base.text.implementation.TextComponentFactory
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

    @Provides
    @Singleton
    fun provideInfoComponentFactory(
        resourceProvider: IResourceProvider
    ): IInfoComponentFactory =
        InfoComponentFactory(
            resourceProvider = resourceProvider
        )

    @Provides
    @Singleton
    fun provideButtonComponentFactory(
        resourceProvider: IResourceProvider
    ): IButtonComponentFactory =
        ButtonComponentFactory(
            resourceProvider = resourceProvider
        )
}
