package knaufdan.android.core.dagger

import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import knaufdan.android.core.ContextProvider
import knaufdan.android.core.IContextProvider
import knaufdan.android.core.preferences.ISharedPrefService
import knaufdan.android.core.preferences.implementation.SharedPrefService
import knaufdan.android.core.resources.IResourceProvider
import knaufdan.android.core.resources.implementation.ResourceProvider

@Module
class CoreModule {

    @Provides
    @Singleton
    internal fun provideContextProvider(contextProvider: ContextProvider): IContextProvider = contextProvider

    @Provides
    @Singleton
    internal fun provideResourceProvider(resourceProvider: ResourceProvider): IResourceProvider = resourceProvider

    @Provides
    @Singleton
    internal fun provideSharedPrefService(sharedPrefService: SharedPrefService): ISharedPrefService = sharedPrefService
}
