# HabitFlow — Architecture (v1 Offline)

Lead Android/KMP architecture for the first version: **offline-first**, single codebase for Android and iOS.

---

## 1. Principles

| Principle | Meaning |
|-----------|--------|
| **Offline-first** | All critical data lives in a local database. No network required for core flows. |
| **Single source of truth** | Local DB is the authority; UI reads from and writes to it via repositories. |
| **Unidirectional UI** | State → UI, Events → ViewModel → State/Effects (existing Contract pattern). |
| **Plan → Execute → Focus → Reflect → Reward** | Navigation and features follow this product loop. |

---

## 2. Tech Stack (KMP)

| Layer | Choice | Reason |
|-------|--------|--------|
| **Database** | **SQLDelight** | KMP-native, type-safe SQL, shared Android + iOS. (Room is Android-only.) |
| **Async** | Kotlin Coroutines + Flow | Shared; repositories expose `Flow` for reactive UI. |
| **UI** | Compose Multiplatform | Shared screens; existing pattern (Contract: State, Event, Effect). |
| **DI** | Koin (koin-core, koin-compose, koin-compose-viewmodel) | Shared modules; `koinViewModel()` in Compose. |

---

## 2b. Libraries (version catalog: `gradle/libs.versions.toml`)

All versions are centralised in the version catalog. Single place to bump versions.

| Category | Library | Purpose |
|----------|---------|--------|
| **Kotlin** | kotlin (multiplatform) | KMP, shared code |
| **Compose** | compose (runtime, foundation, ui, material3, components-resources, ui-tooling-preview) | UI; Material 3 for components |
| **Android** | androidx.activity (activity-compose), androidx.lifecycle (viewmodel-compose, runtime-compose), core-ktx | Activity, ViewModel, Compose integration |
| **Coroutines** | kotlinx-coroutines-core | Async, Flow, suspend |
| **DI** | koin-core, koin-core-viewmodel, koin-compose, koin-compose-viewmodel | Dependency injection; `habitFlowModule` in `di/`; `koinViewModel()` in screens. |
| **Database (planned)** | SQLDelight + driver (android, native) | Offline persistence, shared schema |
| **Testing** | kotlin-test, junit, androidx.test (espresso, ext-junit) | Unit and UI tests |

Screens and feature code must **not** hard-code colours, text styles, or spacing. They use `MaterialTheme` and the shared design tokens (see Design system below).

---

## 2c. Design System — Single Source for All Styles

All visual attributes are controlled from **one place**. Change a value once and the whole app updates.

**Design reference:** The theme is aligned with the HabitFlow hi-fi design (UX Pilot mockups): rounded corners, card-based layout, primary purple, success green, focus orange, clear typography. When making UI decisions, treat that design as the single source of truth.

### Where styles live

| Token type | File | How screens use it |
|------------|------|--------------------|
| **Colors** | `ui/theme/DesignTokens.kt` | `MaterialTheme.colorScheme.*`; semantic: `HabitFlowColors` (Success, Focus, TextPrimary, TextSecondary, TextPlaceholder) |
| **Typography** | `ui/theme/DesignTokens.kt` | `MaterialTheme.typography.*` (headlineLarge for “Good Morning”, titleMedium for labels, bodyMedium for descriptions) |
| **Shapes** | `ui/theme/DesignTokens.kt` | `MaterialTheme.shapes.*` (rounded: small 8dp, medium/large 12dp, extraLarge 16dp) |
| **Spacing** | `ui/theme/DesignTokens.kt` | `LocalHabitFlowSpacing.current` (screenHorizontal, cardPadding, inputPaddingVertical/Horizontal, etc.) |
| **Component tokens** | `ui/theme/DesignTokens.kt` | `LocalHabitFlowComponents.current` — input (cornerRadius, border, minHeight), date/time (same as input), button (cornerRadius), card (elevation, cornerRadius), chip (cornerRadius), FAB (size, iconSize) |

### Theme entry point

- **`ui/theme/HabitFlowTheme.kt`** — Composable `HabitFlowTheme(darkTheme, content)` that sets `MaterialTheme` with:
  - `colorScheme` from `DesignTokens` (light/dark)
  - `typography` from `DesignTokens`
  - `shapes` from `DesignTokens`
- **`App.kt`** uses only `HabitFlowTheme { ... }` (no raw `MaterialTheme` with defaults).

### Rules for screens

1. **Do not** use hard-coded colours (e.g. `Color(0xFF…)`) or font sizes in screen composables.
2. **Do** use `MaterialTheme.colorScheme`, `MaterialTheme.typography`, `MaterialTheme.shapes` for all UI.
3. **Do** use shared spacing from `HabitFlowSpacing` (or theme-backed dimensions) for padding/margins.
4. For one-off overrides (e.g. category chips), define the palette in `DesignTokens` and reference it from theme or a small `CategoryColors` object so it stays centralised.

Adding a new colour or text style: add it in `DesignTokens.kt` and (if needed) expose it via `MaterialTheme` or a `CompositionLocal` so all screens can use it consistently.

---

## 3. Layers (Clean Architecture, simplified)

```
┌─────────────────────────────────────────────────────────────┐
│  UI (Compose) — Screens, ViewModels, Contract (State/Event/Effect)  │
├─────────────────────────────────────────────────────────────┤
│  Domain — Use cases, repository interfaces, domain models   │
├─────────────────────────────────────────────────────────────┤
│  Data — SQLDelight DB, DAOs, repository implementations      │
└─────────────────────────────────────────────────────────────┘
```

- **Data** depends on nothing app-specific (only Kotlin/SQLDelight).
- **Domain** depends only on data types and repository interfaces (no DB details).
- **UI** depends on domain (use cases / repo interfaces) and composes screens.

---

## 4. Database (SQLDelight) — Shared Schema

Single shared DB with tables aligned to the 8-screen flow.

### 4.1 Tables (conceptual)

| Table | Purpose |
|-------|--------|
| **Task** | Tasks: title, description, category, priority, due date/time, completed, created_at. |
| **Habit** | Habits: name, icon_id, frequency (daily/custom), reminder_enabled, time, target_count. |
| **HabitCompletion** | One row per habit per day (or per occurrence) for “Today’s Habits” and streaks. |
| **FocusSession** | Pomodoro sessions: planned_duration_sec, actual_sec, started_at, completed_at, optional task_id. |
| **Achievement** | Rewards: achievement_type_id, unlocked_at, optional metadata. |
| **Category** (optional) | Task categories (Work, Study, Health, etc.) if you want them editable. |

### 4.2 SQLDelight placement

- **Module:** `composeApp` (or a dedicated `:database` module later).
- **Path:** `src/commonMain/sqldelight/com/hk/habitflow/db/`
- **Generated:** Kotlin from `.sq` files; expect/actual for `Driver` (Android `AndroidSqliteDriver`, iOS `NativeSqliteDriver`).

---

## 5. Domain Models (commonMain)

Keep domain models in `domain` and map from DB in the data layer.

- **Task** — id, title, description, category, priority, dueDate, isCompleted, createdAt.
- **Habit** — id, name, iconId, frequency, reminderEnabled, time, targetCount, customDays (e.g. list of weekdays).
- **HabitCompletion** — habitId, date (e.g. YYYY-MM-DD), completedAt.
- **FocusSession** — id, plannedDurationSec, actualDurationSec, startedAt, completedAt, taskId?.
- **Achievement** — id, type (enum or string), unlockedAt, title?, description?.

---

## 6. Repositories (interfaces in domain, impl in data)

| Repository | Responsibility |
|------------|----------------|
| **TaskRepository** | CRUD tasks; stream `Flow<List<Task>>` for today/upcoming/completed. |
| **HabitRepository** | CRUD habits; stream today’s habits with completion state; record completions. |
| **FocusSessionRepository** | Insert session; stream recent sessions for Insights. |
| **AchievementRepository** | Unlock achievement; stream list for Achievements screen. |
| **InsightsRepository** (or use cases only) | Aggregate: tasks done (week), focus sessions (week), streaks. Can be use cases that call the above repos. |

---

## 7. Screens and ViewModels (8 screens)

| # | Screen | ViewModel | Main responsibility |
|---|--------|-----------|---------------------|
| 1 | **Home** | `HomeViewModel` | Today’s progress (tasks + habits), “Today’s Tasks” list, “Today’s Habits” list, Focus card (current/next Pomodoro) → navigate to Focus. |
| 2 | **Tasks** | `TasksViewModel` | Tabs: Today / Upcoming / Completed; list from `TaskRepository`; navigate to Add/Edit Task. |
| 3 | **Habits** | `HabitsViewModel` | Today’s progress; list habits + completion; navigate to Add/Edit Habit. |
| 4 | **Add/Edit Task** | `AddEditTaskViewModel` | Form state (title, description, category, priority, due date); save/cancel → TaskRepository. |
| 5 | **Add/Edit Habit** | `AddEditHabitViewModel` | Form state (name, icon, frequency, time, count, reminder); save/cancel → HabitRepository. |
| 6 | **Focus (Pomodoro)** | `FocusViewModel` | Timer state (e.g. 25:00), start/pause/stop; on complete → insert `FocusSession`, optional link to task. |
| 7 | **Insights** | `InsightsViewModel` | Weekly stats (tasks done, progress %, streaks, habits); charts from aggregated data. |
| 8 | **Achievements** | `AchievementsViewModel` | List achievements from `AchievementRepository`; display locked/unlocked. |

All screens use the existing **Contract** pattern: `State`, `Event`, `Effect` (navigation, toasts).

---

## 8. Navigation (NavGraph)

Single `NavHost` in `App.kt` (or `AppContent`), routes as sealed class or string routes.

```
                    ┌─────────────┐
                    │    Home     │
                    └──────┬──────┘
         ┌─────────────────┼─────────────────┐
         ▼                 ▼                 ▼
   ┌──────────┐     ┌──────────┐     ┌─────────────┐
   │  Tasks   │     │  Habits  │     │    Focus    │
   └────┬─────┘     └────┬─────┘     └─────────────┘
        │                │
        ▼                ▼
   ┌──────────────┐ ┌──────────────┐
   │ Add/Edit Task│ │ Add/Edit Habit│
   └──────────────┘ └──────────────┘
         │                 │
         └────────┬────────┘
                  ▼
   ┌─────────────┐     ┌──────────────┐
   │   Insights  │     │ Achievements │
   └─────────────┘     └──────────────┘
```

- **Bottom nav or drawer:** Home, Tasks, Habits, Focus, Insights, Achievements.
- **Add/Edit Task** and **Add/Edit Habit** are separate routes (e.g. with taskId/habitId as optional argument for edit).
- Effects from ViewModels trigger navigation (e.g. `Effect.NavigateToAddTask`, `Effect.NavigateBack`).

---

## 9. Package Structure (single module for v1)

```
com.hk.habitflow/
├── data/
│   ├── local/           # SQLDelight DB, Driver expect/actual
│   │   └── HabitFlowDb.kt (driver creation)
│   ├── repository/      # Repository implementations
│   └── mapping/         # DB → domain mappers (optional)
├── di/
│   └── HabitFlowModule.kt  # Koin module: repos, use cases, ViewModels
├── domain/
│   ├── model/
│   ├── repository/
│   └── usecase/
├── ui/
│   ├── theme/           # HabitFlowTheme, DesignTokens (single source for colors, typography, shapes, spacing)
│   ├── navigation/      # NavGraph, routes, NavHost
│   ├── screen/
│   │   ├── home/
│   │   ├── tasks/
│   │   ├── habits/
│   │   ├── addedittask/
│   │   ├── addedithabit/
│   │   ├── focus/
│   │   ├── insights/
│   │   └── achievements/
│   └── component/       # Reusable composables (optional)
└── App.kt
```

---

## 10. Implementation Order (MVP)

1. **SQLDelight** — Add plugin, create `.sq` schema (Task, Habit, HabitCompletion, FocusSession, Achievement), implement `Driver` expect/actual.
2. **Domain** — Models + repository interfaces + 1–2 use cases (e.g. GetTodayTasks, SaveTask).
3. **Data** — Repository implementations using SQLDelight queries and `Flow`.
4. **Navigation** — Define routes, single `NavHost`, bottom nav (or drawer) with 6 main destinations.
5. **Screens (order)** — Home (shell) → Tasks + Add/Edit Task → Habits + Add/Edit Habit → Focus → Insights → Achievements.
6. **DI** — Add new definitions to `HabitFlowModule.kt`; use `koinViewModel<T>()` in Compose screens.

---

## 11. Out of Scope for v1

- Backend / sync / auth (login can stay as stub or be removed for v1).
- Social or cloud achievements.
- Widgets or watch.
- Onboarding/splash (per product brief).

---

## 12. Modularization: Journey-Based (recommended)

**One journey = one flow = one module.** Each journey owns UI + domain + data (local, and later API). Adding API stays inside that journey; you do not touch a shared “core” or many modules.

| Module | Role |
|--------|------|
| **design** | Theme, design tokens, reusable components only. |
| **journey-home** | Daily control center (today’s tasks/habits, progress, quick focus). |
| **journey-tasks** | Task list + Add/Edit Task (owns Task model + repository + DB/API). |
| **journey-habits** | Habit list + Add/Edit Habit. |
| **journey-focus** | Pomodoro / focus session. |
| **journey-insights** | Weekly summary, charts. |
| **journey-achievements** | Rewards / badges. |
| **app** | Shell: navigation, DI, Compose entry (current composeApp). |

**Dependency rule:** `app` → `journey-*` → `design`. No journey depends on another. No shared “core”.

**Full design (API-ready, minimal modules):** See [ARCHITECTURE-JOURNEY-MODULES.md](ARCHITECTURE-JOURNEY-MODULES.md).

**Incremental steps:** See [MODULARIZATION.md](MODULARIZATION.md) if you prefer a step-by-step migration (e.g. add :design first, then one journey at a time).
