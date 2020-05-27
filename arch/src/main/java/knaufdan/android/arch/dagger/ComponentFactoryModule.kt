package knaufdan.android.arch.dagger

import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import knaufdan.android.arch.base.component.text.ITextComponentFactory
import knaufdan.android.arch.base.component.text.implementation.TextComponentFactory
import knaufdan.android.core.resources.IResourceProvider

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
