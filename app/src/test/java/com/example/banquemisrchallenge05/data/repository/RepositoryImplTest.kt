package com.example.banquemisrchallenge05.data.repository

import com.example.banquemisrchallenge05.data.localDS.FakeRemoteDSImpl
import com.example.banquemisrchallenge05.data.network.ApiState
import com.example.banquemisrchallenge05.test.movieDetailsResponse
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test


class RepositoryImplTest() {
    lateinit var repository: RepositoryImpl
    lateinit var remoteDS: FakeRemoteDSImpl
    val expectedMovie = movieDetailsResponse[0]

    @Before
    fun setup() {
        remoteDS = FakeRemoteDSImpl()
        repository = RepositoryImpl(remoteDS)
    }


    @Test
    fun test_getMovieDetails_vaild_id() = runTest {
        repository.getMovieDetails(123456.toString()).collect {
            assertEquals(it, expectedMovie)
        }

    }

    @Test
    fun test_getMovieDetails_invaild_id() = runTest {
        assertThrows(Exception::class.java) {
            runTest {
                repository.getMovieDetails("999911223").collect {
                    throw Exception("Movie not found")

                }
            }
        }
    }

    @Test
    fun test_getMovieDetails_network_Error() = runTest {
        remoteDS.isNetworkError = true
        assertThrows(Exception::class.java) {
            runTest {
                repository.getMovieDetails("999911223").collect {
                    throw Exception("Network error")

                }
            }
        }
    }

    @Test
    fun test_getMovieDetails_Empty_List() = runTest {
        remoteDS.isListEmpty = true
        assertThrows(Exception::class.java) {
            runTest {
                repository.getMovieDetails("999911223").collect {
                    throw Exception("Movie not found")

                }
            }
        }
    }

}