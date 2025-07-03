# DVTWeatherApp ☀️🌧️

DVTWeatherApp is a modern Android weather application built with **Jetpack Compose**, **Kotlin**, and **MVVM architecture**. It fetches the current and past week's weather data using location services, offering a clean, responsive UI and handling permissions gracefully.

## 📱 Features

- 🌍 Fetch weather based on current location
- 🔍 Search weather by city name
- 🌦️ View current and past 5 days of weather forecasts
- 🔒 Handles location permission and GPS enablement

## 🖼 UI Screens

- **CurrentWeatherCard**  
  Displays today's temperature, icon, condition, and city.

- **PastWeekWeatherCard**  
  Shows a scrollable list of average temperatures and conditions for the last 7 days.

- **Error / Loading States**  
  Intuitive UI messaging with retry capability.

<img src="assets/DVTWeatherApp.jpg" alt="rapidweatherImage" width="300"/>

## 🚀 Getting Started

### Prerequisites

- Android Studio Giraffe or newer
- Minimum SDK: 24
- Kotlin 1.8+
- Internet connection (for fetching weather data)
- A valid Weather API key

### ⚙️ Setup Instructions

- Clone this repo
  ```bash
  git clone https://github.com/dennisluke11/DVTWeatherApp.git
