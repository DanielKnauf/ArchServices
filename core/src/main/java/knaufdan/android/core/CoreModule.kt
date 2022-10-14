package knaufdan.android.core

import dagger.Module
import dagger.Provides
import knaufdan.android.core.calendar.datepicker.IDatePickerService
import knaufdan.android.core.calendar.datepicker.implementation.DatePickerService
import knaufdan.android.core.calendar.daypicker.IDayPickerService
import knaufdan.android.core.calendar.daypicker.implementation.DayPickerService
import knaufdan.android.core.calendar.timepicker.ITimePickerService
import knaufdan.android.core.calendar.timepicker.implementation.TimePickerService
import knaufdan.android.core.context.IContextProvider
import knaufdan.android.core.context.implementation.ContextProvider
import knaufdan.android.core.resources.IResourceProvider
import knaufdan.android.core.resources.implementation.ResourceProvider
import javax.inject.Singleton

@Module
class CoreModule {

    @Provides
    @Singleton
    internal fun provideContextProvider(): IContextProvider = ContextProvider()

    @Provides
    @Singleton
    internal fun provideResourceProvider(
        contextProvider: IContextProvider
    ): IResourceProvider =
        ResourceProvider(
            contextProvider = contextProvider
        )

    @Provides
    @Singleton
    fun provideTimePickerService(
        contextProvider: IContextProvider
    ): ITimePickerService =
        TimePickerService(
            contextProvider = contextProvider
        )

    @Provides
    @Singleton
    fun provideDatePickerService(
        contextProvider: IContextProvider
    ): IDatePickerService =
        DatePickerService(
            contextProvider = contextProvider
        )

    @Provides
    @Singleton
    fun provideDayPickerService(
        contextProvider: IContextProvider,
        resourceProvider: IResourceProvider
    ): IDayPickerService =
        DayPickerService(
            contextProvider = contextProvider,
            resourceProvider = resourceProvider
        )
}
