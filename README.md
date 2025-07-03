# DVTWeatherApp ☀️🌧️

DVTWeatherApp is a modern Android weather application that provides weather information based on the user's location. It fetches weather data from the OpenWeather API and displays 
it on a user-friendly interface.

## 📱 Features

- 🌍 Fetch weather based on current location
- 🌍 Location Permissions: Requests location permissions from the user and updates weather information accordingly.
- 🔍 Search weather by city name
- 🌦️ View the next 5 days of weather forecasts
- 🔒 Handles location permission and GPS enablement

Libraries & Dependencies
The app uses the following libraries:
* Koin: Dependency injection.
* Compose UI: For building the user interface.
* OpenWeather API: For fetching weather data.
* Kotlin Coroutines: For asynchronous operations.
* Permissions API: For location permissions.
* Material3: For Material Design 3 components.
Key Components
* WeatherViewModel: The ViewModel for managing weather data and theme updates.
* WeatherUiState: A data class representing the current state of weather information and UI.
* WeatherCard: A composable function displaying weather data (temperature, icon, city).
* MainActivity: The main activity where the UI components are composed, and location permissions are handled.

### ⚙️ Setup Instructions

1. Clone the repository: git clone [[https://github.com/your-repository/weather-task-app.git](https://github.com/dennisluke11/WeatherTaskApp.git](https://github.com/dennisluke11/DVTWeatherApp.git)
2. Add your OpenWeather API key:
    * FlowConstants class => const val API_KEY = ""  
3. Install the dependencies:
    * Open the project in Android Studio and wait for Gradle to sync.
4. Run the app:
    * After syncing the dependencies, click on the "Run" button in Android Studio to install and launch the app on an emulator or a physical device.
Usage
1. Permissions:
    * The app will request location permissions upon the first launch. If granted, it will fetch weather data for the current location.
2. Error Handling:
    * If the app fails to fetch weather data or if location permissions are denied, appropriate error messages will be displayed and the user can as well search for any city on the search bar to get the weather data

## 🖼 UI Screens

- **CurrentWeatherCard**  
  Displays the next five dats temperature, icon, day of the week, and city.

  <img src="https://github.com/user-attachments/assets/43364faf-f20e-4405-8e65-a2376aad0b93" alt="Screenshot_20250703_162738_DVTWeatherApp" width="300"/>

- **Error / Loading States**  
  Intuitive UI messaging.

## 🚀 Getting Started

### Prerequisites

- Android Studio Giraffe or newer
- Minimum SDK: 24
- Kotlin 1.8+
- Internet connection (for fetching weather data)
- A valid Weather API key
