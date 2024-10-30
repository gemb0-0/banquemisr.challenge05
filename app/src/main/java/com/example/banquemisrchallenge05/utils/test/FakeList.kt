package com.example.banquemisrchallenge05.utils.test

import com.example.banquemisrchallenge05.data.model.Genre
import com.example.banquemisrchallenge05.data.model.Movie
import com.example.banquemisrchallenge05.data.model.MovieDetailsResponse
import com.example.banquemisrchallenge05.data.model.MovieResponse
import com.example.banquemisrchallenge05.data.model.ProductionCompany
import com.example.banquemisrchallenge05.data.model.ProductionCountry
import com.example.banquemisrchallenge05.data.model.SpokenLanguage

val now_playing = listOf(
    MovieResponse(
        page = 1,
        results = listOf(
           // Movie(false, "/backdrop1.jpg", listOf(12, 14), 123456, "en", "Original Title 1", "Overview 1", 9.5, "/poster1.jpg", "2023-10-01", "Epic Adventure", false, 8.1, 200),
            Movie(false, "/backdrop2.jpg", listOf(16, 35), 102, "en", "Original Title 2", "Overview 2", 8.4, "/poster2.jpg", "2023-09-01", "Title 2", false, 7.3, 180),
            Movie(true, "/backdrop3.jpg", listOf(18, 80), 103, "es", "Original Title 3", "Overview 3", 7.2, "/poster3.jpg", "2023-08-01", "Title 3", false, 6.9, 170)
        ),
        total_pages = 3,
        total_results = 5
    ),
    MovieResponse(
        page = 2,
        results = listOf(
            Movie(false, "/backdrop4.jpg", listOf(12, 18), 123456, "fr", "Original Title 4", "Overview 4", 5.6, "/poster4.jpg", "2023-07-01", "Epic Adventure", true, 6.2, 150)
        ),
        total_pages = 3,
        total_results = 5
    ),
    MovieResponse(
        page = 3,
        results = listOf(
            Movie(false, "/backdrop5.jpg", listOf(28, 35), 123456, "en", "Original Title 5", "Overview 5", 8.9, "/poster5.jpg", "2023-06-01", "Epic Adventure", false, 7.8, 300)
        ),
        total_pages = 3,
        total_results = 5
    )
)

val popular = listOf(
    MovieResponse(
        page = 1,
        results = listOf(
            Movie(false, "/backdrop6.jpg", listOf(14, 16), 123456, "ja", "Original Title 6", "Overview 6", 6.5, "/poster6.jpg", "2023-05-01", "Epic Adventure", false, 7.1, 190),
            Movie(true, "/backdrop7.jpg", listOf(12, 878), 107, "en", "Original Title 7", "Overview 7", 9.0, "/poster7.jpg", "2023-04-01", "Title 7", true, 8.5, 210)
        ),
        total_pages = 2,
        total_results = 4
    ),
    MovieResponse(
        page = 2,
        results = listOf(
            Movie(false, "/backdrop8.jpg", listOf(53, 10749), 123456, "es", "Original Title 8", "Overview 8", 7.5, "/poster8.jpg", "2023-03-01", "Epic Adventure", false, 7.4, 160),
            Movie(false, "/backdrop9.jpg", listOf(28, 18), 109, "en", "Original Title 9", "Overview 9", 8.2, "/poster9.jpg", "2023-02-01", "Title 9", false, 8.3, 300)
        ),
        total_pages = 2,
        total_results = 4
    )
)

val upcoming = listOf(
    MovieResponse(
        page = 1,
        results = listOf(
            Movie(true, "/backdrop10.jpg", listOf(27, 53), 123456, "en", "Original Title 10", "Overview 10", 9.9, "/poster10.jpg", "2023-01-01", "Epic Adventure", true, 8.9, 400)
        ),
        total_pages = 3,
        total_results = 4
    ),
    MovieResponse(
        page = 2,
        results = listOf(
            Movie(false, "/backdrop11.jpg", listOf(35, 10751), 123456, "fr", "Original Title 11", "Overview 11", 6.1, "/poster11.jpg", "2022-12-01", "Epic Adventure", false, 5.9, 220),
            Movie(true, "/backdrop12.jpg", listOf(80, 99), 112, "it", "Original Title 12", "Overview 12", 7.3, "/poster12.jpg", "2022-11-01", "Title 12", false, 7.7, 100)
        ),
        total_pages = 3,
        total_results = 4
    ),
    MovieResponse(
        page = 3,
        results = listOf(
            Movie(false, "/backdrop13.jpg", listOf(18, 36), 123456, "en", "Original Title 13", "Overview 13", 8.0, "/poster13.jpg", "2022-10-01", "Epic Adventure", false, 8.2, 275)
        ),
        total_pages = 3,
        total_results = 4
    )
)



val movieDetailsResponse = mutableListOf( MovieDetailsResponse(
    adult = false,
    backdrop_path = "/mpEbs71wwwkkIxiiEvH5XHTLnWv.jpg",
    belongs_to_collection = "null",
    budget = 200000000,
    genres = listOf(
        Genre(id = 28, name = "Action"),
        Genre(id = 12, name = "Adventure"),
        Genre(id = 878, name = "Science Fiction")
    ),
    homepage = "http://example.com/movie",
    id = 123456,
    imdb_id = "tt1234567",
    origin_country = listOf("USA", "UK"),
    original_language = "en",
    original_title = "Epic Adventure",
    overview = "An epic adventure that takes you on a thrilling ride through time and space.",
    popularity = 85.5,
    poster_path = "/path/to/poster.jpg",
    production_companies = listOf(
        ProductionCompany(id = 1, logo_path = "/mpEbs71wwwkkIxiiEvH5XHTLnWv.jpg", name = "Epic Films", origin_country = "USA"),
        ProductionCompany(id = 2, logo_path = "/mpEbs71wwwkkIxiiEvH5XHTLnWv.jpg", name = "Adventure Studios", origin_country = "UK")
    ),
    production_countries = listOf(
        ProductionCountry(iso_3166_1 = "US", name = "United States"),
        ProductionCountry(iso_3166_1 = "GB", name = "United Kingdom")
    ),
    release_date = "2024-05-20",
    revenue = 500000000,
    runtime = 150,
    spoken_languages = listOf(
        SpokenLanguage(english_name = "English", iso_639_1 = "en", name = "English"),
        SpokenLanguage(english_name = "Spanish", iso_639_1 = "es", name = "Español")
    ),
    status = "Released",
    tagline = "A journey beyond imagination.",
    title = "Epic Adventure",
    video = false,
    vote_average = 8.7,
    vote_count = 1200
)
)