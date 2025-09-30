# 💰 Our Finances

<div align="center">
  <img src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white" alt="Android" />
  <img src="https://img.shields.io/badge/Kotlin-0095D5?&style=for-the-badge&logo=kotlin&logoColor=white" alt="Kotlin" />
  <img src="https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white" alt="Jetpack Compose" />
  <img src="https://img.shields.io/badge/version-0.1.0-blue?style=for-the-badge" alt="Version" />
  <img src="https://img.shields.io/badge/License-MIT-green.svg?style=for-the-badge" alt="License" />
  <img src="https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=for-the-badge" alt="PRs Welcome" />
</div>

<div align="center">
  <img src="https://img.shields.io/github/stars/Penfore/our-finances?style=social" alt="GitHub stars" />
  <img src="https://img.shields.io/github/forks/Penfore/our-finances?style=social" alt="GitHub forks" />
  <img src="https://img.shields.io/github/watchers/Penfore/our-finances?style=social" alt="GitHub watchers" />
</div>

<div align="center">
  <h3>📊 Take control of your personal finances</h3>
  <p>A beautiful, modern personal finance management app built with Jetpack Compose following Clean Architecture and MVVM principles.</p>
</div>

---

## ✨ Features

### 🚀 **Current Features (v0.1.0)**
- 💰 **Transaction Management** - Add, view, and delete financial transactions ✅
- 🏷️ **Category System** - Organize transactions with predefined categories ✅
- 💳 **Transaction Types** - Support for income and expense transactions ✅
- 📊 **Financial Summary** - Real-time overview of balance, income, and expenses ✅
- 🎨 **Modern UI** - Clean Material Design 3 interface with Jetpack Compose ✅
- 💾 **Local Database** - Secure data storage with Room database ✅
- 🏗️ **Clean Architecture** - Maintainable, testable, and scalable codebase ✅
- 🧪 **Unit Testing** - Comprehensive test coverage for business logic ✅
- 🌐 **Unicode Support** - Full support for international characters and currencies ✅
- 📱 **Responsive Design** - Optimized for different screen sizes ✅

### 🔧 **Technical Foundation Complete**
- 📦 **Dependency Injection** - Manual DI setup with factory pattern ✅
- 🏛️ **Domain Layer** - Entities, repositories, and use cases ✅
- 💾 **Data Layer** - Room database with proper entity mapping ✅
- 🎛️ **Presentation Layer** - MVVM with Jetpack Compose ✅
- 🔄 **State Management** - Reactive UI with Compose state ✅

### 📋 **Planned Features**
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

## 📱 Screenshots

### 🎯 Current App Interface (v0.1.0)

<div align="center">

| Home Screen | Add Transaction | Transaction List |
|:-----------:|:---------------:|:----------------:|
| ![Home screen showing financial summary with balance, income and expenses](images/home-screen.png) | ![Add transaction screen with amount input and category selection](images/add-transaction.png) | ![List of transactions with categories and amounts](images/transaction-list.png) |
| Dashboard with financial overview | Clean transaction entry form | Organized transaction history |

</div>

### ✨ **Interface Highlights**

- **💰 Financial Dashboard** - Clean overview of your financial status
- **📊 Summary Cards** - Balance, total income, and total expenses at a glance
- **💳 Transaction Form** - Intuitive form for adding new transactions
- **🏷️ Category Selection** - Easy category assignment with visual indicators
- **📱 Material Design 3** - Modern, consistent design language
- **🎨 Clean Typography** - Readable fonts optimized for financial data
- **📊 Visual Hierarchy** - Clear information organization and flow
- **🔄 Responsive Layout** - Adapts to different screen sizes and orientations

### 🎨 **Visual Features**
- **Material Design 3** components with modern styling
- **Color-coded Categories** - Different colors for income and expense categories
- **Clean Card Layout** - Organized information in easy-to-read cards
- **Intuitive Icons** - Clear visual representation of different transaction types
- **Consistent Spacing** - Proper padding and margins for comfortable viewing
- **Typography Hierarchy** - Different text sizes for better information hierarchy

## 🚀 Getting Started

### Prerequisites

Before you begin, ensure you have the following installed:

- [Android Studio](https://developer.android.com/studio) (latest version)
- [Kotlin](https://kotlinlang.org/) (1.9.0 or later)
- Android SDK (API level 28 or higher)
- Java 11 or higher

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/Penfore/our-finances.git
   cd our-finances
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an existing project"
   - Navigate to the cloned repository folder

3. **Sync the project**
   - Android Studio will automatically sync Gradle dependencies
   - Wait for the sync to complete

4. **Run the app**
   - Connect an Android device or start an emulator
   - Click the "Run" button or press `Shift + F10`

### Build for Production

**Debug APK**
```bash
./gradlew assembleDebug
```

**Release APK**
```bash
./gradlew assembleRelease
```

**App Bundle (for Play Store)**
```bash
./gradlew bundleRelease
```

> 📱 **Platform Notes**:
> - **Android**: Minimum API level 28 (Android 9.0)
> - **Target SDK**: API level 36 (Android 14)
> - **Architecture**: Supports ARM64 and x86_64

## 🏗️ Architecture

This project follows **Clean Architecture** and **MVVM** principles, ensuring:

- **Separation of Concerns** - Each layer has a single responsibility
- **Dependency Inversion** - High-level modules don't depend on low-level modules
- **Testability** - Easy to unit test business logic
- **Maintainability** - Easy to modify and extend
- **Scalability** - Architecture supports feature growth

### Project Structure

```
app/src/main/java/net/sertiva/ourfinances/
├── data/                     # Data layer
│   ├── database/            # Room database setup
│   │   ├── dao/            # Data Access Objects
│   │   ├── entities/       # Database entities
│   │   └── converters/     # Type converters
│   └── repository/         # Repository implementations
├── domain/                  # Domain layer
│   ├── model/              # Domain models
│   └── usecase/            # Business use cases
├── ui/                     # Presentation layer
│   ├── screen/             # Compose screens
│   ├── theme/              # App theming
│   └── viewmodel/          # ViewModels
├── utils/                  # Utility classes
└── MainActivity.kt         # App entry point
```

### Tech Stack

- **Framework**: Android Native with Jetpack Compose
- **Language**: Kotlin 1.9.0+
- **UI**: Jetpack Compose with Material Design 3
- **Architecture**: Clean Architecture + MVVM
- **Database**: Room (SQLite)
- **Dependency Injection**: Manual DI with Factory pattern
- **Navigation**: Jetpack Navigation Compose
- **State Management**: Compose State + ViewModels
- **Build Tool**: Gradle with Kotlin DSL

### Key Dependencies

- **Jetpack Compose** - Modern UI toolkit
- **Room** - Local database
- **ViewModel** - UI state management
- **Navigation Compose** - Type-safe navigation
- **Material 3** - Modern design components
- **Kotlin Coroutines** - Asynchronous programming

## 🧪 Testing

Run all tests:
```bash
./gradlew test
```

Run instrumented tests:
```bash
./gradlew connectedAndroidTest
```

Run tests with coverage:
```bash
./gradlew testDebugUnitTestCoverage
```

### Testing Strategy

- **Unit Tests** - Business logic and use cases
- **Repository Tests** - Data layer testing
- **ViewModel Tests** - Presentation logic testing
- **Database Tests** - Room database testing
- **UI Tests** - Compose UI testing (planned)

## 📋 Development Roadmap

### Phase 1: Core Foundation ✅
- [x] Clean Architecture setup
- [x] Room database integration
- [x] Basic MVVM structure
- [x] Domain models and use cases

### Phase 2: Basic Functionality ✅
- [x] Transaction CRUD operations
- [x] Category system
- [x] Financial summary calculation
- [x] Basic UI with Compose
- [x] Navigation between screens

### Phase 3: Enhanced UI/UX 📝
- [ ] Dark theme implementation
- [ ] Improved visual design
- [ ] Better responsive layouts
- [ ] Enhanced animations and transitions
- [ ] Accessibility improvements

### Phase 4: Advanced Features 📝
- [ ] Financial charts and analytics
- [ ] Date range filtering
- [ ] Data export functionality
- [ ] Search and advanced filtering
- [ ] Budget management system

### Phase 5: Data & Sync 📝
- [ ] Data backup and restore
- [ ] Cloud synchronization
- [ ] Multi-device support
- [ ] Data migration tools

### Phase 6: Intelligence & Insights 📝
- [ ] Spending pattern analysis
- [ ] Budget recommendations
- [ ] Financial goal tracking
- [ ] Smart categorization
- [ ] Predictive analytics

## 🤝 Contributing

We love contributions! Please read our [Contributing Guide](CONTRIBUTING.md) to learn about our development process.

### Quick Start for Contributors

1. **Fork** the repository
2. **Create** a feature branch (`git checkout -b feature/AmazingFeature`)
3. **Commit** your changes (`git commit -m 'Add some AmazingFeature'`)
4. **Push** to the branch (`git push origin feature/AmazingFeature`)
5. **Open** a Pull Request

### Development Guidelines

- Follow [Kotlin coding conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Write meaningful commit messages following [Conventional Commits](https://www.conventionalcommits.org/)
- Add tests for new features
- Update documentation as needed
- Ensure code passes `./gradlew check`

### Types of Contributions

- 🐛 **Bug reports** - Help us identify issues
- 💡 **Feature requests** - Suggest new functionality  
- 🔧 **Code contributions** - Implement features or fix bugs
- 📚 **Documentation** - Improve docs and examples
- 🎨 **Design** - UI/UX improvements
- 🧪 **Testing** - Improve test coverage

## 🤖 AI-Assisted Development

This project embraces modern development practices and acknowledges the role of AI in today's software development landscape.

### Our Position on AI

- 🎯 **AI as a Tool**: We recognize that AI tools (like GitHub Copilot, ChatGPT, etc.) are valuable assistants that help developers learn, explore new technologies, and increase productivity
- 👥 **Human Review Required**: While AI can assist with code generation and problem-solving, **all code must be reviewed, understood, and validated by real humans** before being merged
- 🧠 **Learning Enhancement**: AI tools are excellent for learning new patterns, understanding complex architectures, and exploring different implementation approaches
- 🔍 **Quality Assurance**: Contributors should always understand the code they're submitting, regardless of how it was generated

### Guidelines for AI-Assisted Contributions

- ✅ **Use AI tools** to help with boilerplate code, documentation, or learning new concepts
- ✅ **Review and understand** all AI-generated code before submitting
- ✅ **Test thoroughly** - AI-generated code should be tested just like any other code
- ✅ **Document your approach** - If AI helped solve a complex problem, consider documenting the solution for others
- ❌ **Don't blindly copy-paste** AI-generated code without understanding it
- ❌ **Don't rely solely on AI** for architectural decisions or critical business logic

### The Human Touch

While we embrace AI assistance, we believe in:
- **Human creativity** in solving complex problems
- **Human judgment** in making architectural decisions
- **Human empathy** in understanding user needs
- **Human responsibility** for code quality and security

This project is built by humans, for humans, with AI as a helpful companion in our development journey.

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Authors & Contributors

- **[Fúlvio Leo]** - *Initial work* - [@Penfore](https://github.com/Penfore)

See also the list of [contributors](https://github.com/Penfore/our-finances/contributors) who participated in this project.

## 📞 Support & Community

- 🐛 **Issues**: [GitHub Issues](https://github.com/Penfore/our-finances/issues)
- 💬 **Discussions**: [GitHub Discussions](https://github.com/Penfore/our-finances/discussions)
- 📧 **Email**: fulvioleo.dev@pm.me

## 🙏 Acknowledgments

- Built with [Jetpack Compose](https://developer.android.com/jetpack/compose) by Google
- Icons provided by [Material Design Icons](https://fonts.google.com/icons)
- Inspired by modern personal finance management principles
- Thanks to all [contributors](https://github.com/Penfore/our-finances/contributors)

## 📈 Project Stats

<div align="center">

![GitHub Issues](https://img.shields.io/github/issues/Penfore/our-finances?style=flat-square&color=red)
![GitHub Pull Requests](https://img.shields.io/github/issues-pr/Penfore/our-finances?style=flat-square&color=blue)
![Last Commit](https://img.shields.io/github/last-commit/Penfore/our-finances?style=flat-square&color=green)
![Repo Size](https://img.shields.io/github/repo-size/Penfore/our-finances?style=flat-square&color=orange)

</div>

---

<div align="center">
  <p>Made with ❤️ and ☕ by developers, for developers</p>
  <p>If this project helped you, please consider giving it a ⭐!</p>
</div>
