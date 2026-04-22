package com.hk.habitflow

import androidx.compose.ui.window.ComposeUIViewController
import com.hk.habitflow.environment.setAppEnvironment

fun MainViewController(appEnvironment: String = "dev") = ComposeUIViewController {
    setAppEnvironment(appEnvironment)
    App()
}