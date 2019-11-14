package knaufdan.android.services.dagger

import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import knaufdan.android.core.alarm.AlarmService
import knaufdan.android.core.alarm.IAlarmService
import knaufdan.android.core.dagger.CoreModule
import knaufdan.android.core.notification.INotificationService
import knaufdan.android.core.notification.NotificationService
import knaufdan.android.services.service.IServiceDispatcher
import knaufdan.android.services.service.ServiceDispatcher
import knaufdan.android.services.service.broadcast.BroadcastService
import knaufdan.android.services.service.broadcast.IBroadcastService
import knaufdan.android.services.userinteraction.audio.AudioService
import knaufdan.android.services.userinteraction.audio.IAudioService

@Module(includes = [CoreModule::class])
class ServiceModule {
    @Singleton
    @Provides
    internal fun provideAlarmService(alarmService: AlarmService): IAlarmService = alarmService

    @Singleton
    @Provides
    internal fun provideAudioService(audioService: AudioService): IAudioService = audioService

    @Singleton
    @Provides
    internal fun provideBroadcastService(broadcastService: BroadcastService): IBroadcastService =
            broadcastService

    @Singleton
    @Provides
    internal fun provideNotificationService(notificationService: NotificationService): INotificationService =
            notificationService

    @Singleton
    @Provides
    internal fun provideServiceDispatcher(serviceDispatcher: ServiceDispatcher): IServiceDispatcher = serviceDispatcher
}