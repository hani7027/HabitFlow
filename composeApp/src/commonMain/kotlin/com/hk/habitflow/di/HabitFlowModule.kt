package com.hk.habitflow.di

import com.hk.habitflow.data.repository.DatabaseLoginRepositoryImpl
import com.hk.habitflow.domain.repository.LoginRepository
import com.hk.habitflow.domain.repository.TaskRepository
import com.hk.habitflow.domain.repository.UserRepository
import com.hk.habitflow.domain.repository.HabitRepository
import com.hk.habitflow.domain.usecase.LoginUseCase
import com.hk.habitflow.habit.HabitsViewModel
import com.hk.habitflow.habit.create.CreateHabitViewModel
import com.hk.habitflow.platform.PlatformContext
import com.hk.habitflow.task.TasksViewModel
import com.hk.habitflow.ui.screen.home.HomeViewModel
import com.hk.habitflow.ui.screen.login.LoginViewModel
import com.hk.habitflow.database.DriverFactory
import com.hk.habitflow.database.HabitFlowDatabase
import com.hk.habitflow.database.UserRepositoryImpl
import com.hk.habitflow.database.TaskRepositoryImpl
import com.hk.habitflow.database.HabitRepositoryImpl
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val habitFlowModule = module {
    single { DriverFactory(PlatformContext.value) }
    single {
        HabitFlowDatabase(get<DriverFactory>().createDriver())
    }
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<TaskRepository> { TaskRepositoryImpl(get()) }
    single<HabitRepository> { HabitRepositoryImpl(get()) }
    single<LoginRepository> { DatabaseLoginRepositoryImpl(get()) }
    single { LoginUseCase(get()) }
    viewModelOf(::LoginViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::TasksViewModel)
    viewModelOf(::HabitsViewModel)
    viewModelOf(::CreateHabitViewModel)
}
