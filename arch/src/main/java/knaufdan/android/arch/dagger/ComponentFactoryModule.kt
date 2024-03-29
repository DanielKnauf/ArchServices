package knaufdan.android.arch.dagger

import dagger.Module
import dagger.Provides
import knaufdan.android.arch.base.component.common.button.IButtonComponentFactory
import knaufdan.android.arch.base.component.common.button.implementation.ButtonComponentFactory
import knaufdan.android.arch.base.component.common.divider.IHorizontalDividerComponentFactory
import knaufdan.android.arch.base.component.common.divider.implementation.HorizontalDividerComponentFactory
import knaufdan.android.arch.base.component.common.entry.IEntryComponentFactory
import knaufdan.android.arch.base.component.common.entry.implementation.EntryComponentFactory
import knaufdan.android.arch.base.component.common.info.IInfoComponentFactory
import knaufdan.android.arch.base.component.common.info.implementation.InfoComponentFactory
import knaufdan.android.arch.base.component.common.progress.IProgressComponentFactory
import knaufdan.android.arch.base.component.common.progress.implementation.ProgressComponentFactory
import knaufdan.android.arch.base.component.common.spacing.ISpacingComponentFactory
import knaufdan.android.arch.base.component.common.spacing.implementation.SpacingComponentFactory
import knaufdan.android.arch.base.component.common.text.ITextComponentFactory
import knaufdan.android.arch.base.component.common.text.implementation.TextComponentFactory
import knaufdan.android.core.resources.IResourceProvider
import javax.inject.Singleton

@Module
class ComponentFactoryModule {

    @Provides
    @Singleton
    fun provideButtonComponentFactory(
        resourceProvider: IResourceProvider
    ): IButtonComponentFactory =
        ButtonComponentFactory(resourceProvider)

    @Provides
    @Singleton
    fun provideEntryComponentFactory(
        resourceProvider: IResourceProvider
    ): IEntryComponentFactory =
        EntryComponentFactory(resourceProvider)

    @Provides
    @Singleton
    fun provideHorizontalDividerComponentFactory(
        resourceProvider: IResourceProvider
    ): IHorizontalDividerComponentFactory =
        HorizontalDividerComponentFactory(resourceProvider)

    @Provides
    @Singleton
    fun provideInfoComponentFactory(
        resourceProvider: IResourceProvider
    ): IInfoComponentFactory =
        InfoComponentFactory(resourceProvider)

    @Provides
    @Singleton
    fun provideProgressComponentFactory(): IProgressComponentFactory = ProgressComponentFactory()

    @Provides
    @Singleton
    fun provideSpacingComponentFactory(
        resourceProvider: IResourceProvider
    ): ISpacingComponentFactory =
        SpacingComponentFactory(resourceProvider)

    @Provides
    @Singleton
    fun provideTextComponentFactory(
        resourceProvider: IResourceProvider
    ): ITextComponentFactory =
        TextComponentFactory(resourceProvider)
}
