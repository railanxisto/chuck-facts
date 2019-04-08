package br.com.railanxisto.chuckfacts.presentation.facts

import androidx.lifecycle.MutableLiveData
import br.com.railanxisto.chuckfacts.data.remote.repositories.FactsRepository
import br.com.railanxisto.chuckfacts.domain.Fact
import br.com.railanxisto.chuckfacts.presentation.common.BaseViewModel
import br.com.railanxisto.chuckfacts.presentation.utils.ext.getRestErrorMessage

class FactsViewModel(private val repository: FactsRepository) : BaseViewModel() {

    private val facts = MutableLiveData<List<Fact>>()

    fun getFacts(term: String) {
        val disposable = repository
            .getFacts(term)
            .doOnSubscribe { isLoading.value = true }
            .observeOn(scheduler)
            .subscribe({
                if (it.result.isEmpty()) {
                    isEmpty.value = true
                } else {
                    facts.value = it.result
                }
                isLoading.value = false
            }, {
                isLoading.value = false
                error.value = it.getRestErrorMessage()
            })

        saveSearch(term)

        disposables.add(disposable)
    }

    fun saveSearch(term: String) {
        val disposable = repository
            .saveSearch(term)
            .observeOn(scheduler)
            .onErrorComplete {
                false
            }
            .subscribe()
        disposables.add(disposable)
    }

    fun getFactsList() = facts
}