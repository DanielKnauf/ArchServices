package knaufdan.android.arch.dagger

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ArchModule {

    @Singleton
    @Provides
    internal fun provideNavigator(navigationService: knaufdan.android.arch.navigation.NavigationService): knaufdan.android.arch.navigation.INavigationService =
        navigationService

}