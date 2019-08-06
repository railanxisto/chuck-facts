package br.com.railanxisto.chuckfacts.data

import data.local.DAO.TermDao
import br.com.railanxisto.chuckfacts.data.remote.ChuckFactsService
import br.com.railanxisto.chuckfacts.data.remote.model.FactsResponse
import br.com.railanxisto.chuckfacts.data.remote.repositories.FactsRepositoryImpl
import domain.Fact
import io.reactivex.Single
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.robolectric.RobolectricTestRunner
import retrofit2.Response

@RunWith(RobolectricTestRunner::class)
class FactsRepositoryTest : AutoCloseKoinTest() {
    val service by lazy { mock(ChuckFactsService::class.java) }
    private val termDao by lazy { mock(TermDao::class.java) }
    private val factsRepository by lazy { FactsRepositoryImpl(service, termDao) }

    @Test
    fun `should return list of facts`() {
        val response = FactsResponse(
            1,
            listOf(
                Fact("1", "url", "Food", null, "")
            )
        )

        val single = Single.just(Response.success(response))
        `when`(service.getFacts("food")).thenReturn(single)
        factsRepository.getFacts("food").test()
            .await()
            .assertNoErrors()
            .assertComplete()
            .assertResult(response)
    }

    @Test
    fun `should return empty list if has no results for the term`() {
        val queryFactsResponse = FactsResponse(0, listOf())
        `when`(service.getFacts("railannorris"))
            .thenReturn(Single.just(Response.success(queryFactsResponse)))

        factsRepository.getFacts("railannorris").test()
            .await()
            .assertNoErrors()
            .assertComplete()
            .assertResult(queryFactsResponse)
    }
    @Test
    fun test_mock_service() {
        val result = Single.just(Response.success(FactsResponse(0, listOf())))
        `when`(service.getFacts("")).thenReturn(result)
        Assert.assertEquals(service.getFacts(""), result)
    }
}
