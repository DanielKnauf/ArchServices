package knaufdan.android.services.dagger

import dagger.Module
import dagger.Provides
import knaufdan.android.core.IContextProvider
import knaufdan.android.core.dagger.CoreModule
import knaufdan.android.services.alarm.AlarmService
import knaufdan.android.services.alarm.IAlarmService
import knaufdan.android.services.service.IServiceDispatcher
import knaufdan.android.services.service.ServiceDispatcher
import knaufdan.android.services.service.broadcast.BroadcastService
import knaufdan.android.services.service.broadcast.IBroadcastService
import knaufdan.android.services.userinteraction.audio.AudioService
import knaufdan.android.services.userinteraction.audio.IAudioService
import knaufdan.android.services.userinteraction.notification.INotificationService
import knaufdan.android.services.userinteraction.notification.implementation.NotificationService
import javax.inject.Singleton

@Module(includes = [CoreModule::class])
class ServicesModule {
    @Provides
    @Singleton
    internal fun provideAlarmService(contextProvider: IContextProvider): IAlarmService =
        AlarmService(
            contextProvider = contextProvider
        )

    @Singleton
    @Provides
    internal fun provideAudioService(audioService: AudioService): IAudioService = audioService

    @Singleton
    @Provides
    internal fun provideBroadcastService(broadcastService: BroadcastService): IBroadcastService =
        broadcastService

    @Singleton
    @Provides
    internal fun provideNotificationService(contextProvider: IContextProvider): INotificationService =
        NotificationService(
            contextProvider = contextProvider
        )

    @Singleton
    @Provides
    internal fun provideServiceDispatcher(serviceDispatcher: ServiceDispatcher): IServiceDispatcher =
        serviceDispatcher
}
