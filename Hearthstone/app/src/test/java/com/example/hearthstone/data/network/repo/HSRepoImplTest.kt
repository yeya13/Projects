package com.example.hearthstone.data.network.repo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.hearthstone.data.model.HSCardsByClassModel
import com.example.hearthstone.data.model.HearthstoneModel
import com.example.hearthstone.data.model.Result
import io.mockk.*
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
class HSRepoImplTest {
    @get:Rule
    var instantTaskExecutor = InstantTaskExecutorRule()

    private val mockRepoImpl = mockk<HSRepoImpl>(relaxed = true)
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
    fun `getCards should return Success`() = runBlocking {
        //Give
        coEvery { mockRepoImpl.getCards() } returns expectedTestList

        //When
        val result = mockRepoImpl.getCards()

        //Then
        //coVerify { mockRepoImpl.getCards() }
        assertEquals(expectedTestList, result)
    }

    @Test
    fun `getCards should return Error`() = runBlocking {
        //Give
        coEvery { mockRepoImpl.getCards() } returns expectedError

        //When
        val result = mockRepoImpl.getCards()

        //Then
        assertEquals(expectedError, result)

    }

    @Test
    fun `getCardsByClass should return Success`() = runBlocking {
        //Give
        coEvery { mockRepoImpl.getCardsByClass(any()) } returns expectedTestList2

        //When
        val result = mockRepoImpl.getCardsByClass("Kotlin")

        //Then
        assertEquals(expectedTestList2, result)

    }

    @Test
    fun `getCardsByClass should return Error`() = runBlocking {
        //Give
        coEvery { mockRepoImpl.getCardsByClass(any()) } returns expectedError

        //When
        val result = mockRepoImpl.getCardsByClass("Kotlin")

        //Then
        assertEquals(expectedError, result)
    }

    @Test
    fun `getCardsByName should return Success`() = runBlocking {
        //Give
        coEvery { mockRepoImpl.getCardsByName(any()) } returns expectedTestList2

        //When
        val result = mockRepoImpl.getCardsByName("Mana Bind")

        //Then
        //assertEquals(Result.Success::class.java, result.javaClass)
        assertEquals(expectedTestList2, result)

    }

    @Test
    fun `getCardsByName should return Error`() = runBlocking {
        //Give
        coEvery { mockRepoImpl.getCardsByName(any()) } returns expectedError

        //When
        val result = mockRepoImpl.getCardsByName("Mana Bind")

        //Then
        assertEquals(expectedError, result)

    }

    companion object {
        val expectedTestList = Result.Success(
            HearthstoneModel(
                listOf<String>(
                    "Claudia",
                    "Jair"
                )
            )
        )

        val expectedError = Result.Error(Exception())

        val expectedTestList2 = Result.Success(
            listOf<HSCardsByClassModel>(
                mockk(relaxed = true),
                mockk(relaxed = true),
                mockk(relaxed = true) {
                    every { type } returns "sleepy"
                },
                mockk(relaxed = true)
            )
        )
    }
}
