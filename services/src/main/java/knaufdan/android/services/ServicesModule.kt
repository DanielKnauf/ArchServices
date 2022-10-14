package knaufdan.android.services

import dagger.Module
import dagger.Provides
import knaufdan.android.core.CoreModule
import knaufdan.android.core.context.IContextProvider
import knaufdan.android.services.alarm.IAlarmService
import knaufdan.android.services.alarm.implementation.AlarmService
import knaufdan.android.services.media.IMediaService
import knaufdan.android.services.media.implementation.MediaService
import knaufdan.android.services.service.IServiceDispatcher
import knaufdan.android.services.service.ServiceDispatcher
import knaufdan.android.services.service.broadcast.IBroadcastService
import knaufdan.android.services.service.broadcast.implementation.BroadcastService
import knaufdan.android.services.userinteraction.audio.AudioService
import knaufdan.android.services.userinteraction.audio.IAudioService
import knaufdan.android.services.userinteraction.notification.INotificationService
import knaufdan.android.services.userinteraction.notification.implementation.NotificationService
import javax.inject.Singleton

@Module(includes = [CoreModule::class])
class ServicesModule {

    @Singleton
    @Provides
    fun provideAlarmService(
        contextProvider: IContextProvider
    ): IAlarmService =
        AlarmService(contextProvider)

    @Provides
    @Singleton
    internal fun provideAudioService(
        contextProvider: IContextProvider
    ): IAudioService =
        AudioService(contextProvider)

    @Provides
    @Singleton
    fun provideBroadcastService(
        contextProvider: IContextProvider
    ): IBroadcastService =
        BroadcastService(contextProvider)

    @Provides
    @Singleton
    fun provideNotificationService(
        contextProvider: IContextProvider
    ): INotificationService =
        NotificationService(contextProvider)

    @Provides
    @Singleton
    fun provideMediaService(
        contextProvider: IContextProvider
    ): IMediaService =
        MediaService(contextProvider)

    @Provides
    @Singleton
    internal fun provideServiceDispatcher(
        contextProvider: IContextProvider
    ): IServiceDispatcher =
        ServiceDispatcher(contextProvider)
}
