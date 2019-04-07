package br.com.railanxisto.chuckfacts

import br.com.railanxisto.chuckfacts.data.remote.ChuckFactsService
import br.com.railanxisto.chuckfacts.data.remote.model.FactsResponse
import br.com.railanxisto.chuckfacts.domain.Fact
import io.reactivex.Single
import org.mockito.Mockito

class MockService(private val chuckFactsService: ChuckFactsService) {

    val QUERY = "food"
    val QUERY_EMPTY = "QUERY_EMPTY"
    val QUERY_ERROR = "QUERY_ERROR"

    val queryTermForError = ""
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
        val response = FactsResponse(2, listOfFacts)
        val responseEmpty = FactsResponse(2, listOf())

        Mockito.`when`(chuckFactsService.getFacts(QUERY))
            .thenReturn(Single.just(response))

        Mockito.`when`(chuckFactsService.getFacts(QUERY_EMPTY))
            .thenReturn(Single.just(responseEmpty))

        Mockito.`when`(chuckFactsService.getFacts(QUERY_ERROR))
            .thenReturn(Single.just(response))
    }
    /* private fun mockResponseOfErrorInQuery() {
        val expectedResponseBody = ResponseBody.create(MediaType.get("text/plain"), "Internal server QUERY_ERROR")
        //val expectedResponse2 = Response.QUERY_ERROR<QueryFactsResponse>(500, expectedResponseBody)

        Mockito.`when`(se.queryFacts(queryTermForError))
            .thenReturn(Single.just(expectedResponse2))
    } */

    private fun mockResponseOfGetCategories() {
        Mockito.`when`(chuckFactsService.getCategories())
            .thenReturn(Single.just(listOfCategories))
    }
}