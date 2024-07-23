# Jetpack Compose + MVI + Modern Architecture
## Dependency
- Jetpack Compose
- MVI (UIState)
- DI (Dagger Hilt)
- Modern Architecutre (Clean Architecture)
- Room DB
- Ktor Client for android
- Preference DataStore

## Data Flow
- Data Module: LocalDB, RESTFul API, Preference 등 Data 관련 모듈
- Domain Module: Repository Interface, Model for App, usecase 관련 모듈
- App Module: View 관련 모듈

데이터의 흐름은 App -> Domain -> Data -> Domain -> App 순으로 진행

## Jetpack Compose + MVI
선언형 UI + UIState에 따라 상태에 따른 UI/UX 관리
