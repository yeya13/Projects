package com.example.hearthstone.data.network.repo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.hearthstone.data.model.Result
import com.example.hearthstone.data.model.modelGooglePlaces.GooglePlaces
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GooglePlacesRepoImplTest {
    @get:Rule
    var instantTaskExecutor = InstantTaskExecutorRule()

    private val mockRepoImpl = mockk<GooglePlacesRepoImpl>(relaxed = true)
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `getPlaces should return Success`() = runBlocking {
        //Give
        coEvery { mockRepoImpl.getPlaces(any()) } returns expectedTestList

        //When
        val result = mockRepoImpl.getPlaces("Monterrey")

        //Then
        assertEquals(expectedTestList, result)
    }

    @Test
    fun `getPlaces should return Error`() = runBlocking {
        //Give
        coEvery { mockRepoImpl.getPlaces(any()) } returns expectedError

        //When
        val result = mockRepoImpl.getPlaces("Zacatecas")

        //Then
        assertEquals(expectedError, result)
    }

    companion object {
        val expectedTestList = Result.Success(
            GooglePlaces(
                listOf(
                    mockk(relaxed = true),
                    mockk(relaxed = true) {
                        every { name } returns "Cinepolis"
                    },
                    mockk(relaxed = true)
                )
            )
        )
        val expectedError = Result.Error(Exception())
    }
}
