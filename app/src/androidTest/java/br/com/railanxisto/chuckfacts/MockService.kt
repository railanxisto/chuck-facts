package br.com.railanxisto.chuckfacts

import br.com.railanxisto.chuckfacts.data.remote.ChuckFactsService
import br.com.railanxisto.chuckfacts.data.remote.model.FactsResponse
import br.com.railanxisto.chuckfacts.domain.Fact
import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.mockito.Mockito
import retrofit2.Response

class MockService(private val chuckFactsService: ChuckFactsService) {

    val QUERY = "food"
    val QUERY_EMPTY = "QUERY_EMPTY"
    val QUERY_ERROR = "QUERY_ERROR"

    val listOfCategories = listOf("food", "dev")
    val listOfFacts = listOf(
        Fact("1", "url", "Fact", null, "icon"),
        Fact("2", "url", "Fact2", listOf("food"), "icon")
    )

    init {
        mockResponseOfFacts()
        mockResponseOfGetCategories()
    }

    private fun mockResponseOfFacts() {
        val response = Response.success(FactsResponse(2, listOfFacts))
        val responseEmpty = Response.success(FactsResponse(2, listOf()))

        val expectedResponseBody = ResponseBody.create(MediaType.get("text/plain"), "Internal server error")
        val errorResponse = Response.error<FactsResponse>(500, expectedResponseBody)

        Mockito.`when`(chuckFactsService.getFacts(QUERY))
            .thenReturn(Single.just(response))

        Mockito.`when`(chuckFactsService.getFacts(QUERY_EMPTY))
            .thenReturn(Single.just(responseEmpty))

        Mockito.`when`(chuckFactsService.getFacts(QUERY_ERROR))
            .thenReturn(Single.just(errorResponse))
    }

    private fun mockResponseOfGetCategories() {
        Mockito.`when`(chuckFactsService.getCategories())
            .thenReturn(Single.just(listOfCategories))
    }
}