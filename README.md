# 🎬 MovieCloneApp

**MovieCloneApp** is a native Android application built using Kotlin, showcasing movies by leveraging [The Movie Database (TMDb) API](https://api.themoviedb.org).  
> 🧪 **Note**: In this case, I’m trying to clone the **Netflix App** experience, focusing on movie browsing, detailed views, trailers, and reviews.

The app offers users a smooth experience to browse movies by genre, explore detailed information, watch trailers, and read user reviews — all while implementing clean architecture with modern Android development tools.

## ✨ Features

- 🧾 **List of Movie Genres**: Displays all official movie genres retrieved from TMDb.
- 🎥 **Discover Movies by Genre**: Shows a list of movies filtered by selected genre.
- 📋 **Movie Details**: Displays primary information about a selected movie.
- 🗣 **User Reviews**: View user reviews for the selected movie with endless scrolling support.
- ▶️ **YouTube Trailers**: Watch the official YouTube trailer for a movie.
- 🔁 **Endless Scrolling**: Implemented on both movie listings and review lists for a seamless experience.

## 🧰 Tech Stack

| Tech | Description |
|------|-------------|
| **Kotlin** | Primary development language |
| **MVVM Architecture** | Clean separation of concerns with ViewModel & LiveData |
| **Navigation Component** | Handles navigation and passing data between fragments |
| **Dagger Hilt** | Dependency Injection for easier scalability and testing |
| **Retrofit + Moshi** | API networking with JSON parsing |
| **Lottie Animation** | Smooth animations for loading states or transitions |
| **Paging 3** | Efficient endless scrolling support |
| **View Binding** | Safe and efficient view references |

## 📸 Screenshots

> Screenshots of the app UI can be found in the [screenshots folder](https://github.com/fadlanam30/MovieCloneApp/tree/master/screenshots).

## 🚀 Getting Started

1. Clone the repository:

```bash
git clone https://github.com/fadlanam30/MovieCloneApp.git
```

2. Get a free API key from [TMDb](https://www.themoviedb.org/documentation/api)

3. Add your API key & BASE URLS to `local.properties`:

```properties
TMDB_BASE_URL="https://api.themoviedb.org/3/"
TMDB_IMAGE_BASE_URL="https://image.tmdb.org/t/p/w500/"
TMDB_API_KEY=your_api_key_here
```

4. Build and run the project in Android Studio.

## 🗂 Architecture

The project follows the **MVVM (Model-View-ViewModel)** architecture pattern:
- **Repository pattern** to abstract data sources
- **UseCases** for business logic
- **ViewModel** for UI-related data handling
- Clean UI with **single-responsibility** fragments
