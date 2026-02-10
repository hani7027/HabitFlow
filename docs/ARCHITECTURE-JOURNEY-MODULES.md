# HabitFlow — Journey-Based Module Architecture

One **journey** = one user flow = one module. Each journey owns its UI, domain, and data. Adding API later stays inside that journey; you do **not** touch a shared “core” or a pile of small modules.

---

## 1. Principles

| Principle | Meaning |
|-----------|--------|
| **Vertical slice** | One module = one journey. UI + use cases + repository (interface + impl) + local DB (and later API) live together. |
| **API-ready** | When you add a backend, you only change the **data** layer inside that journey (e.g. add `TaskApi`, wrap in repository). No changes to a global “core” or to other journeys. |
| **Minimal shared surface** | Only **design** (theme, components) and **app shell** (navigation, DI) are shared. No big “core” with all models and interfaces. |
| **Readable** | Few modules, clear names, obvious “where does this flow live?”. |

---

## 2. Module map (high level)

```
HabitFlow/
├── design/                    # Shared: theme, tokens, reusable components only
├── journey-home/              # Daily control center (today’s tasks/habits, progress, quick focus)
├── journey-tasks/             # Task list + Add/Edit Task
├── journey-habits/            # Habit list + Add/Edit Habit
├── journey-focus/             # Pomodoro / focus session
├── journey-insights/          # Weekly summary, charts
├── journey-achievements/      # Rewards / badges
└── app/                       # Shell: navigation, DI, Koin, Compose entry (current composeApp)
```

**Dependency rule:**  
`app` → `journey-*` → `design`.  
No journey depends on another journey. No “core” or “database” module required.

---

## 3. Inside one journey (vertical slice)

Each journey module is self-contained. Example: **journey-tasks**.

```
journey-tasks/
└── src/commonMain/kotlin/com/hk/habitflow/journey/tasks/
    ├── ui/                          # Presentation
    │   ├── TasksScreen.kt
    │   ├── TasksViewModel.kt
    │   ├── AddEditTaskScreen.kt
    │   ├── AddEditTaskViewModel.kt
    │   └── TasksContract.kt         # State, Event, Effect
    ├── domain/                      # This journey’s business logic
    │   ├── model/                   # Task, TaskCategory (used only in this journey)
    │   │   └── Task.kt
    │   ├── repository/
    │   │   └── TaskRepository.kt    # Interface (owned by journey)
    │   └── usecase/
    │       ├── GetTodayTasks.kt
    │       └── SaveTask.kt
    └── data/                        # Implementation: local first, API later
        ├── local/
        │   ├── TaskDao.kt           # or SQLDelight queries
        │   └── TaskRepositoryImpl.kt
        └── remote/                  # Add when you have a backend
            ├── TaskApi.kt
            └── TaskRepositoryImpl.kt  # Switches to or merges with API
```

- **UI** uses only this journey’s ViewModels and domain types.
- **Domain** defines this journey’s model + repository **interface** + use cases. No reference to DB or API.
- **Data** implements the repository (local DB, and later remote). When you add API, you only touch **data** (and maybe a small `remote/` package). You do **not** touch `design` or `app` or other journeys.

---

## 4. Shared “design” module only

```
design/
└── src/commonMain/kotlin/com/hk/habitflow/design/
    ├── theme/                  # HabitFlowTheme, DesignTokens, colors, typography
    └── component/              # SectionHeader, ProgressRingCard, TaskItemRow, HabitCard, …
```

- No ViewModels, no repositories, no use cases.
- Journey modules depend on `design` for consistent UI. Adding API or new flows does not change `design`.

---

## 5. App shell (navigation + DI)

```
app/
└── src/commonMain/kotlin/com/hk/habitflow/
    ├── App.kt                  # KoinApplication, HabitFlowTheme, NavHost
    ├── navigation/
    │   ├── NavGraph.kt
    │   └── Routes.kt
    └── di/
        └── AppModule.kt        # modules(journeyHomeModule, journeyTasksModule, …)
```

- **app** depends on every **journey-*** and on **design**.
- Each journey exposes a **Koin module** (e.g. `journeyTasksModule` with ViewModels and repository). App just `modules(…)` them.
- Navigation lives only in **app**; journeys expose composable screens (e.g. `TasksScreen()`) and effects like “navigate to AddTask” or “back”. App turns effects into NavController calls.

---

## 6. Why this is API-ready without touching “core”

- There is **no** shared “core” with all models and repository interfaces. Each journey owns its **own** model and repository interface.
- When you add an API for **Tasks**:
  - You add `TaskApi` and (optionally) a data source in **journey-tasks/data/remote/**.
  - You change **TaskRepositoryImpl** inside **journey-tasks** to use local + remote (or only remote). Use cases and UI stay the same.
- You do **not** touch:
  - **design**
  - **app** (except maybe registering a new API client in DI if you inject it in the journey’s module)
  - **journey-habits**, **journey-focus**, etc.

So: “add API” = change one journey’s **data** layer only. No core, no cross-journey refactors.

---

## 7. Dependency diagram

```
                    ┌─────────────┐
                    │     app     │
                    └──────┬──────┘
         ┌─────────────────┼─────────────────┐
         ▼                 ▼                 ▼
  ┌─────────────┐   ┌─────────────┐   ┌─────────────┐
  │ journey-    │   │ journey-    │   │ journey-    │  …
  │ home        │   │ tasks       │   │ habits      │
  └──────┬──────┘   └──────┬──────┘   └──────┬──────┘
         │                 │                 │
         └─────────────────┼─────────────────┘
                           ▼
                    ┌─────────────┐
                    │   design    │
                    └─────────────┘
```

- **app** → all journey modules → **design**.
- Journeys do not depend on each other.

---

## 8. When two journeys share a model (e.g. Task)

- **Option A (preferred):** One journey **owns** the model and the other consumes a **minimal DTO** or an interface. Example: **journey-tasks** owns `Task` and exposes `Task` (or a read-only view) to **journey-home**. journey-home depends on journey-tasks only for that type (or you pass it via navigation/DI). Keep this rare.
- **Option B:** Introduce a **tiny shared contract** (e.g. `:contracts` with only `TaskId` and a minimal `TaskSummary`). Both journey-tasks and journey-home depend on `contracts`. Use only when A is messy.

Prefer **Option A** so you don’t grow a “core” again.

---

## 9. Summary

- **One journey type** = one entire flow in one module (UI + domain + data).
- **Adding API** = change only that journey’s **data** layer; no need to touch a core or many modules.
- **Few modules**, clear names: **design**, **journey-***, **app**.
- **Readable and maintainable**: any senior dev can see that “Tasks” = **journey-tasks** and “API for tasks” = **journey-tasks/data**.

This is the structure to follow when you modularize: journey-first, API-ready, minimal shared surface, no over-engineering.
