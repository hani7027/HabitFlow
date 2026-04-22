import UIKit
import SwiftUI
import ComposeApp

struct ComposeView: UIViewControllerRepresentable {
    private func resolvedEnvironment() -> String {
        if let env = ProcessInfo.processInfo.environment["APP_ENV"], !env.isEmpty {
            return env
        }
        if let plistEnv = Bundle.main.object(forInfoDictionaryKey: "APP_ENV") as? String, !plistEnv.isEmpty {
            return plistEnv
        }
        return "dev"
    }

    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController(appEnvironment: resolvedEnvironment())
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ComposeView()
            .ignoresSafeArea()
    }
}



