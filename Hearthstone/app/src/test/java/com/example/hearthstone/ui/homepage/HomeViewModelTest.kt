package com.example.hearthstone.ui.homepage

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.viewModelScope
import com.example.hearthstone.data.model.HearthstoneModel
import com.example.hearthstone.data.model.Result
import com.example.hearthstone.data.network.repo.HSRepoImpl
import com.example.hearthstone.dialogue.ErrorDialog
import io.mockk.*
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
class HomeViewModelTest {
    @get:Rule
    var instantTaskExecutor = InstantTaskExecutorRule()

    private lateinit var testViewModel: HomeViewModel
    private val mockApp = mockk<Application>(relaxed = true)
    private val mockRepoImpl = mockk<HSRepoImpl>(relaxed = true)
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        testViewModel = HomeViewModel(mockApp, mockRepoImpl)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `getCardsByClass should return list of cards`() {
        runTest {
            //Given
            coEvery { mockRepoImpl.getCards() } returns expectedTestList

            //When
            testViewModel.getCardsByClass()
            (testViewModel.viewModelScope.coroutineContext[Job]?.children?.forEach { it.join() })
        }
        //Then
        assertEquals(listOf<String>("Claudia", "Jair"), testViewModel.cards.value)
    }

    @Test
    fun `getCardsByClass should return Error`() {
        runTest {
            //Given
            coEvery { mockRepoImpl.getCards() } returns expectedError

            //When
            testViewModel.getCardsByClass()
            (testViewModel.viewModelScope.coroutineContext[Job]?.children?.forEach { it.join() })
        }
        //Then
        assertEquals(ErrorDialog::class.java, testViewModel.errorDialog.value?.javaClass)
    }

    @Test
    fun `onTextChange should update query variable`() {
        runTest {
            //When
            testViewModel.onTextChanged("Kotlin")

        }
        //Then
        assertEquals(expectedQuery, testViewModel.query.value)
    }

    @Test
    fun `validateSearch should return true`() {

        //When
        testViewModel.onTextChanged("Kotlin")
        val result = testViewModel.validateSearch()

        //Then
        assertEquals(true, result)
    }

    @Test
    fun `validateSearch should return false`() {

        //When
        testViewModel.onTextChanged("")
        val result = testViewModel.validateSearch()

        //Then
        assertEquals(false, result)
    }

    @Test
    fun `buttonSearch should update userSearch variable`() {
        runTest {
            //When
            testViewModel.onTextChanged("Kotlin")
            testViewModel.validateSearch()
            testViewModel.buttonSearch()

        }
        //Then
        assertEquals("Kotlin", testViewModel.userSearch.value)
    }

    @Test
    fun `buttonSearch should not update userSearch variable`() {
        runTest {
            //When
            testViewModel.onTextChanged("")
            testViewModel.validateSearch()
            testViewModel.buttonSearch()
        }
        //Then
        assertEquals(null, testViewModel.userSearch.value)
    }


    companion object {
        val expectedTestList = Result.Success(
            HearthstoneModel(listOf<String>("Claudia", "Jair"))
        )

        val expectedError = Result.Error(Exception())

        val expectedQuery = "Kotlin"
    }
}
