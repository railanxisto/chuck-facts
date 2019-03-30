package br.com.railanxisto.chuckfacts.presentation.common

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable


abstract class BaseViewModel : ViewModel() {
    val disposables = CompositeDisposable()
    val isLoading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}