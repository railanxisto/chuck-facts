package br.com.railanxisto.chuckfacts.data.remote.repositories

import br.com.railanxisto.chuckfacts.data.remote.ChuckFactsService
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

interface SearchFactsRepository {
    fun getCategories(): Maybe<List<String>>

    fun saveSearch(term: String)

    fun getPastSearches(quantity: Int): Response<List<String>>
}

class SearchFactsRepositoryImpl(val apiService: ChuckFactsService) : SearchFactsRepository {
    override fun getCategories(): Maybe<List<String>> {

        val data = getCategoriesFromApi()
            .doOnSuccess {  }

        return Maybe
            .concat(data, data)
            .subscribeOn(Schedulers.io())
            .firstElement()
    }

    override fun saveSearch(term: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPastSearches(quantity: Int): Response<List<String>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun getCategoriesFromApi() = apiService
        .getCategories()
        .filter { !it.isEmpty() }
        .map { list ->
            list.map { it }
        }

}