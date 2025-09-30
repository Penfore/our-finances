# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Planned
- 📈 **Financial Analytics** - Charts and graphs for spending patterns
- 🗓️ **Date Range Filtering** - Filter transactions by custom date ranges
- 💹 **Budget Management** - Set and track spending budgets by category
- 📤 **Data Export** - Export transactions to CSV/JSON formats
- 🌙 **Dark Theme** - Complete dark mode support
- ☁️ **Cloud Sync** - Backup and synchronization across devices
- 🔍 **Advanced Search** - Search transactions by description, amount, or category
- 📊 **Detailed Reports** - Monthly, quarterly, and annual financial reports
- 🎯 **Financial Goals** - Set and track savings goals
- 🔔 **Smart Notifications** - Budget alerts and spending reminders

## [0.1.0] - 2025-01-09

### Added
- 🏗️ **Clean Architecture Foundation**
  - Domain layer with entities, repositories, and use cases
  - Data layer with Room database integration
  - Presentation layer with MVVM pattern and Jetpack Compose
  - Manual dependency injection with factory pattern

- 💰 **Core Transaction Management**
  - Add new transactions with amount, description, and category
  - View transaction history with organized list display
  - Delete transactions with confirmation
  - Transaction entity with proper Room database mapping

- 🏷️ **Category System**
  - Predefined transaction categories (Food, Transport, Entertainment, etc.)
  - Income and expense transaction type support
  - Category-based transaction organization
  - Color-coded category display

- 📊 **Financial Summary Dashboard**
  - Real-time balance calculation (income - expenses)
  - Total income and total expenses tracking
  - Clean card-based summary display
  - Automatic updates when transactions change

- 🎨 **Modern UI with Jetpack Compose**
  - Material Design 3 components and theming
  - Responsive design for different screen sizes
  - Clean, intuitive user interface
  - Navigation between Home and Add Transaction screens

- 💾 **Local Database Storage**
  - Room database with SQLite backend
  - Type-safe database queries and operations
  - Proper entity relationships and data mapping
  - Local data persistence without network dependency

- 🌐 **Unicode Support**
  - Full support for international characters and accents
  - Proper UTF-8 encoding configuration
  - Support for various currency symbols and formats
  - Text input validation for unicode characters

- 🧪 **Testing Framework**
  - Unit test structure for business logic
  - Repository and use case testing setup
  - ViewModel testing foundation
  - Comprehensive test coverage planning

### Technical Details
- **Architecture**: Clean Architecture with clear layer separation
- **Database**: Room with proper entity mapping and type converters
- **UI Framework**: Jetpack Compose with Material Design 3
- **State Management**: MVVM pattern with reactive UI updates
- **Build System**: Gradle with Kotlin DSL and modern Android tooling
- **Minimum SDK**: API level 28 (Android 9.0)
- **Target SDK**: API level 36 (Android 14)
- **Language**: Kotlin with modern language features

### Project Structure
```
app/src/main/java/net/sertiva/ourfinances/
├── data/                 # Data layer implementation
├── domain/              # Business logic and entities
├── ui/                  # Presentation layer with Compose
└── utils/               # Utility classes and helpers
```

---

## Format Guidelines

### Types of Changes
- **Added** for new features
- **Changed** for changes in existing functionality
- **Deprecated** for soon-to-be removed features
- **Removed** for now removed features
- **Fixed** for any bug fixes
- **Security** in case of vulnerabilities

### Version Format
- Follow [Semantic Versioning](https://semver.org/)
- MAJOR.MINOR.PATCH
- MAJOR: Breaking changes
- MINOR: New features (backward compatible)
- PATCH: Bug fixes (backward compatible)
