# HabitFlow Database Module

SQLDelight-based offline-first database for HabitFlow (KMP). All user data is scoped by `userId`.

## Build

The `:database` module uses the Android Multiplatform Library plugin; there is no `compileKotlinAndroid` task. Use:

- **Build the database module (Android):** `./gradlew :database:assemble`
- **Build the full app (compiles database as dependency):** `./gradlew :composeApp:assembleDebug`
- **List tasks:** `./gradlew :database:tasks`

**Unresolved reference to `HabitFlowDatabase`:** This class is **generated** by SQLDelight; it does not exist in source. Run a build so the code is generated, then sync the project:

1. Run: `./gradlew :database:generateCommonMainHabitFlowDatabaseInterface` (or `:database:assemble`).
2. In Android Studio / Cursor: **Sync Project with Gradle** (or **File → Sync Project with Gradle Files**).

Generated code lives under `database/build/generated/` and is added to the Kotlin source set by the SQLDelight plugin.

## Schema

| Table | Purpose |
|-------|---------|
| **User** | Auth: id, name, email, passwordHash, createdAt. Use hashed passwords only. |
| **Task** | User tasks; FK to TaskCategory, TaskPriority. Indexed on userId, (userId, isCompleted), (userId, dueDate). |
| **Habit** | User habits; FK to HabitIcon, HabitFrequencyType. Indexed on userId, (userId, isArchived). |
| **TaskCategory** | Reference: Work, Personal, Health, etc. |
| **TaskPriority** | Reference: Low, Medium, High. |
| **HabitIcon** | Reference: icon names for habits. |
| **HabitFrequencyType** | Reference: Daily, Custom. |

## Setup

1. Create driver: `DriverFactory(platformContext)` (Android: Application context; iOS: null).
2. Create database: `HabitFlowDatabase(driverFactory.createDriver())`.
3. Seed reference data: call `initializeDatabase(database)` from a coroutine (suspend, idempotent).
4. Register repositories in DI: `TaskRepositoryImpl(database)`, `HabitRepositoryImpl(database)`, `UserRepositoryImpl(database)`.

## Queries

- **User**: `selectByEmail`, `selectById`, `insert`.
- **Task**: `insert`, `update`, `deleteById`, `selectAllByUserId`, `selectByUserIdAndId`, `selectIncompleteByUserId`.
- **Habit**: `insert`, `update`, `deleteById`, `selectHabitsAllByUserId`, `selectHabitsActiveByUserId`, `selectHabitByUserIdAndId`.
- **Reference**: `selectAll` / `selectById` per table; seed via `SeedDataQueries.seedTaskCategories()` etc.

See `docs/DATABASE-SCHEMA-AND-MVI.md` for repository interfaces, mappings, and example MVI ViewModels.
