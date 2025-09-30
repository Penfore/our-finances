# Contributing to Our Finances

First off, thank you for considering contributing to Our Finances! 🎉

## Table of Contents

- [Code of Conduct](#code-of-conduct)
- [Getting Started](#getting-started)
- [How Can I Contribute?](#how-can-i-contribute)
- [Development Process](#development-process)
- [Style Guidelines](#style-guidelines)
- [Commit Guidelines](#commit-guidelines)
- [Pull Request Process](#pull-request-process)

## Code of Conduct

This project and everyone participating in it is governed by our Code of Conduct. By participating, you are expected to uphold this code.

### Our Pledge

We pledge to make participation in our project a harassment-free experience for everyone, regardless of age, body size, disability, ethnicity, gender identity and expression, level of experience, nationality, personal appearance, race, religion, or sexual identity and orientation.

### Our Standards

Examples of behavior that contributes to creating a positive environment include:
- Using welcoming and inclusive language
- Being respectful of differing viewpoints and experiences
- Gracefully accepting constructive criticism
- Focusing on what is best for the community
- Showing empathy towards other community members

## Getting Started

### Prerequisites

- Android Studio (latest version)
- Kotlin 1.9.0 or later
- Android SDK (API level 28 or higher)
- Java 11 or higher
- Git
- A code editor (Android Studio, IntelliJ IDEA, VS Code, etc.)

### Setup Development Environment

1. Fork the repository on GitHub
2. Clone your fork locally:
   ```bash
   git clone https://github.com/Penfore/our-finances.git
   cd our-finances
   ```
3. Add the original repository as upstream:
   ```bash
   git remote add upstream https://github.com/Penfore/our-finances.git
   ```
4. Open the project in Android Studio:
   - Start Android Studio
   - Select "Open an existing project"
   - Navigate to the cloned repository folder
5. Sync the project:
   - Android Studio will automatically sync Gradle dependencies
   - Wait for the sync to complete
6. Run the app to make sure everything works:
   - Connect an Android device or start an emulator
   - Click the "Run" button or press `Shift + F10`

## How Can I Contribute?

### Reporting Bugs

Before creating bug reports, please check existing issues as you might find that the problem has already been reported.

When you create a bug report, please include:
- **A clear and descriptive title**
- **Steps to reproduce** the behavior
- **Expected behavior**
- **Actual behavior**
- **Screenshots** if applicable
- **Device information** (Android version, device model, etc.)
- **App version** (current: v0.1.0)
- **Database state** (if relevant to the bug)
- **Transaction data examples** (if safe to share)

### Suggesting Enhancements

Enhancement suggestions are tracked as GitHub issues. When creating an enhancement suggestion, please include:
- **A clear and descriptive title**
- **A detailed description** of the suggested enhancement
- **The motivation** for the enhancement
- **Examples** of how the enhancement would be used
- **Mockups or wireframes** if applicable for UI enhancements

### Code Contributions

#### Good First Issues

Look for issues labeled `good first issue` if you're new to the project.

#### Areas Where We Need Help

- UI/UX improvements and Material Design 3 enhancements
- Financial analytics and charting features
- Dark theme implementation
- Performance optimizations
- Testing coverage (unit tests, UI tests)
- Documentation improvements
- Accessibility features
- Internationalization (additional languages)
- Data export/import functionality
- Advanced filtering and search features

## Development Process

### Branching Strategy

We use a simplified Git flow:
- `main` - Production ready code
- `develop` - Integration branch for features
- `feature/feature-name` - Feature branches
- `bugfix/bug-description` - Bug fix branches
- `hotfix/critical-fix` - Critical fixes

### Workflow

1. Create a new branch from `develop`:
   ```bash
   git checkout develop
   git pull upstream develop
   git checkout -b feature/your-feature-name
   ```

2. Make your changes and commit them:
   ```bash
   git add .
   git commit -m "feat: add your feature description"
   ```

3. Push to your fork:
   ```bash
   git push origin feature/your-feature-name
   ```

4. Create a Pull Request

## Style Guidelines

### Kotlin Code Style

We follow the [Kotlin coding conventions](https://kotlinlang.org/docs/coding-conventions.html):

- Use `./gradlew ktlintFormat` to format your code
- Run `./gradlew check` before committing
- Follow the existing code patterns in the project
- Use meaningful variable and function names
- Add KDoc comments for public APIs

### Architecture Guidelines

- Follow Clean Architecture principles
- Maintain separation of concerns between layers
- Use dependency injection (manual DI with factory pattern)
- Write testable code
- Keep business logic in use cases
- Use MVVM pattern for presentation layer
- Follow single responsibility principle

### File Organization

```
app/src/main/java/net/sertiva/ourfinances/
├── data/                 # Data layer
│   ├── database/        # Room database setup
│   └── repository/      # Repository implementations
├── domain/              # Domain layer
│   ├── model/          # Domain models
│   └── usecase/        # Business use cases
├── ui/                 # Presentation layer
│   ├── screen/         # Compose screens
│   ├── theme/          # App theming
│   └── viewmodel/      # ViewModels
└── utils/              # Utility classes
```

### UI Guidelines

- Follow Material Design 3 principles
- Use Jetpack Compose for all UI components
- Ensure responsive design for different screen sizes
- Implement proper accessibility features
- Use consistent spacing and typography
- Follow the existing color scheme and theming

## Commit Guidelines

We follow the [Conventional Commits](https://www.conventionalcommits.org/) specification:

### Commit Format

```
<type>[optional scope]: <description>

[optional body]

[optional footer(s)]
```

### Types

- `feat`: A new feature
- `fix`: A bug fix
- `docs`: Documentation only changes
- `style`: Changes that don't affect code meaning (white-space, formatting, etc.)
- `refactor`: A code change that neither fixes a bug nor adds a feature
- `perf`: A code change that improves performance
- `test`: Adding missing tests or correcting existing tests
- `chore`: Changes to the build process or auxiliary tools

### Examples

```bash
feat(transactions): add transaction filtering by category
feat(ui): implement dark theme support
fix(database): resolve transaction deletion issue
fix(ui): fix amount input field validation
docs(readme): update installation instructions
test(repository): add unit tests for transaction repository
refactor(viewmodel): simplify transaction state management
perf(database): optimize transaction queries
chore(deps): update Room to latest version
```

## Pull Request Process

### Before Submitting

1. Ensure your code follows the style guidelines
2. Run `./gradlew check` and fix any issues
3. Run `./gradlew test` and ensure all tests pass
4. Test your changes on different screen sizes
5. Add tests for new functionality
6. Update documentation if needed
7. Rebase your branch on the latest `develop`
8. Test database migrations if applicable

### Pull Request Template

When creating a PR, please include:

- **Description** - Clear description of what the PR does
- **Type of Change** - Bug fix, new feature, documentation, etc.
- **Testing** - How you tested your changes
- **Screenshots** - If UI changes are involved
- **Database Changes** - If schema changes are involved
- **Checklist** - Ensure all requirements are met

### Review Process

1. At least one maintainer must approve the PR
2. All CI checks must pass
3. No merge conflicts
4. Code must follow project standards
5. Adequate test coverage for new features

### After Approval

- PRs are typically merged by maintainers
- Your branch will be deleted after merge
- Thank you for your contribution! 🎉

## Database Guidelines

### Schema Changes

- Always provide migration scripts for schema changes
- Test migrations thoroughly on different data sets
- Document breaking changes clearly
- Consider backward compatibility when possible

### Data Handling

- Always validate financial data inputs
- Use appropriate data types for monetary values
- Handle edge cases (negative amounts, large numbers, etc.)
- Ensure data integrity and consistency

## Testing Guidelines

### Unit Tests

- Write tests for all business logic
- Test edge cases and error conditions
- Use meaningful test names
- Mock external dependencies

### UI Tests

- Test critical user flows
- Test different screen sizes and orientations
- Test accessibility features
- Verify proper error handling in UI

## Recognition

Contributors are recognized in:
- README.md contributors section
- Release notes for significant contributions
- Special recognition for long-term contributors

## Questions?

Feel free to:
- Open a GitHub Discussion
- Create an issue with the `question` label
- Contact maintainers directly

Thank you for contributing to Our Finances! 💰
