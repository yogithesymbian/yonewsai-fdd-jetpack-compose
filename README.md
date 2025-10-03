# 📰 NewsApp - YoNewsAi

A native Android application built with **Kotlin**, **Jetpack Compose**, and **Clean Architecture** using **Feature-Driven Development (FDD)**.  
The app consumes data from [NewsAPI.org](https://newsapi.org) and demonstrates modern Android development practices.


---

## ✨ Features
- Fetch and display the latest news headlines.  
- View detailed information about each news article.  
- Built with **Jetpack Compose Material3** for modern UI/UX.  
- Navigation with **Navigation-Compose**.  
- **Hilt** for dependency injection.  
- **Coroutines + Flow** for async and reactive data handling.  
- **Retrofit + Moshi** for network calls.  
- **Coil** for image loading.  
- Error & loading state handling.
  
NB: build gradle still not clean need to use lib.version.toml.

---

## 🏛️ Architecture
This project follows **Clean Architecture** principles with **Feature-Driven Development (FDD)**.  

```
com.yogiveloper.yonewsai
├── core/           # shared utilities, common DI, Result wrapper
├── feature_news/   # main feature module # but i wrap with modules folder
│    ├── data/      # repositories, API service, DTOs
│    ├── domain/    # models, use cases, repository interfaces
│    ├── presentation/
│    │    ├── list/    # NewsListScreen + ViewModel
│    │    └── detail/  # NewsDetailScreen + ViewModel
│    └── di/        # Hilt modules for feature
└── MainActivity.kt # host navigation
````

### Flow
1. **Data Layer** → `NewsApiService` (Retrofit) → `NewsRepositoryImpl`.  
2. **Domain Layer** → `GetTopHeadlinesUseCase` (business logic).  
3. **Presentation Layer** → `NewsListViewModel` → `NewsListScreen` (Compose UI).  

---

## 📸 Screenshots
| News List | News Detail |
|-----------|-------------|
| ![List Screenshot](screenshots/news_list.png) | ![Detail Screenshot](screenshots/news_detail.png) |

---

## 🛠️ Tech Stack
- **Kotlin 2.0.21**  
- **Jetpack Compose (Material3)**  
- **Navigation-Compose**  
- **Hilt (DI)**  
- **Retrofit + Moshi**  
- **Coil (image loading)**  
- **Coroutines + Flow**  
- **JUnit, Mockito, Compose UI Test**  

---

## ✅ Requirements
- Android Studio **Koala Feature Drop | 2025**  
- Gradle 8.5+  
- Min SDK 24  

---

## 🔧 Setup & Installation
1. Clone repository:
```bash
   git clone https://github.com/yogithesymbian/yonewsai-fdd-jetpack-compose.git
````

2. Get an API key from [NewsAPI.org](https://newsapi.org).
3. Add the key in `gradle.properties`:

   ```
   NEWS_API_KEY=your_api_key_here
   ```
4. Build & run the project:

   ```bash
   ./gradlew assembleDebug
   ```

---

## 🧪 Testing

* Unit tests:

    * `GetTopHeadlinesUseCaseTest`
* UI tests:

    * `NewsListScreenTest`

Run all tests:

```bash
./gradlew test
./gradlew connectedAndroidTest
```

---

## 📚 Learning Goals

This project demonstrates:

* Building UI with **Jetpack Compose**.
* Applying **Clean Architecture** in an Android app.
* Using **FDD (Feature-Driven Development)** for modularity.
* Writing **unit tests & UI tests** in Compose.

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

Copyright (c) 2025 yogiveloper / yogithesymbian (Yogi Arif Widodo)
