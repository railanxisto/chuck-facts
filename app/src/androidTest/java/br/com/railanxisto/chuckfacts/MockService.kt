package br.com.railanxisto.chuckfacts

import br.com.railanxisto.chuckfacts.data.remote.ChuckFactsService
import br.com.railanxisto.chuckfacts.data.remote.model.FactsResponse
import br.com.railanxisto.chuckfacts.domain.Fact
import io.reactivex.Single
import org.mockito.Mockito

class MockService(private val chuckFactsService: ChuckFactsService) {

    val query = "food"
    val queryTermForError = ""
    val listOfCategories = listOf("food", "dev")
    val listOfFacts = listOf(
        Fact("1", "url", "Fact", null, "icon"),
        Fact("2", "url", "Fact2", listOf("food"), "icon")
    )

    init {
        mockResponseSucess()

        // mockResponseOfErrorInQuery()

        mockResponseOfGetCategories()
    }

    private fun mockResponseSucess() {
        val response = FactsResponse(2, listOfFacts)

        Mockito.`when`(chuckFactsService.getFacts(query))
            .thenReturn(Single.just(response))
    }

    /* private fun mockResponseOfErrorInQuery() {
        val expectedResponseBody = ResponseBody.create(MediaType.get("text/plain"), "Internal server error")
        //val expectedResponse2 = Response.error<QueryFactsResponse>(500, expectedResponseBody)

        Mockito.`when`(se.queryFacts(queryTermForError))
            .thenReturn(Single.just(expectedResponse2))
    } */

    private fun mockResponseOfGetCategories() {
        Mockito.`when`(chuckFactsService.getCategories())
            .thenReturn(Single.just(listOfCategories))
    }
}