# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

**FinTrack Pro** is a CLI-based personal finance tracker for university students planning a BTO (Build-To-Order) housing downpayment. It helps track expenses against a monthly allowance and projects savings progress toward an individual's BTO contribution goal.

## Build & Run Commands

```bash
# Build the project
./gradlew build

# Run the application
./gradlew run

# Run all tests
./gradlew test

# Run a single test class
./gradlew test --tests "seedu.duke.CommandHandlerTest"

# Run a single test method
./gradlew test --tests "seedu.duke.CommandHandlerTest.testHandleAdd"

# Build a fat JAR
./gradlew shadowJar   # outputs build/libs/duke.jar

# Run checkstyle
./gradlew checkstyle
```

Assertions are enabled at runtime (`enableAssertions = true` in `build.gradle`).

## Architecture

The app follows a layered architecture: `Duke` (entry) → `FinTrackPro` (app controller) → `CommandHandler` (command logic), with shared data objects passed by reference.

### Key Classes

- **`Duke`** — entry point; creates `Ui` and `FinTrackPro`, calls `run()`.
- **`FinTrackPro`** — app lifecycle: loads persisted data, runs first-time setup or welcomes back user, then drives the main command loop. Dispatches to `CommandHandler` via `handleCommand()`. Also owns `printList()`.
- **`CommandHandler`** — implements all mutating commands (`handleAdd`, `handleDelete`, `handleSaveMonth`, `handleSummary`, etc.). Receives `Profile`, `ExpenseList`, `RecurringExpenseList`, and `Storage` via constructor injection.
- **`Parser`** — stateless; extracts command tokens and validates indices.
- **`Storage`** — reads/writes `fintrack.txt` using a pipe-delimited format: `P |` (profile), `E |` (one-off expense), `R |` (recurring expense). Monthly archives go to `monthly_archives/Month{N}.txt` via `MonthlyArchive`.

### Data Model

- **`Profile`** — user's name, monthly allowance, current savings, BTO goal, contribution ratio (0–1), deadline, and current month counter. Calling `setContributionRatio()` automatically recalculates `btoGoal` if `housePrice` is set.
- **`ExpenseList`** / **`RecurringExpenseList`** — in-memory lists backed by `ArrayList`. `ExpenseList` supports sort-by-name, sort-by-category, and sort-by-insertion-order.
- **`BtoCalculator`** — computes `totalDownpayment` (2.5% of house price) and `yourShare` (adjusted by contribution ratio + legal fees).
- **`SummaryReport`** — derives BTO readiness metrics (distance to goal, monthly surplus, % progress, estimated months) from `Profile` + `ExpenseList` + `RecurringExpenseList`.

### Categories

`Category` is an enum-like abstraction with concrete subclasses in `seedu.duke.category`: `FOOD`, `TRANSPORT`, `ENTERTAINMENT`, `UTILITIES`, `OTHER`. Use `Category.fromString()` and `Category.isValid()` for parsing.

### Month Progression

`save` command archives the current `ExpenseList` to `monthly_archives/`, transfers unspent allowance to savings, clears the list, and increments `profile.currentMonth`. The `list` command reads all archived months plus the current in-memory list.

### Persistence

Data is saved to `fintrack.txt` on `bye` and on `reset`. It is loaded at startup; if the file is missing, the app starts fresh. Monthly archives are append-only files in `monthly_archives/`.

### Logging

`LoggerUtil` configures a `FileHandler` writing to `logs/fintrack.log`. All classes obtain their logger via `LoggerUtil.getLogger(MyClass.class)`.

## Checkstyle

Config is at `config/checkstyle/checkstyle.xml` with suppressions in `config/checkstyle/suppressions.xml`. Checkstyle version 10.2 is enforced via Gradle.
