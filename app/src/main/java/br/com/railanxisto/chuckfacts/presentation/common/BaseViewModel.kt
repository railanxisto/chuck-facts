package br.com.railanxisto.chuckfacts.presentation.common

import androidx.lifecycle.ViewModel
import br.com.railanxisto.chuckfacts.presentation.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {
    val disposables = CompositeDisposable()
    val isLoading = SingleLiveEvent<Boolean>()
    val error = SingleLiveEvent<String>()
    val isEmpty = SingleLiveEvent<Boolean>()
    val scheduler = AndroidSchedulers.mainThread()

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}