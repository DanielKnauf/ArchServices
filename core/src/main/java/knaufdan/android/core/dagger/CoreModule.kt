package knaufdan.android.core.dagger

import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import knaufdan.android.core.ContextProvider
import knaufdan.android.core.IContextProvider
import knaufdan.android.core.preferences.ISharedPrefService
import knaufdan.android.core.preferences.SharedPrefService
import knaufdan.android.core.resources.ITextProvider
import knaufdan.android.core.resources.TextProvider

@Module
class CoreModule {

    @Singleton
    @Provides
    internal fun provideContextProvider(contextProvider: ContextProvider): IContextProvider = contextProvider

    @Singleton
    @Provides
    internal fun provideSharedPrefService(sharedPrefService: SharedPrefService): ISharedPrefService = sharedPrefService

    @Singleton
    @Provides
    internal fun provideTextProvider(textProvider: TextProvider): ITextProvider = textProvider
}
