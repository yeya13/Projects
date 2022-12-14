package com.example.hearthstone.ui.cardOverview

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.viewModelScope
import com.example.hearthstone.data.model.HSCardsByClassModel
import com.example.hearthstone.data.network.repo.HSRepoImpl
import com.example.hearthstone.database.dao.HearthstoneDAO
import com.example.hearthstone.database.model.HearthstoneEntity
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
class CardOverviewViewModelTest {
    @get:Rule
    var instantTaskExecutor = InstantTaskExecutorRule()

    private lateinit var testViewModel: CardOverviewViewModel
    private val mockApp = mockk<Application>(relaxed = true)
    private val mockRepoImpl = mockk<HSRepoImpl>(relaxed = true)
    private val mockDB = mockk<HearthstoneDAO>(relaxed = true)
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        testViewModel = CardOverviewViewModel(mockApp, mockDB)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    //This test have an error: Method fromHtml in android.text.Html not mocked
    @Test
    fun `getInformationCards should copy a HSCardsByClassModel to the variable card`() {
        runTest {
            testViewModel.getInformationCards(obHSCardsByClassModel)
            (testViewModel.viewModelScope.coroutineContext[Job]?.children?.forEach { it.join() })
        }

        //Then
        assertEquals("Claudia", testViewModel.card.value?.name)
    }

    @Test
    fun `getCardData should return a Hearthstone Entity with the same values as the HSCardsByClassModel variable `() {
        testViewModel.queryCard(obHSCardsByClassModel)
        val result = testViewModel.getCardData()

        //Then
        assertEquals("Claudia", result.name)
    }

    @Test
    fun `insertCard should call the insertCard function of the database`() {
        runTest {
            //When
            testViewModel.insertCard()
            (testViewModel.viewModelScope.coroutineContext[Job]?.children?.forEach { it.join() })
        }
        //Then
        verify { mockDB.insertCard(any()) }
    }

    @Test
    fun `deleteCard should call the removeCard function of the database`() {
        runTest {
            //When
            testViewModel.deleteUser()
            (testViewModel.viewModelScope.coroutineContext[Job]?.children?.forEach { it.join() })
        }
        //Then
        verify { mockDB.removeCard(any()) }
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
        assertEquals(3, testViewModel.cardList.value?.size)
    }

    @Test
    fun `getAllCards should verify if in the list in the index 0 name = TURTLE`() {
        runTest {
            //Given
            coEvery { mockDB.getAll() } returns expectedListCards

            //When
            testViewModel.getAllCards()
            (testViewModel.viewModelScope.coroutineContext[Job]?.children?.forEach { it.join() })
        }
        //Then
        assertEquals("TURTLE", testViewModel.cardList.value?.get(0)?.name)
    }

    @Test
    fun `queryCard should set the value to true to the variable fav`() {
        runTest {
            testViewModel.queryCard(obHSCardsByClassModel)
            (testViewModel.viewModelScope.coroutineContext[Job]?.children?.forEach { it.join() })
        }
        //Then
        assertEquals(true, testViewModel.fav.value)
    }

    @Test
    fun `queryCard should set the value to false to the variable fav`() {
        runTest {
            coEvery { mockDB.doDataQuery(any()) } returns null
            testViewModel.queryCard(obHSCardsByClassModel)
            (testViewModel.viewModelScope.coroutineContext[Job]?.children?.forEach { it.join() })
        }
        //Then
        assertEquals(false, testViewModel.fav.value)
    }


    @Test
    fun `checkFavorite should receive a parameter true and verify that the function insertCard is called`() {
        runTest {
            //When
            testViewModel.checkFavorite(true)
            (testViewModel.viewModelScope.coroutineContext[Job]?.children?.forEach { it.join() })
        }
        //Then
        verify { mockDB.insertCard(any()) }
    }

    @Test
    fun `checkFavorite should receive a parameter false and verify that the function removeCard is called`() {
        runTest {
            //When
            testViewModel.checkFavorite(false)
            (testViewModel.viewModelScope.coroutineContext[Job]?.children?.forEach { it.join() })
        }
        //Then
        verify { mockDB.removeCard(any()) }
    }

    companion object {
        val obHSCardsByClassModel = mockk<HSCardsByClassModel>(relaxed = true) {
            every { name } returns "Claudia"
        }

        val expectedListCards = listOf<HearthstoneEntity>(
            mockk(relaxed = true) {
                every { name } returns "TURTLE"
            },
            mockk(relaxed = true) {
                every { id } returns "10"
            },
            mockk(relaxed = true)
        )
    }
}
