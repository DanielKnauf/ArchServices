package knaufdan.android.arch.dagger

import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import knaufdan.android.arch.navigation.INavigationService
import knaufdan.android.arch.navigation.NavigationService
import knaufdan.android.core.dagger.CoreModule

@Module(includes = [CoreModule::class])
class ArchModule {

    @Singleton
    @Provides
    internal fun provideNavigator(navigationService: NavigationService): INavigationService =
            navigationService
}
