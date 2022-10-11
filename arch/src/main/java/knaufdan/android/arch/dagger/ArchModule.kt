package knaufdan.android.arch.dagger

import dagger.Module
import dagger.Provides
import knaufdan.android.arch.navigation.INavigationService
import knaufdan.android.arch.navigation.implementation.NavigationService
import knaufdan.android.core.CoreModule
import knaufdan.android.core.context.IContextProvider
import javax.inject.Singleton

@Module(
    includes = [
        CoreModule::class,
        ComponentFactoryModule::class
    ]
)
class ArchModule {

    @Provides
    @Singleton
    internal fun provideNavigator(
        contextProvider: IContextProvider
    ): INavigationService =
        NavigationService(
            contextProvider = contextProvider
        )
}
