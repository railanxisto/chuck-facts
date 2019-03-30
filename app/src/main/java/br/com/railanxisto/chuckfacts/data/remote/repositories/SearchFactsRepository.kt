package br.com.railanxisto.chuckfacts.data.remote.repositories

import br.com.railanxisto.chuckfacts.data.local.DAO.CategoryDao
import br.com.railanxisto.chuckfacts.data.remote.ChuckFactsService
import br.com.railanxisto.chuckfacts.domain.Category
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

interface SearchFactsRepository {
    fun getCategories(): Maybe<List<Category>>

    fun saveSearch(term: String)

    fun getPastSearches(quantity: Int): Response<List<String>>
}

class SearchFactsRepositoryImpl(val apiService: ChuckFactsService, val categoryDao: CategoryDao) : SearchFactsRepository {
    override fun getCategories(): Maybe<List<Category>> {

        val data = getCategoriesFromApi()
            .doOnSuccess { categoryDao.insertCategories(
                    it.map {
                        br.com.railanxisto.chuckfacts.data.local.model.Category(it.name)
                    })
            }

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
            list.map { Category(it) }
        }

}