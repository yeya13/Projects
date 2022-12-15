package com.example.hearthstone.ui.shopPage

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.viewModelScope
import com.example.hearthstone.data.model.Result
import com.example.hearthstone.data.model.modelGooglePlaces.Geometry
import com.example.hearthstone.data.model.modelGooglePlaces.GooglePlaces
import com.example.hearthstone.data.network.repo.GooglePlacesRepoImpl
import com.example.hearthstone.dialogue.ErrorDialog
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ShopViewModelTest {
    @get:Rule
    var instantTaskExecutor = InstantTaskExecutorRule()

    private lateinit var testViewModel: ShopViewModel
    private val mockApp = mockk<Application>(relaxed = true)
    private val mockRepoImpl = mockk<GooglePlacesRepoImpl>(relaxed = true)
    private val mockGeo = mockk<Geometry>(relaxed = true)
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        testViewModel = ShopViewModel(mockApp, mockRepoImpl)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `getPlaces should return list of places`() {
        runTest {
            //Give
            coEvery { mockRepoImpl.getPlaces(any()) } returns expectedTestList

            //When
            testViewModel.getPlaces("Monterrey")
            (testViewModel.viewModelScope.coroutineContext[Job]?.children?.forEach { it.join() })
        }

        //Then
        assertEquals(3, testViewModel.places.value?.size)
    }

    @Test
    fun `getPlaces should verify if in the list in the index 1 name = Cinepolis`() {
        runTest {
            ///Give
            coEvery { mockRepoImpl.getPlaces(any()) } returns expectedTestList

            //When
            testViewModel.getPlaces("Monterrey")
            (testViewModel.viewModelScope.coroutineContext[Job]?.children?.forEach { it.join() })
        }

        //Then
        assertEquals("Cinepolis", testViewModel.places.value?.get(1)?.name)
    }

    @Test
    fun `getPlaces should return Error`() {
        runTest {
            //Give
            coEvery { mockRepoImpl.getPlaces(any()) } returns expectedError

            //When
            testViewModel.getPlaces("Zacatecas")
            (testViewModel.viewModelScope.coroutineContext[Job]?.children?.forEach { it.join() })
        }
        //Then
        assertEquals(ErrorDialog::class.java, testViewModel.errorDialog.value?.javaClass)
    }

    @Test
    fun `getAllItem`() {
        val result = testViewModel.getAllItem("Cinemex", mockGeo)

        //Then
        assertEquals("Cinemex", result.first().movieTheaterName)
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
