package br.com.railanxisto.chuckfacts.data.remote.repositories

import data.local.DAO.CategoryDao
import data.local.DAO.TermDao
import data.local.model.Term
import br.com.railanxisto.chuckfacts.data.remote.ChuckFactsService
import br.com.railanxisto.chuckfacts.domain.Category
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

interface SearchFactsRepository {
    fun getCategories(): Maybe<List<Category>>

    fun getPastSearches(): Observable<List<Term>>
}

class SearchFactsRepositoryImpl(val apiService: ChuckFactsService, val categoryDao: CategoryDao, val termDao: TermDao) : SearchFactsRepository {
    override fun getCategories(): Maybe<List<Category>> {

        val data = getCategoriesFromDatabase()
        val dataFromApi = getCategoriesFromApi()
            .doOnSuccess { categoryDao.insertCategories(
                    it.map {
                        data.local.model.Category(it.name)
                    })
            }

        return Maybe
            .concat(data, dataFromApi)
            .subscribeOn(Schedulers.io())
            .firstElement()
    }

    override fun getPastSearches(): Observable<List<Term>> = termDao
        .getTerms()
        .subscribeOn(Schedulers.io())

    private fun getCategoriesFromDatabase() = categoryDao
        .getCategories()
        .filter { !it.isEmpty() }
        .map { list ->
            list.map { Category(it.name) }
        }

    private fun getCategoriesFromApi() = apiService
        .getCategories()
        .filter { !it.isEmpty() }
        .map { list ->
            list.map { Category(it) }
        }
}