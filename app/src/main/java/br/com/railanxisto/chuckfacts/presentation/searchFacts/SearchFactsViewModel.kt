package br.com.railanxisto.chuckfacts.presentation.searchFacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import data.local.model.Term
import br.com.railanxisto.chuckfacts.data.remote.repositories.SearchFactsRepository
import br.com.railanxisto.chuckfacts.domain.Category
import br.com.railanxisto.chuckfacts.presentation.common.BaseViewModel

class SearchFactsViewModel(private val repository: SearchFactsRepository) : BaseViewModel() {

    private val categories = MutableLiveData<List<Category>>()
    private val pastTerms = MutableLiveData<List<Term>>()

    fun getCategories() {
        val disposable = repository
            .getCategories()
            .doOnSubscribe { isLoading.value = true }
            .observeOn(scheduler)
            .doAfterTerminate { isLoading.value = false }
            .subscribe({
                categories.value = it
            }, { error.value = "Error" })

        disposables.add(disposable)
    }

    fun getPastTerms() {
        val disposable = repository
            .getPastSearches()
            .observeOn(scheduler)
            .subscribe({
                pastTerms.value = it
            }, { error.value = "Error" })

        disposables.add(disposable)
    }

    fun getPastTermsList(): LiveData<List<Term>> {
        return pastTerms
    }

    fun getCategoriesList(): MutableLiveData<List<Category>> {
        return categories
    }
}