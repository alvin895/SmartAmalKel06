# SmartAmal Mobile Application

Flutter application untuk SmartAmal - IoT Device Management System.

## Deskripsi

SmartAmal App adalah aplikasi mobile yang digunakan untuk:
- Mengontrol IoT devices
- Melihat sensor readings dan data
- Manage device configuration
- Receive notifications dari system

## Struktur Project

```
smartamal-app/
├── lib/              # Source code aplikasi
├── assets/           # Images, icons, fonts, animations
├── android/          # Android-specific configuration
├── ios/              # iOS-specific configuration
├── pubspec.yaml      # Dependencies dan project configuration
└── README.md         # File ini
```

## Prerequisites

- Flutter SDK 3.0.0 or higher
- Dart 3.0.0 or higher
- Android Studio / Xcode (untuk development)
- Emulator atau physical device

## Setup

### 1. Install Dependencies

```bash
flutter pub get
```

### 2. Run Development Build

**Development Environment:**
```bash
flutter run
```

**Production Environment:**
```bash
flutter run --release
```

## Build

### Android Build

```bash
# Debug
flutter build apk --debug

# Release
flutter build apk --release

# App Bundle (untuk Google Play)
flutter build appbundle --release
```

### iOS Build

```bash
# Debug
flutter build ios --debug

# Release
flutter build ios --release
```

## Project Structure

### lib/
- `main.dart` - Entry point aplikasi
- `screens/` - UI screens
- `widgets/` - Reusable UI components
- `models/` - Data models
- `services/` - Business logic & API services
- `providers/` - State management (provider/bloc/riverpod)
- `utils/` - Utility functions
- `config/` - Configuration files

### assets/
- `images/` - Application images
- `icons/` - Custom icons
- `animations/` - Animation assets
- `fonts/` - Custom fonts

## Features (TODO)

- [ ] Authentication & Login
- [ ] Device List & Management
- [ ] Real-time Device Monitoring
- [ ] Sensor Data Visualization
- [ ] Device Control
- [ ] Notification Management
- [ ] User Profile Management
- [ ] Offline Support
- [ ] Dark Mode Support

## Configuration

### API Endpoints

Update file konfigurasi dengan backend URL:

```dart
// lib/config/api_config.dart
const String BASE_URL = "http://your-backend-url:port/api";
```

### Firebase (Optional)

Untuk push notifications:

1. Setup Firebase project di Firebase Console
2. Download google-services.json (Android) dan GoogleService-Info.plist (iOS)
3. Place di android/app/ dan ios/Runner/

## Testing

```bash
# Run all tests
flutter test

# Run specific test file
flutter test test/path/to/test.dart

# Run with coverage
flutter test --coverage
```

## Debugging

### Enable Logging

```bash
flutter run -v
```

### Use DevTools

```bash
flutter pub global activate devtools
flutter devtools

# Open http://localhost:9100 in browser
```

## State Management

Pilih salah satu approach:
- **Provider**: Simple dan ringan
- **BLoC**: Enterprise-grade, event-driven
- **Riverpod**: Modern, functional approach

## Best Practices

1. **Folder Structure**: Organize code by feature atau layer
2. **Naming Convention**: Follow Flutter conventions (camelCase, PascalCase)
3. **Comments**: Tambahkan dokumentasi untuk complex logic
4. **Error Handling**: Implement proper error handling & user feedback
5. **Performance**: Optimize build time, UI rendering, dan memory usage
6. **Testing**: Write unit tests, widget tests, integration tests

## Troubleshooting

### Common Issues

1. **Gradle build failed** (Android):
   ```bash
   flutter clean
   flutter pub get
   cd android && ./gradlew clean
   ```

2. **Pod install failed** (iOS):
   ```bash
   cd ios
   rm -rf Pods Podfile.lock
   pod install --repo-update
   ```

3. **Device not recognized**:
   ```bash
   flutter devices
   flutter devices --verbose
   ```

## Documentation Links

- [Flutter Documentation](https://flutter.dev/docs)
- [Dart Documentation](https://dart.dev/guides)
- [Flutter Best Practices](https://flutter.dev/docs/testing/best-practices)

## Contributing

1. Create feature branch: `git checkout -b feature/feature-name`
2. Commit changes: `git commit -m 'Add feature-name'`
3. Push to branch: `git push origin feature/feature-name`
4. Create Pull Request

## License

Proprietary - SmartAmal

## Team

- Backend Team
- IoT Team
- Mobile Development Team
- DevOps Team

## Next Steps

- [ ] Setup project structure dan folder organization
- [ ] Implement API service layer
- [ ] Setup authentication
- [ ] Create main navigation structure
- [ ] Implement device list screen
- [ ] Implement device control features
- [ ] Setup push notifications
- [ ] Performance optimization
- [ ] Testing setup
- [ ] CI/CD pipeline untuk mobile
