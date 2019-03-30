package br.com.railanxisto.chuckfacts.data.remote

import br.com.railanxisto.chuckfacts.data.remote.config.RetrofitManager

interface ChuckFactsService {

    companion object {
        fun create(): ChuckFactsService {
            val retrofit = RetrofitManager()
            return retrofit.retrofitInstance().create(ChuckFactsService::class.java)
        }
    }
}