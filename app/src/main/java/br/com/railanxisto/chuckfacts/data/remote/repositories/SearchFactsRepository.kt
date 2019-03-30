package br.com.railanxisto.chuckfacts.data.remote.repositories

import br.com.railanxisto.chuckfacts.data.remote.ChuckFactsService
import br.com.railanxisto.chuckfacts.domain.Category
import retrofit2.Call
import retrofit2.Response

interface SearchFactsRepository {
    fun getCategories(): Call<List<String>>

    fun saveSearch(term: String)

    fun getPastSearches(quantity: Int): Response<List<String>>
}

class SearchFactsRepositoryImpl(val apiService: ChuckFactsService) : SearchFactsRepository {
    override fun getCategories(): Call<List<String>> {
        println("aqui " + apiService.getCategories())
        return apiService.getCategories()
    }

    override fun saveSearch(term: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPastSearches(quantity: Int): Response<List<String>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}