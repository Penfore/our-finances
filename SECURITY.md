# Security Policy

## Supported Versions

We currently support the following versions with security updates:

| Version | Supported          |
| ------- | ------------------ |
| 0.1.x   | :white_check_mark: |
| < 0.1   | :x:                |

**Note**: As this project is in early development (v0.1.0), we recommend using only the latest released version.

## Reporting a Vulnerability

While Our Finances is not a security-critical application, we understand our responsibility as an open source project to address security concerns when they arise, especially given that it handles personal financial data.

### How to Report

**Please do not report security vulnerabilities through public GitHub issues.**

Instead, please report them via email to: fulvioleo.dev@pm.me

Please include the following information in your report:
- Type of issue (buffer overflow, SQL injection, data exposure, etc.)
- Full paths of source file(s) related to the manifestation of the issue
- The location of the affected source code (tag/branch/commit or direct URL)
- Any special configuration required to reproduce the issue
- Step-by-step instructions to reproduce the issue
- Proof-of-concept or exploit code (if possible)
- Impact of the issue, including how an attacker might exploit the issue

### What to Expect

- We will review and respond to vulnerability reports on a best-effort basis
- We will work with you to understand and validate the vulnerability when possible
- We will credit you in our security advisory (unless you prefer to remain anonymous)

### Security Update Process

When security issues are confirmed, we will:
1. Assess the severity and impact of the vulnerability
2. Develop and test appropriate fixes
3. Release updates as feasible
4. Publish security advisories when appropriate

## Data Protection

Our Finances takes data protection seriously:

- **Local Storage Only**: All financial data is stored locally on the device using Room database
- **No Network Access**: The app currently does not transmit any data over the network
- **Encrypted Storage**: Database files are protected by Android's built-in security features
- **No Third-party Analytics**: We do not collect or share any user data with third parties

## Security Best Practices

We follow these security practices in development:

- **Input Validation**: All user inputs are validated and sanitized
- **SQL Injection Prevention**: Using Room's type-safe queries and parameterized statements
- **Secure Coding**: Following Android security best practices
- **Dependency Management**: Regularly updating dependencies to patch known vulnerabilities

## Preferred Languages

We prefer all communications to be in English or Portuguese.

## Safe Harbor

We support safe harbor for security researchers who:
- Make a good faith effort to avoid privacy violations, destruction of data, and interruption or degradation of our services
- Only interact with accounts you own or with explicit permission of the account holder
- Do not access, modify, or store user data
- Contact us immediately if you inadvertently encounter user data

Thank you for helping keep Our Finances and our users safe!
