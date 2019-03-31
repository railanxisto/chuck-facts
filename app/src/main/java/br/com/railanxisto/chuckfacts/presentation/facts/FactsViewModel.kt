package br.com.railanxisto.chuckfacts.presentation.facts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.railanxisto.chuckfacts.data.remote.repositories.FactsRepository
import br.com.railanxisto.chuckfacts.domain.Fact
import br.com.railanxisto.chuckfacts.presentation.common.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers

class FactsViewModel(private val repository: FactsRepository): BaseViewModel() {

    private val facts = MutableLiveData<List<Fact>>()

    fun getFacts(): LiveData<List<Fact>> {
        val disposable = repository
            .getFacts(term = "")
            .doOnSubscribe { isLoading.value = true }
            .observeOn(scheduler)
            .doAfterTerminate { isLoading.value = false }
            .subscribe({

            }, {error.value = "Error"})

        disposables.add(disposable)
        return facts
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

}