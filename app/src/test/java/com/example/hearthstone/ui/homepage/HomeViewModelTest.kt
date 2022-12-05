package com.example.hearthstone.ui.homepage

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.hearthstone.data.model.HSCardsByClassModel
import com.example.hearthstone.data.model.HearthstoneModel
import com.example.hearthstone.data.network.repo.HSRepoImpl
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
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
            //SET UP
            coEvery { mockRepoImpl.getCards() } returns mockkedExpectedTestList

            //WHEN
            testViewModel.getCardsByClass()

        }
        //Then
        //Expected       //Actual
        assertEquals(7, testViewModel.cards.value?.size)
        assertEquals("RARE", testViewModel.cards.value?.get(2)?.rarity)
    }

    companion object {
//        val expectedTestList = HearthstoneModel(
//            listOf("batman", "robin", "joker", "Zelda", "Link")
//        )

        val mockkedExpectedTestList = mockk<HearthstoneModel>(relaxed = true) {
            every { classes } returns listOf<HSCardsByClassModel>(
                mockk(relaxed = true),
                mockk(relaxed = true),
                mockk(relaxed = true) {
                    every { rarity } returns "SUPER RARE"
                },
                mockk(relaxed = true),
                mockk(relaxed = true),
                mockk(relaxed = true),
                mockk(relaxed = true)
            )
        }
    }
}
