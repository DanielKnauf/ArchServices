package knaufdan.android.arch.dagger

import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import knaufdan.android.arch.navigation.INavigationService
import knaufdan.android.arch.navigation.NavigationService
import knaufdan.android.core.dagger.CoreModule

@Module(
    includes = [
        CoreModule::class,
        ComponentFactoryModule::class
    ]
)
class ArchModule {

    @Provides
    @Singleton
    internal fun provideNavigator(navigationService: NavigationService): INavigationService =
        navigationService
}
