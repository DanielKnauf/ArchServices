package knaufdan.android.arch.dagger

import dagger.Module
import dagger.Provides
import knaufdan.android.arch.base.component.text.ITextComponentFactory
import knaufdan.android.arch.base.component.text.implementation.TextComponentFactory
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
}
