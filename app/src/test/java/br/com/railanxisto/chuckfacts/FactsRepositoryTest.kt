package br.com.railanxisto.chuckfacts

import br.com.railanxisto.chuckfacts.data.remote.ChuckFactsService
import br.com.railanxisto.chuckfacts.data.remote.model.FactsResponse
import io.reactivex.Single
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class FactsRepositoryTest : KoinTest {
    val service by lazy { mock(ChuckFactsService::class.java) }

    @Test
    fun test_mock() {
        val result = Single.just(FactsResponse(0, listOf()))
        `when`(service.getFacts("")).thenReturn(result)
        Assert.assertEquals(service.getFacts(""), result)
    }
}