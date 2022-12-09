package com.example.hearthstone.ui.searchpage

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.viewModelScope
import com.example.hearthstone.data.model.HSCardsByClassModel
import com.example.hearthstone.data.model.Result
import com.example.hearthstone.data.network.repo.HSRepoImpl
import com.example.hearthstone.database.dao.HearthstoneDAO
import com.example.hearthstone.database.model.HearthstoneEntity
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
class SearchPageViewModelTest {
    @get:Rule
    var instantTaskExecutor = InstantTaskExecutorRule()

    private lateinit var testViewModel: SearchPageViewModel
    private val mockApp = mockk<Application>(relaxed = true)
    private val mockRepoImpl = mockk<HSRepoImpl>(relaxed = true)
    private val mockDB = mockk<HearthstoneDAO>(relaxed = true)
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        testViewModel = SearchPageViewModel(mockApp, mockRepoImpl, mockDB)
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
            coEvery { mockRepoImpl.getCardsByClass(any()) } returns expectedTestList

            //When
            testViewModel.getCardsByClass("Mage")
            (testViewModel.viewModelScope.coroutineContext[Job]?.children?.forEach { it.join() })
        }
        //Then
        assertEquals(4, testViewModel.cards.value?.size)
    }

    @Test
    fun `getCardsByClass should verify if in the list in the index 2 type = sleepy`() {
        runTest {
            //Given
            coEvery { mockRepoImpl.getCardsByClass(any()) } returns expectedTestList

            //When
            testViewModel.getCardsByClass("Mage")
            (testViewModel.viewModelScope.coroutineContext[Job]?.children?.forEach { it.join() })
        }
        //Then
        assertEquals("sleepy", testViewModel.cards.value?.get(2)?.type)
    }

    @Test
    fun `getCardsByClass should return Error`() {
        runTest {
            //Given
            coEvery { mockRepoImpl.getCardsByClass(any()) } returns expectedError

            //When
            testViewModel.getCardsByClass("Chocolate")
            (testViewModel.viewModelScope.coroutineContext[Job]?.children?.forEach { it.join() })
        }
        //Then
        assertEquals(ErrorDialog::class.java, testViewModel.errorDialog.value?.javaClass)
    }

    @Test
    fun `getCardsByName should return list of cards`() {
        runTest {
            //Given
            coEvery { mockRepoImpl.getCardsByClass(any()) } returns expectedTestList

            //When
            testViewModel.getCardsByClass("Mystery")
            (testViewModel.viewModelScope.coroutineContext[Job]?.children?.forEach { it.join() })
        }
        //Then
        assertEquals(4, testViewModel.cards.value?.size)
    }

    @Test
    fun `getCardsByName should return Error`() {
        runTest {
            //Given
            coEvery { mockRepoImpl.getCardsByName(any()) } returns expectedError

            //When
            testViewModel.getCardsByName("Chocolate")
            (testViewModel.viewModelScope.coroutineContext[Job]?.children?.forEach { it.join() })
        }
        //Then
        assertEquals(ErrorDialog::class.java, testViewModel.errorDialog.value?.javaClass)
    }

    @Test
    fun `getAllID should return list of cardsID`() {
        runTest {
            //Given
            coEvery { mockDB.getAllId() } returns expectedListCardsID

            //When
            testViewModel.getAllID()
            (testViewModel.viewModelScope.coroutineContext[Job]?.children?.forEach { it.join() })
        }
        //Then
        assertEquals(expectedListCardsID, testViewModel.cardsID.value)
    }

    @Test
    fun `getAllID should verify if in the list the index 1 = 25`() {
        runTest {
            //Given
            coEvery { mockDB.getAllId() } returns expectedListCardsID

            //When
            testViewModel.getAllID()
            (testViewModel.viewModelScope.coroutineContext[Job]?.children?.forEach { it.join() })
        }
        //Then
        assertEquals("25", testViewModel.cardsID.value?.get(1))
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
    fun `getCardData should return a HearthstoneEntity, to check it we will put it in the DB`() {
        val result = testViewModel.getCardData(obHSCardsByClassModel)
        runTest {

            coEvery { mockDB.getAll() } returns listOf(result, obHearthstoneEntity)
            (testViewModel.viewModelScope.coroutineContext[Job]?.children?.forEach { it.join() })
        }

        //Then
        assertEquals(mockDB.getAll().first(), result)
    }

    @Test
    fun `insertCard`() {
        runTest {
            coEvery { testViewModel.getAllCards() }

            //when
            testViewModel.insertCard(obHSCardsByClassModel)
            (testViewModel.viewModelScope.coroutineContext[Job]?.children?.forEach { it.join() })
        }
        //Then
        assertEquals(1, mockDB.getAll().size)
    }


    companion object {
        val expectedTestList = Result.Success(
            listOf<HSCardsByClassModel>(
                mockk(relaxed = true),
                mockk(relaxed = true),
                mockk(relaxed = true) {
                    every { type } returns "sleepy"
                },
                mockk(relaxed = true)
            )
        )

        val expectedError = Result.Error(Exception())

        val expectedListCards = listOf<HearthstoneEntity>(
            mockk(relaxed = true) {
                every { name } returns "TURTLE"
            },
            mockk(relaxed = true) {
                every { id } returns "10"
            },
            mockk(relaxed = true)
        )
        val expectedListCardsID = listOf<String>("10", "25", "30", "40")

        val obHearthstoneEntity = mockk<HearthstoneEntity>(relaxed = true)

        val obHSCardsByClassModel = mockk<HSCardsByClassModel>(relaxed = true) {
            every { name } returns "Claudia"
        }
    }
}
