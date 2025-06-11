lawapp/
│
├── data/                           # 📦 DATA LAYER: Handles data sources (Firebase), models (DTOs), and mapping to domain.
│   ├── repository/
│   │   └── LawRepositoryImpl.kt     # Implements domain repository interface using Firebase (delegates to services).
│   ├── remote/
│   │   └── firebase/
│   │       ├── AuthService.kt       # Firebase Auth wrapper (login, register, logout, get current user).
│   │       ├── FirestoreService.kt  # Firebase Firestore wrapper (CRUD for consultations and requests).
│   ├── model/
│   │   └── ConsultationDto.kt       # Firebase document model for consultations (raw data format).
│   └── mapper/
│       └── ConsultationMapper.kt    # Converts ConsultationDto ⇄ Consultation (domain model).
│
├── domain/                         # 🧠 DOMAIN LAYER: Core business logic, use cases, and clean models.
│   ├── model/
│   │   └── Consultation.kt          # Pure business model used throughout domain and presentation layers.
│   ├── repository/
│   │   └── LawRepository.kt         # Interface defining all operations (e.g., getConsultations, login).
│   └── usecase/
│       ├── GetConsultationsUseCase.kt  # Business logic to retrieve consultation history.
│       ├── SubmitRequestUseCase.kt     # Encapsulates use case for submitting a new legal request.
│       └── AuthUseCase.kt              # Wraps auth-related use cases (login, logout, register).
│
├── presentation/                  # 🎨 PRESENTATION LAYER: Jetpack Compose UI and ViewModels per screen.
│   ├── auth/
│   │   ├── AuthScreen.kt             # Composable for login/register screen (Firebase-based auth).
│   │   └── AuthViewModel.kt          # Manages UI state for authentication, interacts with AuthUseCase.
│   ├── home/
│   │   ├── HomeScreen.kt             # Main screen with 4 feature cards (in Arabic).
│   │   └── HomeViewModel.kt          # Handles UI events or navigation from home screen.
│   ├── history/
│   │   ├── HistoryScreen.kt          # LazyColumn of consultation history, using horizontal cards.
│   │   └── HistoryViewModel.kt       # Loads data from GetConsultationsUseCase and exposes state.
│   ├── payments/
│   │   ├── PaymentsScreen.kt         # Displays a centered “Coming Soon” placeholder UI.
│   │   └── PaymentsViewModel.kt      # Stub ViewModel for future payment-related features.
│   ├── profile/
│   │   ├── ProfileScreen.kt          # Displays user info (name, email) and logout button.
│   │   └── ProfileViewModel.kt       # Handles logout logic and profile data retrieval from Firebase.
│   └── navigation/
│       └── AppNavHost.kt             # Central navigation graph managing composable destinations.
│
├── ui/theme/                      # 🎨 APP THEME: Material3 theme definitions and custom colors.
│   ├── Theme.kt                     # App-wide MaterialTheme, supports RTL for Arabic.
│   └── Color.kt                     # Custom Material3 color scheme.
│
├── di/                            # 🧩 DEPENDENCY INJECTION: Provides UseCases, Firebase, Repository (Hilt).
│   └── AppModule.kt                # Hilt module with `@Provides` functions for singletons and use cases.
│
└── MainActivity.kt                # 🚀 Entry point. Sets up Compose, navigation, and the app theme.
