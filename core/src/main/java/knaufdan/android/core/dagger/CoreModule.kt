package knaufdan.android.core.dagger

import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import knaufdan.android.core.ISharedPrefService
import knaufdan.android.core.ITextProvider
import knaufdan.android.core.SharedPrefService
import knaufdan.android.core.TextProvider
import knaufdan.android.core.alarm.AlarmService
import knaufdan.android.core.alarm.IAlarmService
import knaufdan.android.core.audio.AudioService
import knaufdan.android.core.audio.IAudioService
import knaufdan.android.core.broadcast.BroadcastService
import knaufdan.android.core.broadcast.IBroadcastService
import knaufdan.android.core.notification.INotificationService
import knaufdan.android.core.notification.NotificationService

@Module
class CoreModule {

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
    internal fun provideSharedPrefService(sharedPrefService: SharedPrefService): ISharedPrefService = sharedPrefService

    @Singleton
    @Provides
    internal fun provideTextProvider(textProvider: TextProvider): ITextProvider = textProvider
}
