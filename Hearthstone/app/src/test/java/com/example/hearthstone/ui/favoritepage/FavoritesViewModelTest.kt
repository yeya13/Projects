package com.example.hearthstone.ui.favoritepage

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.viewModelScope
import com.example.hearthstone.data.network.repo.HSRepoImpl
import com.example.hearthstone.database.dao.HearthstoneDAO
import com.example.hearthstone.database.model.HearthstoneEntity
import io.mockk.*
import junit.framework.TestCase
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
class FavoritesViewModelTest{
    @get:Rule
    var instantTaskExecutor = InstantTaskExecutorRule()

    private lateinit var testViewModel: FavoritesViewModel
    private val mockApp = mockk<Application>(relaxed = true)
    private val mockRepoImpl = mockk<HSRepoImpl>(relaxed = true)
    private val mockDB = mockk<HearthstoneDAO>(relaxed = true)
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        testViewModel = FavoritesViewModel(mockApp, mockDB)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `getAllCards should return list of cards`() {
        runTest {
            //Given
            coEvery { mockDB.getAll() } returns expectedListCards

            //When
            testViewModel.getAllCards()
            (testViewModel.viewModelScope.coroutineContext[Job]?.children?.forEach { it.join() })
        }
        //Then
        TestCase.assertEquals(3, testViewModel.cardList.value?.size)
    }

    @Test
    fun `getAllCards should verify if in the list in the index 2 name = RED BIRD`() {
        runTest {
            //Given
            coEvery { mockDB.getAll() } returns expectedListCards

            //When
            testViewModel.getAllCards()
            (testViewModel.viewModelScope.coroutineContext[Job]?.children?.forEach { it.join() })
        }
        //Then
        TestCase.assertEquals("RED BIRD", testViewModel.cardList.value?.get(2)?.name)
    }

    @Test
    fun `deleteCard should call the removeCard function of the database`() {
        runTest {
            //When
            testViewModel.deleteUser(expectedListCards[0])
            (testViewModel.viewModelScope.coroutineContext[Job]?.children?.forEach { it.join() })
        }
        //Then
        verify { mockDB.removeCard(any()) }
    }

    @Test
    fun `getCardData should return a Hearthstone Entity with the same values that it receives as a parameter`() {
        val result = testViewModel.getCardData(expectedListCards[0])

        //Then
        TestCase.assertEquals("TURTLE", result.name)
    }


    companion object {
        val expectedListCards = listOf<HearthstoneEntity>(
            mockk(relaxed = true) {
                every { name } returns "TURTLE"
            },
            mockk(relaxed = true),
            mockk(relaxed = true){
                every{name} returns "RED BIRD"
            }
        )
    }
}
