package com.hk.habitflow.di

import com.hk.habitflow.data.repository.LoginRepositoryImpl
import com.hk.habitflow.domain.repository.LoginRepository
import com.hk.habitflow.domain.usecase.LoginUseCase
import com.hk.habitflow.habit.HabitsViewModel
import com.hk.habitflow.habit.create.CreateHabitViewModel
import com.hk.habitflow.task.TasksViewModel
import com.hk.habitflow.ui.screen.home.HomeViewModel
import com.hk.habitflow.ui.screen.login.LoginViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val habitFlowModule = module {
    single<LoginRepository> { LoginRepositoryImpl() }
    single { LoginUseCase(get()) }
    viewModelOf(::LoginViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::TasksViewModel)
    viewModelOf(::HabitsViewModel)
    viewModelOf(::CreateHabitViewModel)
}
