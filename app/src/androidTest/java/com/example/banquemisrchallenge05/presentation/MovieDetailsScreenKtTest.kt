package com.example.banquemisrchallenge05.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.banquemisrchallenge05.data.repoistory.FakeRepositoryImpl
import com.example.banquemisrchallenge05.utils.test.movieDetailsResponse
import com.example.banquemisrchallenge05.viewModel.MovieDetailsViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class MovieDetailsScreenKtTest() {
    lateinit var movieDetailsViewModel: MovieDetailsViewModel
    lateinit var repository: FakeRepositoryImpl
    val expectedMovie = movieDetailsResponse[0]

    @Before
    fun setUp() {
        repository = FakeRepositoryImpl()
        movieDetailsViewModel = MovieDetailsViewModel(repository)
    }

    @get:Rule
    val compoeableTestRule = createComposeRule()

    @Test
    fun testDisplayMovieDetailsScreen() {


        compoeableTestRule.setContent {
            val navController = rememberNavController()

            MovieDetailsScreen(
                id = expectedMovie.id.toString(),
                navController = navController,
                movieDeatilsViewModel = movieDetailsViewModel
            )

        }
        compoeableTestRule.onNodeWithText(expectedMovie.title).assertIsDisplayed()
        compoeableTestRule.onNodeWithText(expectedMovie.overview).assertIsDisplayed()
    }
}