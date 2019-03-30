package br.com.railanxisto.chuckfacts.presentation.searchFacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.railanxisto.chuckfacts.data.remote.repositories.SearchFactsRepository
import br.com.railanxisto.chuckfacts.presentation.common.BaseViewModel
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class SearchFactsViewModel(private val repository: SearchFactsRepository): BaseViewModel() {

    private val categories = MutableLiveData<List<String>>()
    private val scheduler = AndroidSchedulers.mainThread()

    fun getCategories(): LiveData<List<String>> {
        val disposable = repository
            .getCategories()
            .doOnSubscribe { isLoading.value = true }
            .observeOn(scheduler)
            .doAfterTerminate { isLoading.value = false }
            .subscribe({
                categories.value = it
            }, {error.value = "Error"})

        disposables.add(disposable)
        return categories
    }

    fun getCategoriesList(): MutableLiveData<List<String>> {
        return categories
    }

}