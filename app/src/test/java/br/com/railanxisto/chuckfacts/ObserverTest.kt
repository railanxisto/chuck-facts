package br.com.railanxisto.chuckfacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

class TestObserver<T> : Observer<T> {

    val values = mutableListOf<T?>()

    override fun onChanged(value: T?) {
        values.add(value)
    }
}

fun <T> LiveData<T>.testObserver() = TestObserver<T>().also {
    observeForever(it)
}