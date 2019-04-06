package br.com.railanxisto.chuckfacts

import br.com.railanxisto.chuckfacts.data.local.DAO.TermDao
import br.com.railanxisto.chuckfacts.data.remote.ChuckFactsService
import br.com.railanxisto.chuckfacts.data.remote.model.FactsResponse
import br.com.railanxisto.chuckfacts.data.remote.repositories.FactsRepositoryImpl
import br.com.railanxisto.chuckfacts.domain.Fact
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class FactsRepositoryTest {

    val service by lazy { mock(ChuckFactsService::class.java) }
    private val termDao by lazy { mock(TermDao::class.java) }
    private val factsRepository by lazy { FactsRepositoryImpl(service, termDao) }

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `should return list of facts`() {
        val response = FactsResponse(
            1,
            listOf(
                Fact("1", "url", "Food", null, "")
            )
        )

        val single = Single.just(response)
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
            .thenReturn(Single.just(queryFactsResponse))

        factsRepository.getFacts("railannorris").test()
            .await()
            .assertNoErrors()
            .assertComplete()
            .assertResult(queryFactsResponse)
    }
    @Test
    fun test_mock_service() {
        val result = Single.just(FactsResponse(0, listOf()))
        `when`(service.getFacts("")).thenReturn(result)
        Assert.assertEquals(service.getFacts(""), result)
    }
}
