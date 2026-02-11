# HabitFlow — How to Follow Modularization

**Preferred approach:** One **journey** per module (one full flow: UI + domain + data in one place). Adding API later = change only that journey’s data layer; no shared “core” to touch. See **[MODULARIZATION-STRATEGY.md](MODULARIZATION-STRATEGY.md)** for design analysis, **final 9 modules**, and how to create/manage in KMP. See **[ARCHITECTURE-JOURNEY-MODULES.md](ARCHITECTURE-JOURNEY-MODULES.md)** for the full journey-based design.

This guide gives **incremental steps**: one module at a time, then build and run.

---

## 1. Target structure (two options)

```
HabitFlow/
├── core/                 # Shared models, interfaces (no UI, no DB)
├── database/             # SQLDelight + repository implementations (optional: merge with data)
├── data/                 # Optional: if you keep repo impls separate from DB
├── feature-home/
├── feature-tasks/
├── feature-habits/
├── feature-focus/
├── feature-insights/
├── feature-achievements/
├── feature-login/        # Optional
├── ui-design/            # Shared: theme, design tokens, common components
└── composeApp/           # App entry, navigation, DI, depends on all
```

**Dependency rule:** `composeApp` → `feature-*` → `ui-design` → `database` / `data` → `core`. No cycles; `core` depends on nothing.

**Alternative (recommended): Journey-based** — Fewer modules, API-ready, no core:

- **design** — Theme + components.
- **journey-home**, **journey-tasks**, **journey-habits**, **journey-focus**, **journey-insights**, **journey-achievements** — Each = one flow (UI + domain + data).
- **app** — Navigation + DI.

Rule: `app` → `journey-*` → `design`. See [ARCHITECTURE-JOURNEY-MODULES.md](ARCHITECTURE-JOURNEY-MODULES.md).

---

## 2. Step-by-step (incremental)

### Step 1: Add `:core` module

**Purpose:** Domain models and repository interfaces only. No Android, no Compose, no DB.

1. Create folder `core/` next to `composeApp/`.
2. Add `core/build.gradle.kts`:

```kotlin
plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

kotlin {
    androidTarget { /* ... same as composeApp */ }
    listOf(iosArm64(), iosSimulatorArm64()).forEach { /* ... */ }
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
        }
    }
}
```

3. In `settings.gradle.kts`: `include(":core")`
4. Move from `composeApp/.../domain/` into `core/src/commonMain/kotlin/.../domain/`:
   - `model/` (Task, Habit, etc.)
   - `repository/` (interfaces only, e.g. `TaskRepository`, `HabitRepository`)
5. In `composeApp/build.gradle.kts` → `commonMain.dependencies`:  
   `implementation(project(":core"))`
6. Delete the moved files from composeApp and fix imports (use `com.hk.habitflow.domain` from core).
7. Build: `./gradlew :core:build composeApp:assembleDebug`

---

### Step 2: Add `:ui-design` module (shared UI primitives)

**Purpose:** Theme, design tokens, and reusable components used by multiple features.

1. Create `ui-design/` and `ui-design/build.gradle.kts` (KMP + Compose, but no ViewModel/lifecycle).
2. `settings.gradle.kts`: `include(":ui-design")`
3. Move from `composeApp` into `ui-design/src/commonMain/kotlin/.../ui/`:
   - `theme/` (DesignTokens, HabitFlowTheme, HabitFlowColors, etc.)
   - `component/` (SectionHeader, ProgressRingCard, TaskItemRow, HabitCard, QuickFocusCard, HomeHeader)
4. In `composeApp` and (later) each `feature-*`:  
   `implementation(project(":ui-design"))`
5. Update package names if you keep the same `com.hk.habitflow.ui.theme` / `com.hk.habitflow.ui.component` in ui-design.
6. Build again.

---

### Step 3: Add `:database` module (when you add SQLDelight)

**Purpose:** SQLDelight schema, queries, and Driver expect/actual. Repository implementations can live here or in a separate `:data` module.

1. Create `database/` with `build.gradle.kts` (KMP + SQLDelight plugin).
2. `include(":database")`
3. Put all `.sq` files and DB-related code in `database/`.
4. `database` depends on `:core` only.
5. `composeApp` (and optionally feature modules) depend on `project(":database")`.

---

### Step 4: Add one feature module (e.g. `:feature-home`)

**Purpose:** One screen (or a small group) per module: UI + ViewModel + screen-specific state/events.

1. Create `feature-home/build.gradle.kts`:
   - Kotlin Multiplatform + Compose + ViewModel (same as composeApp for the parts you need).
   - Dependencies: `implementation(project(":core"))`, `implementation(project(":ui-design"))`, and if you use repos directly: `implementation(project(":database"))`.
2. `include(":feature-home")`
3. Move from `composeApp` into `feature-home/src/commonMain/kotlin/.../home/`:
   - `HomeContract.kt`, `HomeViewModel.kt`, `HomeContent.kt`, `HomeScreen.kt`
4. In `composeApp`:  
   `implementation(project(":feature-home"))`  
   and remove the home code from composeApp.
5. In `composeApp`’s Koin module, keep registering `HomeViewModel` (or move it to a `feature-home` Koin module and include it in the app’s `modules(...)`).
6. Build and run; then repeat for `feature-tasks`, `feature-habits`, etc.

---

### Step 5: Split Koin modules per layer/feature

- **core** (or **app**) Koin module: only things with no UI (e.g. repositories, use cases).
- **feature-home** (optional): define a Koin module that declares `HomeViewModel` and depends on shared modules; in `composeApp` call `modules(habitFlowModule, homeModule)`.

This keeps DI close to the feature and makes it clear what each module provides.

---

## 3. Dependency matrix

| Module        | Depends on                          |
|---------------|-------------------------------------|
| **core**      | —                                   |
| **database**  | core                                |
| **ui-design** | (Compose only, no app modules)      |
| **feature-*** | core, ui-design; optionally database|
| **composeApp**| core, ui-design, database, feature-*|

---

## 4. Package naming (suggestion)

- **core:** `com.hk.habitflow.domain`
- **ui-design:** `com.hk.habitflow.ui.theme`, `com.hk.habitflow.ui.component`
- **feature-home:** `com.hk.habitflow.home` (or `com.hk.habitflow.feature.home`)
- **composeApp:** `com.hk.habitflow` (App.kt, navigation, app-level DI)

---

## 5. Minimal first move (if you want to start small)

1. Add only **:core**.
2. Move **domain** (models + repository interfaces) into **:core**.
3. Make **composeApp** depend on **:core** and fix imports.

After that, add **:ui-design** and then one **feature-*** module so you get used to the pattern before splitting the rest.

---

## 6. Summary

- **One module at a time:** core → ui-design → database → feature-home → other features.
- **Strict dependency direction:** app → features → design/database → core.
- **Build after each step:** `./gradlew assembleDebug` (and iOS if you use it).
- Keep **navigation and app DI** in **composeApp**; feature modules only expose screens and ViewModels.
